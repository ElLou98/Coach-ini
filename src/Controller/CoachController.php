<?php

namespace App\Controller;

use App\Entity\Coach;
use App\Entity\Compte;
use App\Form\CoachType;
use App\Form\CompteType;
use phpDocumentor\Reflection\Types\Integer;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\Form\Extension\Core\Type\TextType;
use Twilio\Rest\Client;

/**
 * @Route("/coach")
 */
class CoachController extends AbstractController
{
    /**
     * @Route("/", name="coach_index", methods={"GET"})
     */
    public function index(): Response
    {
        $coaches = $this->getDoctrine()
            ->getRepository(Coach::class)
            ->findAll();

        return $this->render('coach/index.html.twig', [
            'coaches' => $coaches,
        ]);
    }

    /**
     * @Route("/create", name="coach_create", methods={"GET","POST"})
     */
    public function create(Request $request): Response
    {
        $coach = new coach();
        $compte = new Compte();
        $form = $this->createForm(CompteType::class, $compte);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $compte->setType('coach');
            $compte->setMotDePasse(self::cryptElyes($compte->getMotDePasse()));
            $entityManager = $this->getDoctrine()->getManager();
            $entityManager->persist($compte);
            $entityManager->flush();
            $em = $this->getDoctrine()->getManager();
            $query = $em->createQuery('SELECT c ,max(c.id) as maxID FROM App\Entity\Compte c ');
            $result=$query->getScalarResult();
            $data= array();
            $coach->setIdc($result[0]['maxID']);
            //echo $result[0]['maxID'];
            $entityManager = $this->getDoctrine()->getManager();
            $entityManager->persist($coach);
            $entityManager->flush();
            return $this->redirectToRoute('sms');
        }

        return $this->render('coach/new.html.twig', [
            'coach' => $coach,
            'compte' => $compte,
            'form' => $form->createView(),
        ]);
    }

    /**
     * @Route("/sms", name="sms", methods={"GET","POST"})
     */
    public function sms(Request $request): Response
    {
        $sid    = "AC5110b71cce19bab16cde98a018778611";
        $token  = "b48e2f174cc3773ef8db3653def1854d";
        $twilio = new Client($sid, $token);


        $message = $twilio->messages
            ->create("+21626213651",
                array(
                    "body" => "159753",
                    "from" => "+19282482909"
                )
            );
        $form = $this->createFormBuilder()
            ->add('code', textType::class)
            ->getForm();
        $form->handleRequest($request);


        if ($form->isSubmitted() ) {
            if($form['code']->getData()=='159753')
            {

                return $this->redirect('../compte/login');
            }
        }

        return $this->render('coach/verifierSMS.html.twig', [
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
     * @Route("/new", name="coach_new", methods={"GET","POST"})
     */
    public function new(Request $request): Response
    {
        $coach = new Coach();
        $form = $this->createForm(CoachType::class, $coach);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $entityManager = $this->getDoctrine()->getManager();
            $entityManager->persist($coach);
            $entityManager->flush();

            return $this->redirectToRoute('coach_index');
        }

        return $this->render('coach/new.html.twig', [
            'coach' => $coach,
            'form' => $form->createView(),
        ]);
    }

    /**
     * @Route("/{id}", name="coach_show", methods={"GET"})
     */
    public function show(Coach $coach): Response
    {
        return $this->render('coach/show.html.twig', [
            'coach' => $coach,
        ]);
    }

    /**
     * @Route("/{id}/edit", name="coach_edit", methods={"GET","POST"})
     */
    public function edit(Request $request, Coach $coach): Response
    {
        $form = $this->createForm(CoachType::class, $coach);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $this->getDoctrine()->getManager()->flush();

            return $this->redirectToRoute('coach_index');
        }

        return $this->render('coach/edit.html.twig', [
            'coach' => $coach,
            'form' => $form->createView(),
        ]);
    }

    /**
     * @Route("/{id}", name="coach_delete", methods={"POST"})
     */
    public function delete(Request $request, Coach $coach): Response
    {
        if ($this->isCsrfTokenValid('delete'.$coach->getId(), $request->request->get('_token'))) {
            $entityManager = $this->getDoctrine()->getManager();
            $entityManager->remove($coach);
            $entityManager->flush();
        }

        return $this->redirectToRoute('coach_index');
    }
}
