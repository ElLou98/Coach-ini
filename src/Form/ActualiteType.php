<?php

namespace App\Form;

use App\Entity\Actualite;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\Extension\Core\Type\FileType;
use Symfony\Component\Form\Extension\Core\Type\TextType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;
use Symfony\Component\Validator\Constraints\File;


class ActualiteType extends AbstractType
{
    public function buildForm(FormBuilderInterface $builder, array $options)
    {
        $builder
            ->add('statut',TextType::class,array(
                'attr'=>['class' => 'form-control']
            ))
            ->add('image',FileType::class, array(
                'attr'=>['class' => 'form-control'],
                'data_class' => null,
                'required' => false,
                'constraints' => [
                    new File([
                        'maxSize' => '2048k',
                        'mimeTypes' => [
                            'image/png',
                            'image/jpeg',
                            'image/jpg',
                            'image/gif',
                        ],
                        'mimeTypesMessage' => 'Please upload a valid image document',
                    ])
                ]))
            ->add('fichier',FileType::class, array(
                'attr'=>['class' => 'form-control'],
                'data_class' => null,
                'required' => false,
                'constraints' => [
                    new File([
                        'maxSize' => '2048k',
                        'mimeTypes' => [
                                "application/pdf",
                                 "application/x-pdf"
                        ],
                        'mimeTypesMessage' => 'Please upload a valid PDF document',
                    ])
                ]))
            ->add('bio',TextType::class,array(
                'attr'=>['class' => 'form-control'])
            )
            ->add('competence',TextType::class,array(
                'attr'=>['class' => 'form-control']))
        ;
    }

    public function configureOptions(OptionsResolver $resolver)
    {
        $resolver->setDefaults([
            'data_class' => Actualite::class,
        ]);
    }
}
