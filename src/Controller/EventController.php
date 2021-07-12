<?php

namespace App\Controller;

use App\Entity\Event;
use App\Form\EventType;
use App\Repository\CategorieeventRepository;
use Knp\Component\Pager\PaginatorInterface;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\Serializer\Normalizer\NormalizerInterface;
use Dompdf\Dompdf;
use Dompdf\Options;
use Symfony\Component\Mailer\MailerInterface;
use Symfony\Component\Mime\Email;
use App\Entity\Compte;


/**
 * @Route("/event")
 */
class EventController extends AbstractController
{


    /**
     * @Route("/email")
     */
    public function sendEmail(MailerInterface $mailer)
    {
        $email = (new Email())
            ->from('coachiniapp@gmail.com')
            ->to('zepushkagha@gmail.com')
            ->priority(Email::PRIORITY_HIGH)
            ->subject('Votre Event')
            ->text('Votre attachement PDF');
            //->html('<p>See Twig integration for better HTML integration!</p>');

        $mailer->send($email);

        $events = $this->getDoctrine()
            ->getRepository(Event::class)
            ->findAll();

        return $this->render('event/index.html.twig', [
            'events' => $events,
        ]);

    }

    /**
     * @Route("/see/{id}", name="see", methods={"GET"})
     */
    public function see(Event $event,MailerInterface $mailer): Response
    {


        // Configure Dompdf according to your needs
        $pdfOptions = new Options();
        $pdfOptions->set('defaultFont', 'Arial');
        $pdfOptions->setIsRemoteEnabled(true);
        // Instantiate Dompdf with our options
        $dompdf = new Dompdf($pdfOptions);
        $contxt = stream_context_create([
            'ssl' => [
                'verify_peer' => FALSE,
                'verify_peer_name' => FALSE,
                'allow_self_signed'=> TRUE
            ]
        ]);
        $dompdf->setHttpContext($contxt);

        // Retrieve the HTML generated in our twig file
        $html = $this->renderView('event/pdf.html.twig', [
            'event' => $event,
        ]);

        // Load HTML to Dompdf
        $dompdf->loadHtml($html);

        // (Optional) Setup the paper size and orientation 'portrait' or 'portrait'
        $dompdf->setPaper('A4', 'portrait');

        // Render the HTML as PDF
        $dompdf->render();

        $file_to_save = 'C:\xampp\htdocs\coachini\uploads\file.pdf';
        //save the pdf file on the server
        file_put_contents($file_to_save, $dompdf->output());
        header('Content-type: application/pdf');
        header('Content-Disposition: inline; filename="file.pdf"');
        header('Content-Transfer-Encoding: binary');
        header('Content-Length: ' . filesize($file_to_save));
        header('Accept-Ranges: bytes');
        readfile($file_to_save);

    }


    /**
     * @Route("/email/{id}", name="email", methods={"GET"})
     */
    public function mailing(Event $event,MailerInterface $mailer,Request $request,PaginatorInterface $paginator): Response
    {


        // Configure Dompdf according to your needs
        $pdfOptions = new Options();
        $pdfOptions->set('defaultFont', 'Arial');
        $pdfOptions->setIsRemoteEnabled(true);
        // Instantiate Dompdf with our options
        $dompdf = new Dompdf($pdfOptions);
        $contxt = stream_context_create([
            'ssl' => [
                'verify_peer' => FALSE,
                'verify_peer_name' => FALSE,
                'allow_self_signed'=> TRUE
            ]
        ]);
        $dompdf->setHttpContext($contxt);

        // Retrieve the HTML generated in our twig file
        $html = $this->renderView('event/pdf.html.twig', [
            'event' => $event,
        ]);

        // Load HTML to Dompdf
        $dompdf->loadHtml($html);

        // (Optional) Setup the paper size and orientation 'portrait' or 'portrait'
        $dompdf->setPaper('A4', 'portrait');

        // Render the HTML as PDF
        $dompdf->render();

        $file_to_save = 'C:\xampp\htdocs\coachini\uploads\file.pdf';
        //save the pdf file on the server
        file_put_contents($file_to_save, $dompdf->output());

        $session = $request->getSession();
        $repository =$this->getDoctrine()->getRepository(Compte::class);
        $compte = $repository->findOneBy(array('username'=>$session->get('Username')));

        $email = (new Email())
            ->from('coachiniapp@gmail.com')
            ->to($compte->getAdresseMail())
            ->priority(Email::PRIORITY_HIGH)
            ->subject('Votre Event')
            ->text('Votre attachement PDF')
            ->attachFromPath('C:\xampp\htdocs\coachini\uploads\file.pdf', 'Event');
        //->html('<p>See Twig integration for better HTML integration!</p>');

        $mailer->send($email);

        $events = $this->getDoctrine()
            ->getRepository(Event::class)
            ->findAll();
        $events=$paginator->paginate(
            $events,
            $request->query->getInt('page',1),
            2
        );

        if ($compte->getType() == 'coach'){
        return $this->render('event/index.html.twig', [
            'events' => $events,
        ]);}
        else{
            return $this->render('event/indexClient.html.twig', [
                'events' => $events,
            ]);
        }

    }

