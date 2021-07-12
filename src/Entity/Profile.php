<?php

namespace App\Entity;

use App\Repository\ProfileRepository;
use Doctrine\ORM\Mapping as ORM;
use Symfony\Component\Validator\Constraints as Assert;

/**
 * @ORM\Entity(repositoryClass=ProfileRepository::class)
 */
class Profile
{
    /**
     * @ORM\Id
     * @ORM\GeneratedValue
     * @ORM\Column(type="integer",name="ID_Coach")
     */
    private $id;

    /**
     * @ORM\Column(type="string", length=2083,name="Photo")
     *  @Assert\NotBlank(message="photo is required")
     */
    private $photo;

    /**
     * @ORM\Column(type="string", length=255,name="Description")
     * @Assert\NotBlank(message="description is required")
     */
    private $description;

    /**
     * @ORM\Column(type="integer",name="Rating")
     * @Assert\NotBlank(message="rating is required")
     */
    private $rating;

    /**
     * @ORM\Column(type="string", length=255,name="Catégorie")
     *
     */
    private $categorie;

    /**
     * @ORM\Column(type="string", length=255,name="Détail")
     * @Assert\NotBlank(message="detail is required")
     */
    private $detail;

    /**
     * @ORM\Column(type="string", length=255,name="Nom")
     * @Assert\NotBlank(message="nom is required")
     */
    private $nom;

    /**
     * @ORM\Column(type="integer",name="ID_Compte")
     */
    private $id_compte;

    public function getId(): ?int
    {
        return $this->id;
    }

    /**
     * @return mixed
     */
    public function getPhoto()
    {
        return $this->photo;
    }

    /**
     * @param mixed $photo
     */
    public function setPhoto($photo): void
    {
        $this->photo = $photo;
    }

    /**
     * @return mixed
     */
    public function getDescription()
    {
        return $this->description;
    }

    /**
     * @param mixed $description
     */
    public function setDescription($description): void
    {
        $this->description = $description;
    }

    /**
     * @return mixed
     */
    public function getRating()
    {
        return $this->rating;
    }

    /**
     * @param mixed $rating
     */
    public function setRating($rating): void
    {
        $this->rating = $rating;
    }

    /**
     * @return mixed
     */
    public function getCategorie()
    {
        return $this->categorie;
    }

    /**
     * @param mixed $categorie
     */
    public function setCategorie($categorie): void
    {
        $this->categorie = $categorie;
    }

    /**
     * @return mixed
     */
    public function getDetail()
    {
        return $this->detail;
    }

    /**
     * @param mixed $detail
     */
    public function setDetail($detail): void
    {
        $this->detail = $detail;
    }

    /**
     * @return mixed
     */
    public function getNom()
    {
        return $this->nom;
    }

    /**
     * @param mixed $nom
     */
    public function setNom($nom): void
    {
        $this->nom = $nom;
    }

    /**
     * @return mixed
     */
    public function getIdCompte()
    {
        return $this->id_compte;
    }

    /**
     * @param mixed $id_compte
     */
    public function setIdCompte($id_compte): void
    {
        $this->id_compte = $id_compte;
    }


}
