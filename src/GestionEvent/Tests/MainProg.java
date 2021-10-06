/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GestionEvent.Tests;

import GestionEvent.Services.Service_Event;

/**
 *
 * @author kagha
 */
public class MainProg 
{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) 
    {
           /* java.util.Date utilDate = new java.util.Date();
            java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
            Service_Event sp = new Service_Event();
            sp.ajouter(new Event("event life",sqlDate,"10:20",sqlDate,"12:00","yes",100,"testing"));
            //sp.modifier(new Event(1,"event life",sqlDate,"10:20",sqlDate,"12:00","yes",100,"testing"));
            //sp.supprimer_id(new Event(1));
            //Event e= new Event();
            sp.afficher()
                    .forEach(name -> System.out.println(name.toString(true)));*/
        
        Service_Event sp = new Service_Event();
        System.out.println(sp.db_Image(186));
            
            
            
        
    }
    
}
