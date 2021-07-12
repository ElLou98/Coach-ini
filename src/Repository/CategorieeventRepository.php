<?php

namespace App\Repository;

use App\Entity\Categorieevent;
use Doctrine\Bundle\DoctrineBundle\Repository\ServiceEntityRepository;
use Doctrine\Persistence\ManagerRegistry;

/**
 * @method Categorieevent|null find($id, $lockMode = null, $lockVersion = null)
 * @method Categorieevent|null findOneBy(array $criteria, array $orderBy = null)
 * @method Categorieevent[]    findAll()
 * @method Categorieevent[]    findBy(array $criteria, array $orderBy = null, $limit = null, $offset = null)
 */
class CategorieeventRepository extends ServiceEntityRepository
{
    public function __construct(ManagerRegistry $registry)
    {
        parent::__construct($registry, Categorieevent::class);
    }

    // /**
    //  * @return Categorieevent[] Returns an array of Categorieevent objects
    //  */
    /*
    public function findByExampleField($value)
    {
        return $this->createQueryBuilder('c')
            ->andWhere('c.exampleField = :val')
            ->setParameter('val', $value)
            ->orderBy('c.id', 'ASC')
            ->setMaxResults(10)
            ->getQuery()
            ->getResult()
        ;
    }
    */

    /*
    public function findOneBySomeField($value): ?Categorieevent
    {
        return $this->createQueryBuilder('c')
            ->andWhere('c.exampleField = :val')
            ->setParameter('val', $value)
            ->getQuery()
            ->getOneOrNullResult()
        ;
    }
    */
}
