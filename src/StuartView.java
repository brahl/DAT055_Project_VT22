import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
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



    private double res;
    private double count;
    JFrame frame = new JFrame("STU.ART");
    String[][] gradesession = new String[100][4];

    Grades grades = new Grades();




    public StuartView(String user){
        Database db = new Database();
        initMinaBetygView(user);
        initProfilView(user);

        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setSize(600,600);
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

        listCourses.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {

            }
        });

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
                Database.updatePassword(user,newPassw);
                passwordProfilLabel.setText(Database.readPassword(user));

            }
        });
        minaBetygBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tabbedPane1.setSelectedIndex(1);
            }
        });
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
    private void initMinaBetygView(String user) {
        listModel = new DefaultListModel<String>();
        listCourses.setModel(listModel);
        initCourseLabel(user);
        meritLabel.setText("Merit: "+ grades.printGPA());
    }

    /**
     * Reads grades for user in database, adds to listmodel.
     * @param user target user
     */
    public void initCourseLabel(String user) {
        listModel.removeAllElements();

        for(int i = 0; i<Database.countGrades(user)+1;i++){
            grades.addGrade(new Grade(Database.readDatabaseGrades(user)[i][0], Database.readDatabaseGrades(user)[i][2], Database.readDatabaseGrades(user)[i][1]));
            courselistitem = Database.readDatabaseGrades(user)[i][0]+" "+Database.readDatabaseGrades(user)[i][1]+ " "+ Database.readDatabaseGrades(user)[i][2];
            listModel.addElement(courselistitem);

        }

    }


    public Component showView() {
        StuartPanel.setBackground(Color.WHITE);

        return StuartPanel;
    }

}


