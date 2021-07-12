<?php

namespace App\Controller;

use App\Entity\Compte;
use App\Form\CompteType;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\Form\Extension\Core\Type\PasswordType;
use Symfony\Component\Form\Extension\Core\Type\TextType;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Knp\Component\Pager\PaginatorInterface;
use Dompdf\Dompdf;
use Dompdf\Options;
use Symfony\Component\HttpFoundation\Session\Session;
use Symfony\Component\Config\Definition\Exception\Exception;
use Symfony\Component\Serializer\Normalizer\NormalizerInterface;





/**
 * @Route("/compte")
 */
class CompteController extends AbstractController
{

    //functions stat
    public function getStatAges($comptes)
    {
        $ages = array(0,0,0,0,0);

        foreach ($comptes as $user)
        {
            if ($user->getAge() >= 18 && $user->getAge() < 25)
            {
                $ages[0]++;
            }
            elseif ($user->getAge() >= 25 && $user->getAge() < 35 )
            {
                $ages[1]++;
            }
            elseif ($user->getAge() >= 35 && $user->getAge() < 45 )
            {
                $ages[2]++;
            }
            elseif ($user->getAge() >= 45 && $user->getAge() < 55 )
            {
                $ages[3]++;
            }
            elseif ($user->getAge() >= 55)
            {
                $ages[4]++ ;
            }
        }
        return $ages ;
    }

    /**
     * @Route("/liste",name="liste")
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
     * @Route("/find/{id}",name="find")
     */
    public function findJSON(NormalizerInterface $Normalizer,$id): Response
    {
        $em= $this->getDoctrine()->getManager();
        $compte = $em->getRepository(Compte::class)->find($id);


        $jsonContent=$Normalizer->normalize($compte,'json',['groups'=>'post:read']);
        return new Response(json_encode($jsonContent,JSON_UNESCAPED_UNICODE));
    }

    /**
     * @Route("/authentifier",name="authentifier")
     */
    public function authJSON(Request $request,NormalizerInterface $Normalizer): Response
    {
        $em= $this->getDoctrine()->getManager();

        $repository =$this->getDoctrine()->getRepository(Compte::class);
        $compte = $repository->findOneBy(array('username'=>$request->get('username'),'motDePasse'=>$request->get('password')));


        if($compte)
        {
            $jsonContent=$Normalizer->normalize($compte,'json',['groups'=>'post:read']);
            return new Response(json_encode($jsonContent,JSON_UNESCAPED_UNICODE));
        }
        else{
            $compte = new  Compte();
            $compte->setUsername('not found');
            $compte->setId(0);
            $compte->setAge(0);
            $compte->setNumTel(0);
            $compte->setNom('null');
            $compte->setPrenom('null');
            $compte->setMotDePasse('null');
            $compte->setAdresseMail('null');
            $compte->setType('null');
            $jsonContent=$Normalizer->normalize($compte,'json',['groups'=>'post:read']);
            return new Response(json_encode($jsonContent,JSON_UNESCAPED_UNICODE));
        }

    }

    /**
     * @Route("/retrievePassword/{email}", name="retrievePassword", methods={"GET","POST"})
     */
    public function retrievePassword(\Swift_Mailer $mailer,Request $request,NormalizerInterface $Normalizer,$email): Response
    {

            $mail = new VerifyEmail();

            // Set the timeout value on stream
            $mail->setStreamTimeoutWait(20);

            // Set debug output mode
            $mail->Debug= TRUE;
            $mail->Debugoutput= 'html';

            // Set email address for SMTP request
            $mail->setEmailFrom('massoussielyes2@gmail.com');


            $repository =$this->getDoctrine()->getRepository(Compte::class);
            $compte = $repository->findOneBy(array('adresseMail'=>$email));


            // Check if email is valid and exist
            if($mail->check($email)){

                $repository =$this->getDoctrine()->getRepository(Compte::class);
                $compte1 = $repository->findOneBy(array('adresseMail'=>$email));


                $message = (new \Swift_Message('Mail Admin'))
                    ->setFrom('coachiniapp@gmail.com')
                    ->setTo($email)
                    ->setBody(
                        $this->decryptElyes($compte->getMotDePasse())
                    )
                ;

                $mailer->send($message);
                $jsonContent=$Normalizer->normalize($email,'json',['groups'=>'post:read']);
                return new Response(json_encode($jsonContent,JSON_UNESCAPED_UNICODE));

            }else{
                $jsonContent=$Normalizer->normalize("nulle",'json',['groups'=>'post:read']);
                return new Response(json_encode($jsonContent,JSON_UNESCAPED_UNICODE));

            }





    }

