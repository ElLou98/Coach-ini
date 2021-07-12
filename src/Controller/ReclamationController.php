<?php

namespace App\Controller;

use App\Entity\Reclamation;
use App\Entity\Typereclamation;
use App\Form\ReclamationType;
use App\Form\ReclamationEditType;
use App\Form\ReclamationAjoutType;
use Symfony\Component\Form\Extension\Core\Type\SubmitType;
use Symfony\Component\Form\Extension\Core\Type\TextareaType;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\Serializer\Normalizer\NormalizerInterface;
use Symfony\Component\Validator\Constraints\DateTime;
use Knp\Component\Pager\PaginatorInterface;
use Dompdf\Dompdf;
use Dompdf\Options;
use Psr\Log\LoggerInterface;
use Sensio\Bundle\FrameworkExtraBundle\Configuration\ParamConverter;

/**
 * @Route("/reclamation")
 */
class ReclamationController extends AbstractController
{
    /**
     * @Route("/", name="reclamation_index", methods={"GET"})
     */
    public function index(Request $request , PaginatorInterface $paginator): Response
    {
        $donnees=$this->getDoctrine()->getManager()->getRepository(Reclamation::class)->findAll();

        $reclamations = $paginator->paginate(
            $donnees ,
            $request->query->getInt('page',1),
            4
        );

        return $this->render('reclamation/index.html.twig', [
            'reclamations' => $reclamations,
        ]);
    }

    /**
     * @Route("/f", name="index_reclamation", methods={"GET"})
     */
    public function indexf(Request $request): Response
    {
        $session = $request->getSession();
        $myUsername=$session->get('Username');

        $reclamations = $this->getDoctrine()
            ->getRepository(Reclamation::class)
            ->findAll();

        return $this->render('reclamation/indexfront.html.twig', [
            'reclamations' => $reclamations,
            'myUsername' => $myUsername,
        ]);
    }


    /**
     * @Route("/getReclamationsJSON", name="getReclamationsJSON")
     */
    public function getReclamationsJSON(NormalizerInterface $Normalizer): Response
    {
        $reclamations = $this->getDoctrine()
            ->getRepository(Reclamation::class)
            ->findAll();

        $jsonContent=$Normalizer->normalize($reclamations,'json',['groups'=>'post:read']);
        /*return $this->render('reclamation/getReclamationsJSON.html.twig', [
            'data' => $jsonContent,
        ]);*/
        return new Response(json_encode($jsonContent,JSON_UNESCAPED_UNICODE));
    }

    /**
     * @Route("/deleteReclamationsJSON/{id}", name="deleteReclamationsJSON")
     */
    public function deleteReclamationsJSON(Request $request,NormalizerInterface $Normalizer,$id): Response
    {
        $em=$this->getDoctrine()->getManager();
        $reclamation=$em->getRepository(Reclamation::class)->find($id);
        $em->remove($reclamation);
        $em->flush();
        $jsonContent=$Normalizer->normalize($reclamation,'json',['groups'=>'post:read']);
        return new Response("Reclamation deleted successfully".json_encode($jsonContent,JSON_UNESCAPED_UNICODE));
    }

    /**
     * @Route("/addReclamationsJSON", name="addReclamationsJSON")
     */
    public function addReclamationsJSON(Request $request,NormalizerInterface $Normalizer): Response
    {

        $typeReclamation = $this->getDoctrine()->getRepository(Typereclamation::class)->find((int)$request->get('typereclamation'));

        $reclamation=new Reclamation();
        $timeDate = new \DateTime ();
        $timeString= $timeDate->format('d/m/Y');
        $reclamation->setLogin($request->get('login'));
        $reclamation->setDescriptionreclamation($request->get('description'));
        $reclamation->setTypereclamation($typeReclamation->getTypereclamation());
        $reclamation->setDatereclamation($timeString);
        $reclamation->setEncours(0);
        $reclamation->setTraite(0);
        $reclamation->setIdtypereclamation($typeReclamation);


        $entityManager = $this->getDoctrine()->getManager();
        $entityManager->persist($reclamation);
        $entityManager->flush();

        $jsonContent=$Normalizer->normalize($reclamation,'json',['groups'=>'post:read']);
        return new Response("Reclamation added successfully".json_encode($jsonContent,JSON_UNESCAPED_UNICODE));
    }

