<?php

namespace App\Entity;

use App\Entity\Offre;
use Doctrine\Bundle\DoctrineBundle\Repository\ServiceEntityRepository;
use Doctrine\Persistence\ManagerRegistry;

/**
 * @method Offre|null find($id, $lockMode = null, $lockVersion = null)
 * @method Offre|null findOneBy(array $criteria, array $orderBy = null)
 * @method Offre[]    findAll()
 * @method Offre[]    findBy(array $criteria, array $orderBy = null, $limit = null, $offset = null)
 */
class OffreRepository extends ServiceEntityRepository
{
    public function __construct(ManagerRegistry $registry)
    {
        parent::__construct($registry, Offre::class);
    }
    public function OrderByNameQB()
    {
        return $this->createQueryBuilder('r')
            ->orderBy('r.date','DESC')
            ->getQuery()
            ->getResult()
            ;
    }
    public function search()
    {
        $entityManager = $this->getEntityManager();
        $query = $entityManager->createQuery(
            "SELECT *
         FROM App\Entity\Offre o
         JOIN App\Entity\Categoriesport c
         WHERE o.id_categorie = c.id_categorie 
         ");

        // returns an array of Product objects
        return $query->execute();
    }

    public function findItemsCreatedBetweenTwoDates(\DateTime $beginDate, \DateTime $endDate)
    {
        return $this->createQueryBuilder('m')
            ->where("m.date >= ?1")
            ->andWhere("m.date <= ?2")
            ->setParameter(1, $beginDate)
            ->setParameter(2, $endDate)
            ->getQuery()
            ->getResult();
    }

    public function findStudentByfield($titre){
        return $this->createQueryBuilder('offre')
            ->Where('offre.titre LIKE :titre')
            ->setParameter('titre', '%'.$titre.'%')
            ->getQuery()
            ->getResult();
    }

    public function getNB()
    {

        $qb = $this->createQueryBuilder('v')
            ->select('COUNT(v.id) AS vid, SUBSTRING(v.date, 1, 10) AS date')
            ->groupBy('date');
        return $qb->getQuery()
            ->getResult();

    }

    // /**
    //  * @return Offre[] Returns an array of Offre objects
    //  */
    /*
     *
    public function findByExampleField($value)
    {
        return $this->createQueryBuilder('o')
            ->andWhere('o.exampleField = :val')
            ->setParameter('val', $value)
            ->orderBy('o.id', 'ASC')
            ->setMaxResults(10)
            ->getQuery()
            ->getResult()
        ;
    }
    */

    /*
    public function findOneBySomeField($value): ?Offre
    {
        return $this->createQueryBuilder('o')
            ->andWhere('o.exampleField = :val')
            ->setParameter('val', $value)
            ->getQuery()
            ->getOneOrNullResult()
        ;
    }
    */
}