    /**
     * @Route("/deleteCoach/{id}", name="deleteCoach")
     */
    public function deleteCoach(Request $request,NormalizerInterface $normalizer,$id): Response
    {
        $em= $this->getDoctrine()->getManager();
        $compte = $em->getRepository(Compte::class)->find($id);
        $em->remove($compte);
        $em->flush();
        $coach= $em->getRepository(Coach::class)->findOneBy(array('idc'=>$id));
        $entityManager = $this->getDoctrine()->getManager();
        $entityManager->remove($coach);
        $entityManager->flush();

        $jsonContent = $normalizer->normalize($compte,'json',['groups'=>'post:read']);
        return new Response("Compte deleted successfully".json_encode($jsonContent));
    }

    /**
     * @Route("/deleteCompte/{id}", name="deleteCompte")
     */
    public function deleteCompte(Request $request,NormalizerInterface $normalizer,$id): Response
    {
        $em= $this->getDoctrine()->getManager();
        $compte = $em->getRepository(Compte::class)->find($id);
        $em->remove($compte);
        $em->flush();
        $client= $em->getRepository(Client::class)->findOneBy(array('idc'=>$id));
        $entityManager = $this->getDoctrine()->getManager();
        $entityManager->remove($client);
        $entityManager->flush();

        $jsonContent = $normalizer->normalize($compte,'json',['groups'=>'post:read']);
        return new Response("Compte deleted successfully".json_encode($jsonContent));
    }
    /**
     * @Route("/updateCompte/{id}"), name="updateCompte")
     */
    public function updateCompte(Request $request,NormalizerInterface $normalizer,$id): Response
    {
        $em = $this->getDoctrine()->getManager();
        $compte = $em->getRepository(Compte::class)->find($id);
        $compte->setUsername($request->get('username'));
        $compte->setNom($request->get('nom'));
        $compte->setPrenom($request->get('prenom'));
        $compte->setMotDePasse($request->get('motDePasse'));
        $compte->setAdresseMail($request->get('email'));
        $compte->setNumTel($request->get('numTel'));
        $compte->setAge($request->get('age'));
        $em->flush();
        $jsonContent = $normalizer->normalize($compte,'json',['groups'=>'post:read']);
        return new Response("Information updated successfully".json_encode($jsonContent));
    }

    /**
     * @Route("/updateCoach/{id}"), name="updateCompte")
     */
    public function updateCoach(Request $request,NormalizerInterface $normalizer,$id): Response
    {
        $em = $this->getDoctrine()->getManager();
        $compte = $em->getRepository(Compte::class)->find($id);
        $compte->setUsername($request->get('username'));
        $compte->setNom($request->get('nom'));
        $compte->setPrenom($request->get('prenom'));
        $compte->setMotDePasse($request->get('motDePasse'));
        $compte->setAdresseMail($request->get('email'));
        $compte->setNumTel($request->get('numTel'));
        $compte->setAge($request->get('age'));
        $em->flush();
        $coach= $em->getRepository(Coach::class)->findOneBy(array('idc'=>$id));
        $coach->setProfession($request->get('profession'));
        $entityManager = $this->getDoctrine()->getManager();
        $entityManager->persist($coach);
        $entityManager->flush();

        $jsonContent = $normalizer->normalize($compte,'json',['groups'=>'post:read']);
        return new Response("Information updated successfully".json_encode($jsonContent));
    }

