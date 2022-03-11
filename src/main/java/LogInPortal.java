import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.IOException;



/**
 * @author Anja Tomovic
 * @version 1
 *
 */

public class LogInPortal extends JFrame implements ActionListener {

    Container container = getContentPane();
    JLabel welcomeLabel = new JLabel("Welcome to Stu.art!");
    JLabel userLabel = new JLabel("EMAIL");
    JLabel passwordLabel = new JLabel("PASSWORD");
    JLabel memberLabel = new JLabel("No account? Create one now!");
    JButton loginButton = new JButton("LOGIN");
    JButton createAccountButton = new JButton("CREATE ACCOUNT");
    JCheckBox showPassword = new JCheckBox("Show Password");
    JPasswordField passwordField = new JPasswordField();
    JTextField userTextField = new JTextField();
    //String username = userTextField.getText();
    //char[] password = passwordField.getPassword();
    String user;

    LogInPortal()
    {
        setLayoutManager();
        setLocationAndSize();
        addComponentsToWindow();
        setFonts();
        addActionEvent();
    }

    public void setLayoutManager(){
        container.setLayout(null);
    }

    public void setLocationAndSize(){
        welcomeLabel.setBounds(30, 50, 500, 50);
        userLabel.setBounds(50,150,100,30);
        passwordLabel.setBounds(50,220,100,30);
        userTextField.setBounds(150,150,150,30);
        passwordField.setBounds(150,220,150,30);
        showPassword.setBounds(150,250,150,30);
        loginButton.setBounds(180,300,100,30);
        memberLabel.setBounds(150,350,500,30);
        createAccountButton.setBounds(140,400,200,30);

    }

    public void addComponentsToWindow(){
        container.add(welcomeLabel);
        container.add(userLabel);
        container.add(passwordLabel);
        container.add(userTextField);
        container.add(passwordField);
        container.add(showPassword);
        container.add(loginButton);
        container.add(memberLabel);
        container.add(createAccountButton);
    }

    public void setFonts(){
        Font myFont = new Font("Source Code Pro", Font.BOLD, 30);
        Font myFont1 = new Font("Source Code Pro", Font.BOLD, 15);
        welcomeLabel.setFont(myFont);
        memberLabel.setFont(myFont1);
        passwordLabel.setFont(myFont1);
        userLabel.setFont(myFont1);
        showPassword.setFont(myFont1);
        loginButton.setFont(myFont1);
        createAccountButton.setFont(myFont1);
    }

    public void addActionEvent(){
        loginButton.addActionListener(this);
        createAccountButton.addActionListener(this);
        showPassword.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {


        if (e.getSource() == createAccountButton) {
            JFrame frame2 = new JFrame("Create Account");
            frame2.setVisible(true);
            frame2.setSize(400,130);
            frame2.setLocation(600,350);

            JPanel panel = new JPanel();
            JPanel panel2 = new JPanel();
            JPanel panel3 = new JPanel();
            frame2.setLayout(new FlowLayout());
            panel.setLayout(new GridLayout(4,1));
            JTextField email = new JTextField();
            JLabel emailL = new JLabel("Enter email:");
            JTextField firstName = new JTextField();
            JLabel firstNameL = new JLabel("Enter first name:");
            JTextField lastName = new JTextField();
            JLabel lastNameL = new JLabel("Enter last name");
            JTextField password = new JTextField();
            JLabel passwordL = new JLabel("Enter password");
            JButton caButton = new JButton("CREATE ACCOUNT");
            panel.add(emailL);
            panel.add(email);
            panel.add(firstNameL);
            panel.add(firstName);
            panel.add(lastNameL);
            panel.add(lastName);
            panel.add(passwordL);
            panel.add(password);
            panel2.add(caButton);
            panel.setVisible(true);

            frame2.add(panel);
            frame2.add(panel2);
            frame2.setDefaultCloseOperation(EXIT_ON_CLOSE);

            caButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if(validateEmail(email.getText())){
                        Database.addUser(email.getText(), firstName.getText(), lastName.getText(), password.getText());
                        JOptionPane.showMessageDialog(null, "Account has been created");
                        frame2.dispose();
                    }
                    else{
                        JOptionPane.showMessageDialog(null,"Invalid Email");
                    }

                }
            });



        }

        if (e.getSource() == loginButton) {
            container.setVisible(true);

            Container container1 = new Container();
            container1.setLayout(null);
            container1.setVisible(true);

            boolean success = false;
            try {
                success = tryLogin(userTextField.getText(),passwordField.getText());
            } catch (IOException ex) {
                ex.printStackTrace();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
            if(success){
                JLabel logIn = new JLabel("Successful login!");
                logIn.setBounds(10,10,500,600);
                container1.add(logIn);
                //super.wait(1000);
                //this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));

            }
            else{
                JOptionPane.showMessageDialog(this, "Wrong password!");
            }

        }

        if (e.getSource() == showPassword) {
            if (showPassword.isSelected()) {
                passwordField.setEchoChar((char) 0);
            } else {
                passwordField.setEchoChar('*');
            }
        }
    }

    private boolean validateEmail(String e) {
        return e.contains("@") && e.contains(".");
    }

    private boolean tryLogin(String userTextField, String passwordField) throws IOException, InterruptedException {
         //returns a string or throw message
        user = Database.userExists(userTextField);
        if(!user.equals("")){
             boolean match = Database.passwordMatch(userTextField.toString(),passwordField.toString());
             if(match){
               this.dispose();
               new StuartView(user);
               return true;
             }
        }
        else{
            JLabel logIn = new JLabel("No user with that email exists, create an account");
            logIn.setBounds(10,10,500,600);
            //container1.add(logIn);   
        }
        return false;
    }


}


