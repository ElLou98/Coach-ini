<?php

namespace App\Controller;

use App\Entity\Amis;
use App\Entity\Compte;
use App\Entity\Utilisateurbloque;
use App\Form\AmisType;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\Serializer\Normalizer\NormalizerInterface;

/**
 * @Route("/amis")
 */
class AmisController extends AbstractController
{

    /**
     * @Route("/", name="amis_index", methods={"GET"})
     */
    public function index(Request $request): Response
    {
        $session = $request->getSession();
        $myUsername=$session->get('Username');

        $comptes = $this->getDoctrine()
            ->getRepository(Compte::class)
            ->findAll();

        $uBloques = $this->getDoctrine()
            ->getRepository(Utilisateurbloque::class)
            ->findAll();

        $amis = $this->getDoctrine()
            ->getRepository(Amis::class)
            ->findAll();

        return $this->render('amis/index.html.twig', [
            'amis' => $amis,
            'comptes' => $comptes,
            'uBloques'=>$uBloques,
            'myUsername'=>$myUsername,
        ]);
    }

    /**
     * @Route("/getAmisJSON", name="getAmisJSON")
     */
    public function getAmisJSON(NormalizerInterface $Normalizer): Response
    {
        $amis = $this->getDoctrine()
            ->getRepository(Amis::class)
            ->findAll();

        $jsonContent=$Normalizer->normalize($amis,'json',['groups'=>'post:read']);
        return new Response(json_encode($jsonContent,JSON_UNESCAPED_UNICODE));
    }

    /**
     * @Route("/getCompteJSON", name="getCompteJSON")
     */
    public function getCompteJSON(NormalizerInterface $Normalizer): Response
    {
        $comptes = $this->getDoctrine()
            ->getRepository(Compte::class)
            ->findAll();


        $jsonContent=$Normalizer->normalize($comptes,'json',['groups'=>'post:read']);
        return new Response(json_encode($jsonContent,JSON_UNESCAPED_UNICODE));
    }

    /**
     * @Route("/deleteAmisJSON/{id}", name="deleteAmisJSON")
     */
    public function deleteAmisJSON(Request $request,NormalizerInterface $Normalizer,$id): Response
    {
        $em=$this->getDoctrine()->getManager();
        $ami=$em->getRepository(Amis::class)->find($id);
        $em->remove($ami);
        $em->flush();
        $jsonContent=$Normalizer->normalize($ami,'json',['groups'=>'post:read']);
        return new Response("Ami deleted successfully".json_encode($jsonContent,JSON_UNESCAPED_UNICODE));
    }

    /**
     * @Route("/ajoutAmisJSON", name="ajoutAmisJSON")
     */
    public function ajoutAmisJSON(Request $request,\Swift_Mailer $mailer,NormalizerInterface $Normalizer): Response
    {

        $destinataire=$request->get('destinataire');
        $expediteur=$request->get('expediteur');
        $entityManager = $this->getDoctrine()->getManager();
        $ami = new Amis();
        $ami->setExpediteur($expediteur);
        $ami->setDestinataire($destinataire);
        $ami->setEtatdemande(0);
        $entityManager->persist($ami);
        $entityManager->flush();


        $query = $entityManager->createQuery("SELECT c.adresseMail FROM App\Entity\Compte c WHERE c.username='".$destinataire."'");
        $queryResult =$query->getResult();
        $userMail=$queryResult[0]['adresseMail'];
        $message = (new \Swift_Message('Nouvelle demande d\'ami(e)'))
            ->setFrom('coachiniapp@gmail.com')
            ->setTo($userMail)
            ->setBody(
                "Salut M/Mme. ".$destinataire." \nl'équipe de Coach'ini vous informe que vous venez "
                . "de reçevoir une demande d'ami(e) de la part de ".$expediteur.".\nCordialement, Coach'ini"
            )
        ;
        $mailer->send($message);


        $jsonContent=$Normalizer->normalize($ami,'json',['groups'=>'post:read']);
        return new Response("Ami added successfully".json_encode($jsonContent,JSON_UNESCAPED_UNICODE));
    }