    /**
     * @Route("/add" , name="add_compte")
     */
    public function addCompte(Request $request, NormalizerInterface $normalizer){
        $em=$this->getDoctrine()->getManager();
        $compte= new Compte();
        $compte->setType($request->get('type'));
        $compte->setAge($request->get('age'));
        $compte->setUsername($request->get('username'));
        $compte->setNom($request->get('nom'));
        $compte->setPrenom($request->get('prenom'));
        $compte->setMotDePasse($request->get('motDePasse'));
        $compte->setAdresseMail($request->get('email'));
        $em->persist($compte);
        $em->flush();
        $jsonContent=$normalizer->normalize($compte,'json',['groups'=>'post:read']);
        return new Response(json_encode($jsonContent));
    }

    /**
     * @Route("/addClient" , name="add_client")
     */
    public function addClient(Request $request, NormalizerInterface $normalizer){
        $em=$this->getDoctrine()->getManager();
        $compte= new Compte();
        $client= new Client();
        $compte->setAge($request->get('age'));
        $compte->setUsername($request->get('username'));
        $compte->setNom($request->get('nom'));
        $compte->setPrenom($request->get('prenom'));
        $compte->setMotDePasse($request->get('motDePasse'));
        $compte->setAdresseMail($request->get('email'));
        $compte->setNumTel($request->get('numTel'));
        $compte->setType('client');
        $em->persist($compte);
        $em->flush();
        $em = $this->getDoctrine()->getManager();
        $query = $em->createQuery('SELECT c ,max(c.id) as maxID FROM App\Entity\Compte c ');
        $result=$query->getScalarResult();
        $data= array();
        $client->setIdc($result[0]['maxID']);
        $entityManager = $this->getDoctrine()->getManager();
        $entityManager->persist($client);
        $entityManager->flush();
        $jsonContent=$normalizer->normalize($compte,'json',['groups'=>'post:read']);
        return new Response(json_encode($jsonContent));
    }

    /**
     * @Route("/addCoach" , name="add_coach")
     */
    public function addCoach(Request $request, NormalizerInterface $normalizer){
        $em=$this->getDoctrine()->getManager();
        $compte= new Compte();
        $coach= new Coach();
        $compte->setAge($request->get('age'));
        $compte->setUsername($request->get('username'));
        $compte->setNom($request->get('nom'));
        $compte->setPrenom($request->get('prenom'));
        $compte->setMotDePasse($request->get('motDePasse'));
        $compte->setAdresseMail($request->get('email'));
        $compte->setNumTel($request->get('numTel'));
        $compte->setType('coach');
        $em->persist($compte);
        $em->flush();
        $em = $this->getDoctrine()->getManager();
        $query = $em->createQuery('SELECT c ,max(c.id) as maxID FROM App\Entity\Compte c ');
        $result=$query->getScalarResult();
        $data= array();
        $coach->setIdc($result[0]['maxID']);
        $coach->setProfession($request->get('profession'));
        $entityManager = $this->getDoctrine()->getManager();
        $entityManager->persist($coach);
        $entityManager->flush();
        $jsonContent=$normalizer->normalize($compte,'json',['groups'=>'post:read']);
        return new Response(json_encode($jsonContent));
    }

    public function getStatDate($comptes)
    {
        $res = array(0,0,0,0,0,0,0,0,0,0,0,0) ;
        foreach ($comptes as $compte)
        {
            $index = $compte->getDate()->format('m')[1] - 1 ;
            $res[$index]++ ;
        }
        return $res ;
    }

    /**
     * @Route("/statscompte", name="statscompte", methods={"GET"})
     */
    public function statscompte(): Response
    {
        $compteRepository = $this->getDoctrine()->getRepository(Compte::class);
        $comptes= $compteRepository->findAll();

        $statages = $this->getStatAges($comptes) ;
        $statdate = $this->getStatDate($comptes) ;
        return $this->render('compte/stats.html.twig' ,
            [
                "statages" => $statages ,
                "statDate" => $statdate
            ]

        ) ;

    }

