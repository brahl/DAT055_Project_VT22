import java.net.*;
import java.io.*;

public class ReceiveMsg extends Thread{
    private DatagramSocket socket = new DatagramSocket();
    private int port;
    private byte[] data;
    private String recievedMessage;

    public ReceiveMsg() throws SocketException {
        this.port = 1235;
        this.socket = new DatagramSocket(0);
        this.data = new byte[1024];
    }

    public ReceiveMsg(int port) throws SocketException {
        this.port = port;
        this.socket = new DatagramSocket(port);
        this.data = new byte[1024];
    }


    public void start(){
        while(true){
            DatagramPacket packet = new DatagramPacket(data, data.length);
            try {
                socket.receive(packet);
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("Message from " + packet.getAddress().getHostAddress());
            this.recievedMessage = new String(packet.getData(),0, packet.getLength());
        }
    }

    public String getRecievedMessage(){
        return this.recievedMessage;
    }

}
