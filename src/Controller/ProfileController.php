<?php

namespace App\Controller;

use App\Entity\Compte;
use App\Entity\Profile;
use App\Form\ProfileType;
use App\Repository\ProfileRepository;
use App\Services\UploadImage;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use MercurySeries\FlashyBundle\FlashyNotifier;
use Knp\Component\Pager\PaginatorInterface;
use Dompdf\Dompdf;
use Dompdf\Options;



/**
 * @Route("/profile")
 */
class ProfileController extends AbstractController
{
    /**
     * @Route("/listprofil/{id}", name="listprofil", methods={"GET"})
     */
    public function print(ProfileRepository $profileRepository,int $id)
    {
        // Configure Dompdf according to your needs
        $pdfOptions = new Options();
        $pdfOptions->set('defaultFont', 'Arial');
        $pdfOptions->set('isRemoteEnabled', true);
        $pdfOptions->setIsRemoteEnabled(true);
        // Instantiate Dompdf with our options
        $dompdf = new Dompdf($pdfOptions);

        $profile=$profileRepository->findBy(["id"=>$id]);

        // Retrieve the HTML generated in our twig file
        $html = $this->renderView('profile/listprofil.html.twig', [
            'profiles' => $profile,
        ]);

        // Load HTML to Dompdf
        $dompdf->loadHtml($html);

        // (Optional) Setup the paper size and orientation 'portrait' or 'portrait'
        $dompdf->setPaper('A4', 'portrait');

        // Render the HTML as PDF
        $dompdf->render();

        // Output the generated PDF to Browser (force download)
        $dompdf->stream("profilespdf.pdf", [
            "Attachment" => true
        ]);
    }

    /**
     * @Route("/", name="profile_index", methods={"GET"})
     */
    public function index(ProfileRepository $profileRepository,Request $request): Response
    {
        $session = $request->getSession();
        $profil = $this->getDoctrine()
            ->getRepository(Profile::class)
            ->findProfileByIdCompte($session->get('id'));


        return $this->render('profile/index.html.twig', [
            'profiles' => $profil,
        ]);
    }
    /**
     * @Route("/showBack", name="profile_back", methods={"GET"})
     */
    public function showBack(ProfileRepository $profileRepository): Response
    {
        return $this->render('profile/showBack.html.twig', [
            'profiles' => $profileRepository->findAll(),
        ]);
    }


    /**
     * @Route("/new", name="profile_new", methods={"GET","POST"})
     */
    public function new(Request $request,FlashyNotifier $flashy): Response
    {
        $profile = new Profile();
        $form = $this->createForm(ProfileType::class, $profile);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $entityManager = $this->getDoctrine()->getManager();
            $file = $profile->getPhoto();

            if($file) {
                $originalFilename = pathinfo($file->getClientOriginalName(), PATHINFO_FILENAME);
                $fileName = $originalFilename."-".uniqid().'.'.$file->guessExtension();

                $file->move(
                    $this->getParameter('image_directory'),
                    $fileName
                );

                $profile->setPhoto($fileName);

            }
            $session = $request->getSession();
            $profile->setIdCompte($session->get('id'));
            // Add Id Of current User

           /* $userConnected = $this->getUser();
            $userConnectedId = $userConnected->getId();
            $profile->setIdCompte($userConnectedId);*/

            $entityManager->persist($profile);
            $entityManager->flush();
            $flashy->success('profile created!');

            return $this->redirectToRoute('profile_index');
        }

        return $this->render('profile/new.html.twig', [
            'profile' => $profile,
            'form' => $form->createView(),
        ]);
    }

    /**
     * @Route("/{id}", name="profile_show", methods={"GET"})
     */
    public function show(Profile $profile): Response
    {
        return $this->render('profile/show.html.twig', [
            'profile' => $profile,
        ]);
    }

    /**
     * @Route("/{id}/edit", name="profile_edit", methods={"GET","POST"})
     */
    public function edit(Request $request, Profile $profile): Response
    {
        $form = $this->createForm(ProfileType::class, $profile);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $file = $profile->getPhoto();

            if($file) {
                $originalFilename = pathinfo($file->getClientOriginalName(), PATHINFO_FILENAME);
                $fileName = $originalFilename."-".uniqid().'.'.$file->guessExtension();

                $file->move(
                    $this->getParameter('image_directory'),
                    $fileName
                );

                $profile->setPhoto($fileName);

            }
            $profile->setIdCompte(000);
            $this->getDoctrine()->getManager()->flush();

            return $this->redirectToRoute('profile_index');
        }

        return $this->render('profile/edit.html.twig', [
            'profile' => $profile,
            'form' => $form->createView(),
        ]);
    }

    /**
     * @Route("/{id}", name="profile_delete", methods={"POST"})
     */
    public function delete(Request $request, Profile $profile,FlashyNotifier $flashy): Response
    {
        if ($this->isCsrfTokenValid('delete'.$profile->getId(), $request->request->get('_token'))) {
            $entityManager = $this->getDoctrine()->getManager();
            $entityManager->remove($profile);
            $entityManager->flush();
            $flashy->success('Profile supprime!');

        }

        return $this->redirectToRoute('profile_index');
    }

}
