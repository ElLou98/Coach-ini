package MessagerieEtAmi.gui;

import Messagerie.clientChat.ChatClient;

/**
 *
 * @author Louay
 */
public class EncapsulationUserListMessage {
    
    private static String login;
    private static ChatClient client;
    private static String myLogin;

    public EncapsulationUserListMessage(String login, ChatClient client, String myLogin) {
        this.login = login;
        this.client = client;
        this.myLogin=myLogin;
    }

    public EncapsulationUserListMessage() {
       
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public ChatClient getClient() {
        return client;
    }

    public void setClient(ChatClient client) {
        this.client = client;
    }

    public static String getMyLogin() {
        return myLogin;
    }

    public static void setMyLogin(String myLogin) {
        EncapsulationUserListMessage.myLogin = myLogin;
    }
    
    
    
}
