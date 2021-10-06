/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GestionCompte.services;


import GestionCompte.entities.Compte;
import GestionCompte.interfaces.ICompte;
import tools.MyConnection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author masso
 */
public class CompteCRUD implements ICompte<Compte>{

    @Override
    public  void ajouterCompte(Compte t) {
        try {
            String requete  = "INSERT INTO `compte` (`id`, `userName` , `nom`, `prenom`, `age`, `adresse_mail`, `mot_de_passe`, `num_tel`) VALUES (NULL, '"+t.getNomDutilisateur()+"' , '"+t.getNom()+"', '"+t.getPrenom()+"', '"+t.getAge()+"', '"+t.getAdresseMail()+"', '"+t.getMotDePasse()+"', '"+t.getNumTel()+"') ";
            Statement st = MyConnection.getInstance().getCnx().createStatement() ;
            st.executeUpdate(requete);
            System.out.println("Compte ajouté");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void supprimerCompte(Compte t) {
        try {
            String requete = "DELETE FROM compte WHERE id=?";
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(requete);
            pst.setInt(1, t.getId());
            pst.executeUpdate();
            System.out.println("Compte supprimer");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void modifierCompte(Compte t) {
        try {
            String requete = " UPDATE compte SET nom=?, prenom=?,age=?,adresse_mail=?,mot_de_passe=?,num_tel=? WHERE id=?" ;
            PreparedStatement pst= MyConnection.getInstance().getCnx().prepareStatement(requete);
            pst.setString(1,t.getNom());
            pst.setString(2,t.getPrenom());
            pst.setInt(3,t.getAge());
            pst.setString(4, t.getAdresseMail());
            pst.setString(5, t.getMotDePasse());
            pst.setInt(6, t.getNumTel());
            pst.setInt(7, t.getId());
            pst.executeUpdate();
            System.out.println("compte modifié!");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public List<Compte> comptesList() {
        List<Compte> myList = new ArrayList<>();
        try {

            String requete = "SELECT * FROM compte";
            Statement st = MyConnection.getInstance().getCnx().createStatement();
            ResultSet rs = st.executeQuery(requete);
            while (rs.next()) {
                Compte c = new Compte();
                c.setId(rs.getInt(1));
                c.setNom(rs.getString("nom"));
                c.setPrenom(rs.getString("prenom"));
                c.setAge(rs.getInt("age"));
                c.setAdresseMail(rs.getString("adresse_mail"));
                c.setMotDePasse(rs.getString("mot_de_passe"));
                c.setNumTel(rs.getInt("num_tel"));
                c.setNomDutilisateur(rs.getString("userName"));
                myList.add(c);
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return myList;
    }
    
    public List<Compte> comptesListTrie() {
        List<Compte> myList = new ArrayList<>();
        try {

            String requete = "SELECT * FROM compte ORDER BY nom";
            Statement st = MyConnection.getInstance().getCnx().createStatement();
            ResultSet rs = st.executeQuery(requete);
            while (rs.next()) {
                Compte c = new Compte();
                c.setId(rs.getInt(1));
                c.setNom(rs.getString("nom"));
                c.setPrenom(rs.getString("prenom"));
                c.setAge(rs.getInt("age"));
                c.setAdresseMail(rs.getString("adresse_mail"));
                c.setMotDePasse(rs.getString("mot_de_passe"));
                c.setNumTel(rs.getInt("num_tel"));
                myList.add(c);
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return myList;
    }
    
    public static boolean existe(String nomDutilisateur)
    {
        boolean resultat=false;
        try {
            String requete = "SELECT EXISTS(SELECT * FROM compte WHERE userName='"+nomDutilisateur+"')";
            Statement st = MyConnection.getInstance().getCnx().createStatement();
            ResultSet rs = st.executeQuery(requete);
            int n = 0;
            if ( rs.next() ) {
                n = rs.getInt(1);
            }
            if (n!=0)
            {
                resultat= true;
            }
            
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return resultat;
    }
    
    public static boolean existe(int id)
    {
        boolean resultat=false;
        try {
            String requete = "SELECT EXISTS(SELECT * FROM compte WHERE id='"+id+"')";
            Statement st = MyConnection.getInstance().getCnx().createStatement();
            ResultSet rs = st.executeQuery(requete);
            int n = 0;
            if ( rs.next() ) {
                n = rs.getInt(1);
            }
            if (n!=0)
            {
                resultat= true;
            }
            
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return resultat;
    }
    
    public static boolean existe(int id,String nomTable)
    {
        boolean resultat=false;
        try {
            String requete = "SELECT EXISTS(SELECT * FROM "+nomTable+" WHERE idc='"+id+"')";
            Statement st = MyConnection.getInstance().getCnx().createStatement();
            ResultSet rs = st.executeQuery(requete);
            int n = 0;
            if ( rs.next() ) {
                n = rs.getInt(1);
            }
            if (n!=0)
            {
                resultat= true;
            }
            
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return resultat;
    }
    
    public static boolean isAdmin(int id)
    {
        boolean resultat=false;
        try {
            String requete = "SELECT EXISTS(SELECT * FROM admin WHERE id='"+id+"')";
            Statement st = MyConnection.getInstance().getCnx().createStatement();
            ResultSet rs = st.executeQuery(requete);
            int n = 0;
            if ( rs.next() ) {
                n = rs.getInt(1);
            }
            if (n!=0)
            {
                resultat= true;
            }
            
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return resultat;
    }
    
    public static int CompteExiste(String nomDutilisateur,String motDePasse)
    {   
        int resultat=0 ;
        try {
            String requete = "SELECT * FROM compte WHERE (userName='"+nomDutilisateur+"' AND mot_de_passe='"+motDePasse+"')";
            Statement st = MyConnection.getInstance().getCnx().createStatement();
            ResultSet rs = st.executeQuery(requete);
            int n = 0;
            if ( rs.next() ) {
                n = rs.getInt(1);
            }
            if (n!=0)
            {
                resultat= n;
            }
        }
        catch (SQLException ex) {
            System.out.println(ex.getMessage());
            
        }
     return resultat; 
    }
    
    public static String typeCompte(int id)
    {
        String type= "Ce compte n'existe pas" ;
        
        if(CompteCRUD.existe(id, "client"))
            return "client" ;
        if(CompteCRUD.existe(id, "coach"))
            return "coach" ;
        
        return type;
    }
    
    public static boolean mailIsValid(String mail){
        Pattern pattern = Pattern.compile("^[a-zA-z0-9]*@[a-zA-z0-9]*\\.[a-zA-z0-9]*$");
        Matcher matcher = pattern.matcher(mail);
        boolean matchFound = matcher.find();
        return matchFound;
    }
    public static boolean telIsValid(String tel){
        Pattern pattern = Pattern.compile("^[234579][0-9]{7}$");
        Matcher matcher = pattern.matcher(tel);
        boolean matchFound = matcher.find();
        return matchFound;
    }
    
    public static String encrypt(String  motDePasse){
    String result="";
    String alphabet="ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    int i=0;
    while(i<motDePasse.length())
    {
        char c=motDePasse.charAt(i);
        int j=alphabet.indexOf(c);
        if(j!=-1)
        {
            if(i+j>51)
            {
                int k=i+j-52;
                result+= alphabet.charAt(k);
            }
            else
                result+= alphabet.charAt(i+j);
        }
        else
            result+= c;
        i++;
    }
    return  result;
    }
    
    public static String decrypt(String  motDePasse){
    String result="";
    String alphabet="ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    int i=0;
    while(i<motDePasse.length())
    {
        char c=motDePasse.charAt(i);
        int j=alphabet.indexOf(c);
        if(j!=-1)
        {
            if(j-i<0)
            {
                int k=j-i+52;
                result+= alphabet.charAt(k);
            }
            else
                result+= alphabet.charAt(j-i);
        }
        else
            result+= c;
        i++;
    }
    return  result;
    }
    
    public static Compte rechercherCompte(int id) {
        if (CompteCRUD.existe(id)) {
            try {
                String requete = "SELECT * FROM compte where id='" + id + "'";
                Statement st = MyConnection.getInstance().getCnx().createStatement();
                ResultSet rs = st.executeQuery(requete);
                Compte c = new Compte();
                while (rs.next()) {
                    c.setId(id);
                    c.setNom(rs.getString("nom"));
                    c.setPrenom(rs.getString("prenom"));
                    c.setAge(rs.getInt("age"));
                    c.setAdresseMail(rs.getString("adresse_mail"));
                    c.setMotDePasse(rs.getString("mot_de_passe"));
                    c.setNumTel(rs.getInt("num_tel"));
                    c.setNomDutilisateur(rs.getString("userName"));
                    return c;
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }

        }
        return null;
    }
    
    
    public static Compte rechercherCompte(String userName) {
        if (CompteCRUD.existe(userName)) {
            try {
                String requete = "SELECT * FROM compte where userName='" + userName + "'";
                Statement st = MyConnection.getInstance().getCnx().createStatement();
                ResultSet rs = st.executeQuery(requete);
                Compte c = new Compte();
                while (rs.next()) {
                    c.setId(rs.getInt("id"));
                    c.setNom(rs.getString("nom"));
                    c.setPrenom(rs.getString("prenom"));
                    c.setAge(rs.getInt("age"));
                    c.setAdresseMail(rs.getString("adresse_mail"));
                    c.setMotDePasse(rs.getString("mot_de_passe"));
                    c.setNumTel(rs.getInt("num_tel"));
                    c.setNomDutilisateur(rs.getString("userName"));
                    return c;
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }

        }
        return null;
    }
    
        public static int  pourcentageAccountClient() {
        int count=0;
        int somme=0;
        
        try {
            String requete = "SELECT count(id) as count FROM compte";
            Statement st = MyConnection.getInstance().getCnx().createStatement();
            ResultSet rs = st.executeQuery(requete);
            while(rs.next())
            {
                count= rs.getInt(1);
            }
            requete = "SELECT count(id) as count FROM client";
            st = MyConnection.getInstance().getCnx().createStatement();
            rs = st.executeQuery(requete);
            while(rs.next())
            {
                somme= rs.getInt(1);
            }
            
            

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return somme*100/count ;
        
    }
    
    public static ArrayList<Integer>  signninStatisque() {
        
        ArrayList<Integer> list= new ArrayList<Integer>();
        for(int i=0;i<13;i++)
        {
            list.add(0);
        }
        
        try {
            String requete = "SELECT extract(month from date) as month FROM compte";
            Statement st = MyConnection.getInstance().getCnx().createStatement();
            ResultSet rs = st.executeQuery(requete);
            while(rs.next())
            {   
                int month= rs.getInt("month");
                int value=list.get(month)+1;
                list.set(month, value);
                
            }
            
            

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return list ;
        
    }
    
}

