<?php

namespace App\Repository;

use App\Entity\EncapsulationMessage;
use Doctrine\Bundle\DoctrineBundle\Repository\ServiceEntityRepository;
use Doctrine\Persistence\ManagerRegistry;

/**
 * @method EncapsulationMessage|null find($id, $lockMode = null, $lockVersion = null)
 * @method EncapsulationMessage|null findOneBy(array $criteria, array $orderBy = null)
 * @method EncapsulationMessage[]    findAll()
 * @method EncapsulationMessage[]    findBy(array $criteria, array $orderBy = null, $limit = null, $offset = null)
 */
class EncapsulationMessageRepository extends ServiceEntityRepository
{
    public function __construct(ManagerRegistry $registry)
    {
        parent::__construct($registry, EncapsulationMessage::class);
    }

    // /**
    //  * @return EncapsulationMessage[] Returns an array of EncapsulationMessage objects
    //  */
    /*
    public function findByExampleField($value)
    {
        return $this->createQueryBuilder('e')
            ->andWhere('e.exampleField = :val')
            ->setParameter('val', $value)
            ->orderBy('e.id', 'ASC')
            ->setMaxResults(10)
            ->getQuery()
            ->getResult()
        ;
    }
    */

    /*
    public function findOneBySomeField($value): ?EncapsulationMessage
    {
        return $this->createQueryBuilder('e')
            ->andWhere('e.exampleField = :val')
            ->setParameter('val', $value)
            ->getQuery()
            ->getOneOrNullResult()
        ;
    }
    */
}
