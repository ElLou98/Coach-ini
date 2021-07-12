<?php

namespace App\Entity;

use Doctrine\ORM\Mapping as ORM;
use Symfony\Component\Serializer\Annotation\Groups;

/**
 * Amis
 *
 * @ORM\Table(name="amis")
 * @ORM\Entity
 */
class Amis
{
    /**
     * @var int
     *
     * @ORM\Column(name="idAmis", type="integer", nullable=false)
     * @ORM\Id
     * @ORM\GeneratedValue(strategy="IDENTITY")
     * @Groups("post:read")
     */
    private $idamis;

    /**
     * @var string
     *
     * @ORM\Column(name="destinataire", type="string", length=20, nullable=false)
     * @Groups("post:read")
     */
    private $destinataire;

    /**
     * @var string
     *
     * @ORM\Column(name="expediteur", type="string", length=20, nullable=false)
     * @Groups("post:read")
     */
    private $expediteur;

    /**
     * @var int
     *
     * @ORM\Column(name="etatDemande", type="integer", nullable=false)
     * @Groups("post:read")
     */
    private $etatdemande;

    public function getIdamis(): ?int
    {
        return $this->idamis;
    }

    public function getDestinataire(): ?string
    {
        return $this->destinataire;
    }

    public function setDestinataire(string $destinataire): self
    {
        $this->destinataire = $destinataire;

        return $this;
    }

    public function getExpediteur(): ?string
    {
        return $this->expediteur;
    }

    public function setExpediteur(string $expediteur): self
    {
        $this->expediteur = $expediteur;

        return $this;
    }

    public function getEtatdemande(): ?int
    {
        return $this->etatdemande;
    }

    public function setEtatdemande(int $etatdemande): self
    {
        $this->etatdemande = $etatdemande;

        return $this;
    }


}
