<?php

namespace App\Controller;

use App\Entity\Amis;
use App\Entity\Utilisateurbloque;
use App\Form\UtilisateurbloqueType;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\Serializer\Normalizer\NormalizerInterface;

/**
 * @Route("/utilisateurbloque")
 */
class UtilisateurbloqueController extends AbstractController
{
    /**
     * @Route("/", name="utilisateurbloque_index", methods={"GET"})
     */
    public function index(): Response
    {
        $utilisateurbloques = $this->getDoctrine()
            ->getRepository(Utilisateurbloque::class)
            ->findAll();

        return $this->render('utilisateurbloque/index.html.twig', [
            'utilisateurbloques' => $utilisateurbloques,
        ]);
    }

    /**
     * @Route("/getBlockJSON", name="getBlockJSON")
     */
    public function getBlockJSON(NormalizerInterface $Normalizer): Response
    {
        $utilisateurbloques = $this->getDoctrine()
            ->getRepository(Utilisateurbloque::class)
            ->findAll();

        $jsonContent=$Normalizer->normalize($utilisateurbloques,'json',['groups'=>'post:read']);
        return new Response(json_encode($jsonContent,JSON_UNESCAPED_UNICODE));
    }

    /**
     * @Route("/ajoutBloqueJSON", name="ajoutBloqueJSON")
     */
    public function ajoutBloqueJSON(Request $request,NormalizerInterface $Normalizer): Response
    {

        $utilisateur=$request->get('utilisateur');
        $abloque=$request->get('abloque');
        $entityManager = $this->getDoctrine()->getManager();
        $utilisateurbloque = new Utilisateurbloque();
        $utilisateurbloque->setUtilisateur($utilisateur);
        $utilisateurbloque->setAbloque($abloque);
        $entityManager->persist($utilisateurbloque);
        $entityManager->flush();

        $jsonContent=$Normalizer->normalize($utilisateurbloque,'json',['groups'=>'post:read']);
        return new Response("Block added successfully".json_encode($jsonContent,JSON_UNESCAPED_UNICODE));
    }

    /**
     * @Route("/deleteBloqueJSON/{id}", name="deleteBloqueJSON")
     */
    public function deleteBloqueJSON(NormalizerInterface $Normalizer,$id): Response
    {
        $em=$this->getDoctrine()->getManager();
        $utilisateurbloque=$em->getRepository(Utilisateurbloque::class)->find($id);
        $em->remove($utilisateurbloque);
        $em->flush();
        $jsonContent=$Normalizer->normalize($utilisateurbloque,'json',['groups'=>'post:read']);
        return new Response("Block deleted successfully".json_encode($jsonContent,JSON_UNESCAPED_UNICODE));
    }

    /**
     * @Route("/new", name="utilisateurbloque_new", methods={"GET","POST"})
     */
    public function new(Request $request): Response
    {
        $utilisateurbloque = new Utilisateurbloque();
        $form = $this->createForm(UtilisateurbloqueType::class, $utilisateurbloque);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $entityManager = $this->getDoctrine()->getManager();
            $entityManager->persist($utilisateurbloque);
            $entityManager->flush();

            return $this->redirectToRoute('utilisateurbloque_index');
        }

        return $this->render('utilisateurbloque/new.html.twig', [
            'utilisateurbloque' => $utilisateurbloque,
            'form' => $form->createView(),
        ]);
    }

    /**
     * @Route("/newBloque/{idamis}", name="bloquerUtilisateur", methods={"GET","POST"})
     */
    public function newBloque(Request $request, Amis $ami): Response
    {

        $session = $request->getSession();
        $myUsername=$session->get('Username');
        if ($this->isCsrfTokenValid('bloque'.$ami->getIdamis(), $request->request->get('_token'))) {
            $abloque="";
            if($ami->getDestinataire()==$myUsername)
            {
                $abloque=$ami->getExpediteur();
                $utilisateurbloque =new Utilisateurbloque();
                $utilisateurbloque->setUtilisateur($ami->getDestinataire());
                $utilisateurbloque->setAbloque($abloque);
                $entityManager = $this->getDoctrine()->getManager();
                $entityManager->persist($utilisateurbloque);
                $entityManager->flush();
            }
            else
            {
                $abloque=$ami->getDestinataire();
                $utilisateurbloque =new Utilisateurbloque();
                $utilisateurbloque->setUtilisateur($ami->getExpediteur());
                $utilisateurbloque->setAbloque($abloque);
                $entityManager = $this->getDoctrine()->getManager();
                $entityManager->persist($utilisateurbloque);
                $entityManager->flush();
            }

        }

        return $this->redirectToRoute('amis_index');
    }



    /**
     * @Route("/{idbloque}", name="utilisateurbloque_show", methods={"GET"})
     */
    public function show(Utilisateurbloque $utilisateurbloque): Response
    {
        return $this->render('utilisateurbloque/show.html.twig', [
            'utilisateurbloque' => $utilisateurbloque,
        ]);
    }

    /**
     * @Route("/{idbloque}/edit", name="utilisateurbloque_edit", methods={"GET","POST"})
     */
    public function edit(Request $request, Utilisateurbloque $utilisateurbloque): Response
    {
        $form = $this->createForm(UtilisateurbloqueType::class, $utilisateurbloque);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $this->getDoctrine()->getManager()->flush();

            return $this->redirectToRoute('utilisateurbloque_index');
        }

        return $this->render('utilisateurbloque/edit.html.twig', [
            'utilisateurbloque' => $utilisateurbloque,
            'form' => $form->createView(),
        ]);
    }

    /**
     * @Route("/{idbloque}", name="utilisateurbloque_delete", methods={"POST"})
     */
    public function delete(Request $request, Utilisateurbloque $utilisateurbloque): Response
    {
        if ($this->isCsrfTokenValid('delete'.$utilisateurbloque->getIdbloque(), $request->request->get('_token'))) {
            $entityManager = $this->getDoctrine()->getManager();
            $entityManager->remove($utilisateurbloque);
            $entityManager->flush();
        }

        return $this->redirectToRoute('amis_index');
    }
}
