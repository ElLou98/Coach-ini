<?php

namespace App\Controller;

use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\Filesystem\Filesystem;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use App\Entity\Message;
use App\Server\Chat;
use Symfony\Component\Validator\Constraints\Json;


class ChatController extends AbstractController
{
    /**
     * @Route("/chat", name="frontchat", methods={"GET"})
     */
    public function index(): Response
    {

        return $this->render('chat/index.html.twig',[
            'ws_url' => '127.0.0.1:8080',
        ]);
    }


    /**
     * @Route("/chat/{friend}/{me}", name="openChat", methods={"GET"})
     */
    public function openChat(String $friend , String $me ){

        $messages = $this->getDoctrine()
            ->getRepository(Message::class)
            ->findAll();

        $channel='';
        if($friend>$me)
        {
            $channel=$friend.$me;
        }
        else{
            $channel=$me.$friend;
        }

        return $this->render('chat/chatBox.html.twig',[
            'tempChannel' => $channel,
            'me'=>$me,
            'friend'=>$friend,
            'ws_url' => '127.0.0.1:8080',
            'messages' => $messages,
        ]);

    }

    /*
    /**
     * @Route("/chat/{friend}/{me}", name="newMessage", methods={"GET","POST"})

    public function newf(Request $request): Response
    {
        $chat =new Chat();
        $timeDate = new \DateTime ();
        $msgBD = new Message();
        $msgBD->setExpediteur($chat->getMe());
        $msgBD->setDestinataire($chat->getFriend());
        $msgBD->setContenumessage($chat->getContenuMessage());
        $msgBD->setDateenvoie($timeDate);
        $entityManager = $this->getDoctrine()->getManager();
        $entityManager->persist($msgBD);
        $entityManager->flush();

    }
    */

    /**
     * @Route("/chat/{friend}/{me}/msg", name="jsonMessageTest", methods={"GET","POST"})
     */
    public function ajoutParChatTest(Request $request,$friend,$me): Response
    {
        sleep(1);
        $jsonString = file_get_contents('../../webCoachini/file.json');

        if($jsonString !=null){
            $tab = json_decode($jsonString, true);
            $timeDate = new \DateTime ();
            $msgBD = new Message();
            $msgBD->setExpediteur($tab['me']);
            $msgBD->setDestinataire($tab['friend']);
            $msgBD->setContenumessage($tab['message']);
            $msgBD->setDateenvoie($timeDate);
            $entityManager = $this->getDoctrine()->getManager();
            $entityManager->persist($msgBD);
            $entityManager->flush();
        }

        if($friend>$me)
        {
            $channel=$friend.$me;
        }
        else{
            $channel=$me.$friend;
        }

        $filesystem = new Filesystem();
        $filesystem->dumpFile('../../webCoachini/file.json', '');

        $messages = $this->getDoctrine()
            ->getRepository(Message::class)
            ->findAll();
        return $this->render('chat/chatBox.html.twig',[
            'tempChannel' => $channel,
            'me'=>$me,
            'friend'=>$friend,
            'messages' => $messages,
            'ws_url' => '127.0.0.1:8080',
        ]);

    }




}