    /**
     * @Route("/editReclamationJSON/{id}", name="editReclamationJSON")
     */

    public function editReclamationJSON(Request $request,Reclamation $reclamation,\Swift_Mailer $mailer,NormalizerInterface $Normalizer,$id): Response
    {

        $entityManager=$this->getDoctrine()->getManager();
        $reclamation=$entityManager->getRepository(Reclamation::class)->find($id);

        $query = $entityManager->createQuery("SELECT c.adresseMail FROM App\Entity\Compte c WHERE c.username='".$reclamation->getLogin()."'");
        $queryResult =$query->getResult();
        $userMail=$queryResult[0]['adresseMail'];

        if ($reclamation->getEncours()==0 && $reclamation->getTraite()==0 ) {
            $reclamation->setEncours(1);
            $entityManager->flush();
        }

        elseif ($reclamation->getEncours()==1 && $reclamation->getTraite()==0 ) {
            $reclamation->setEncours(0);
            $reclamation->setTraite(1);
            $entityManager->flush();
            $message = (new \Swift_Message('Réclamation : Traité'))
                ->setFrom('coachiniapp@gmail.com')
                ->setTo($userMail)
                ->setBody(
                    "Salut M/Mme. ".$reclamation->getLogin()." \nl'équipe de Coach'ini vous informe que l'une de vos réclamation "
                    . "qui était en cours de traitement vient d'être terminé.\nCordialement, Coach'ini"
                )
            ;

            $mailer->send($message);
        }

        $jsonContent=$Normalizer->normalize($reclamation,'json',['groups'=>'post:read']);
        return new Response("Reclamation edited successfully".json_encode($jsonContent,JSON_UNESCAPED_UNICODE));

    }



    /**
     * @Route("/reclamationsliste", name="reclamationsliste", methods={"GET"})
     */
    public function print()
    {
        // Configure Dompdf according to your needs
        $pdfOptions = new Options();
        $pdfOptions->set('defaultFont', 'Arial');

        // Instantiate Dompdf with our options
        $dompdf = new Dompdf($pdfOptions);

        // Retrieve the HTML generated in our twig file
        $html = $this->renderView('reclamation/reclamationsliste.html.twig', [
            'title' => "Welcome to our PDF Test",
            'reclamations' => $this->getDoctrine()->getManager()->getRepository(Reclamation::class)->findAll(),
        ]);

        // Load HTML to Dompdf
        $dompdf->loadHtml($html);

        // (Optional) Setup the paper size and orientation 'portrait' or 'portrait'
        $dompdf->setPaper('A4', 'portrait');

        // Render the HTML as PDF
        $dompdf->render();

        // Output the generated PDF to Browser (force download)
        $dompdf->stream("Reclamations.pdf", [
            "Attachment" => true

        ]);
    }

    /**
     * @Route("/new", name="reclamation_new", methods={"GET","POST"})
     */
    public function new(Request $request): Response
    {
        $reclamation = new Reclamation();
        $form = $this->createForm(ReclamationType::class, $reclamation);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $entityManager = $this->getDoctrine()->getManager();
            $entityManager->persist($reclamation);
            $entityManager->flush();

            return $this->redirectToRoute('reclamation_index');
        }

