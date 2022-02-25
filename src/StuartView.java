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
    private final String user = "001";


    private double res;
    private double count;
    JFrame frame = new JFrame("STU.ART");
    String[][] gradesession = new String[100][4];

    Grades grades = new Grades();




    public StuartView(){
        Database db = new Database();
        initMinaBetygView();
        initProfilView();

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
                newGrade(kursField.getText(),betygField.getText(),pointsField.getText());
                Database.addGrade("001",kursField.getText(),pointsField.getText(),betygField.getText());
                kursField.setText("");
                betygField.setText("");
                pointsField.setText("");
                updateCourseLabel();

            }

            private void updateCourseLabel() {
                listModel.removeAllElements();
                int n=0;
                for(Grade g : grades){
                    n++;
                    courselistitem = coursesToLabels(unpackGradeObjects(),n)+" "+pointToLabel(unpackGradeObjects(),n)+ " "+ betygToLabel(unpackGradeObjects(),n);
                    listModel.addElement(courselistitem);
                }

            }

            private String coursesToLabels(String[][] courses,int n) {
                return courses[n][1];
            }
            private String pointToLabel(String[][] courses,int n) {
                return courses[n][2];
            }
            private String betygToLabel(String[][] courses,int n) {
                return courses[n][3];
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
    }

    private void initProfilView() {
        nameLabel.setText(Database.readFirstName(user)+" "+Database.readLastName(user));
        emailLabel.setText(Database.readEmail(user));

    }


    private void initMinaBetygView() {
        listModel = new DefaultListModel<String>();
        listCourses.setModel(listModel);
        initCourseLabel();


        meritLabel.setText("Merit: "+ printGPA());




    }


    public void initCourseLabel() {
        listModel.removeAllElements();

        System.out.println(Database.countGrades(user));

        for(int i = 0; i<Database.countGrades(user)+1;i++){

            newGrade(Database.readDatabaseGrades(user)[i][0], Database.readDatabaseGrades(user)[i][2], Database.readDatabaseGrades(user)[i][1]);
            courselistitem = Database.readDatabaseGrades(user)[i][0]+" "+Database.readDatabaseGrades(user)[i][1]+ " "+ Database.readDatabaseGrades(user)[i][2];
            System.out.println(courselistitem);
            listModel.addElement(courselistitem);

        }

    }


    public void newGrade(String k, String lg, String kp){
        grades.addGrade(new Grade(k,lg,kp));
        //denna kan ligga lite var som, kör den här sålänge.
        meritLabel.setText("Merit: "+ printGPA());
    }


    public String[][] unpackGradeObjects(){
        int count = 0;
        for(Grade grade : grades){
            count++;
            if(grade.kurs != null){
                String kurs = grade.kurs;
                String point = grade.kurspoäng;
                String betyg = grade.lettergrade;
                gradesession[count][1] = kurs;
                gradesession[count][2]= point;
                gradesession[count][3]= betyg;
            }
            else{
                String kurs = " ";
                String point = " ";
                String betyg = " ";
                gradesession[count][1] = kurs;
                gradesession[count][2]= point;
                gradesession[count][3]= betyg;
            }
        }
        return gradesession;
    }

    public double printGPA(){
        try {
            for (Grade grade : grades) {
                int kp = Integer.parseInt(grade.kurspoäng);
                count += kp;
                res += grade.grade * kp;
            }
        } catch (Exception e){
            JOptionPane.showMessageDialog(this,"Fel format på input");
        }

        return (res/count);
    }

    public Component showView() {
        StuartPanel.setBackground(Color.WHITE);

        return StuartPanel;
    }

}


