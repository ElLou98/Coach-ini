<?php

namespace App\Entity;

use Doctrine\ORM\Mapping as ORM;
use Symfony\Component\Serializer\Annotation\Groups;
use Symfony\Component\Validator\Constraints as Assert;

/**
 * Offre
 *
 * @ORM\Table(name="offre", indexes={@ORM\Index(name="test", columns={"id_categorie"}), @ORM\Index(name="test2", columns={"id_coach"})})
 * @ORM\Entity
 * @ORM\Entity(repositoryClass=OffreRepository::class)

 */
class Offre
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
     * @ORM\Column(name="titre", type="string", length=255, nullable=false)
     *  @Assert\NotBlank(message="veuillez saisir votre titre")
     *  @Groups("post:read")

     */
    private $titre;

    /**
     * @var \DateTime
     *
     * @ORM\Column(name="date", type="date", nullable=false)
     *  @Assert\NotBlank(message="veuillez saisir votre date")
     *  @Groups("post:read")

     */
    private $date;

    /**
     * @var string
     *
     * @ORM\Column(name="description", type="string", length=255, nullable=false)
     *  @Assert\NotBlank(message="Description is required")
     * @Assert\Length(
     * min = 2,
     * max = 50,
     * minMessage = "Your description must be at least {{ limit }} characters long",
     * maxMessage = "Your description cannot be longer than {{ limit }} characters"
     * )
     * @Groups("post:read")

     */
    private $description;

    /**
     * @var int|null
     *
     * @ORM\Column(name="idCompte", type="integer", nullable=true)
     * @Groups("post:read")

     */
    private $idCompte;

    /**
     * @var \Coach
     *
     * @ORM\ManyToOne(targetEntity="Coach")
     * @ORM\JoinColumns({
     *   @ORM\JoinColumn(name="id_coach", referencedColumnName="id")
     * })
     * @Groups("post:read")

     */
    private $idCoach;

    /**
     * @var \Categoriesport
     *
     * @ORM\ManyToOne(targetEntity="Categoriesport")
     * @ORM\JoinColumns({
     *   @ORM\JoinColumn(name="id_categorie", referencedColumnName="id")
     * })
     * @Groups("post:read")

     */
    private $idCategorie;

    public function getId(): ?int
    {
        return $this->id;
    }

    /**
     * @return int
     */
    public function getIdCompte()
    {
        return $this->idCompte;
    }

    /**
     * @param int $idCompte
     */
    public function setIdCompte($idCompte)
    {
        $this->idCompte = $idCompte;
    }

    public function getTitre(): ?string
    {
        return $this->titre;
    }

    public function setTitre(string $titre): self
    {
        $this->titre = $titre;

        return $this;
    }

    public function getDate(): ?\DateTimeInterface
    {
        return $this->date;
    }

    public function setDate(\DateTimeInterface $date): self
    {
        $this->date = $date;

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

    public function getIdCoach(): ?Coach
    {
        return $this->idCoach;
    }

    public function setIdCoach(?Coach $idCoach): self
    {
        $this->idCoach = $idCoach;

        return $this;
    }

    public function getIdCategorie(): ?Categoriesport
    {
        return $this->idCategorie;
    }

    public function setIdCategorie(?Categoriesport $idCategorie): self
    {
        $this->idCategorie = $idCategorie;

        return $this;
    }


}
