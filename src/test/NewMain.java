/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import GestionCompte.services.CompteCRUD;

/**
 *
 * @author masso
 */
public class NewMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        CompteCRUD ped = new CompteCRUD();

        System.out.println(CompteCRUD.encrypt("popo"));
    }
    
}