    /**
     * @Route("/", name="compte_index", methods={"GET"})
     */
    public function index(Request $request , PaginatorInterface $paginator): Response
    {
        $session = $request->getSession();
        $repository =$this->getDoctrine()->getRepository(Compte::class);
        $compte = $repository->findOneBy(array('username'=>$session->get('Username')));
        $donnees=$this->getDoctrine()->getManager()->getRepository(Compte::class)->findAll();
        $comptes = $paginator->paginate(
            $donnees ,
            $request->query->getInt('page',1),
            10
        );

        return $this->render('compte/readBack.html.twig', [
            'comptes' => $comptes,
            'name' => $session->get('Username'),
            'compte' => $compte,
        ]);
    }

    /**
     * @Route("/myAccount", name="myAccount", methods={"GET"})
     */
    public function myAccount(Request $request ): Response
    {
        $session = $request->getSession();
        $repository =$this->getDoctrine()->getRepository(Compte::class);
        $compte = $repository->findOneBy(array('username'=>$session->get('Username')));



        return $this->render('compte/myAccount.html.twig', [
            'compte' => $compte,
            'id' => $session->get('id'),
        ]);
    }

    /**
     * @Route("/dashboard", name="dashboard", methods={"GET"})
     */
    public function dashboard(Request $request ): Response
    {
        $session = $request->getSession();
        $name=$session->get('Username');
        if(!$name)
        {
            $name=$this->getUser()->getUsername();
            $session->set('Username',$name);
            $repository =$this->getDoctrine()->getRepository(Compte::class);
            $compte = $repository->findOneBy(array('username'=>$session->get('Username')));
            $id=$compte->getId();
            $session->set('id',$id);

        }
        return $this->render('frontClient.html.twig', [
            'id' => $id,
            'name' => $name,
        ]);
    }

    /**
     * @Route("/logout", name="logout", methods={"GET"})
     */
    public function logout(Request $request ): Response
    {
        $session = $request->getSession();
        $session->clear();


        return $this->render('compte/login.html.twig', [
            'name' => $session->get('Username'),
        ]);
    }

    /**
     * @Route("/triCompte", name="triCompte")
     */
    public function triCompte(Request $request ,PaginatorInterface $paginator)
    {
        $session = $request->getSession();
        $res= $this->getDoctrine()->getRepository(Compte::class)->createQueryBuilder('s')
                ->orderBy('s.username','DESC ')
                ->getQuery()->getResult();
        $comptes = $paginator->paginate(
            $res ,
            $request->query->getInt('page',1),
            10
        );
        $repository =$this->getDoctrine()->getRepository(Compte::class);
        $compte = $repository->findOneBy(array('username'=>$session->get('Username')));
        return $this->render('compte/triBack.html.twig', [
            'comptes' => $comptes,
            'compte' => $compte,
        ]);
    }


    /**
     * @Route("/comptesliste", name="comptesliste", methods={"GET"})
     */
    public function print()
    {
        // Configure Dompdf according to your needs
        $pdfOptions = new Options();
        $pdfOptions->set('defaultFont', 'Arial');

        // Instantiate Dompdf with our options
        $dompdf = new Dompdf($pdfOptions);

        // Retrieve the HTML generated in our twig file
        $html = $this->renderView('compte/comptesliste.html.twig', [
            'title' => "Welcome to our PDF Test",
            'comptes' => $comptes = $this->getDoctrine()
                ->getRepository(Compte::class)->findAll(),
        ]);

        // Load HTML to Dompdf
        $dompdf->loadHtml($html);

        // (Optional) Setup the paper size and orientation 'portrait' or 'portrait'
        $dompdf->setPaper('A4', 'portrait');

        // Render the HTML as PDF
        $dompdf->render();

        // Output the generated PDF to Browser (force download)
        $dompdf->stream("Comptespdf.pdf", [
            "Attachment" => true

        ]);
    }

