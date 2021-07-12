<?php

namespace App\Entity;

use Doctrine\ORM\Mapping as ORM;
use PhpParser\Node\Scalar\String_;
use Symfony\Component\Validator\Constraints as Assert;
use Symfony\Component\HttpFoundation\File\File;
use Vich\UploaderBundle\Mapping\Annotation as Vich;

/**
 * Categorieevent
 *
 * @ORM\Table(name="CategorieEvent")
 * @ORM\Entity
 * @Vich\Uploadable
 * @ORM\Entity(repositoryClass="App\Repository\CategorieeventRepository")
 */
class Categorieevent
{
    /**
     * @var int
     *
     * @ORM\Column(name="id", type="integer", nullable=false)
     * @ORM\Id
     * @ORM\GeneratedValue(strategy="IDENTITY")
     */
    private $id;

    /**
     * @var string
     * @Assert\NotBlank(message="ce champ ne doit pas être vide")
     *  @Assert\Length(
     *      min = 3,
     *      max = 35,
     *      minMessage = "Votre Catégorie Event doit comporter au moins {{ limit }} caractères",
     *      maxMessage = "Votre Catégorie Event ne peut pas comporter plus de {{ limit }} caractères"
     * )
     * @Assert\Regex("/^[a-zàâçéèêëîïôûùüÿñæœ0-9 .-]*$/i",message="Votre Valeur {{ value }} ne doit pas être un entier.")
     * @ORM\Column(name="nom", type="string", length=60, nullable=false)
     */
    private $nom;

    /**
     * @var string
     * @Assert\NotBlank(message="ce champ ne doit pas être vide")
     *  @Assert\Length(
     *      min = 10,
     *      max = 200,
     *      minMessage = "Votre Catégorie Event doit comporter au moins {{ limit }} caractères",
     *      maxMessage = "Votre Catégorie Event ne peut pas comporter plus de {{ limit }} caractères"
     * )
     * @Assert\Regex("/^[a-zàâçéèêëîïôûùüÿñæœ .-]*$/i",message="champ doit être valide ")
     * @ORM\Column(name="description", type="string", length=2500, nullable=false)
     */
    private $description;
    private $updatedAt;

    /**
     * @var string|null
     *
     * @ORM\Column(name="photo", type="string", length=2000, nullable=false)
     */
    private $photo;

    /**
     * NOTE: This is not a mapped field of entity metadata, just a simple property.
     * @Assert\NotBlank(message="ce champ ne doit pas être vide")
     * @Vich\UploadableField(mapping="property_image", fileNameProperty="photo")
     * @var File|null
     */
    private ?File $dbPicture= null;


    /**
     * @ORM\Column(name="db_picture", type="string", length=255, nullable=false)
     * @var string|null
     */
    private $image;


    public function getId(): ?int
    {
        return $this->id;
    }

    public function getNom(): ?string
    {
        return $this->nom;
    }

    public function setNom(string $nom): self
    {
        $this->nom = $nom;

        return $this;
    }

    public function getDescription(): ?string
    {
        return $this->description;
    }

    public function setDescription(string $description): self
    {
        $this->description = $description;

        return $this;
    }

    public function getPhoto(): ?string
    {
        return $this->photo;
    }

    public function setPhoto(?string $photo): self
    {
        $this->photo = $photo;
        return $this;
    }

    public function getDbPicture(): ?File
    {
        return $this->dbPicture;
    }
    public function getimage(): ?String
    {
        return $this->image;
    }

    public function setDbPicture(?File $dbPicture): self
    {
        $this->dbPicture = $dbPicture;
        $dbPicture = "localhost/coachini/uploads/".str_replace("C:\\xampp\\htdocs\\coachini\\uploads\\", "", "$this->dbPicture");
        $this->image = $dbPicture;
        return $this;
    }
    public function __toString() {
        return $this->nom  ;
    }


}


