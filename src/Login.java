import javax.swing.*;

public class Login {
    public static void main(String[] a) {
        LogInPortal frame = new LogInPortal();
        frame.setTitle("Stu.art");
        frame.setVisible(true);
        frame.setBounds(10, 10, 500, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);

    }
}