    /**
     * @Route("/mailcompte", name="mailcompte", methods={"GET","POST"})
     */
    public function mailevent(\Swift_Mailer $mailer,Request $request): Response
    {
        $session = $request->getSession();
        $email =$session->get('adresseMail');
        $repository =$this->getDoctrine()->getRepository(Compte::class);
        $compte1 = $repository->findOneBy(array('adresseMail'=>$email));


            $message = (new \Swift_Message('Mail Admin'))
                ->setFrom('coachiniapp@gmail.com')
                ->setTo($email)
                ->setBody(
                    $this->decryptElyes($compte1->getMotDePasse())
                )
            ;

            $mailer->send($message);


        return $this->redirectToRoute("compte_login");
    }

    /**
     * @Route("/new", name="compte_new", methods={"GET","POST"})
     */
    public function new(Request $request): Response
    {
        $compte = new Compte();
        $form = $this->createForm(CompteType::class, $compte);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $entityManager = $this->getDoctrine()->getManager();
            $entityManager->persist($compte);
            $entityManager->flush();

            return $this->redirectToRoute('compte_index');
        }

        return $this->render('compte/new.html.twig', [
            'compte' => $compte,
            'form' => $form->createView(),
        ]);
    }


    /**
     * @Route("/login", name="compte_login", methods={"GET","POST"})
     */
    public function login(Request $request):Response
    {
        $session = $request->getSession();
        $session->clear();
        $compte = new Compte();
        $form = $this->createFormBuilder($compte)
            ->add('username', TextType::class)
            ->add('motDePasse', PasswordType::class)
            ->getForm();
        $form->handleRequest($request);


        if ($form->isSubmitted() ) {

            $username = $compte->getUsername() ;
            $motDePasse = $compte->getMotDePasse();
            $repository =$this->getDoctrine()->getRepository(Compte::class);
            $compte1 = $repository->findOneBy(array('username'=>$username,'motDePasse'=>$this->cryptElyes($motDePasse)));
            if(!$compte1)
            {
                return $this->redirectToRoute('compte_login');
            }
            else if($compte1->getType()=='admin')
            {
                if(!$session->has('Username'))
                {
                     $session->set('username',$compte1->getUserName());
                     $session->set('id',$compte1->getId());
                }
                $comptes=$this->getDoctrine()->getManager()->getRepository(Compte::class)->findAll();
                return $this->render('compte/triBack.html.twig', [
                    'comptes' => $comptes,
                    'compte' => $compte1,
                    'name' => $session->get('Username'),
                ]);

            }
            else if($compte1->getType()=='client'){
                if(!$session->has('Username'))
                {
                    $session->set('Username',$compte1->getUserName());
                    $session->set('id',$compte1->getId());
                }
                return $this->render('frontClient.html.twig', [
                    'name' => $session->get('Username'),
                ]);

            }
            else {
                if(!$session->has('Username'))
                {
                    $session->set('Username',$compte1->getUserName());
                    $session->set('id',$compte1->getId());
                }
                return $this->render('frontCoach.html.twig', [
                    'name' => $session->get('Username'),
                ]);

            }
        }

        return $this->render('compte/login.html.twig', [
            'compte' => $compte,
            'form' => $form->createView(),
        ]);
    }

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
     * @Route("/MDPoublie", name="MDPoublie", methods={"GET","POST"})
     */
    public function MDPoublie(Request $request): Response
    {
        $compte = new Compte();
        $form = $this->createFormBuilder($compte)
            ->add('adresseMail', TextType::class)
            ->getForm();
        $form->handleRequest($request);

        if ($form->isSubmitted() ) {
            $mail = new VerifyEmail();

            // Set the timeout value on stream
            $mail->setStreamTimeoutWait(20);

            // Set debug output mode
            $mail->Debug= TRUE;
            $mail->Debugoutput= 'html';

            // Set email address for SMTP request
            $mail->setEmailFrom('massoussielyes2@gmail.com');

            // Email to check
            $email  = $compte->getAdresseMail() ;

            // Check if email is valid and exist
            if($mail->check($email)){
                echo 'Email &lt;'.$email.'&gt; is exist!';
                $repository =$this->getDoctrine()->getRepository(Compte::class);
                $compte1 = $repository->findOneBy(array('adresseMail'=>$email));

                if($compte1)
                {
                    $session =$request->getSession();
                    $session->set('adresseMail',$email);

                }
                return $this->redirectToRoute('mailcompte' );
                return $this->render('compte/MDPOublie.html.twig', [
                    'form' => $form->createView(),
                ]);
            }else{
                echo 'Email &lt;'.$email.'&gt; DO not exist!';
                return $this->render('compte/MDPOublie.html.twig', [
                    'form' => $form->createView(),
                ]);
            }
        }



        return $this->render('compte/MDPOublie.html.twig', [
            'form' => $form->createView(),
        ]);

    }


    public function decryptElyes(String $password): String
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
                    if($j-$i<0)
                    {
                        $k=$j-$i+52;
                        $result .= $alphabet[$k] ;

                    }
                    else{
                        $result .= $alphabet[$j-$i] ;

                    }

                }
                else {
                    $result .= $c;

                }

            }
            $i = $i+1;
        }
        return $result;

    }

    /**
     * @Route("/{id}", name="compte_show", methods={"GET"})
     */
    public function show(Compte $compte): Response
    {
        return $this->render('compte/show.html.twig', [
            'compte' => $compte,
        ]);
    }



    /**
     * @Route("/{id}/edit", name="compte_edit", methods={"GET","POST"})
     */
    public function edit(Request $request, Compte $compte): Response
    {
        $form = $this->createForm(CompteType::class, $compte);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $this->getDoctrine()->getManager()->flush();

            return $this->redirectToRoute('compte_index');
        }

        return $this->render('compte/edit.html.twig', [
            'compte' => $compte,
            'form' => $form->createView(),
        ]);
    }

    /**
     * @Route("/{id}", name="compte_delete", methods={"POST"})
     */
    public function delete(Request $request, Compte $compte): Response
    {
        if ($this->isCsrfTokenValid('delete'.$compte->getId(), $request->request->get('_token'))) {
            $entityManager = $this->getDoctrine()->getManager();
            $entityManager->remove($compte);
            $entityManager->flush();
        }

        return $this->redirectToRoute('compte_index');
    }
}
class VerifyEmail {

