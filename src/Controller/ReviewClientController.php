<?php

namespace App\Controller;

use App\Entity\Compte;
use App\Entity\ReviewClient;
use App\Form\ReviewClientType;
use App\Entity\ReviewClientRepository;
use Knp\Component\Pager\PaginatorInterface;
use phpDocumentor\Reflection\Types\Integer;
use phpDocumentor\Reflection\Types\String_;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Dompdf\Dompdf;
use Dompdf\Options;
use MercurySeries\FlashyBundle\FlashyNotifier;
use CMEN\GoogleChartsBundle\GoogleCharts\Charts\PieChart;


/**
 * @Route("/review/client")
 */
class ReviewClientController extends AbstractController
{
    /**
     * @Route("/", name="review_client_index", methods={"GET"})
     */
    public function index(ReviewClientRepository $reviewClientRepository): Response
    {
        return $this->render('review_client/index.html.twig', [ #review_client/index.html.twig
            'review_clients' => $reviewClientRepository->findAll(),
        ]);
    }
    /**
     * @Route("/clientall", name="review_client_index_all", methods={"GET"})
     */
    public function indexclientall(ReviewClientRepository $reviewClientRepository): Response
    {
        return $this->render('review_client/indexclientall.html.twig', [
            'review_clients' => $reviewClientRepository->findAll(),
        ]);
    }

    /**
     * @Route("/clientallcoach", name="review_client_index_all_coach", methods={"GET"})
     */
    public function indexclientallcoach(ReviewClientRepository $reviewClientRepository): Response
    {
        return $this->render('review_client/indexclientallcoach.html.twig', [
            'review_clients' => $reviewClientRepository->findAll(),
        ]);
    }


    /**
     * @Route("/coachonly", name="review_coach_index_only", methods={"GET"})
     */
    public function indexcoachonly(ReviewClientRepository $reviewClientRepository, Request $request): Response
    {

        $session = $request->getSession();
        $repository =$this->getDoctrine()->getRepository(Compte::class);
        $compte = $repository->findOneBy(array('username'=>$session->get('Username')));

        return $this->render('review_client/indexcoachonly.html.twig', [
            'review_clients' => $reviewClientRepository->findAll(),
            'username'=>$compte->getUsername(),
        ]);
    }

    /**
     * @Route("/clientonly", name="review_client_index_only", methods={"GET"})
     */
    public function indexclientonly(ReviewClientRepository $reviewClientRepository, Request $request): Response
    {
        $session = $request->getSession();
        $repository =$this->getDoctrine()->getRepository(Compte::class);
        $compte = $repository->findOneBy(array('username'=>$session->get('Username')));


        return $this->render('review_client/indexclientonly.html.twig', [
            'review_clients' => $reviewClientRepository->findAll(),
            'username'=>$compte->getUsername(),
        ]);
    }
    /**
     * @Route("/reviewback", name="review_client_index_back", methods={"GET"})
     */
    public function indexback(ReviewClientRepository $reviewClientRepository,Request $request, PaginatorInterface $paginator): Response
    {
        $reviews = $this->getDoctrine()->getRepository(ReviewClient::class)->findAll();
        $reviewsclient = $paginator->paginate(
            $reviews,
            $request->query->getInt('page', 1),
            4
        );

        //stats
        $data = [['nomCoachReview', 'rating']];


        $em = $this->getDoctrine()->getManager();
        $query = $em->createQuery("SELECT c.rating as SumRating FROM App\Entity\ReviewClient c ");
        $result = $query->getResult();

        $query2 = $em->createQuery("SELECT n ,n.nomCoachReview as nom ,avg(n.rating) as rate from App\Entity\ReviewClient n group by n.nomCoachReview");
        $result2 = $query2->getResult();


        foreach ($result2 as $row)
        {
            $data[] = array(
                $row['nom'], $row['rate'] *100,
                //array_sum(array($review->getRating()))$review->getRating()
            );

        }


        $pieChart = new PieChart();
        $pieChart->getData()->setArrayToDataTable(
            $data
        );
        $pieChart->getOptions()->setTitle('Coach ratings');
        $pieChart->getOptions()->setHeight(500);
        $pieChart->getOptions()->setWidth(900);
        $pieChart->getOptions()->getTitleTextStyle()->setBold(true);
        $pieChart->getOptions()->getTitleTextStyle()->setColor('#009900');
        $pieChart->getOptions()->getTitleTextStyle()->setItalic(true);
        $pieChart->getOptions()->getTitleTextStyle()->setFontName('Arial');
        $pieChart->getOptions()->getTitleTextStyle()->setFontSize(20);
        //end stats

        return $this->render('review_client/indexback.html.twig', [
            'review_clients' => $reviewsclient,
             'piechart' => $pieChart,
        ]);
    }



