<?php

namespace App\Entity;

use Doctrine\ORM\Mapping as ORM;
use Symfony\Component\Validator\Constraints as Assert;
use Symfony\Component\Serializer\Annotation\Groups;

/**
 * Reclamation
 *
 * @ORM\Table(name="reclamation", indexes={@ORM\Index(name="FK_ForeignKey", columns={"idTypeReclamation"})})
 * @ORM\Entity
 */
class Reclamation
{
    /**
     * @var int
     *
     * @ORM\Column(name="idReclamation", type="integer", nullable=false)
     * @ORM\Id
     * @ORM\GeneratedValue(strategy="IDENTITY")
     * @Groups("post:read")
     */
    private $idreclamation;

    /**
     * @var string
     *
     * @ORM\Column(name="login", type="string", length=25, nullable=false)
     * @Groups("post:read")
     */
    private $login;

    /**
     * @var string
     *
     * @ORM\Column(name="descriptionReclamation", type="string", length=4000, nullable=false)
     * @Assert\NotBlank
     * @Groups("post:read")
     */
    private $descriptionreclamation;

    /**
     * @var string
     *
     * @ORM\Column(name="typeReclamation", type="string", length=100, nullable=false)
     * @Groups("post:read")
     */
    private $typereclamation;

    /**
     * @var string
     *
     * @ORM\Column(name="dateReclamation", type="string", length=25, nullable=false)
     * @Groups("post:read")
     */
    private $datereclamation;

    /**
     * @var int
     *
     * @ORM\Column(name="enCours", type="integer", nullable=false)
     * @Groups("post:read")
     */
    private $encours;

    /**
     * @var int
     *
     * @ORM\Column(name="traite", type="integer", nullable=false)
     * @Groups("post:read")
     */
    private $traite;

    /**
     * @var \Typereclamation
     *
     * @ORM\ManyToOne(targetEntity="Typereclamation")
     * @ORM\JoinColumns({
     *   @ORM\JoinColumn(name="idTypeReclamation", referencedColumnName="idTypeReclamation")
     * })
     * @Assert\NotBlank
     * @Groups("post:read")
     */
    private $idtypereclamation;

    public function getIdreclamation(): ?int
    {
        return $this->idreclamation;
    }

    public function getLogin(): ?string
    {
        return $this->login;
    }

    public function setLogin(string $login): self
    {
        $this->login = $login;

        return $this;
    }

    public function getDescriptionreclamation(): ?string
    {
        return $this->descriptionreclamation;
    }

    public function setDescriptionreclamation(string $descriptionreclamation): self
    {
        $this->descriptionreclamation = $descriptionreclamation;

        return $this;
    }

    public function getTypereclamation(): ?string
    {
        return $this->typereclamation;
    }

    public function setTypereclamation(string $typereclamation): self
    {
        $this->typereclamation = $typereclamation;

        return $this;
    }

    public function getDatereclamation(): ?string
    {
        return $this->datereclamation;
    }

    public function setDatereclamation(string $datereclamation): self
    {
        $this->datereclamation = $datereclamation;

        return $this;
    }

    public function getEncours(): ?int
    {
        return $this->encours;
    }

    public function setEncours(int $encours): self
    {
        $this->encours = $encours;

        return $this;
    }

    public function getTraite(): ?int
    {
        return $this->traite;
    }

    public function setTraite(int $traite): self
    {
        $this->traite = $traite;

        return $this;
    }

    public function getIdtypereclamation(): ?Typereclamation
    {
        return $this->idtypereclamation;
    }

    public function setIdtypereclamation(?Typereclamation $idtypereclamation): self
    {
        $this->idtypereclamation = $idtypereclamation;

        return $this;
    }

    public function __toString()
    {
        return $this->login;
    }


}
