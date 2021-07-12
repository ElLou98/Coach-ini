<?php

namespace App\Entity;

use Doctrine\ORM\Mapping as ORM;
use Symfony\Component\Validator\Constraints as Assert;
use Symfony\Component\Serializer\Annotation\Groups;

/**
 * Message
 *
 * @ORM\Table(name="message")
 * @ORM\Entity
 */
class Message
{
    /**
     * @var int
     *
     * @ORM\Column(name="idMessage", type="integer", nullable=false)
     * @ORM\Id
     * @ORM\GeneratedValue(strategy="IDENTITY")
     * @Groups("post:read")
     */
    private $idmessage;

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
     * @var string
     *
     * @ORM\Column(name="contenuMessage", type="string", length=255, nullable=false)
     * @Assert\NotNull
     * @Groups("post:read")
     */
    private $contenumessage;

    /**
     * @var \DateTime
     *
     * @ORM\Column(name="dateEnvoie", type="datetime", nullable=false)
     * @Groups("post:read")
     */
    private $dateenvoie;

    public function getIdmessage(): ?int
    {
        return $this->idmessage;
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

    public function getContenumessage(): ?string
    {
        return $this->contenumessage;
    }

    public function setContenumessage(string $contenumessage): self
    {
        $this->contenumessage = $contenumessage;

        return $this;
    }

    public function getDateenvoie(): ?\DateTimeInterface
    {
        return $this->dateenvoie;
    }

    public function setDateenvoie(\DateTimeInterface $dateenvoie): self
    {
        $this->dateenvoie = $dateenvoie;

        return $this;
    }


}
