<?php

namespace App\Entity;

use App\Entity\Categoriesport;
use Doctrine\Bundle\DoctrineBundle\Repository\ServiceEntityRepository;
use Doctrine\Persistence\ManagerRegistry;

/**
 * @method Categoriesport|null find($id, $lockMode = null, $lockVersion = null)
 * @method Categoriesport|null findOneBy(array $criteria, array $orderBy = null)
 * @method Categoriesport[]    findAll()
 * @method Categoriesport[]    findBy(array $criteria, array $orderBy = null, $limit = null, $offset = null)
 */
class CategoriesortRepository extends ServiceEntityRepository
{
    public function __construct(ManagerRegistry $registry)
    {
        parent::__construct($registry, Categoriesport::class);
    }

    // /**
    //  * @return Categoriesport[] Returns an array of Categoriesport objects
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
    public function findOneBySomeField($value): ?Categoriesport
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
