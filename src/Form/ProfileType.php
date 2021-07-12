<?php

namespace App\Form;

use App\Entity\Profile;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\Extension\Core\Type\ChoiceType;
use Symfony\Component\Form\Extension\Core\Type\FileType;
use Symfony\Component\Form\Extension\Core\Type\NumberType;
use Symfony\Component\Form\Extension\Core\Type\RangeType;
use Symfony\Component\Form\Extension\Core\Type\TextType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;
use Symfony\Component\Validator\Constraints\File;

class ProfileType extends AbstractType
{
    public function buildForm(FormBuilderInterface $builder, array $options)
    {
        $builder
            ->add('nom',TextType::class,array(
                'attr'=>['class' => 'form-control',]
            ))
            ->add('photo',FileType::class, array(
                'attr'=>['class' => 'form-control'],
                'data_class' => null,
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
            ->add('description',TextType::class,array(
                'attr'=>['class' => 'form-control']
            ))
            ->add('rating',RangeType::class,array(
                'attr'=>[
                    'class' => 'form-control',
                    'min'   => 1,
                    'max'   => 5
                ]
            ))
            ->add('categorie',ChoiceType::class,array(
                'attr'=>['class' => 'form-control'],
                'choices'  => [
                    'Football' => 'Football',
                    'Tenis' => 'Tenis',
                    'Musculation' => 'Musculation',
                    'Basketball ' => 'Basketball',
                    'Athlétisme' => 'Athlétisme',
                ],
            ))
            ->add('detail',TextType::class,array(
                'attr'=>['class' => 'form-control']
            ))


        ;
    }

    public function configureOptions(OptionsResolver $resolver)
    {
        $resolver->setDefaults([
            'data_class' => Profile::class,
        ]);
    }
}