    /**
     * @Route("/accepterAmisJSON/{id}", name="accepterAmisJSON")
     */
    public function accepterAmisJSON(NormalizerInterface $Normalizer,$id): Response
    {
        $entityManager=$this->getDoctrine()->getManager();
        $ami=$entityManager->getRepository(Amis::class)->find($id);
        $ami->setEtatdemande(1);
        $entityManager->flush();
        $jsonContent=$Normalizer->normalize($ami,'json',['groups'=>'post:read']);
        return new Response("Ami edited successfully".json_encode($jsonContent,JSON_UNESCAPED_UNICODE));
    }

    /**
     * @Route("/new", name="amis_new", methods={"GET","POST"})
     */
    public function new(Request $request): Response
    {
        $ami = new Amis();
        $form = $this->createForm(AmisType::class, $ami);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $entityManager = $this->getDoctrine()->getManager();
            $entityManager->persist($ami);
            $entityManager->flush();

            return $this->redirectToRoute('amis_index');
        }

        return $this->render('amis/new.html.twig', [
            'ami' => $ami,
            'form' => $form->createView(),
        ]);
    }

    /**
     * @Route("/{idamis}", name="amis_show", methods={"GET"})
     */
    public function show(Amis $ami): Response
    {
        return $this->render('amis/show.html.twig', [
            'ami' => $ami,
        ]);
    }

    /**
     * @Route("/{idamis}/edit", name="amis_edit", methods={"GET","POST"})
     */
    public function edit(Request $request, Amis $ami): Response
    {
        /*      $form = $this->createForm(AmisType::class, $ami);
              $form->handleRequest($request);*/

        $ami->setEtatdemande(1);

        /*if ($form->isSubmitted() && $form->isValid()) {*/
        $this->getDoctrine()->getManager()->flush();

        return $this->redirectToRoute('amis_index');
        /*}*/

        return $this->render('amis/edit.html.twig', [
            'ami' => $ami,
            'form' => $form->createView(),
        ]);
    }

    /**
     * @Route("/{idamis}", name="amis_delete", methods={"POST"})
     */
    public function delete(Request $request, Amis $ami): Response
    {
        if ($this->isCsrfTokenValid('delete'.$ami->getIdamis(), $request->request->get('_token'))) {
            $entityManager = $this->getDoctrine()->getManager();
            $entityManager->remove($ami);
            $entityManager->flush();
        }

        return $this->redirectToRoute('amis_index');
    }

    /**
     * @Route("/{id}/ajout", name="amis_ajouter", methods={"POST"})
     */
    public function ajouter(Request $request, Compte $compte,\Swift_Mailer $mailer): Response
    {

        $session = $request->getSession();
        $myUsername=$session->get('Username');
        /*$userRep = $this->getDoctrine()->getRepository(User::class);
            $user = $userRep->find($session->get('user')); */
        if ($this->isCsrfTokenValid('ajouter'.$compte->getId(), $request->request->get('_token'))) {
            $entityManager = $this->getDoctrine()->getManager();
            $ami = new Amis();
            $ami->setExpediteur($myUsername);
            $ami->setDestinataire($compte->getUsername());
            $ami->setEtatdemande(0);
            $entityManager->persist($ami);
            $entityManager->flush();
            $userMail=$compte->getAdresseMail();
            $demandeur=$myUsername;
            $message = (new \Swift_Message('Nouvelle demande d\'ami(e)'))
                ->setFrom('coachiniapp@gmail.com')
                ->setTo($userMail)
                ->setBody(
                    "Salut M/Mme. ".$compte->getUsername()." \nl'équipe de Coach'ini vous informe que vous venez "
                    . "de reçevoir une demande d'ami(e) de la part de ".$demandeur.".\nCordialement, Coach'ini"
                )
            ;
            $mailer->send($message);
        }

        return $this->redirectToRoute('amis_index');
    }
}
