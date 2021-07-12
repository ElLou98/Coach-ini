<?php

namespace App\Server;

use App\Controller\MessageController;
use App\Entity\EncapsulationMessage;
use App\Entity\Message;
use Doctrine\ORM\EntityManager;
use Ratchet\ConnectionInterface;
use Ratchet\MessageComponentInterface;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Psr\Log\LoggerInterface;
use Snipe\BanBuilder\CensorWords;


/**
 * Chat Server.
 */
class Chat extends AbstractController implements MessageComponentInterface
{
    /**
     * @var \SplObjectStorage
     */
    private $clients;

    /**
     * @var array
     */
    private $users = [];

    /**
     * @var string
     */
    private $botName = 'ChatBot';

    /**
     * @var string
     */
    private $defaultChannel = 'general';

    /**
     * @var string
     */
    private $me = '';

    /**
     * @var string
     */
    private $friend = '';

    /**
     * @var string
     */
    private $contenuMessage = '';

    private LoggerInterface $logger;
    private MessageController $messageController;




    /**
     * Chat constructor.
     */
    public function __construct()
    {
        // Initialize
        $this->clients = new \SplObjectStorage();
        $this->users = [];
    }


    /**
     * A new websocket connection.
     *
     * @param ConnectionInterface $conn
     */
    public function onOpen(ConnectionInterface $conn)
    {
        // Initialize: Add connection to clients list
        $this->clients->attach($conn);
        $this->users[$conn->resourceId] = [
            'connection' => $conn,
            'user' => '',
            'channels' => [],
        ];

        // Send hello message
        $conn->send(json_encode([
            'action' => 'message',
            'channel' => $this->defaultChannel,
            'user' => $this->botName,
            'message' => sprintf('Connexion établie. Bienvenue #%d!', $conn->resourceId),
            'messageClass' => 'success',
            'me'=>$this->me,
            'friend'=>$this->friend,
        ]));
    }

    /**
     *
     * @param ConnectionInterface $closedConnection
     * A connection is closed.
     */
    public function onClose(ConnectionInterface $closedConnection)
    {

        // Send Goodbye message
        $this->sendMessageToChannel(
            $closedConnection,
            $this->defaultChannel,
            $this->botName,
            $this->users[$closedConnection->resourceId]['user'].' a été déconnecté',
            $this->me,
            $this->friend
        );

        // Remove connection from users
        unset($this->users[$closedConnection->resourceId]);

        // Detach connection and send message (for log purposes)
        $this->clients->detach($closedConnection);
        echo sprintf('Connexion #%d a été arrêter\n', $closedConnection->resourceId);
    }

    /**
     * Error handling.
     *
     * @param ConnectionInterface $conn
     * @param \Exception $e
     */
    public function onError(ConnectionInterface $conn, \Exception $e)
    {
        $conn->send(json_encode([
            'action' => 'message',
            'channel' => $this->defaultChannel,
            'user' => $this->botName,
            'message' => 'An error has occurred: '.$e->getMessage(),
            'me'=>$this->me,
            'friend'=>$this->friend,
        ]));
        $conn->close();
    }

    /**
     * Handle message sending.
     *
     * @param ConnectionInterface $conn
     * @param string $message
     *
     * @return bool - False if message is not a valid JSON or action is invalid
     */
    public function onMessage(ConnectionInterface $conn, $message)
    {
        // Initialize
        $messageData = json_decode($message);

        // Check message data
        if (null === $messageData) {
            return false;
        }

        // Check connection user
        if (empty($this->users[$conn->resourceId]['user']) && $messageData->user) {
            $this->users[$conn->resourceId]['user'] = $messageData->user;
        }

        // Initialize message data
        $action = $messageData->action ?? 'unknown';
        $channel = $messageData->channel ?? $this->defaultChannel;
        $user = $messageData->user ?? $this->botName;
        $message = $messageData->message ?? '';
        $me=$messageData->me ?? $this->me;
        $friend=$messageData->friend ?? $this->friend;

        // Check action
        switch ($action) {
            case 'subscribe':
                $this->subscribeToChannel($conn, $channel, $user);

                return true;
            case 'unsubscribe':
                $this->unsubscribeFromChannel($conn, $channel, $user);

                return true;
            case 'message':
                return $this->sendMessageToChannel($conn, $channel, $user, $message,$me,$friend);

            default:
                echo sprintf('Action "%s" is not supported yet!', $action);
                break;
        }

        // Return error
        return false;
    }

    /**
     * Subscribe connection to a given channel.
     *
     * @param ConnectionInterface $conn - Active connection
     * @param $channel - Channel to subscribe to
     * @param $user - Username of subscribed user
     */
    private function subscribeToChannel(ConnectionInterface $conn, $channel, $user)
    {
        // Add channel to connection channels
        $this->users[$conn->resourceId]['channels'][$channel] = $channel;

        // Send joined message to channel
        $this->sendMessageToChannel(
            $conn,
            $channel,
            $this->botName,
            $user.' a rejoint #'.$channel,
            $this->me,
            $this->friend
        );
    }

