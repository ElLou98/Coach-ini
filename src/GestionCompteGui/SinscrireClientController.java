/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GestionCompteGui;

import GestionCompte.entities.Client;
import GestionCompte.entities.Compte;
import GestionCompte.entities.EncapsulationComtpe;
import GestionCompte.services.ClientCRUD;
import GestionCompte.services.CompteCRUD;
import com.twilio.Twilio;

import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author masso
 */
public class SinscrireClientController implements Initializable {

    @FXML
    private TextField in_userName;
    @FXML
    private TextField in_nom;
    @FXML
    private TextField in_prenom;
    @FXML
    private TextField in_password;
    @FXML
    private TextField in_verif;
    @FXML
    private TextField in_age;
    @FXML
    private TextField in_email;
    @FXML
    private TextField in_numTel;
    @FXML
    private PasswordField in_secret;
    @FXML
    private PasswordField in_vsecret;
    @FXML
    private ImageView icon_show;
    @FXML
    private ImageView icon_hide;
    @FXML
    private ImageView icon_vshow;
    @FXML
    private ImageView icon_vhide;
    @FXML
    private Button btn_ajout;
    @FXML
    private Label l_userName;
    @FXML
    private Label l_password;
    @FXML
    private Label l_email;
    @FXML
    private Label l_numTel;
    @FXML
    private TextField in_verifCode;
    @FXML
    private Label l_verifCode;
    @FXML
    private Button btn_verifCode;
    @FXML
    private Button btn_back;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        // TODO
        
        btn_verifCode.setVisible(false);
        l_verifCode.setVisible(false);
        in_verifCode.setVisible(false);
        in_userName.setPromptText("Nom d'utilisateur");
        in_nom.setPromptText("Nom");
        in_prenom.setPromptText("Prenom");
        in_password.setPromptText("Mot de passe");
        in_secret.setPromptText("Mot de passe");
        in_verif.setPromptText("Mot de passe");
        in_vsecret.setPromptText("Mot de passe");
        in_age.setPromptText("Age");
        in_email.setPromptText("Adresse email");
        in_numTel.setPromptText("Numero de téléphone");
        
