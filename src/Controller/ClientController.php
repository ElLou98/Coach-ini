<?php

namespace App\Controller;

use App\Entity\Client;
use App\Entity\Compte;
use App\Form\ClientType;
use App\Form\CompteType;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;

/**
 * @Route("/client")
 */
class ClientController extends AbstractController
{
    /**
     * @Route("/", name="client_index", methods={"GET"})
     */
    public function index(): Response
    {
        $clients = $this->getDoctrine()
            ->getRepository(Client::class)
            ->findAll();

        return $this->render('client/index.html.twig', [
            'clients' => $clients,
        ]);
    }

    /**
     * @Route("/create", name="client_create", methods={"GET","POST"})
     */
    public function create(Request $request): Response
    {
        $client = new Client();
        $compte = new Compte();
        $form = $this->createForm(CompteType::class, $compte);
        $form->handleRequest($request);


        if ($form->isSubmitted() && $form->isValid()) {
            $compte->setType('client');
            $compte->setMotDePasse(self::cryptElyes($compte->getMotDePasse()));
            $entityManager = $this->getDoctrine()->getManager();
            $entityManager->persist($compte);
            $entityManager->flush();
            $em = $this->getDoctrine()->getManager();
            $query = $em->createQuery('SELECT c ,max(c.id) as maxID FROM App\Entity\Compte c ');
            $result=$query->getScalarResult();
            $data= array();
            $client->setIdc($result[0]['maxID']);
            //echo $result[0]['maxID'];
            $entityManager = $this->getDoctrine()->getManager();
            $entityManager->persist($client);
            $entityManager->flush();
            return $this->redirect('../compte/login');
        }

        return $this->render('client/new.html.twig', [
            'client' => $client,
            'compte' => $compte,
            'form' => $form->createView(),
        ]);
    }

    /**
     * @Route("/cryptElyes", name="cryptElyes", methods={"GET"})
     */
    public function cryptElyes(String $password): String
    {
        $result="";
        $alphabet="ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

        $i = 0 ;
        while ($i < strlen($password))
        {

            $c= substr($password, $i, 1);
            if (!empty($c)){
                $j = strpos($alphabet, $c);
                if ( $j != -1)
                {
                    if($i+$j>51)
                    {
                        $k=$i+$j-52;
                        $result .= $alphabet[$k] ;
                    }
                    else
                        $result .= $alphabet[$i+$j] ;
                }
                else
                    $result .= $c;
            }
            $i = $i+1;
        }
        return $result;

    }

    /**
     * @Route("/new", name="client_new", methods={"GET","POST"})
     */
    public function new(Request $request): Response
    {
        $client = new Client();
        $form = $this->createForm(ClientType::class, $client);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $entityManager = $this->getDoctrine()->getManager();
            $entityManager->persist($client);
            $entityManager->flush();

            return $this->redirectToRoute('client_index');
        }

        return $this->render('client/new.html.twig', [
            'client' => $client,
            'form' => $form->createView(),
        ]);
    }

    /**
     * @Route("/{id}", name="client_show", methods={"GET"})
     */
    public function show(Client $client): Response
    {
        return $this->render('client/show.html.twig', [
            'client' => $client,
        ]);
    }

    /**
     * @Route("/{id}/edit", name="client_edit", methods={"GET","POST"})
     */
    public function edit(Request $request, Client $client): Response
    {
        $form = $this->createForm(ClientType::class, $client);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $this->getDoctrine()->getManager()->flush();

            return $this->redirectToRoute('client_index');
        }

        return $this->render('client/edit.html.twig', [
            'client' => $client,
            'form' => $form->createView(),
        ]);
    }

    /**
     * @Route("/{id}", name="client_delete", methods={"POST"})
     */
    public function delete(Request $request, Client $client): Response
    {
        if ($this->isCsrfTokenValid('delete'.$client->getId(), $request->request->get('_token'))) {
            $entityManager = $this->getDoctrine()->getManager();
            $entityManager->remove($client);
            $entityManager->flush();
        }

        return $this->redirectToRoute('client_index');
    }
}
