<?php

namespace App\Form;

use App\Entity\Event;
use App\Entity\Map;
use Symfony\Component\DomCrawler\Field\TextareaFormField;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\Extension\Core\Type\ChoiceType;
use Symfony\Component\Form\Extension\Core\Type\FileType;
use Symfony\Component\Form\Extension\Core\Type\TextareaType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;
use Vich\UploaderBundle\Entity\File;
use Vich\UploaderBundle\Form\Type\VichImageType;

class EventType extends AbstractType
{

    public function buildForm(FormBuilderInterface $builder, array $options)
    {
        $builder
            ->add('nomEvent')
            ->add('dateDebut',)
            ->add('heureDebut')
            ->add('dateFin')
            ->add('heureFin')
            #->add('participation')
            ->add('participation', ChoiceType::class, [
                'choices' => [
                    'On Ligne' => 'On Ligne',
                    'En Personne' => 'En Personne',
                ],
                'expanded' => true,

            ])
            ->add('nbParticipant')
            ->add('description')
            ->add('map',TextareaType::class, array(
                'attr' => array(
                    'readonly' => true,
                ),
            ))
            ->add('imageFile',FileType::class,[
                'required'=> false,
            ])
            #->add('idCoach')
            ->add('idCoach')
            ->add('idCat',);

    }

    public function configureOptions(OptionsResolver $resolver)
    {
        $resolver->setDefaults([
            'data_class' => Event::class,
        ]);
    }
}