    /**
     * @Route("/reviewback/reviewAllclient", name="review_client_index_back_all", methods={"GET"})
     */
    public function pdfreviews(ReviewClientRepository $reviewClientRepository): Response
    {
        // Configure Dompdf according to your needs
        $pdfOptions = new Options();
        $pdfOptions->set('defaultFont', 'Arial');

        // Instantiate Dompdf with our options
        $dompdf = new Dompdf($pdfOptions);


        // Retrieve the HTML generated in our twig file
        $html = $this->renderView('review_client/reviewAllclients.html.twig', [
            'review_clients' => $reviewClientRepository->findAll(),
        ]);

        // Load HTML to Dompdf
        $dompdf->loadHtml($html);

        // (Optional) Setup the paper size and orientation 'portrait' or 'portrait'
        $dompdf->setPaper('A4', 'portrait');

        // Render the HTML as PDF
        $dompdf->render();

        // Output the generated PDF to Browser (force download)
        $dompdf->stream("mypdf.pdf", [
            "Attachment" => false
        ]);

    }


    /**
     * @Route("/new", name="review_client_new", methods={"GET","POST"})
     */
    public function new(Request $request , FlashyNotifier $flashy): Response
    {

        $reviewClient = new ReviewClient();
        $form = $this->createForm(ReviewClientType::class, $reviewClient);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $reviewClient->setRating($request->request->get('note'));

            $entityManager = $this->getDoctrine()->getManager();
            $entityManager->persist($reviewClient);
            $entityManager->flush();

            $flashy->success('Your Review has been successfully created!');
            return $this->redirectToRoute('review_client_index');
        }

