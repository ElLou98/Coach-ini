<?php

namespace App\Entity;

use Doctrine\ORM\Mapping as ORM;
use Symfony\Component\Serializer\Annotation\Groups;
use Symfony\Component\Validator\Constraints as Assert;

/**
 * Categoriesport
 *
 * @ORM\Table(name="categoriesport")
 * @ORM\Entity
 * @ORM\Entity(repositoryClass=CategoriesortRepository::class)

 */
class Categoriesport
{
    /**
     * @var int
     *
     * @ORM\Column(name="id", type="integer", nullable=false)
     * @ORM\Id
     * @ORM\GeneratedValue(strategy="IDENTITY")
     * @Groups("post:read")
     */
    private $id;

    /**
     * @var string
     *
     * @ORM\Column(name="nom", type="string", length=30, nullable=false)
     * @Groups("post:read")
     * @Assert\NotBlank(message="veuillez saisir votre nom")
     */

    private $nom;

    /**
     * @var string
     *
     * @ORM\Column(name="description", type="string", length=255, nullable=false)
     * @Groups("post:read")
     * @Assert\NotBlank(message="veuillez saisir votre description")
     * @Assert\Length(
     * min = 2,
     * max = 50,
     * minMessage = "Your description must be at least {{ limit }} characters long",
     * maxMessage = "Your description cannot be longer than {{ limit }} characters"
     * )
     */
    private $description;

    /**
     * @var string
     *
     * @ORM\Column(name="photo", type="string", length=2083, nullable=false)
     * @Groups("post:read")
     * @Assert\NotBlank(message="veuillez charger votre photo")
     */
    private $photo;

    public function getId()
    {
        return $this->id;
    }

    public function getNom()
    {
        return $this->nom;
    }

    public function setNom(string $nom)
    {
        $this->nom = $nom;

        return $this;
    }

    public function getDescription()
    {
        return $this->description;
    }

    public function setDescription(string $description)
    {
        $this->description = $description;

        return $this;
    }

    public function getPhoto()
    {
        return $this->photo;
    }

    public function setPhoto(string $photo)
    {
        $this->photo = $photo;

        return $this;
    }

    public function __toString(){
        // to show the name of the Category in the select
        return $this->nom;
        // to show the id of the Category in the select
        // return $this->id;
    }
}
