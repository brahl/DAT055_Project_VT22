import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//Fixa felhantering som wrong entry, just nu låser programmet sig om man exempelvis skriver text i kurspoäng.

public class StuartView extends JFrame{
    private JTabbedPane tabbedPane1;
    private JPanel profilView;
    private JPanel minaBetygView;
    private JTextField kursField;
    private JTextField pointsField;
    private JTextField betygField;
    private JButton addCourse;

    private JLabel meritLabel;
    private JLabel Kurs;
    private JLabel kurpoint;
    private JLabel letterGrade;
    private JPanel gradeListPanel;
    private JButton minaBetygBtn;
    private JButton updatePassword;
    private JButton updateEmail;
    private JLabel nameLabel;
    private JLabel meritProfilLabel;
    private JLabel passwordProfilLabel;
    private JLabel emailLabel;

    private DefaultListModel<String> listModel;
    private JList<String> listCourses;
    private JPanel StuartPanel;
    private String courselistitem;

    JFrame frame = new JFrame("STU.ART");

    Grades grades = new Grades();




    public StuartView(String user){
        Database db = new Database();
        initMinaBetygView(user,db);
        initProfilView(user);

        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);


        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setUndecorated(false);
        frame.setVisible(true);

        ImageIcon image = new ImageIcon("Stuart.png");
        frame.setIconImage(image.getImage());
        frame.getContentPane().setBackground(new Color(0xFFFFFFF));
        frame.add(showView());
        frame.setVisible(true);

   addCourse.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //add to session
                grades.addGrade(new Grade(kursField.getText(),betygField.getText(),pointsField.getText()));
                Database.addGrade(user,kursField.getText(),pointsField.getText(),betygField.getText());

                //empty input fields
                kursField.setText("");
                betygField.setText("");
                pointsField.setText("");

                //updateJlist
                updateCourseLabel();

            }

            private void updateCourseLabel() {
                listModel.removeAllElements();
                for(Grade g : grades){
                    courselistitem = g.kurs + " " + g.kurspoäng + " "+ g.lettergrade;
                    listModel.addElement(courselistitem);
                }
                meritLabel.setText("Merit: "+ grades.printGPA());
                meritProfilLabel.setText("Merit: "+ grades.printGPA());
            }


        });

        listCourses.addListSelectionListener(e -> {

        });

        updateEmail.addActionListener(e -> {

            String newEmail = JOptionPane.showInputDialog("Enter new email");
            Database.updateEmail(user,newEmail);
            emailLabel.setText(Database.readEmail(user));

        });
        updatePassword.addActionListener(e -> {
            String newPassw = JOptionPane.showInputDialog("Enter new password");
            Database.updatePassword(user,newPassw);
            passwordProfilLabel.setText(Database.readPassword(user));

        });
        minaBetygBtn.addActionListener(e -> tabbedPane1.setSelectedIndex(1));
    }

    private void initProfilView(String user) {
        nameLabel.setText(Database.readFirstName(user)+" "+Database.readLastName(user));
        emailLabel.setText(Database.readEmail(user));
        passwordProfilLabel.setText(Database.readPassword(user));
        meritProfilLabel.setText(Double.toString(grades.printGPA()));
    }

    /**
     * Sets initial courselist and merit
     * @param user target user
     */
    private void initMinaBetygView(String user, Database db) {
        listModel = new DefaultListModel<>();
        listCourses.setModel(listModel);
        initCourseLabel(user,db);
        meritLabel.setText("Merit: "+ grades.printGPA());
    }

    /**
     * Reads grades for user in database, adds to listmodel.
     * @param user target user
     */
    public void initCourseLabel(String user, Database db) {
        listModel.removeAllElements();
        Grades gs = db.readDatabaseGrades(user);

        for(Grade g : gs){
            System.out.println(g.kurs);
            grades.addGrade(new Grade(g.kurs, g.kurspoäng, g.lettergrade));
            courselistitem = g.kurs+" "+g.kurspoäng+ " "+ g.lettergrade;
            listModel.addElement(courselistitem);

        }

    }


    public Component showView() {
        StuartPanel.setBackground(Color.WHITE);

        return StuartPanel;
    }

}