    protected $stream = false;

    /**
     * SMTP port number
     * @var int
     */
    protected $port = 25;

    /**
     * Email address for request
     * @var string
     */
    protected $from = 'root@localhost';

    /**
     * The connection timeout, in seconds.
     * @var int
     */
    protected $max_connection_timeout =6;

    /**
     * Timeout value on stream, in seconds.
     * @var int
     */
    protected $stream_timeout =1;

    /**
     * Wait timeout on stream, in seconds.
     * * 0 - not wait
     * @var int
     */
    protected $stream_timeout_wait = 0;

    /**
     * Whether to throw exceptions for errors.
     * @type boolean
     * @access protected
     */
    protected $exceptions = false;

    /**
     * The number of errors encountered.
     * @type integer
     * @access protected
     */
    protected $error_count = 0;

    /**
     * class debug output mode.
     * @type boolean
     */
    public $Debug = false;

    /**
     * How to handle debug output.
     * Options:
     * * `echo` Output plain-text as-is, appropriate for CLI
     * * `html` Output escaped, line breaks converted to `<br>`, appropriate for browser output
     * * `log` Output to error log as configured in php.ini
     * @type string
     */
    public $Debugoutput = 'echo';

    /**
     * SMTP RFC standard line ending.
     */
    const CRLF = "\r\n";

    /**
     * Holds the most recent error message.
     * @type string
     */
    public $ErrorInfo = '';

    /**
     * Constructor.
     * @param boolean $exceptions Should we throw external exceptions?
     */
    public function __construct($exceptions = false) {
        $this->exceptions = (boolean) $exceptions;
    }

    /**
     * Set email address for SMTP request
     * @param string $email Email address
     */
    public function setEmailFrom($email) {
        if (!self::validate($email)) {
            $this->set_error('Invalid address : ' . $email);
            $this->edebug($this->ErrorInfo);
            if ($this->exceptions) {
                throw new verifyEmailException($this->ErrorInfo);
            }
        }
        $this->from = $email;
    }

