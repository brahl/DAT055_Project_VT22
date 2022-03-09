import java.net.*;
import java.io.*;


public class SendMsg {
    private String message;
    private BufferedReader in;
    private int toPort;
    private InetAddress toAddr;
    private DatagramSocket socket;

    public SendMsg(int port) throws SocketException, UnknownHostException {
        this.toPort = port;
        this.toAddr = InetAddress.getByName("127.0.0.1");
        this.socket = new DatagramSocket();
    }

    public void start() throws IOException {
        while(true){
            if (message == null)
                break;
            byte[] data = message.getBytes();
            socket.send(new DatagramPacket(data,data.length,toAddr,toPort));
        }
    }

    public void newMsg(String s){
        this.message=s;
        InputStream targetStream = new ByteArrayInputStream(s.getBytes());
        this.in = new BufferedReader(new InputStreamReader(targetStream));
    }


}
