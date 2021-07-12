<?php

namespace App\Controller;

use App\Entity\CategoriesortRepository;
use App\Entity\OffreRepository;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use App\Entity\Compte;

use Twilio\Rest\Client;
class IndexController extends AbstractController
{
    /**
     * @Route("/fronthome", name="fronthome", methods={"GET"})
     */
    public function index(): Response
    {
        return $this->render('baseFront.html.twig',[
        ]);
    }

    /**
     * @Route("/", name="welcomePage", methods={"GET"})
     */
    public function welcome(OffreRepository $offreRepository,CategoriesortRepository $categoriesortRepository)
    {
        return $this->render('visiteur.html.twig', [
        'offres' => $offreRepository->findAll(),
        'categories'=>$categoriesortRepository->findAll()
    ]);

    }

    /**
     * @Route("/csv", name="csv", methods={"GET"})
     */
    public function csv(): Response
    {
        $res= $this->getDoctrine()->getRepository(Compte::class)->createQueryBuilder('s')
            ->getQuery()->getResult();

        $fp = fopen("compte.csv", "w");

        foreach ($res as $line)
        {   echo "done";
            fputcsv(
                $fp, // The file pointer
                array($line->getUsername(), $line->getNom(), $line->getPrenom(), $line->getAge(),$line->getAdresseMail(),$line->getMotDePasse(),$line->getNumTel(),$line->getType()), // The fields
                ',' // The delimiter
            );
        }

        fclose($fp);
        $compteRepository = $this->getDoctrine()->getRepository(Compte::class);
        $comptes= $compteRepository->findAll();
        return $this->render('compte/triBack.html.twig',[
            'comptes' => $comptes,

        ]);
    }

    /**
     * @Route("/Sinscrire", name="Sinscrire", methods={"GET"})
     */
    public function Sinscrire(): Response
    {
        return $this->render('Sinscrire.html.twig',[
        ]);
    }

    /**
     * @Route("/backhome", name="backhome", methods={"GET"})
     */
    public function indexBack(): Response
    {
        // Find your Account Sid and Auth Token at twilio.com/console
// DANGER! This is insecure. See http://twil.io/secure
        $sid    = "AC5110b71cce19bab16cde98a018778611";
        $token  = "b48e2f174cc3773ef8db3653def1854d";
        $twilio = new Client($sid, $token);


        $message = $twilio->messages
            ->create("+21626213651",
                array(
                    "body" => "hello there",
                    "from" => "+19282482909"
                )
            );

        return $this->render('baseBack.html.twig',[

        ]);
    }

}