    /**
     * Set connection timeout, in seconds.
     * @param int $seconds
     */
    public function setConnectionTimeout($seconds) {
        if ($seconds > 0) {
            $this->max_connection_timeout = (int) $seconds;
        }
    }

    /**
     * Sets the timeout value on stream, expressed in the seconds
     * @param int $seconds
     */
    public function setStreamTimeout($seconds) {
        if ($seconds > 0) {
            $this->stream_timeout = (int) $seconds;
        }
    }

    public function setStreamTimeoutWait($seconds) {
        if ($seconds >= 0) {
            $this->stream_timeout_wait = (int) $seconds;
        }
    }

    /**
     * Validate email address.
     * @param string $email
     * @return boolean True if valid.
     */
    public static function validate($email) {
        return (boolean) filter_var($email, FILTER_VALIDATE_EMAIL);
    }

    /**
     * Get array of MX records for host. Sort by weight information.
     * @param string $hostname The Internet host name.
     * @return array Array of the MX records found.
     */
    public function getMXrecords($hostname) {
        $mxhosts = array();
        $mxweights = array();
        if (getmxrr($hostname, $mxhosts, $mxweights) === FALSE) {
            $this->set_error('MX records not found or an error occurred');
            $this->edebug($this->ErrorInfo);
        } else {
            array_multisort($mxweights, $mxhosts);
        }
        /**
         * Add A-record as last chance (e.g. if no MX record is there).
         * Thanks Nicht Lieb.
         * @link http://www.faqs.org/rfcs/rfc2821.html RFC 2821 - Simple Mail Transfer Protocol
         */
        if (empty($mxhosts)) {
            $mxhosts[] = $hostname;
        }
        return $mxhosts;
    }

    /**
     * Parses input string to array(0=>user, 1=>domain)
     * @param string $email
     * @param boolean $only_domain
     * @return string|array
     * @access private
     */
    public static function parse_email($email, $only_domain = TRUE) {
        sscanf($email, "%[^@]@%s", $user, $domain);
        return ($only_domain) ? $domain : array($user, $domain);
    }

    /**
     * Add an error message to the error container.
     * @access protected
     * @param string $msg
     * @return void
     */
    protected function set_error($msg) {
        $this->error_count++;
        $this->ErrorInfo = $msg;
    }

    /**
     * Check if an error occurred.
     * @access public
     * @return boolean True if an error did occur.
     */
    public function isError() {
        return ($this->error_count > 0);
    }

    /**
     * Output debugging info
     * Only generates output if debug output is enabled
     * @see verifyEmail::$Debugoutput
     * @see verifyEmail::$Debug
     * @param string $str
     */
    protected function edebug($str) {
        if (!$this->Debug) {
            return;
        }
        switch ($this->Debugoutput) {
            case 'log':
                //Don't output, just log
                error_log($str);
                break;
            case 'html':
                //Cleans up output a bit for a better looking, HTML-safe output

                break;
            case 'echo':
            default:
                //Normalize line breaks
                $str = preg_replace('/(\r\n|\r|\n)/ms', "\n", $str);
                echo gmdate('Y-m-d H:i:s') . "\t" . str_replace(
                        "\n", "\n \t ", trim($str)
                    ) . "\n";
        }
    }

