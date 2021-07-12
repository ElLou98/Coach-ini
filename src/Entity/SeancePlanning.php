<?php

namespace App\Entity;

use Doctrine\ORM\Mapping as ORM;
use Symfony\Component\Validator\Constraints as Assert;
use Symfony\Bridge\Doctrine\Validator\Constraints\UniqueEntity;

/**
 * SeancePlanning
 *
 * @ORM\Table(name="seance_planning")
 * @ORM\Entity(repositoryClass=SeancePlanningRepository::class)
 * @UniqueEntity(fields={"id"}, message="id Seance must be UNIQUE")
 */
class SeancePlanning
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
     * @Assert\NotBlank(message="Please enter your username")
     * @ORM\Column(name="user_name", type="string", length=30, nullable=false)
     */
    private $userName;

    /**
     * @var string
     * @Assert\NotBlank(message="Please enter your a title")
     * @ORM\Column(name="Summary", type="string", length=30, nullable=false)
     */
    private $summary;

    /**
     * @var string
     * @Assert\NotBlank(message="Please add a description!")
     * @Assert\Length(min="2", max="65535",
     *     minMessage="your description must have at least {{ limit }} caracters",
     *     maxMessage="your description lenght can not surpass {{ limit }} caracters")
     * @ORM\Column(name="Description", type="string", length=500, nullable=false)
     */
    private $description;

    /**
     * @var string
     *
     * @ORM\Column(name="Date", type="string", length=30, nullable=false)
     */
    private $date;

    /**
     * @var string
     *
     * @ORM\Column(name="Starts_at", type="string", length=30, nullable=false)
     */
    private $startsAt;

    /**
     * @var string
     *
     * @ORM\Column(name="Finishs_at", type="string", length=30, nullable=false)
     */
    private $finishsAt;

    /**
     * @var string
     * @Assert\NotBlank(message="Please enter the localisation of the meeting")
     * @ORM\Column(name="Localisation", type="string", length=40, nullable=false)
     */
    private $localisation;

    public function getId(): ?int
    {
        return $this->id;
    }

    public function getUserName(): ?string
    {
        return $this->userName;
    }

    public function setUserName(string $userName): self
    {
        $this->userName = $userName;

        return $this;
    }

    public function getSummary(): ?string
    {
        return $this->summary;
    }

    public function setSummary(string $summary): self
    {
        $this->summary = $summary;

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

    public function getDate(): ?string
    {
        return $this->date;
    }

    public function setDate(string $date): self
    {
        $this->date = $date;

        return $this;
    }

    public function getStartsAt(): ?string
    {
        return $this->startsAt;
    }

    public function setStartsAt(string $startsAt): self
    {
        $this->startsAt = $startsAt;

        return $this;
    }

    public function getFinishsAt(): ?string
    {
        return $this->finishsAt;
    }

    public function setFinishsAt(string $finishsAt): self
    {
        $this->finishsAt = $finishsAt;

        return $this;
    }

    public function getLocalisation(): ?string
    {
        return $this->localisation;
    }

    public function setLocalisation(string $localisation): self
    {
        $this->localisation = $localisation;

        return $this;
    }


}