        in_secret.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable,
                    String oldValue, String newValue) {

               in_password.setText(in_secret.getText());
            }
        });
        
        in_password.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable,
                    String oldValue, String newValue) {

               in_secret.setText(in_password.getText());
            }
        });
        
        in_vsecret.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable,
                    String oldValue, String newValue) {

               in_verif.setText(in_vsecret.getText());
            }
        });
        
        in_verif.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable,
                    String oldValue, String newValue) {

               in_vsecret.setText(in_verif.getText());
            }
        });
        
    }

    @FXML
    private void verif(ActionEvent event) {
        l_userName.setText("");
        l_password.setText("");
        l_numTel.setText("");
        l_email.setText("");
        
        if (CompteCRUD.existe(in_userName.getText())) {
            in_userName.setStyle("-fx-border-color: red; -fx-borfrt-width: 2px;");
            new animatefx.animation.RubberBand(in_userName).play();
            l_userName.setText("Ce nom d'utilisateur exixte déja.");
            l_userName.setTextFill(Color.rgb(210, 39, 30));
        } 
        if (!isAddressValid(in_email.getText())) {
            in_email.setStyle("-fx-border-color: red; -fx-borfrt-width: 2px;");
            new animatefx.animation.RubberBand(in_email).play();
            l_email.setText("Cette adresse mail est invalide.");
            l_email.setTextFill(Color.rgb(210, 39, 30));
        } 
        if (!in_password.getText().equals(in_verif.getText())) {
            in_password.setStyle("-fx-border-color: red; -fx-borfrt-width: 2px;");
            new animatefx.animation.RubberBand(in_password).play();
            l_password.setText("Les deux mot de passes ne sont pas identiques.");
            l_password.setTextFill(Color.rgb(210, 39, 30));
        } 
        if(!CompteCRUD.telIsValid(in_numTel.getText())) {
            in_numTel.setStyle("-fx-border-color: red; -fx-borfrt-width: 2px;");
            new animatefx.animation.RubberBand(in_numTel).play();
            l_numTel.setText("Ce numéro de téléphone est invalide.");
            l_numTel.setTextFill(Color.rgb(210, 39, 30));
        }
        if(!CompteCRUD.existe(in_userName.getText())&& isAddressValid(in_email.getText()) && in_password.getText().equals(in_verif.getText()) && CompteCRUD.telIsValid(in_numTel.getText()))
        { 
            System.out.println("hello there");
            in_age.setVisible(false);
            in_email.setVisible(false);
            in_nom.setVisible(false);
            in_numTel.setVisible(false);
            in_password.setVisible(false);
            in_prenom.setVisible(false);
            in_secret.setVisible(false);
            in_userName.setVisible(false);
            in_verif.setVisible(false);
            in_vsecret.setVisible(false);
            btn_ajout.setVisible(false);
            icon_hide.setVisible(false);
            icon_show.setVisible(false);
            icon_vshow.setVisible(false);
            icon_vhide.setVisible(false);
            in_verifCode.setVisible(true);
            l_verifCode.setVisible(true);
            btn_verifCode.setVisible(true);
            sendSMS();
        }
    }

    @FXML
    private void hide(MouseEvent event) {
        in_secret.setVisible(true);
        in_password.setVisible(false);
        icon_hide.setVisible(false);
        icon_show.setVisible(true);
    }

    @FXML
    private void show(MouseEvent event) {
        in_secret.setVisible(false);
        in_password.setVisible(true);
        icon_hide.setVisible(true);
        icon_show.setVisible(false);
    }

    @FXML
    private void vshow(MouseEvent event) {
        in_vsecret.setVisible(false);
        in_verif.setVisible(true);
        icon_vhide.setVisible(true);
        icon_vshow.setVisible(false);
    }

    @FXML
    private void vhide(MouseEvent event) {
        in_vsecret.setVisible(true);
        in_verif.setVisible(false);
        icon_vhide.setVisible(false);
        icon_vshow.setVisible(true);
    }
    
    private static int hear( BufferedReader in ) throws IOException {
      String line = null;
      int res = 0;
      while ( (line = in.readLine()) != null ) {
          String pfx = line.substring( 0, 3 );
          try {
             res = Integer.parseInt( pfx );
          } 
          catch (Exception ex) {
             res = -1;
          }
          if ( line.charAt( 3 ) != '-' ) break;
      }
      return res;
      }
    private static void say( BufferedWriter wr, String text ) 
       throws IOException {
      wr.write( text + "\r\n" );
      wr.flush();
      return;
      }
    private static ArrayList getMX( String hostName )
          throws NamingException {
      // Perform a DNS lookup for MX records in the domain
      Hashtable env = new Hashtable();
      env.put("java.naming.factory.initial",
              "com.sun.jndi.dns.DnsContextFactory");
      DirContext ictx = new InitialDirContext( env );
      Attributes attrs = ictx.getAttributes
                            ( hostName, new String[] { "MX" });
      Attribute attr = attrs.get( "MX" );
      // if we don't have an MX record, try the machine itself
      if (( attr == null ) || ( attr.size() == 0 )) {
        attrs = ictx.getAttributes( hostName, new String[] { "A" });
        attr = attrs.get( "A" );
        if( attr == null ) 
             throw new NamingException
                      ( "No match for name '" + hostName + "'" );
      }
      // Huzzah! we have machines to try. Return them as an array list
      // NOTE: We SHOULD take the preference into account to be absolutely
      //   correct. This is left as an exercise for anyone who cares.
      ArrayList res = new ArrayList();
      NamingEnumeration en = attr.getAll();
      while ( en.hasMore() ) {
         String x = (String) en.next();
         String f[] = x.split( " " );
         if ( f[1].endsWith( "." ) ) 
             f[1] = f[1].substring( 0, (f[1].length() - 1));
         res.add( f[1] );
      }
      return res;
      }
    public static boolean isAddressValid( String address ) {
      // Find the separator for the domain name
      int pos = address.indexOf( '@' );
      // If the address does not contain an '@', it's not valid
      if ( pos == -1 ) return false;
      // Isolate the domain/machine name and get a list of mail exchangers
      String domain = address.substring( ++pos );
      ArrayList mxList = null;
      try {
         mxList = getMX( domain );
      } 
      catch (NamingException ex) {
         return false;
      }
      // Just because we can send mail to the domain, doesn't mean that the
      // address is valid, but if we can't, it's a sure sign that it isn't
      if ( mxList.size() == 0 ) return false;
      // Now, do the SMTP validation, try each mail exchanger until we get
      // a positive acceptance. It *MAY* be possible for one MX to allow
      // a message [store and forwarder for example] and another [like
      // the actual mail server] to reject it. This is why we REALLY ought
      // to take the preference into account.
      for ( int mx = 0 ; mx < mxList.size() ; mx++ ) {
          boolean valid = false;
          try {
              int res;
              Socket skt = new Socket( (String) mxList.get( mx ), 25 );
              BufferedReader rdr = new BufferedReader
                 ( new InputStreamReader( skt.getInputStream() ) );
              BufferedWriter wtr = new BufferedWriter
                 ( new OutputStreamWriter( skt.getOutputStream() ) );
              res = hear( rdr );
              if ( res != 220 ) throw new Exception( "Invalid header" );
              say( wtr, "EHLO orbaker.com" );
              res = hear( rdr );
              if ( res != 250 ) throw new Exception( "Not ESMTP" );
              // validate the sender address  
              say( wtr, "MAIL FROM: <tim@orbaker.com>" );
              res = hear( rdr );
              if ( res != 250 ) throw new Exception( "Sender rejected" );
              say( wtr, "RCPT TO: <" + address + ">" );
              res = hear( rdr );
              // be polite
              say( wtr, "RSET" ); hear( rdr );
              say( wtr, "QUIT" ); hear( rdr );
              if ( res != 250 ) 
                 throw new Exception( "Address is not valid!" );
              valid = true;
              rdr.close();
              wtr.close();
              skt.close();
          } 
          catch (Exception ex) {
            // Do nothing but try next host
          } 
          finally {
            if ( valid ) return true;
          }
      }
      return false;
      }
    public  String call_this_to_validate( String email ) {
        String testData[] = {email};
        String return_string="";
        for ( int ctr = 0 ; ctr < testData.length ; ctr++ ) {
        	return_string=( testData[ ctr ] + " is valid? " + 
                 isAddressValid( testData[ ctr ] ) );
        }
        return return_string;
        }

    @FXML
    private void ajouterCompte(ActionEvent event) {
        if(in_verifCode.getText().equals("457812963"))
        {
            ClientCRUD cc = new ClientCRUD();
            Client c = new Client(in_userName.getText(), in_nom.getText(), in_prenom.getText(), Integer.parseInt(in_age.getText()), in_email.getText(), CompteCRUD.encrypt(in_password.getText()), Integer.parseInt(in_numTel.getText()));
            cc.ajouterClient(c);
            JOptionPane.showMessageDialog(null, "Compte ajouté!");
            Compte com=CompteCRUD.rechercherCompte(in_userName.getText());
            EncapsulationComtpe encapsulationCompte= new EncapsulationComtpe(com.getId(), in_userName.getText(), c.getNom(), c.getPrenom(), c.getAge(), c.getAdresseMail(), c.getMotDePasse(),c.getNumTel());
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../gui/AccueilClient.fxml"));
            try {
                Parent root = loader.load();
                in_userName.getScene().setRoot(root);
                

            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }
        else
        {
            in_verifCode.setStyle("-fx-border-color: red; -fx-borfrt-width: 2px;");
            new animatefx.animation.RubberBand(in_verifCode).play();
        }
    }
   
    public void sendSMS() {
    
        Twilio.init("AC5110b71cce19bab16cde98a018778611", "b48e2f174cc3773ef8db3653def1854d");
        Message message = Message.creator(
                new com.twilio.type.PhoneNumber("+21646025031"),
                new com.twilio.type.PhoneNumber("+19282482909"),
                "Le code de verification est : 457812963")
            .create();
    }

    @FXML
    private void back(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../gui/AccueilVisiteur.fxml"));
            try {
                Parent root = loader.load();
                in_userName.getScene().setRoot(root);
                

            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
    }
}
