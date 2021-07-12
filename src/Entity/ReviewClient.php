<?php

namespace App\Entity;

use Doctrine\ORM\Mapping as ORM;
use Symfony\Component\Validator\Constraints as Assert;
use Symfony\Bridge\Doctrine\Validator\Constraints\UniqueEntity;

/**
 * ReviewClient
 *
 * @ORM\Table(name="review_client")
 * @ORM\Entity(repositoryClass=ReviewClientRepository::class)
 * @UniqueEntity(fields={"idReview"}, message="idReview must be UNIQUE")
 */
class ReviewClient
{
    /**
     * @var int
     * @ORM\Column(name="id_review", type="integer", nullable=false)
     * @ORM\Id
     * @ORM\GeneratedValue(strategy="IDENTITY")
     */
    private $idReview;

    /**
     * @var string
     * @Assert\NotBlank(message="Please add a Review")
     * @Assert\Length(min="2", max="65535",
     *     minMessage="your review must have at least {{ limit }} caracters",
     *     maxMessage="your review lenght can not surpass {{ limit }} caracters")
     * @ORM\Column(name="description_review", type="text", length=65535, nullable=false)
     */
    private $descriptionReview;

    /**
     * @var string
     * @Assert\NotBlank(message="Please enter your username")
     * @ORM\Column(name="nom_client_review", type="string", length=30, nullable=false)
     */
    private $nomClientReview;

    /**
     * @var string
     * @Assert\NotBlank(message="Please enter your coach username")
     * @ORM\Column(name="nom_coach_review", type="string", length=30, nullable=false)
     */
    private $nomCoachReview;

    /**
     * @var string
     * @Assert\NotBlank
     * @ORM\Column(name="date_review", type="string", length=30, nullable=false)
     */
    private $dateReview;

    /**
     * @var float
     *
     * @ORM\Column(name="rating", type="float", precision=10, scale=0, nullable=false)
     */
    private $rating;

    public function getIdReview(): ?int
    {
        return $this->idReview;
    }

    public function getDescriptionReview(): ?string
    {
        return $this->descriptionReview;
    }

    public function setDescriptionReview(string $descriptionReview): self
    {
        $this->descriptionReview = $descriptionReview;

        return $this;
    }

    public function getNomClientReview(): ?string
    {
        return $this->nomClientReview;
    }

    public function setNomClientReview(string $nomClientReview): self
    {
        $this->nomClientReview = $nomClientReview;

        return $this;
    }

    public function getNomCoachReview(): ?string
    {
        return $this->nomCoachReview;
    }

    public function setNomCoachReview(string $nomCoachReview): self
    {
        $this->nomCoachReview = $nomCoachReview;

        return $this;
    }

    public function getDateReview(): ?string
    {
        return $this->dateReview;
    }

    public function setDateReview(string $dateReview): self
    {
        $this->dateReview = $dateReview;

        return $this;
    }

    public function getRating(): ?float
    {
        return $this->rating;
    }

    public function setRating(float $rating): self
    {
        $this->rating = $rating;

        return $this;
    }


}