        return $this->render('review_client/new.html.twig', [ #review_client/new.html.twig
            'review_client' => $reviewClient,
            'form' => $form->createView(),
        ]);
    }

    /**
     * @Route("/newclient", name="review_new_client", methods={"GET","POST"})
     */
    public function newclient(Request $request , FlashyNotifier $flashy): Response
    {

        $reviewClient = new ReviewClient();
        $form = $this->createForm(ReviewClientType::class, $reviewClient);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $reviewClient->setRating($request->request->get('note'));

            $entityManager = $this->getDoctrine()->getManager();
            $entityManager->persist($reviewClient);
            $entityManager->flush();

            $flashy->success('Your Review has been successfully created!');
            return $this->redirectToRoute('review_client_index_only');
        }

        return $this->render('review_client/newclient.html.twig', [ #review_client/new.html.twig
            'review_client' => $reviewClient,
            'form' => $form->createView(),
        ]);
    }

    /**
     * @Route("/{idReview}", name="review_client_show", methods={"GET"})
     */
    public function show(ReviewClient $reviewClient): Response
    {
        return $this->render('review_client/show.html.twig', [
            'review_client' => $reviewClient,
        ]);
    }

    /**
     * @Route("/{idReview}/showclient", name="review_show_client", methods={"GET"})
     */
    public function showclient(ReviewClient $reviewClient): Response
    {
        return $this->render('review_client/showclient.html.twig', [
            'review_client' => $reviewClient,
        ]);
    }

    /**
     * @Route("/showback/{idReview}", name="review_client_show_back", methods={"GET"})
     */
    public function showback(ReviewClient $reviewClient): Response
    {
        return $this->render('review_client/showback.html.twig', [ #review/client/showback/37 exemple d 'id
            'review_client' => $reviewClient,
        ]);
    }

    /**
     * @Route("/{idReview}/edit", name="review_client_edit", methods={"GET","POST"})
     */
    public function edit(Request $request, ReviewClient $reviewClient , FlashyNotifier $flashy): Response
    {
        $form = $this->createForm(ReviewClientType::class, $reviewClient);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {

            $reviewClient->setRating($request->request->get('note'));
            $this->getDoctrine()->getManager()->flush();

            $flashy->info('Your Review have been successfully UPDATED!');
            return $this->redirectToRoute('review_client_index');
        }

        return $this->render('review_client/edit.html.twig', [
            'review_client' => $reviewClient,
            'form' => $form->createView(),
        ]);
    }
    //review_edit_client
    /**
     * @Route("/{idReview}/editclient", name="review_edit_client", methods={"GET","POST"})
     */
    public function editclient(Request $request, ReviewClient $reviewClient , FlashyNotifier $flashy): Response
    {
        $form = $this->createForm(ReviewClientType::class, $reviewClient);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {

            $reviewClient->setRating($request->request->get('note'));
            $this->getDoctrine()->getManager()->flush();

            $flashy->info('Your Review have been successfully UPDATED!');
            return $this->redirectToRoute('review_client_index_only');
        }

        return $this->render('review_client/editclient.html.twig', [
            'review_client' => $reviewClient,
            'form' => $form->createView(),
        ]);
    }


    /**
     * @Route("/{idReview}", name="review_client_delete", methods={"POST"})
     */
    public function delete(Request $request, ReviewClient $reviewClient , FlashyNotifier $flashy): Response
    {
        if ($this->isCsrfTokenValid('delete'.$reviewClient->getIdReview(), $request->request->get('_token'))) {
            $entityManager = $this->getDoctrine()->getManager();
            $entityManager->remove($reviewClient);
            $entityManager->flush();
        }
        $flashy->error('Review Have been successfully DELETED!');
        return $this->redirectToRoute('review_client_index');
    }



    /**
     * @Route("/{idReview}/deleteclientBack", name="review_client_deleteback", methods={"POST"})
     */
    public function deleteback(Request $request, ReviewClient $reviewClient , FlashyNotifier $flashy): Response
    {
        if ($this->isCsrfTokenValid('delete'.$reviewClient->getIdReview(), $request->request->get('_token'))) {
            $entityManager = $this->getDoctrine()->getManager();
            $entityManager->remove($reviewClient);
            $entityManager->flush();
        }
        $flashy->error('Review Have been successfully DELETED!');
        return $this->redirectToRoute('review_client_index_back');
    }


    /**
     * @Route("/{idReview}/deleteclient", name="review_delete_client", methods={"POST"})
     */
    public function deleteclient(Request $request, ReviewClient $reviewClient , FlashyNotifier $flashy): Response
    {
        if ($this->isCsrfTokenValid('delete'.$reviewClient->getIdReview(), $request->request->get('_token'))) {
            $entityManager = $this->getDoctrine()->getManager();
            $entityManager->remove($reviewClient);
            $entityManager->flush();
        }
        $flashy->error('Review Have been successfully DELETED!');
        return $this->redirectToRoute('review_client_index_only');
    }












    /**
     * @Route("/rateReview", name="rateReview", methods={"POST"})
    */
    public function rateAction(Request $request){
    $data = $request->getContent();
    $obj = json_decode($data,true);

    $em = $this->getDoctrine()->getManager();
    $rate =$obj['rating'];
    $id = $obj['ReviewClient'];
    $ReviewClient = $em->getRepository(ReviewClient::class)->find($id);
    $note = ($ReviewClient->getRate()($ReviewClient->getRating() + $rate)/($ReviewClient->getRating()+1));
    $ReviewClient->setRating($note);
    $em->persist($ReviewClient);
    $em->flush();
    return new Response($ReviewClient->getRating());
    }



}
