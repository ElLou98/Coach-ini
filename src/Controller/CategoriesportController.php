<?php

namespace App\Controller;

use App\Entity\Categoriesport;
use App\Form\CategoriesportType;
use App\Entity\CategoriesortRepository;
use MercurySeries\FlashyBundle\FlashyNotifier;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\File\Exception\FileException;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Dompdf\Dompdf;
use Dompdf\Options;
use Knp\Component\Pager\PaginatorInterface;
use Symfony\Component\Serializer\Normalizer\NormalizerInterface;

/**
 * @Route("/backhome/categoriesport")
 */
class CategoriesportController extends AbstractController
{
    /**
     * @Route("/", name="categoriesport_index", methods={"GET"})
     */
    public function index(CategoriesortRepository $categoriesortRepository,Request $request, PaginatorInterface $paginator)

    {
        $donnees = $this->getDoctrine()->getRepository(Categoriesport::class)->findAll();

        $categoriesport = $paginator->paginate(
            $donnees, // Requête contenant les données à paginer (ici nos articles)
            $request->query->getInt('page', 1), // Numéro de la page en cours, passé dans l'URL, 1 si aucune page
            3 // Nombre de résultats par page
        );

        return $this->render('categoriesport/index.html.twig', [
            'categoriesports' => $categoriesport,
        ]);

    }

    /**
     * @Route("/getAllJSON", name="getAllJSON", methods={"GET"})
     */
    public function getAllJSON(NormalizerInterface $Normalizer)

    {
        $categorie = $this->getDoctrine()
            ->getRepository(Categoriesport::class)->findAll();
        $jsonContent=$Normalizer->normalize($categorie,'json',['groups'=>'post:read']);
        /*return $this->render('reclamation/getReclamationsJSON.html.twig', [
            'data' => $jsonContent,
        ]);*/
        return new Response(json_encode($jsonContent,JSON_UNESCAPED_UNICODE));
    }



    /**
     * @Route("/listcategorie", name="listcategorie", methods={"GET"})
     */
    public function listc(CategoriesortRepository $categoriesortRepository)
    {
        // Configure Dompdf according to your needs
        $pdfOptions = new Options();
        $pdfOptions->set('defaultFont', 'Arial');

        // Instantiate Dompdf with our options
        $dompdf = new Dompdf($pdfOptions);
        $categoriesport=$categoriesortRepository->findAll();

        // Retrieve the HTML generated in our twig file
        $html = $this->renderView('categoriesport/listCategorie.html.twig', [
            'categoriesports' => $categoriesport,
        ]);

        // Load HTML to Dompdf
        $dompdf->loadHtml($html);

        // (Optional) Setup the paper size and orientation 'portrait' or 'portrait'
        $dompdf->setPaper('A4', 'portrait');

        // Render the HTML as PDF
        $dompdf->render();

        // Output the generated PDF to Browser (inline view)
        $dompdf->stream("mypdf.pdf", [
            "Attachment" => false
        ]);
    }



