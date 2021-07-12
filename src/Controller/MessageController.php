<?php

namespace App\Controller;

use App\Entity\EncapsulationMessage;
use App\Entity\Message;
use App\Form\MessageType;
use App\Server\Chat;
use Snipe\BanBuilder\CensorWords;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\Filesystem\Exception\IOExceptionInterface;
use Symfony\Component\Filesystem\Filesystem;
use Symfony\Component\Serializer\Normalizer\NormalizerInterface;

/**
 * @Route("/msg")
 */
class MessageController extends AbstractController
{
    /**
     * @Route("/", name="message_index", methods={"GET"})
     */
    public function index(): Response
    {
        $messages = $this->getDoctrine()
            ->getRepository(Message::class)
            ->findAll();

        return $this->render('message/index.html.twig', [
            'messages' => $messages,
        ]);
    }

    /**
     * @Route("/getMessageJSON", name="getMessageJSON")
     */
    public function getMessageJSON(NormalizerInterface $Normalizer): Response
    {
        $messages = $this->getDoctrine()
            ->getRepository(Message::class)
            ->findAll();

        $jsonContent=$Normalizer->normalize($messages,'json',['groups'=>'post:read']);
        return new Response(json_encode($jsonContent,JSON_UNESCAPED_UNICODE));
    }

    /**
     * @Route("/addMessageJSON", name="addMessageJSON")
     */
    public function addMessageJSON(Request $request,NormalizerInterface $Normalizer): Response
    {

        $censor = new CensorWords;
        $censor->setReplaceChar("*");
        $messageCensor = $censor->censorString($request->get('contenumessage'));
        $entityManager = $this->getDoctrine()->getManager();
        $message = new Message();
        $timeDate = new \DateTime ();
        $message->setDestinataire($request->get('destinataire'));
        $message->setExpediteur($request->get('expediteur'));
        $message->setContenumessage($messageCensor['clean']);
        $message->setDateenvoie($timeDate);

        $entityManager->persist($message);
        $entityManager->flush();
        $entityManager->refresh($message);
        $message->setContenuMessage($message->getContenumessage()." (".$message->getIdmessage().")");
        $entityManager->flush();

        $jsonContent=$Normalizer->normalize($message,'json',['groups'=>'post:read']);
        return new Response("Message added successfully".json_encode($jsonContent,JSON_UNESCAPED_UNICODE));
    }

    /**
     * @Route("/deleteMessageJSON/{id}", name="deleteMessageJSON")
     */
    public function deleteMessageJSON(NormalizerInterface $Normalizer,$id): Response
    {
        $em=$this->getDoctrine()->getManager();
        $message=$em->getRepository(Message::class)->find($id);
        $em->remove($message);
        $em->flush();
        $jsonContent=$Normalizer->normalize($message,'json',['groups'=>'post:read']);
        return new Response("Message deleted successfully".json_encode($jsonContent,JSON_UNESCAPED_UNICODE));
    }

    /**
     * @Route("/editMessageJSON/{id}", name="editMessageJSON")
     */
    public function editMessageJSON(Request $request,NormalizerInterface $Normalizer,$id): Response
    {
        $censor = new CensorWords;
        $censor->setReplaceChar("*");
        $messageCensor = $censor->censorString($request->get('contenumessage'));

        $entityManager=$this->getDoctrine()->getManager();
        $message=$entityManager->getRepository(Message::class)->find($id);
        $message->setContenuMessage($messageCensor['clean']);
        $entityManager->flush();

        $jsonContent=$Normalizer->normalize($message,'json',['groups'=>'post:read']);
        return new Response("Message edited successfully".json_encode($jsonContent,JSON_UNESCAPED_UNICODE));

    }

    /**
     * @Route("/new", name="message_new", methods={"GET","POST"})
     */
    public function new(Request $request): Response
    {

        $message = new Message();
        $form = $this->createForm(MessageType::class, $message);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $entityManager = $this->getDoctrine()->getManager();
            $entityManager->persist($message);
            $entityManager->flush();

            return $this->redirectToRoute('message_index');
        }

        return $this->render('message/new.html.twig', [
            'message' => $message,
            'form' => $form->createView(),
        ]);
    }

    /**
     * @Route("/{idmessage}", name="message_show", methods={"GET"})
     */
    public function show(Message $message): Response
    {
        return $this->render('message/show.html.twig', [
            'message' => $message,
        ]);
    }

    /**
     * @Route("/{idmessage}/edit", name="message_edit", methods={"GET","POST"})
     */
    public function edit(Request $request, Message $message): Response
    {
        $form = $this->createForm(MessageType::class, $message);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $this->getDoctrine()->getManager()->flush();
            $me=$message->getExpediteur();
            $friend=$message->getDestinataire();

            if($friend>$me)
            {
                $channel=$friend.$me;
            }
            else{
                $channel=$me.$friend;
            }

            $messages = $this->getDoctrine()
                ->getRepository(Message::class)
                ->findAll();

            return $this->redirectToRoute('openChat' ,[
                'tempChannel' => $channel,
                'me'=>$me,
                'friend'=>$friend,
                'messages' => $messages,
                'ws_url' => '127.0.0.1:8080',
            ]);
        }


        return $this->render('message/edit.html.twig', [
            'message' => $message,
            'form' => $form->createView(),
        ]);
    }

    /**
     * @Route("/{idmessage}", name="message_delete", methods={"POST"})
     */
    public function delete(Request $request, Message $message): Response
    {
        if ($this->isCsrfTokenValid('delete'.$message->getIdmessage(), $request->request->get('_token'))) {
            $entityManager = $this->getDoctrine()->getManager();
            $entityManager->remove($message);
            $entityManager->flush();
        }

        $me=$message->getExpediteur();
        $friend=$message->getDestinataire();

        if($friend>$me)
        {
            $channel=$friend.$me;
        }
        else{
            $channel=$me.$friend;
        }

        $messages = $this->getDoctrine()
            ->getRepository(Message::class)
            ->findAll();

        return $this->redirectToRoute('openChat',[
            'tempChannel' => $channel,
            'me'=>$me,
            'friend'=>$friend,
            'messages' => $messages,
            'ws_url' => '127.0.0.1:8080',
        ]);
    }


    /**
     * @Route("/chatXY/{friend}/{me}", name="jsonMessage", methods={"GET","POST"})
     */
    public function ajoutParChat(Request $request,$friend,$me): Response
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
            $entityManager->refresh($msgBD);
            $msgBD->setContenuMessage($msgBD->getContenumessage()." (".$msgBD->getIdmessage().")");
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
