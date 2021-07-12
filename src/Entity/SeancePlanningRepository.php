<?php

namespace App\Entity;

use App\Entity\SeancePlanning;
use Doctrine\Bundle\DoctrineBundle\Repository\ServiceEntityRepository;
use Doctrine\Persistence\ManagerRegistry;

/**
 * @method SeancePlanning|null find($id, $lockMode = null, $lockVersion = null)
 * @method SeancePlanning|null findOneBy(array $criteria, array $orderBy = null)
 * @method SeancePlanning[]    findAll()
 * @method SeancePlanning[]    findBy(array $criteria, array $orderBy = null, $limit = null, $offset = null)
 */
class SeancePlanningRepository extends ServiceEntityRepository
{
    public function __construct(ManagerRegistry $registry)
    {
        parent::__construct($registry, SeancePlanning::class);
    }

    // /**
    //  * @return SeancePlanning[] Returns an array of SeancePlanning objects
    //  */
    /*
    public function findByExampleField($value)
    {
        return $this->createQueryBuilder('s')
            ->andWhere('s.exampleField = :val')
            ->setParameter('val', $value)
            ->orderBy('s.id', 'ASC')
            ->setMaxResults(10)
            ->getQuery()
            ->getResult()
        ;
    }
    */

    /*
    public function findOneBySomeField($value): ?SeancePlanning
    {
        return $this->createQueryBuilder('s')
            ->andWhere('s.exampleField = :val')
            ->setParameter('val', $value)
            ->getQuery()
            ->getOneOrNullResult()
        ;
    }
    */
}
