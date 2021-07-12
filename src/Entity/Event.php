<?php

namespace App\Entity;

use Doctrine\ORM\Mapping as ORM;
use Symfony\Component\HttpFoundation\File\File;
use Symfony\Component\Validator\Constraints as Assert;
use Vich\UploaderBundle\Mapping\Annotation as Vich;
use Symfony\Component\Serializer\Annotation\Groups;
/**
 * Event
 *
 * @ORM\Table(name="Event", indexes={@ORM\Index(name="FK_Cat", columns={"id_Cat"})})
 * @ORM\Entity
 * @Vich\Uploadable
 * @ORM\Entity(repositoryClass="App\Repository\EventRepository")
 */
class Event
{
    /**
     * @var int
     *
     * @ORM\Column(name="id", type="integer", nullable=false)
     * @ORM\Id
     * @ORM\GeneratedValue(strategy="IDENTITY")
     * @Groups("events:read")
     */
    private $id;

    /**
     * @var string
     * @Assert\NotBlank(message="ce champ ne doit pas être vide")
     *  @Assert\Length(
     *      min = 2,
     *      max = 35,
     *      minMessage = "Votre Event doit comporter au moins {{ limit }} caractères",
     *      maxMessage = "Votre Event ne peut pas comporter plus de {{ limit }} caractères"
     * )
     * @Assert\Regex("/^[a-zàâçéèêëîïôûùüÿñæœ0-9 .-]*$/i",message="champ doit être valide ")
     * @ORM\Column(name="Nom_event", type="string", length=35, nullable=false)
     * @Groups("events:read")
     */
    private $nomEvent;

    /**
     * @var \DateTime
     *
     * @ORM\Column(name="Date_debut", type="date", nullable=false)
     */
    private $dateDebut;

    /**
     * @Assert\NotBlank(message="ce champ ne doit pas être vide")
     * @Assert\Regex("/^(0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]$/",message="Il faut insérer en format HH:MM")
     * @var string A "H:i" formatted value
     * @ORM\Column(name="Heure_debut", type="string", length=35, nullable=false)
     */
    private $heureDebut;

    /**
     * @var \DateTime
     *
     * @ORM\Column(name="Date_fin", type="date", nullable=false)
     */
    private $dateFin;

    /**
     * @Assert\NotBlank(message="ce champ ne doit pas être vide")
     *  @Assert\Regex("/^(0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]$/",message="Il faut insérer en format HH:MM")
     * @var string A "H:i" formatted value
     * @ORM\Column(name="Heure_fin", type="string", length=35, nullable=false)
     */
    private $heureFin;

    /**
     * @var string
     * @Assert\NotBlank(message="ce champ ne doit pas être vide")
     * @ORM\Column(name="Participation", type="string", length=35, nullable=false)
     */
    private $participation;

    /**
     * @var int
     * @Assert\GreaterThanOrEqual(message="Cette valeur doit être supérieure ou égale à 2",
     *     value = 2
     *
     * )
     * @Assert\NotBlank(message="ce champ ne doit pas être vide")
     * @ORM\Column(name="Nb_participant", type="integer", nullable=false)
     */
    private $nbParticipant;

    /**
     * @var string
     * @Assert\NotBlank(message="ce champ ne doit pas être vide")
     * @ORM\Column(name="Description", type="string", length=200, nullable=false)
     */
    private $description;

    /**
     * @var string|null
     *
     * @ORM\Column(name="db_map", type="string", length=255, nullable=true)
     */
    private $dbMap;
    /**
     * @Assert\NotBlank(message="ce champ ne doit pas être vide")
     * NOTE: This is not a mapped field of entity metadata, just a simple property.
     * @Vich\UploadableField(mapping="property_image", fileNameProperty="dbMap")
     * @var File|null
     */
    private ?File $imageFile= null;
    /**
     * @var int|null
     *
     * @ORM\Column(name="id_Coach", type="integer", nullable=true)
     */
    private $idCoach;

    /**
     * @var \Categorieevent
     * @Assert\NotBlank(message="ce champ ne doit pas être vide")
     * @ORM\ManyToOne(targetEntity="Categorieevent")
     * @ORM\JoinColumns({
     *   @ORM\JoinColumn(name="id_Cat", referencedColumnName="id")
     * })
     */
    private $idCat;

    /**
     * @var string|null
     * @Assert\NotBlank(message="ce champ ne doit pas être vide")
     * @ORM\Column(name="map", type="string", length=255, nullable=true)
     */
    private $map;
    public function setImageFile(File $image = null)
    {
        $this->imageFile = $image;

        // VERY IMPORTANT:
        // It is required that at least one field changes if you are using Doctrine,
        // otherwise the event listeners won't be called and the file is lost
        if ($image) {
            // if 'updatedAt' is not defined in your entity, use another property
            $this->updatedAt = new \DateTime('now');
        }
    }

    public function getImageFile()
    {
        return $this->imageFile;
    }
    /**
     * @return string|null
     */
    public function getMap(): ?string
    {
        return $this->map;
    }

    /**
     * @param string|null $map
     */
    public function setMap(?string $map): void
    {
        $this->map = $map;
    }

    public function getId(): ?int
    {
        return $this->id;
    }

    public function getNomEvent(): ?string
    {
        return $this->nomEvent;
    }

    public function setNomEvent(string $nomEvent): self
    {
        $this->nomEvent = $nomEvent;

        return $this;
    }

    public function getDateDebut(): ?\DateTimeInterface
    {
        return $this->dateDebut;
    }

    public function setDateDebut(\DateTimeInterface $dateDebut): self
    {
        $this->dateDebut = $dateDebut;

        return $this;
    }

    public function getHeureDebut(): ?string
    {
        return $this->heureDebut;
    }

    public function setHeureDebut(string $heureDebut): self
    {
        $this->heureDebut = $heureDebut;

        return $this;
    }

    public function getDateFin(): ?\DateTimeInterface
    {
        return $this->dateFin;
    }

    public function setDateFin(\DateTimeInterface $dateFin): self
    {
        $this->dateFin = $dateFin;

        return $this;
    }

    public function getHeureFin(): ?string
    {
        return $this->heureFin;
    }

    public function setHeureFin(string $heureFin): self
    {
        $this->heureFin = $heureFin;

        return $this;
    }

    public function getParticipation(): ?string
    {
        return $this->participation;
    }

    public function setParticipation(string $participation): self
    {
        $this->participation = $participation;

        return $this;
    }

    public function getNbParticipant(): ?int
    {
        return $this->nbParticipant;
    }

    public function setNbParticipant(int $nbParticipant): self
    {
        $this->nbParticipant = $nbParticipant;

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

    public function getDbMap(): ?string
    {
        return $this->dbMap;
    }

    public function setDbMap(?string $dbMap): self
    {
        $this->dbMap = $dbMap;
        $this->dbMap="localhost/coachini/uploads/".$this->dbMap;
        return $this;
    }

    public function getIdCoach(): ?int
    {
        return $this->idCoach;
    }

    public function setIdCoach(?int $idCoach): self
    {
        $this->idCoach = $idCoach;

        return $this;
    }

    public function getIdCat(): ?Categorieevent
    {
        return $this->idCat;
    }

    public function setIdCat(?Categorieevent $idCat): self
    {
        $this->idCat = $idCat;

        return $this;
    }
    public function __construct()
    {
        $this->dateFin = new \DateTime();
        $this->dateDebut = new \DateTime();
    }

}
