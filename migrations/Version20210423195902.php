<?php

declare(strict_types=1);

namespace DoctrineMigrations;

use Doctrine\DBAL\Schema\Schema;
use Doctrine\Migrations\AbstractMigration;

/**
 * Auto-generated Migration: Please modify to your needs!
 */
final class Version20210423195902 extends AbstractMigration
{
    public function getDescription() : string
    {
        return '';
    }

    public function up(Schema $schema) : void
    {
        // this up() migration is auto-generated, please modify it to your needs
        $this->addSql('CREATE TABLE user (id INT AUTO_INCREMENT NOT NULL, username VARCHAR(180) NOT NULL, roles LONGTEXT NOT NULL COMMENT \'(DC2Type:json)\', UNIQUE INDEX UNIQ_8D93D649F85E0677 (username), PRIMARY KEY(id)) DEFAULT CHARACTER SET utf8mb4 COLLATE `utf8mb4_unicode_ci` ENGINE = InnoDB');
        $this->addSql('ALTER TABLE client DROP FOREIGN KEY fk_compte_client');
        $this->addSql('ALTER TABLE client CHANGE idc idc INT NOT NULL');
        $this->addSql('ALTER TABLE coach DROP FOREIGN KEY fk_compte_coach');
        $this->addSql('ALTER TABLE compte CHANGE date date DATE DEFAULT NULL');
    }

    public function down(Schema $schema) : void
    {
        // this down() migration is auto-generated, please modify it to your needs
        $this->addSql('DROP TABLE user');
        $this->addSql('ALTER TABLE client CHANGE idc idc INT DEFAULT NULL');
        $this->addSql('ALTER TABLE client ADD CONSTRAINT fk_compte_client FOREIGN KEY (idc) REFERENCES compte (id) ON UPDATE CASCADE ON DELETE CASCADE');
        $this->addSql('ALTER TABLE coach ADD CONSTRAINT fk_compte_coach FOREIGN KEY (idc) REFERENCES compte (id)');
        $this->addSql('ALTER TABLE compte CHANGE date date DATE DEFAULT \'2012-12-12\'');
    }
}
