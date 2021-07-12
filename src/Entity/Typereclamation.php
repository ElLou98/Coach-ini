<?php

namespace App\Entity;

use Doctrine\ORM\Mapping as ORM;
use Symfony\Component\Validator\Constraints as Assert;
use Symfony\Component\Serializer\Annotation\Groups;

/**
 * Typereclamation
 *
 * @ORM\Table(name="typereclamation")
 * @ORM\Entity
 */
class Typereclamation
{
    /**
     * @var int
     *
     * @ORM\Column(name="idTypeReclamation", type="integer", nullable=false)
     * @ORM\Id
     * @ORM\GeneratedValue(strategy="IDENTITY")
     * @Groups("post:read")
     */
    private $idtypereclamation;

    /**
     * @var string
     *
     * @ORM\Column(name="typeReclamation", type="string", length=255, nullable=false)
     * @Assert\NotBlank
     * @Groups("post:read")
     */
    private $typereclamation;

    public function getIdtypereclamation(): ?int
    {
        return $this->idtypereclamation;
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

    public function __toString()
    {
        return $this->typereclamation;
    }


}
