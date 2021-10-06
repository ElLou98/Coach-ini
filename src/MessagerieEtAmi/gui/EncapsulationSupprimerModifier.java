package MessagerieEtAmi.gui;

import Messagerie.clientChat.ChatClient;

/**
 *
 * @author Louay
 */
public class EncapsulationSupprimerModifier {
    
    private static String messageToEdit;
    private static ChatClient client;
    private static int idMessage;
    private static String login;

    public EncapsulationSupprimerModifier(String messageToEdit,ChatClient client, int idMessage, String login) {
        this.messageToEdit=messageToEdit;
        this.client=client;
        this.idMessage=idMessage;
        this.login=login;
    }

    public static String getLogin() {
        return login;
    }

    public static void setLogin(String login) {
        EncapsulationSupprimerModifier.login = login;
    }

    public EncapsulationSupprimerModifier() {
    }

    public static String getMessageToEdit() {
        return messageToEdit;
    }

    public static void setMessageToEdit(String messageToEdit) {
        EncapsulationSupprimerModifier.messageToEdit = messageToEdit;
    }

    public static ChatClient getClient() {
        return client;
    }

    public static void setClient(ChatClient client) {
        EncapsulationSupprimerModifier.client = client;
    }

    public static int getIdMessage() {
        return idMessage;
    }

    public static void setIdMessage(int idMessage) {
        EncapsulationSupprimerModifier.idMessage = idMessage;
    }
    
    
    
    
    
    
}
