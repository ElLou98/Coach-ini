<?php

namespace App\Entity;

use App\Repository\EncapsulationMessageRepository;
use Doctrine\ORM\Mapping as ORM;

/**
 * @ORM\Entity(repositoryClass=EncapsulationMessageRepository::class)
 */
class EncapsulationMessage
{
    /**
     * @ORM\Id
     * @ORM\GeneratedValue
     * @ORM\Column(type="integer")
     */
    public static $id;

    /**
     * @ORM\Column(type="string", length=255, nullable=true)
     */
    public static $me;

    /**
     * @ORM\Column(type="string", length=255, nullable=true)
     */
    public static $friend;

    /**
     * @ORM\Column(type="string", length=255, nullable=true)
     */
    public static $contenuMessage;

    /**
     * @return mixed
     */
    public static function getId()
    {
        return self::$id;
    }

    /**
     * @param mixed $id
     */
    public static function setId($id): void
    {
        self::$id = $id;
    }

    /**
     * @return mixed
     */
    public static function getMe()
    {
        return self::$me;
    }

    /**
     * @param mixed $me
     */
    public static function setMe($me): void
    {
        self::$me = $me;
    }

    /**
     * @return mixed
     */
    public static function getFriend()
    {
        return self::$friend;
    }

    /**
     * @param mixed $friend
     */
    public static function setFriend($friend): void
    {
        self::$friend = $friend;
    }

    /**
     * @return mixed
     */
    public static function getContenuMessage()
    {
        return self::$contenuMessage;
    }

    /**
     * @param mixed $contenuMessage
     */
    public static function setContenuMessage($contenuMessage): void
    {
        self::$contenuMessage = $contenuMessage;
    }


}