    /**
     * @Route("/pdf/{id}", name="pdf", methods={"GET"})
     */
    public function ind(Event $event): Response
    {


        // Configure Dompdf according to your needs
        $pdfOptions = new Options();
        $pdfOptions->set('defaultFont', 'Arial');
        $pdfOptions->setIsRemoteEnabled(true);
        // Instantiate Dompdf with our options
        $dompdf = new Dompdf($pdfOptions);
        $contxt = stream_context_create([
            'ssl' => [
                'verify_peer' => FALSE,
                'verify_peer_name' => FALSE,
                'allow_self_signed'=> TRUE
            ]
        ]);
        $dompdf->setHttpContext($contxt);

        // Retrieve the HTML generated in our twig file
        $html = $this->renderView('event/pdf.html.twig', [
            'event' => $event,
        ]);

        // Load HTML to Dompdf
        $dompdf->loadHtml($html);

        // (Optional) Setup the paper size and orientation 'portrait' or 'portrait'
        $dompdf->setPaper('A4', 'portrait');

        // Render the HTML as PDF
        $dompdf->render();

        // Output the generated PDF to Browser (force download)
        $dompdf->stream("mypdf.pdf", [
            "Attachment" => true
        ]);


    }



    /**
     * @Route("/", name="event_index", methods={"GET"})
     */
    public function index(Request $request,PaginatorInterface $paginator): Response
    {

        $session = $request->getSession();
        $repository =$this->getDoctrine()->getRepository(Compte::class);
        $compte = $repository->findOneBy(array('username'=>$session->get('Username')));

        $events = $this->getDoctrine()
            ->getRepository(Event::class)
            ->findByExampleField($compte->getId());

        $events=$paginator->paginate(
            $events,
            $request->query->getInt('page',1),
            2
        );
        return $this->render('event/index.html.twig', [
            'events' => $events,
        ]);
    }
    /**
     * @Route("/client", name="event_client_index", methods={"GET"})
     */
    public function indexClient(Request $request,PaginatorInterface $paginator): Response
    {
        $events = $this->getDoctrine()
            ->getRepository(Event::class)
            ->findAll();

        $events=$paginator->paginate(
            $events,
            $request->query->getInt('page',1),
            2

        );

        return $this->render('event/indexClient.html.twig', [
            'events' => $events,
        ]);
    }
    /**
     * @Route("/admin", name="event_admin_index", methods={"GET"})
     */
    public function indexAdmin(Request $request,PaginatorInterface $paginator): Response
    {
        $events = $this->getDoctrine()
            ->getRepository(Event::class)
            ->findAll();

        $events=$paginator->paginate(
            $events,
            $request->query->getInt('page',1),
            2
        );

        return $this->render('event/indexAdmin.html.twig', [
            'events' => $events,
        ]);
    }

    /**
     * @Route("/map", name="event_map", methods={"GET"})
     */
    public function indexing(): Response
    {
        return $this->render('event/map.html.twig');
    }

    /**
     * @Route("/new", name="event_new", methods={"GET","POST"})
     */
    public function new(Request $request,CategorieeventRepository $categorieeventRepository): Response
    {

        $event = new Event();
        $form = $this->createForm(EventType::class, $event);
        $form->handleRequest($request);
        $events = $this->getDoctrine()
            ->getRepository(Event::class)
            ->findAll();

        $session = $request->getSession();
        $repository =$this->getDoctrine()->getRepository(Compte::class);
        $compte = $repository->findOneBy(array('username'=>$session->get('Username')));
        if ($form->isSubmitted() && $form->isValid()) {
            $event->setIdCoach($compte->getId());
            $entityManager = $this->getDoctrine()->getManager();
            $entityManager->persist($event);
            $entityManager->flush();

            return $this->redirectToRoute('event_index');
        }

        return $this->render('event/new.html.twig', [
            'event' => $event,
            'events' => $events,
            'form' => $form->createView(),
            'categorieevents' => $categorieeventRepository->findAll(),
        ]);
    }

    /**
     * @Route("/{id}", name="event_show", methods={"GET"})
     */
    public function show(Event $event): Response
    {
        return $this->render('event/show.html.twig', [
            'event' => $event,
        ]);
    }
    /**
     * @Route("/client/{id}", name="event_show_Client", methods={"GET"})
     */
    public function showClient(Event $event): Response
    {
        return $this->render('event/showClient.html.twig', [
            'event' => $event,
        ]);
    }

    /**
     * @Route("/{id}/edit", name="event_edit", methods={"GET","POST"})
     */
    public function edit(Request $request, Event $event,CategorieeventRepository $categorieeventRepository): Response
    {
        $form = $this->createForm(EventType::class, $event);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $this->getDoctrine()->getManager()->flush();

            return $this->redirectToRoute('event_index');
        }

        return $this->render('event/edit.html.twig', [
            'event' => $event,
            'form' => $form->createView(),
            'categorieevents' => $categorieeventRepository->findAll(),
        ]);
    }
    /**
     * @Route("/searchEventx ", name="searchEventx")
     */
    public function searchEventx(Request $request,NormalizerInterface $Normalizer)
{
    $repository = $this->getDoctrine()->getRepository(Event::class);
    $requestString=$request->get('searchValue');
    $events = $repository->findEventByNsc($requestString);
    $jsonContent = $Normalizer->normalize($events, 'json',['groups'=>'
    events:read']);
    $retour=json_encode($jsonContent);
    return new Response($retour);
}
    /**
     * @Route("/{id}", name="event_delete", methods={"POST"})
     */
    public function delete(Request $request, Event $event): Response
    {
        if ($this->isCsrfTokenValid('delete'.$event->getId(), $request->request->get('_token'))) {
            $entityManager = $this->getDoctrine()->getManager();
            $entityManager->remove($event);
            $entityManager->flush();
        }

        return $this->redirect($request->server->get('HTTP_REFERER'));
    }


}
