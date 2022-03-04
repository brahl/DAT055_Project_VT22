import java.io.IOException;
import java.util.Observable;

public class Chat extends Thread{

    Quotes q = new Quotes();
    private String message;
    private String response;



    public Chat() throws IOException, InterruptedException {
        this.message = "";
        this.response = q.getRes();

    }
    public void start(){


    }
    public void sendMessage(String message){
        this.message = message;
        intepretMessage(this.message);
    }

    public void intepretMessage(String message){
        //could add some switch case here and set the response appropriately.

        if(message.equals("help")){
            this.response = "Du kan söka på Chalmers eller Handels";
        }
        else{
            System.out.println("");
        }

    }




}