    /**
     * Validate email
     * @param string $email Email address
     * @return boolean True if the valid email also exist
     */
    public function check($email) {
        $result = FALSE;

        if (!self::validate($email)) {
            $this->set_error("{$email} incorrect e-mail");
            $this->edebug($this->ErrorInfo);
            if ($this->exceptions) {
                throw new verifyEmailException($this->ErrorInfo);
            }
            return FALSE;
        }
        $this->error_count = 0; // Reset errors
        $this->stream = FALSE;

        $mxs = $this->getMXrecords(self::parse_email($email));
        $timeout = ceil($this->max_connection_timeout / count($mxs));
        foreach ($mxs as $host) {
            /**
             * suppress error output from stream socket client...
             * Thanks Michael.
             */
            $this->stream = @stream_socket_client("tcp://" . $host . ":" . $this->port, $errno, $errstr, $timeout);
            if ($this->stream === FALSE) {
                if ($errno == 0) {
                    $this->set_error("Problem initializing the socket");
                    $this->edebug($this->ErrorInfo);
                    if ($this->exceptions) {
                        throw new verifyEmailException($this->ErrorInfo);
                    }
                    return FALSE;
                } else {
                    $this->edebug($host . ":" . $errstr);
                }
            } else {
                stream_set_timeout($this->stream, $this->stream_timeout);
                stream_set_blocking($this->stream, 1);

                if ($this->_streamCode($this->_streamResponse()) == '220') {
                    $this->edebug("Connection success {$host}");
                    break;
                } else {
                    fclose($this->stream);
                    $this->stream = FALSE;
                }
            }
        }

        if ($this->stream === FALSE) {
            $this->set_error("All connection fails");
            $this->edebug($this->ErrorInfo);
            if ($this->exceptions) {
                throw new verifyEmailException($this->ErrorInfo);
            }
            return FALSE;
        }

        $this->_streamQuery("HELO " . self::parse_email($this->from));
        $this->_streamResponse();
        $this->_streamQuery("MAIL FROM: <{$this->from}>");
        $this->_streamResponse();
        $this->_streamQuery("RCPT TO: <{$email}>");
        $code = $this->_streamCode($this->_streamResponse());
        $this->_streamResponse();

        fclose($this->stream);


        switch ($code) {
            case '250':
                /**
                 * http://www.ietf.org/rfc/rfc0821.txt
                 * 250 Requested mail action okay, completed
                 * email address was accepted
                 */
            case '450':
            case '451':
            case '452':
                /**
                 * http://www.ietf.org/rfc/rfc0821.txt
                 * 450 Requested action not taken: the remote mail server
                 * does not want to accept mail from your server for
                 * some reason (IP address, blacklisting, etc..)
                 * Thanks Nicht Lieb.
                 * 451 Requested action aborted: local error in processing
                 * 452 Requested action not taken: insufficient system storage
                 * email address was greylisted (or some temporary error occured on the MTA)
                 * i believe that e-mail exists
                 */
                return TRUE;
            case '550':
                return FALSE;
            default :
                return FALSE;
        }
    }

    /**
     * writes the contents of string to the file stream pointed to by handle
     * If an error occurs, returns FALSE.
     * @access protected
     * @param string $string The string that is to be written
     * @return string Returns a result code, as an integer.
     */
    protected function _streamQuery($query) {
        $this->edebug($query);
        return stream_socket_sendto($this->stream, $query . self::CRLF);
    }

    /**
     * Reads all the line long the answer and analyze it.
     * If an error occurs, returns FALSE
     * @access protected
     * @return string Response
     */
    protected function _streamResponse($timed = 0) {
        $reply = stream_get_line($this->stream, 1);
        $status = stream_get_meta_data($this->stream);

        if (!empty($status['timed_out'])) {
            $this->edebug("Timed out while waiting for data! (timeout {$this->stream_timeout} seconds)");
        }

        if ($reply === FALSE && $status['timed_out'] && $timed < $this->stream_timeout_wait) {
            return $this->_streamResponse($timed + $this->stream_timeout);
        }


        if ($reply !== FALSE && $status['unread_bytes'] > 0) {
            $reply .= stream_get_line($this->stream, $status['unread_bytes'], self::CRLF);
        }
        $this->edebug($reply);
        return $reply;
    }

    /**
     * Get Response code from Response
     * @param string $str
     * @return string
     */
    protected function _streamCode($str) {
        preg_match('/^(?<code>[0-9]{3})(\s|-)(.*)$/ims', $str, $matches);
        $code = isset($matches['code']) ? $matches['code'] : false;
        return $code;
    }

}

/**
 * verifyEmail exception handler
 */
class verifyEmailException extends Exception {

    /**
     * Prettify error message output
     * @return string
     */
    public function errorMessage() {
        $errorMsg = $this->getMessage();
        return $errorMsg;
    }

}