        return $this->render('reclamation/new.html.twig', [
            'reclamation' => $reclamation,
            'form' => $form->createView(),
        ]);
    }

    /**
     * @Route("/newf", name="new_reclamation", methods={"GET","POST"})
     */
    public function newf(Request $request): Response
    {
        $reclamation = new Reclamation();
        $form = $this->createForm(ReclamationAjoutType::class, $reclamation);
        $form->handleRequest($request);
        $entityManager = $this->getDoctrine()->getManager();


        $session = $request->getSession();
        $myUsername=$session->get('Username');

        if ($form->isSubmitted() && $form->isValid() ) {
            $typeReclamation=$reclamation->getIdtypereclamation()->getTypereclamation();
            $timeDate = new \DateTime ();
            $timeString= $timeDate->format('d/m/Y');
            $reclamation->setLogin($myUsername);
            $reclamation->setTypereclamation($typeReclamation);
            $reclamation->setDatereclamation($timeString);
            $reclamation->setEncours(0);
            $reclamation->setTraite(0);
            $entityManager = $this->getDoctrine()->getManager();
            $entityManager->persist($reclamation);
            $entityManager->flush();

            return $this->redirectToRoute('index_reclamation');
        }

        return $this->render('reclamation/newfront.html.twig', [
            'reclamation' => $reclamation,
            'form' => $form->createView(),
        ]);
    }

    /**
     * @Route("/{idreclamation}", name="reclamation_show", methods={"GET"})
     */
    public function show(Reclamation $reclamation): Response
    {
        return $this->render('reclamation/show.html.twig', [
            'reclamation' => $reclamation,
        ]);
    }

    /**
     * @Route("/{idreclamation}/f", name="show_reclamation", methods={"GET"})
     */
    public function showf(Reclamation $reclamation): Response
    {
        return $this->render('reclamation/showfront.html.twig', [
            'reclamation' => $reclamation,
        ]);
    }

    /**
     * @Route("/{idreclamation}/edit", name="reclamation_edit", methods={"GET","POST"})
     */

    public function edit(Request $request,Reclamation $reclamation,\Swift_Mailer $mailer,PaginatorInterface $paginator): Response
    {

        $form = $this->createForm(ReclamationType::class, $reclamation);
        $form->handleRequest($request);
        $reclamations = $this->getDoctrine()
            ->getRepository(Reclamation::class)
            ->findAll();


        $entityManager=$this->getDoctrine()->getManager();


        $query = $entityManager->createQuery("SELECT c.adresseMail FROM App\Entity\Compte c 
                                                    WHERE c.username='".$reclamation->getLogin()."'");
        $queryResult =$query->getResult();
        $userMail=$queryResult[0]['adresseMail'];

        if ($reclamation->getEncours()==0 && $reclamation->getTraite()==0 ) {
            $reclamation->setEncours(1);
            $entityManager->flush();
        }

        elseif ($reclamation->getEncours()==1 && $reclamation->getTraite()==0 ) {
            $reclamation->setEncours(0);
            $reclamation->setTraite(1);
            $entityManager->flush();
            $message = (new \Swift_Message('Réclamation : Traité'))
                ->setFrom('coachiniapp@gmail.com')
                ->setTo($userMail)
                ->setBody(
                    "Salut M/Mme. ".$reclamation->getLogin()." \nl'équipe de Coach'ini vous informe que l'une de vos réclamation "
                    . "qui était en cours de traitement vient d'être terminé.\nCordialement, Coach'ini"
                )
            ;

            $mailer->send($message);
        }



        $donnees=$this->getDoctrine()->getManager()->getRepository(Reclamation::class)->findAll();

        $reclamations = $paginator->paginate(
            $donnees ,
            $request->query->getInt('page',1),
            4
        );

        $this->render('reclamation/index.html.twig', [
            'reclamations' => $reclamations,
            'form' => $form->createView(),
        ]);
        return $this->redirectToRoute('reclamation_index');

    }


    /**
     * @Route("/{idreclamation}/editf", name="edit_reclamation", methods={"GET","POST"})
     */
    public function editf(Request $request, Reclamation $reclamation): Response
    {
        $form = $this->createForm(ReclamationType::class, $reclamation);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $this->getDoctrine()->getManager()->flush();

            return $this->redirectToRoute('index_reclamation');
        }

        return $this->render('reclamation/editfront.html.twig', [
            'reclamation' => $reclamation,
            'form' => $form->createView(),
        ]);
    }

    /**
     * @Route("/{idreclamation}", name="reclamation_delete", methods={"POST"})
     */
    public function delete(Request $request, Reclamation $reclamation): Response
    {
        if ($this->isCsrfTokenValid('delete'.$reclamation->getIdreclamation(), $request->request->get('_token'))) {
            $entityManager = $this->getDoctrine()->getManager();
            $entityManager->remove($reclamation);
            $entityManager->flush();
        }

        return $this->redirectToRoute('reclamation_index');
    }

    /**
     * @Route("/{idreclamation}/f", name="delete_reclamation", methods={"POST"})
     */
    public function deletef(Request $request, Reclamation $reclamation): Response
    {
        if ($this->isCsrfTokenValid('delete'.$reclamation->getIdreclamation(), $request->request->get('_token'))) {
            $entityManager = $this->getDoctrine()->getManager();
            $entityManager->remove($reclamation);
            $entityManager->flush();
        }

        return $this->redirectToRoute('index_reclamation');
    }


}