    /**
     * Unsubscribe connection to a given channel.
     *
     * @param ConnectionInterface $conn - Active connection
     * @param $channel - Channel to unsubscribe from
     * @param $user - Username of unsubscribed user
     */
    private function unsubscribeFromChannel(ConnectionInterface $conn, $channel, $user)
    {
        // Check connection
        if (\array_key_exists($channel, $this->users[$conn->resourceId]['channels'])) {
            // Delete connection
            unset($this->users[$conn->resourceId]['channels']);
        }

        // Send left message to channel
        $this->sendMessageToChannel(
            $conn,
            $channel,
            $this->botName,
            $user.' a quitté #'.$channel,
            $this->me,
            $this->friend
        );
    }

    /**
     * Send message to all connections of a given channel.
     *
     * @param ConnectionInterface $conn - Active connection
     * @param $channel - Channel to send message to
     * @param $user - User's username
     * @param $message - User's message
     * @param $me
     * @param $friend
     * @return bool - False if channel doesn't exists
     */
    private function sendMessageToChannel(ConnectionInterface $conn, $channel, $user, $message,$me,$friend)
    {
        // Check if connection is linked to channel
        if (!isset($this->users[$conn->resourceId]['channels'][$channel])) {
            // Don't send message
            return false;
        }


        $censor = new CensorWords;
        $censor->setReplaceChar("*");
        $messageCensor = $censor->censorString($message);

        // Loop to send message to all users
        foreach ($this->users as $connectionId => $userConnection) {
            // Check if user has subscribe to channel
            if (\array_key_exists($channel, $userConnection['channels'])) {
                $userConnection['connection']->send(json_encode([
                    'action' => 'message',
                    'channel' => $channel,
                    'user' => $user,
                    'message' => $messageCensor['clean'],
                    'me'=>$me,
                    'friend'=>$friend,
                ]));



               if($me !=null && $friend !=null && $message !=null){
                   $jsonString='{"me":"'.$me.'", "friend":"'.$friend.'", "message":"'.$messageCensor['clean'].'"}';
                   $fs = new \Symfony\Component\Filesystem\Filesystem();

                   try {
                       $fs->dumpFile('./file.json', $jsonString);
                   }
                   catch(IOException $e) {
                   }


                }


                /*
                $timeDate = new \DateTime ();
                $msgBD = new Message();
                $msgBD->setExpediteur($me);
                $msgBD->setDestinataire($friend);
                $msgBD->setContenumessage($message);
                $msgBD->setDateenvoie($timeDate);
                $this->em = $this->getDoctrine()->getManager();
                $this->em->persist($msgBD);
                $this->em->flush();*/


/*
                $sql = "INSERT INTO message (destinataire,expediteur,contenuMessage,dateEnvoie)
            VALUES (".$friend." ".$me." ".$message." ".$timeDate->format('Y-m-d H:i:s').")";

                $stmt = $this->getServiceLocator()
                    ->get('Doctrine\ORM\EntityManager')
                    ->getConnection()
                    ->prepare($sql);
                $stmt->execute();


*/


                $encapsulationMessage=new EncapsulationMessage();

                $encapsulationMessage->setFriend($friend);
                $encapsulationMessage->setMe($me);
                $encapsulationMessage->setContenuMessage($message);
            }
        }

        // Return success
        return true;
    }

    /**
     * @return \SplObjectStorage
     */
    public function getClients(): \SplObjectStorage
    {
        return $this->clients;
    }

    /**
     * @param \SplObjectStorage $clients
     */
    public function setClients(\SplObjectStorage $clients): void
    {
        $this->clients = $clients;
    }

    /**
     * @return array
     */
    public function getUsers(): array
    {
        return $this->users;
    }

    /**
     * @param array $users
     */
    public function setUsers(array $users): void
    {
        $this->users = $users;
    }

    /**
     * @return string
     */
    public function getBotName(): string
    {
        return $this->botName;
    }

    /**
     * @param string $botName
     */
    public function setBotName(string $botName): void
    {
        $this->botName = $botName;
    }

    /**
     * @return string
     */
    public function getDefaultChannel(): string
    {
        return $this->defaultChannel;
    }

    /**
     * @param string $defaultChannel
     */
    public function setDefaultChannel(string $defaultChannel): void
    {
        $this->defaultChannel = $defaultChannel;
    }

    /**
     * @return string
     */
    public function getMe(): string
    {
        return $this->me;
    }

    /**
     * @param string $me
     */
    public function setMe(string $me): void
    {
        $this->me = $me;
    }

    /**
     * @return string
     */
    public function getFriend(): string
    {
        return $this->friend;
    }

    /**
     * @param string $friend
     */
    public function setFriend(string $friend): void
    {
        $this->friend = $friend;
    }

    /**
     * @return string
     */
    public function getContenuMessage(): string
    {
        return $this->contenuMessage;
    }

    /**
     * @param string $contenuMessage
     */
    public function setContenuMessage(string $contenuMessage): void
    {
        $this->contenuMessage = $contenuMessage;
    }




}