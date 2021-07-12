<?php

namespace App\Controller;

use App\Entity\CategoriesortRepository;
use App\Entity\Categoriesport;
use App\Entity\Compte;
use App\Entity\Offre;
use App\Form\OffreType;
use App\Entity\OffreRepository;

use MercurySeries\FlashyBundle\FlashyNotifier;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use CMEN\GoogleChartsBundle\GoogleCharts\Charts\BarChart;
use Symfony\Component\Serializer\Normalizer\NormalizerInterface;


/**
 * @Route("/fronthome/offre")
 */
class OffreController extends AbstractController
{
    /**
     * @Route("/recherche ", name="recherche")
     */
    public function recherche(Request $request,OffreRepository $offreRepository,CategoriesortRepository $categoriesortRepository)
    {
        $field = $request->get('s');
        $offre = $offreRepository->findStudentByfield($field);
        return $this->render('offre/index.html.twig',array('offres'=>$offre,'categories'=>$categoriesortRepository->findAll()));

    }
    /**
     * @Route("/tri", name="tri")
     */
    public function tri(OffreRepository $offreRepository , Request $request,CategoriesortRepository $categoriesortRepository)
    {

        $offre=$offreRepository->OrderByNameQB();
        return $this->render('offre/OffreClient.html.twig',array('offres'=>$offre,'categories'=>$categoriesortRepository->findAll()));

    }

    /**
     * @Route("/search", name="search")
     * @throws \Exception
     */
    public function search(OffreRepository $offreRepository , Request $request,CategoriesortRepository $categoriesortRepository)
    {

        $beginDate=$request->get('search1');
        $endDate=$request->get('search2');
        $DateAModifiee1 = new \DateTime($beginDate);
        $DateAModifiee2 = new \DateTime($endDate);
        $offre=$offreRepository->findItemsCreatedBetweenTwoDates($DateAModifiee1,$DateAModifiee2);
        return $this->render('offre/OffreClient.html.twig',array('offres'=>$offre,'categories'=>$categoriesortRepository->findAll()));

    }

    /**
     * @Route("/statistiques",name="statistiques")
     * @param OffreRepository $offreRepository
     *
     * @return Response
     */
    public function statistiques(OffreRepository $offreRepository  ): Response
    {

        $nbs = $offreRepository->getNB();
        $data = [['Date', 'Offre']];
        foreach($nbs as $nb)
        {
            $data[] = array($nb['date'], $nb['vid']);
        }
        $bar = new barchart();
        $bar->getData()->setArrayToDataTable(
            $data
        );

        $bar->getOptions()->getTitleTextStyle()->setColor('#07600');
        $bar->getOptions()->getTitleTextStyle()->setFontSize(50);
        return $this->render('offre/statistique.html.twig', array('barchart' => $bar,'nbs' => $nbs));

    }
    /**
     * @Route("/", name="offre_index", methods={"GET"})
     */
    public function index(OffreRepository $offreRepository,CategoriesortRepository $categoriesortRepository): Response
    {
        return $this->render('offre/OffreClient.html.twig', [
            'offres' => $offreRepository->findAll(),
            'categories'=>$categoriesortRepository->findAll()
        ]);
    }

    /**
     * @Route("/getAllOffreJSON", name="getAllOffreJSON", methods={"GET"})
     * @throws \Symfony\Component\Serializer\Exception\ExceptionInterface
     */
    public function getAllOffreJSON(NormalizerInterface $Normalizer)

    {
        $offre = $this->getDoctrine()
            ->getRepository(Offre::class)->findAll();
        $jsonContent=$Normalizer->normalize($offre,'json',['groups'=>'post:read']);

        return new Response(json_encode($jsonContent,JSON_UNESCAPED_UNICODE));
    }


    /**
     * @Route("/mesoffres", name="mesoffre", methods={"GET"})
     */
    public function getOffreByUser(OffreRepository $offreRepository,CategoriesortRepository $categoriesortRepository, Request $request){

            $session = $request->getSession();
            $repository =$this->getDoctrine()->getRepository(Compte::class);
            $compte = $repository->findOneBy(array('username'=>$session->get('Username')));


            return $this->render('offre/index.html.twig', [
                'offres' => $offreRepository->findAll(),
                'categories'=>$categoriesortRepository->findAll(),
                'username'=>$compte->getId(),
            ]);
        }


    /**
     * @Route("/new", name="offre_new", methods={"GET","POST"})
     */
    public function new(Request $request,FlashyNotifier $flashy)
    {
        $session = $request->getSession();
        $repository =$this->getDoctrine()->getRepository(Compte::class);
        $compte = $repository->findOneBy(array('username'=>$session->get('Username')));

        $offre = new Offre();
        $form = $this->createForm(OffreType::class, $offre);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $offre->setIdCompte($compte->getId());
            $entityManager = $this->getDoctrine()->getManager();
            $entityManager->persist($offre);
            $entityManager->flush();
            $flashy->success('Offre created!');
            return $this->redirectToRoute('mesoffre');
        }