    /**
     * @Route("/new", name="categoriesport_new", methods={"GET","POST"})
     */
    public function new(Request $request,FlashyNotifier $flashy)
    {
        $categoriesport = new Categoriesport();
        $form = $this->createForm(CategoriesportType::class, $categoriesport);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $entityManager = $this->getDoctrine()->getManager();

            $file = $form->get('photo')->getData();



            $fileName=md5(uniqid()).'.'.$file->guessExtension();
            try{
                $file->move(
                    $this->getParameter('images_directory'),
                    $fileName
                );
            }catch (FileException $e){

            }

                $categoriesport->setPhoto($fileName);


            $entityManager->persist($categoriesport);
            $entityManager->flush();
            $flashy->success('categorie created!');

            return $this->redirectToRoute('categoriesport_index');
        }
        return $this->render('categoriesport/new.html.twig', [
            'categoriesport' => $categoriesport,
            'form' => $form->createView(),
        ]);
    }


    /**
     * @Route("/newCategorieJSON", name="newCategorieJSON", methods={"GET","POST"})
     *
     * @throws \Symfony\Component\Serializer\Exception\ExceptionInterface
     */
    public function newCategorieJSON(Request $request,NormalizerInterface $Normalizer)
    {

        $categoriesport = new Categoriesport();
        $categoriesport->setNom($request->get('nom'));
        $categoriesport->setDescription($request->get('description'));
        $categoriesport->setPhoto($request->get('photo'));


        $entityManager = $this->getDoctrine()->getManager();
        $entityManager->persist($categoriesport);
        $entityManager->flush();

        $jsonContent=$Normalizer->normalize($categoriesport,'json',['groups'=>'post:read']);
        return new Response("Categorie added successfully".json_encode($jsonContent,JSON_UNESCAPED_UNICODE));
    }

    /**
     * @Route("/{id}", name="categoriesport_show", methods={"GET"})
     */
    public function show(Categoriesport $categoriesport)
    {
        return $this->render('categoriesport/show.html.twig', [
            'categoriesport' => $categoriesport,
        ]);
    }

    /**
     * @Route("/{id}/edit", name="categoriesport_edit", methods={"GET","POST"})
     */
    public function edit(Request $request, Categoriesport $categoriesport,FlashyNotifier $flashy)
    {
        $form = $this->createForm(CategoriesportType::class, $categoriesport);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $this->getDoctrine()->getManager()->flush();
            $file = $form->get('photo')->getData();



            $fileName=md5(uniqid()).'.'.$file->guessExtension();
            try{
                $file->move(
                    $this->getParameter('images_directory'),
                    $fileName
                );
            }catch (FileException $e){

            }

            $categoriesport->setPhoto($fileName);

            $flashy->success('Categorie modifié!');
            return $this->redirectToRoute('categoriesport_index');
        }

        return $this->render('categoriesport/edit.html.twig', [
            'categoriesport' => $categoriesport,
            'form' => $form->createView(),
        ]);
    }


    /**
     * @Route("/editCatgorieJSON/{id}", name="editCatgorieJSON", methods={"GET","POST"})
     */

    public function editCatgorieJSON(Request $request, Categoriesport $categoriesport,NormalizerInterface $Normalizer,$id)
    {

        $entityManager=$this->getDoctrine()->getManager();
        $Categoriesport=$entityManager->getRepository(Categoriesport::class)->find($id);
        $categoriesport->setNom($request->get('nom'));
        $categoriesport->setDescription($request->get('description'));
        $categoriesport->setPhoto($request->get('photo'));
        $entityManager->flush();


        $jsonContent=$Normalizer->normalize($categoriesport,'json',['groups'=>'post:read']);
        return new Response("Categorie updated successfully".json_encode($jsonContent,JSON_UNESCAPED_UNICODE));

    }




    /**
     * @Route("/{id}", name="categoriesport_delete", methods={"POST"})
     */
    public function delete(Request $request, Categoriesport $categoriesport,FlashyNotifier $flashy)
    {
        if ($this->isCsrfTokenValid('delete'.$categoriesport->getId(), $request->request->get('_token'))) {
            $entityManager = $this->getDoctrine()->getManager();
            $entityManager->remove($categoriesport);
            $entityManager->flush();
            $flashy->primary('Categorie supprimé!', 'http://your-awesome-link.com');

        }

        return $this->redirectToRoute('categoriesport_index');
    }

    /**
     * @Route("/DeleteCategorieJson/{id}", name="DeleteCategorieJson", methods={"POST"})
     * @throws \Symfony\Component\Serializer\Exception\ExceptionInterface
     */
    public function DeleteCategorieJson(Request $request, Categoriesport $categoriesport,$id,NormalizerInterface $Normalizer)
    {
            $entityManager = $this->getDoctrine()->getManager();
            $categoriesport=$entityManager->getRepository(Categoriesport::class)->find($id);
            $entityManager->remove($categoriesport);
            $entityManager->flush();
            $jsonContent=$Normalizer->normalize($categoriesport,'json',['groups'=>'post:read']);
            return new Response("Categorie deleted successfully".json_encode($jsonContent,JSON_UNESCAPED_UNICODE));
    }

}
