import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;

public class ProfilView{
    private JLabel nameLabel;
    private JLabel meritLabel;
    private JLabel passwLabel;
    private JLabel emailLabel;
    private JButton updateEmail;
    private JButton updatePassword;
    private JPanel profilePanel;
    private final String user = "001";

    public ProfilView() throws IOException {

        //Server server = new Server();
        nameLabel.setText(Database.readFirstName(user)+" "+Database.readLastName(user));
        emailLabel.setText(Database.readEmail(user));
        //passwLabel.setText(Database.readPassw());
        //meritLabel.setText(Database.readMerit());



        updateEmail.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


                String newEmail = JOptionPane.showInputDialog("Enter new email");
                Database.updateEmail(user,newEmail);
                emailLabel.setText(Database.readEmail(user));

            }
        });
        updatePassword.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String newPassw = JOptionPane.showInputDialog("Enter new password");
                //Database.updatePassw(newPassw);
                //passwLabel.setText(server.readPassw());

            }
        });
    }


    public Component show() {
        profilePanel.setBackground(Color.WHITE);



        return profilePanel;
    }



}
