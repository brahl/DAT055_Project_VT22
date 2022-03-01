import javax.swing.*;
import java.io.IOException;

public class App {
    public static void main(String[] args) throws IOException {
        String user = "001";
/*
        //full app
        LogInPortal frame = new LogInPortal();
        frame.setTitle("Stu.art");
        frame.setVisible(true);
        frame.setBounds(10, 10, 500, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        new App();
*/
        //uncomment for testing
        new StuartView(user);


    }
}
