package Messagerie.clientChat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;

/**
 *
 * @author Louay
 */
public class ChatClient {

    private final String serverName;
    private final int serverPort;
    private Socket socket;
    private OutputStream serverOut;
    private InputStream serverIn;
    private BufferedReader bufferedIn;
    
    private ArrayList<UserStatusListener> userStatusListeners= new ArrayList<>();
    private ArrayList<MessageListener> messageListeners= new ArrayList<>();
    
    public ChatClient(String serverName, int serverPort) {
        this.serverName=serverName;
        this.serverPort=serverPort;
    }
    
//    public static void main(String[] args) throws IOException {
//        ChatClient client = new ChatClient("localhost",8818);
//        client.addUserStatusListener(new UserStatusListener() {
//            @Override
//            public void online(String login) {
//                System.out.println("ONLINE: "+login);
//            }
//
//            @Override
//            public void offline(String login) {
//                System.out.println("OFFLINE: "+login);
//            }
//        });
//        client.addMessageListener(new MessageListener() {
//            @Override
//            public void onMessage(String fromLogin, String msgBody) {
//                System.out.println("You got a message from "+fromLogin+" ==>"+msgBody);
//            }
//        });
//        
//        if (!client.connect()) {
//            System.err.println("Connection failed.");
//        }
//        else 
//        {
//            System.out.println("Conenction successful");
//            if(client.login("guest","guest"))
//            {
//                System.out.println("Login successful");
//                //client.msg("louay","hello world");
//            } 
//            else 
//            {
//                System.out.println("Login failed");
//            }
//            //client.logoff();
//        }   
//    }

    public boolean connect() {
        try {
            this.socket=new Socket(serverName, serverPort);
            System.out.println("Client port is "+socket.getLocalPort());
            this.serverOut=socket.getOutputStream();
            this.serverIn=socket.getInputStream();
            this.bufferedIn = new BufferedReader(new InputStreamReader(serverIn));
            return true;
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public boolean login(String login, String password) throws IOException {
        String cmd="login "+login+" "+password+"\n";
        serverOut.write(cmd.getBytes());
        
        String response = bufferedIn.readLine();
        System.out.println("Response Line: "+response);
        
        if ("ok login".equalsIgnoreCase(response)){
            startMessageReader();
            return true;
        }
        else 
        {
            return false;
        }
        
    }
    
    public void addUserStatusListener(UserStatusListener listener){
        userStatusListeners.add(listener);
    }

    public void removeUserStatusListener(UserStatusListener listener){
        userStatusListeners.remove(listener);
    }

    private void startMessageReader() {
        Thread t= new Thread(){
            @Override
            public void run() {
                readMessageLoop();
            }
        };
        t.start();
    }
    
    private void readMessageLoop() {
        String line;
        try {
            while((line=bufferedIn.readLine())!=null) {
                String [] tokens = line.split(" ");
                if (tokens!=null && tokens.length>0)
                {
                    String cmd = tokens[0];
                    if ("online".equalsIgnoreCase(cmd)){
                        handleOnline(tokens);
                    }
                    else if ("offline".equalsIgnoreCase(cmd))
                    {
                        handleOffline(tokens);
                    } else if ("msg".equalsIgnoreCase(cmd)){
                        String[] tokensMsg = line.split(" ", 3);
                        handleMessage(tokensMsg);
                    }
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            try {
                socket.close();
            } catch (IOException ex1) {
                ex.printStackTrace();
            }
        }
    }

    private void handleOnline(String[] tokens) {
        String login=tokens[1];
        for(UserStatusListener listener : userStatusListeners) {
            listener.online(login);
        }
    }

    private void handleOffline(String[] tokens) {
        String login=tokens[1];
        for(UserStatusListener listener : userStatusListeners) {
            listener.offline(login);
        }
    }

    public void logoff() throws IOException {
        String cmd="logoff\n ";
        serverOut.write(cmd.getBytes());
    }
  
    public void msg(String msgBody) throws IOException {
        String cmd="msg "+msgBody+"\n";
        serverOut.write(cmd.getBytes());
    }
    
    public void block(String user) throws IOException {
        String cmd="block "+user+"\n";
        serverOut.write(cmd.getBytes());
    }
    
    public void unBlock(String user) throws IOException {
        String cmd="unblock "+user+"\n";
        serverOut.write(cmd.getBytes());
    }
    
    public void deleteMessage(int idMessage) throws IOException {
        String cmd="delete "+idMessage+"\n";
        serverOut.write(cmd.getBytes());
    }
    
    public void modifyMessage(int idMessage, String textModify) throws IOException {
        String cmd="modify "+idMessage+" "+textModify+"\n";
        serverOut.write(cmd.getBytes());
    }
    
    public void enterConversation(String sendTo) throws IOException {
        String cmd="conversation "+sendTo+" \n";
        serverOut.write(cmd.getBytes());
    }
    
    public void addMessageListener(MessageListener listener){
        messageListeners.add(listener);
    }
    
    public void removeMessageListener(MessageListener listener){
        messageListeners.remove(listener);
    }

    private void handleMessage(String[] tokensMsg) {
        String login=tokensMsg[1];
        String msgBody= tokensMsg[2];
        for(MessageListener listener :messageListeners) {
            listener.onMessage(login, msgBody);
        }
    }
}
