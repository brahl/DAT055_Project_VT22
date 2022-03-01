import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
        System.out.println("HEj");
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
        Font myFont = new Font("Serif", Font.BOLD, 30);
        Font myFont1 = new Font("Serif", Font.BOLD, 15);
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
            System.out.println("HEJ");
            container.setVisible(false);

        }

        if (e.getSource() == loginButton) {
            container.setVisible(false);

            Container container1 = new Container();
            container1.setLayout(null);
            container1.setVisible(true);

            JLabel logIn = new JLabel("Successful login!");
            logIn.setBounds(10,10,500,600);
            container1.add(logIn);

            logIn.setBackground(Color.black);


        }

        if (e.getSource() == showPassword) {
            if (showPassword.isSelected()) {
                passwordField.setEchoChar((char) 0);
            } else {
                passwordField.setEchoChar('*');
            }
        }
    }
}


