<?php

namespace App\Entity;

use Doctrine\ORM\Mapping as ORM;
use Symfony\Component\Serializer\Annotation\Groups;

/**
 * Utilisateurbloque
 *
 * @ORM\Table(name="utilisateurbloque")
 * @ORM\Entity
 */
class Utilisateurbloque
{
    /**
     * @var int
     *
     * @ORM\Column(name="idBloque", type="integer", nullable=false)
     * @ORM\Id
     * @ORM\GeneratedValue(strategy="IDENTITY")
     * @Groups("post:read")
     */
    private $idbloque;

    /**
     * @var string
     *
     * @ORM\Column(name="utilisateur", type="string", length=20, nullable=false)
     * @Groups("post:read")
     */
    private $utilisateur;

    /**
     * @var string
     *
     * @ORM\Column(name="abloque", type="string", length=20, nullable=false)
     * @Groups("post:read")
     */
    private $abloque;

    public function getIdbloque(): ?int
    {
        return $this->idbloque;
    }

    public function getUtilisateur(): ?string
    {
        return $this->utilisateur;
    }

    public function setUtilisateur(string $utilisateur): self
    {
        $this->utilisateur = $utilisateur;

        return $this;
    }

    public function getAbloque(): ?string
    {
        return $this->abloque;
    }

    public function setAbloque(string $abloque): self
    {
        $this->abloque = $abloque;

        return $this;
    }

    public function setIdbloque(int $idbloque): self
    {
        $this->idbloque = $idbloque;

        return $this;
    }



}
