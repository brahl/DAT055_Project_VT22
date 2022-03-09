package chatfunction;// Java implementation for a client
// Save file as Client.java
/**
 * @author Erik Hillestad
 * @version 1
 *
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.*;

// Client class
public class ChatClient extends JFrame implements ActionListener
{
    Container container = getContentPane();
    JButton sendButton = new JButton("Send");
    JTextField textField = new JTextField();
    DefaultListModel<String> chatModel = new DefaultListModel<>();;
    JList<String> chatMessages = new JList<>();
    InetAddress ip = InetAddress.getByName("127.0.0.1");
    String received;

    // establish the connection with server port 5056
    Socket s = new Socket(ip, 5056);

    // obtaining input and out streams
    DataInputStream dis = new DataInputStream(s.getInputStream());
    DataOutputStream dos = new DataOutputStream(s.getOutputStream());

    public ChatClient() throws IOException {
        initChatView();
    }


    public void initChatView(){
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(700,500);
        this.setResizable(false);
        this.setLocation(500,500);
        setLayoutManager();
        setLocationAndSize();
        addComponentsToWindow();
        setFonts();
        addActionEvent();
        this.setVisible(true);
    }


    public void setLayoutManager(){
        container.setLayout(null);
    }

    public void setLocationAndSize(){
        chatMessages.setModel(chatModel);
        chatMessages.setBounds(0,0,700,400);
        textField.setBounds(0,400,150,30);
        sendButton.setBounds(180,400,100,30);

    }

    public void addComponentsToWindow(){
        container.add(chatMessages);
        container.add(textField);
        container.add(sendButton);
    }

    public void setFonts(){
        Font myFont1 = new Font("Source Code Pro", Font.BOLD, 15);
        sendButton.setFont(myFont1);

    }

    public void addActionEvent(){
        sendButton.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == sendButton) {

            String message = textField.getText();
            textField.setText("");
            try
            {

                //System.out.println(dis.readUTF());
                String tosend = message;
                chatModel.addElement("You: "+ message);
                dos.writeUTF(tosend);


                // If client sends exit,close this connection
                // and then break from the while loop
                if(tosend.equals("Exit"))
                {
                    System.out.println("Closing this connection : " + s);
                    s.close();
                    System.out.println("Connection closed");
                }

                // printing date or time as requested by client
                received = dis.readUTF();
                System.out.println("Stuart: " +received);


                // closing resources
                /*
                dis.close();
                dos.close();

                 */
            }catch(Exception exception){
                exception.printStackTrace();
            }
            chatModel.addElement(received);


        }
    }


    public static void main(String[] args) throws IOException
    {
        new ChatClient();

    }
}