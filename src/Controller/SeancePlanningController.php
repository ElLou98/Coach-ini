<?php

namespace App\Controller;

use App\Entity\Compte;
use App\Entity\SeancePlanning;
use App\Form\SeancePlanningType;
use App\Entity\SeancePlanningRepository;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use MercurySeries\FlashyBundle\FlashyNotifier;

/**
 * @Route("/seance/planning")
 */
class SeancePlanningController extends AbstractController
{
    /**
     * @Route("/", name="seance_planning_index", methods={"GET"})
     */
    public function index(SeancePlanningRepository $seancePlanningRepository): Response
    {
        return $this->render('seance_planning/index.html.twig', [ #was basePlanning
            'seance_plannings' => $seancePlanningRepository->findAll(),
        ]);
    }
    /**
     * @Route("/client", name="seance_planning_index_client", methods={"GET"})
     */
    public function indexclient(SeancePlanningRepository $seancePlanningRepository, Request $request): Response
    {

        $session = $request->getSession();
        $repository =$this->getDoctrine()->getRepository(Compte::class);
        $compte = $repository->findOneBy(array('username'=>$session->get('Username')));

        return $this->render('seance_planning/indexclient.html.twig', [ #was basePlanning
            'seance_plannings' => $seancePlanningRepository->findAll(),
            'username'=>$compte->getUsername(),

        ]);
    }

    /**
     * @Route("/new", name="seance_planning_new", methods={"GET","POST"})
     */
    public function new(Request $request , FlashyNotifier $flashy): Response
    {
        $seancePlanning = new SeancePlanning();
        $form = $this->createForm(SeancePlanningType::class, $seancePlanning);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $entityManager = $this->getDoctrine()->getManager();
            $entityManager->persist($seancePlanning);
            $entityManager->flush();
            $flashy->success('Your meeting has been successfully created!');
            return $this->redirectToRoute('seance_planning_index');
        }

        return $this->render('seance_planning/new.html.twig', [
            'seance_planning' => $seancePlanning,
            'form' => $form->createView(),
        ]);
    }

    /**
     * @Route("/{id}", name="seance_planning_show", methods={"GET"})
     */
    public function show(SeancePlanning $seancePlanning): Response
    {
        return $this->render('seance_planning/show.html.twig', [
            'seance_planning' => $seancePlanning,
        ]);
    }

    /**
     * @Route("/{id}/edit", name="seance_planning_edit", methods={"GET","POST"})
     */
    public function edit(Request $request, SeancePlanning $seancePlanning , FlashyNotifier $flashy): Response
    {
        $form = $this->createForm(SeancePlanningType::class, $seancePlanning);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $this->getDoctrine()->getManager()->flush();
            $flashy->info('Your Meeting have been successfully UPDATED!');
            return $this->redirectToRoute('seance_planning_index');
        }

        return $this->render('seance_planning/edit.html.twig', [
            'seance_planning' => $seancePlanning,
            'form' => $form->createView(),
        ]);
    }

    /**
     * @Route("/{id}", name="seance_planning_delete", methods={"POST"})
     */
    public function delete(Request $request, SeancePlanning $seancePlanning , FlashyNotifier $flashy): Response
    {
        if ($this->isCsrfTokenValid('delete'.$seancePlanning->getId(), $request->request->get('_token'))) {
            $entityManager = $this->getDoctrine()->getManager();
            $entityManager->remove($seancePlanning);
            $entityManager->flush();
        }
        $flashy->error('Meeting Have been successfully DELETED!');
        return $this->redirectToRoute('seance_planning_index');
    }
    /**
     * @Route("/calendar", name="booking_calendar", methods={"GET"})
     */
    public function calendar(): Response
    {
        return $this->render('seance_planning/calendar.html.twig');
    }
}