        $flashy->error('Veuillez complétez vos données !');
        return $this->render('offre/new.html.twig', [
            'offre' => $offre,
            'form' => $form->createView(),
        ]);
    }


    /**
     * @Route("/newOffreJSON", name="newOffreJSON", methods={"GET","POST"})
     *
     * @throws \Symfony\Component\Serializer\Exception\ExceptionInterface
     */
    public function newOffreJSON(Request $request,NormalizerInterface $Normalizer)
    {
        $categorie = $this->getDoctrine()->getRepository(Categoriesport::class)->find((int)$request->get('id_categorie'));

        $offre = new Offre();
        $timeDate = new \DateTime ();

        $offre->setTitre($request->get('titre'));
        $offre->setDescription($request->get('description'));
        $offre->setDate($timeDate);
        $offre->setIdCompte($request->get('idCompte'));
        $offre->setIdCategorie($categorie);


        $entityManager = $this->getDoctrine()->getManager();
        $entityManager->persist($offre);
        $entityManager->flush();

        $jsonContent=$Normalizer->normalize($offre,'json',['groups'=>'post:read']);
        return new Response("Offre added successfully".json_encode($jsonContent,JSON_UNESCAPED_UNICODE));
    }




    /**
     * @Route("/{id}", name="offre_show", methods={"GET"})
     */
    public function show(Offre $offre): Response
    {
        return $this->render('offre/show.html.twig', [
            'offre' => $offre,
        ]);
    }

    /**
     * @Route("/show/{id}", name="offre_show_client", methods={"GET"})
     */
    public function showClient(Offre $offre)
    {
        return $this->render('offre/OffreShowDetailClient.html.twig', [
            'offre' => $offre,
        ]);
    }

    /**
     * @Route("/{id}/edit", name="offre_edit", methods={"GET","POST"})
     */
    public function edit(Request $request, Offre $offre,FlashyNotifier $flashy)
    {
        $form = $this->createForm(OffreType::class, $offre);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $this->getDoctrine()->getManager()->flush();
            $flashy->success('Offre modifié!');
            return $this->redirectToRoute('mesoffre');
        }
        $flashy->error('Veuillez complétez vos données !');
        return $this->render('offre/edit.html.twig', [
            'offre' => $offre,
            'form' => $form->createView(),
        ]);
    }

    /**
     * @Route("/editOffreJSON/{id}", name="editOffreJSON", methods={"GET","POST"})
     * @throws \Symfony\Component\Serializer\Exception\ExceptionInterface
     */

    public function editOffreJSON(Request $request, Offre $offre,NormalizerInterface $Normalizer,$id)
    {
        $categorie = $this->getDoctrine()->getRepository(Categoriesport::class)->find((int)$request->get('id_categorie'));

        $entityManager=$this->getDoctrine()->getManager();
        $offre=$entityManager->getRepository(Offre::class)->find($id);
        $timeDate = new \DateTime ();

        $offre->setTitre($request->get('titre'));
        $offre->setDescription($request->get('description'));
        $offre->setDate($timeDate);
        $offre->setIdCompte($request->get('idCompte'));
        $offre->setIdCategorie($categorie);
        $entityManager->flush();

        $jsonContent=$Normalizer->normalize($offre,'json',['groups'=>'post:read']);
        return new Response("Offre updated successfully".json_encode($jsonContent,JSON_UNESCAPED_UNICODE));

    }



    /**
     * @Route("/{id}", name="offre_delete", methods={"POST"})
     */
    public function delete(Request $request, Offre $offre,FlashyNotifier $flashy): Response
    {
        if ($this->isCsrfTokenValid('delete'.$offre->getId(), $request->request->get('_token'))) {
            $entityManager = $this->getDoctrine()->getManager();
            $entityManager->remove($offre);
            $entityManager->flush();
            $flashy->success('Offre modifié!');
        }

        return $this->redirectToRoute('offre_index');
    }

    /**
     * @Route("/DeleteOffreJson/{id}", name="DeleteOffreJson", methods={"POST"})
     * @throws \Symfony\Component\Serializer\Exception\ExceptionInterface
     */
    public function DeleteOffreJson(Request $request,  Offre $offre,$id,NormalizerInterface $Normalizer)
    {
        $entityManager = $this->getDoctrine()->getManager();
        $offre=$entityManager->getRepository(Offre::class)->find($id);
        $entityManager->remove($offre);
        $entityManager->flush();
        $jsonContent=$Normalizer->normalize($offre,'json',['groups'=>'post:read']);
        return new Response("Offre deleted successfully".json_encode($jsonContent,JSON_UNESCAPED_UNICODE));
    }


}
