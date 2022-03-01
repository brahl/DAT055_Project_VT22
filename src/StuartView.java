import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.DecimalFormat;

import static java.nio.file.LinkOption.NOFOLLOW_LINKS;
import static java.nio.file.StandardCopyOption.COPY_ATTRIBUTES;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

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

    private DefaultListModel<String> listCourseModel;
    private JList<String> listCourses;
    private JPanel StuartPanel;
    private JPanel AntagningsStat;
    private JTextField searchField;
    private JButton searchStat;
    private JComboBox urvalsAlt;


    private JLabel meritLabelAntLabel;
    private DefaultTableModel utbModel;
    private DefaultTableModel antModel;
    private JTable utbTable;
    private JTable antTable;
    private JLabel StatusLabel;
    private JLabel ImageLabel;
    private JButton ProfileButton;

    private String courselistitem;


    JFrame frame = new JFrame("STU.ART");
    Grades grades = new Grades();
    Database db = new Database();

    private static final DecimalFormat df = new DecimalFormat("0.00");

    public StuartView(String user){


        initMinaBetygView(user,db);
        initProfilView(user);
        initProfilePic(user);
        initAntagningsView(user);

        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setUndecorated(false);
        frame.setVisible(true);
        ImageIcon image = new ImageIcon("Stuart.png");
        frame.setIconImage(image.getImage());
        frame.getContentPane().setBackground(new Color(0xFFFFFFF));
        frame.add(showView());
        frame.setVisible(true);
        StatusLabel.setText("Laddar...");
        StatusLabel.setVisible(false);

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
                listCourseModel.removeAllElements();
                for(Grade g : grades){
                    courselistitem = g.kurs + " " + g.kurspoäng + " "+ g.lettergrade;
                    listCourseModel.addElement(courselistitem);
                }
                meritLabel.setText("Merit: "+ df.format(grades.printGPA()));
                meritProfilLabel.setText("Merit: "+ df.format(grades.printGPA()));
                meritLabelAntLabel.setText("Merit: "+df.format(grades.printGPA()));

            }

        });

        listCourses.addListSelectionListener(e -> {

        });


        updatePassword.addActionListener(e -> {
            String newPassw = JOptionPane.showInputDialog("Enter new password");
            Database.updatePassword(user,newPassw);
            passwordProfilLabel.setText(Database.readPassword(user));

        });
        minaBetygBtn.addActionListener(e -> tabbedPane1.setSelectedIndex(1));

        updateEmail.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String newEmail = JOptionPane.showInputDialog("Enter new email");
                Database.updateEmail(user,newEmail);
                emailLabel.setText(Database.readEmail(user));
            }
        });
        searchStat.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String query =  searchField.getText();
                try {
                    if(searchValid(query)){
                        StatusLabel.setVisible(true);
                        clearAntagningsView();
                        updateAntagningsView();
                        StatusLabel.setVisible(false);

                    }
                    else{
                        clearAntagningsView();
                    }

                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                //String search = searchField.getText();

            }

            private void clearAntagningsView() {
                utbModel.setRowCount(0);
                antModel.setRowCount(0);
            }

            private void updateAntagningsView() throws IOException {
                Scraper search = new Scraper("BI");
                utbModel.addRow(new Object[]{search.fe.uni,search.fe.pName,search.fe.credits});
                antModel.addRow(new Object[]{"2021",search.fe.admissionM1});
                antModel.addRow(new Object[]{"2020",search.fe.admissionM2});
                antModel.addRow(new Object[]{"2019",search.fe.admissionM3});
                antModel.addRow(new Object[]{"2018",search.fe.admissionM4});
                antModel.addRow(new Object[]{"2017",search.fe.admissionM5});

            }

        });

        ProfileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String newPic = JOptionPane.showInputDialog("Enter source path to your profile picture");
                    Path bytes = Files.copy(new java.io.File(newPic).toPath(),
                            new java.io.File("ProfilePictures/"+user+".jpg").toPath(),
                            REPLACE_EXISTING,
                            COPY_ATTRIBUTES,
                            NOFOLLOW_LINKS);
                } catch (IOException d) {
                    d.printStackTrace();
                }
            }
        });
    }

    private boolean searchValid(String query) {
        if(query.equals("Datateknik")){
            return true;
        }
        if(query.equals("Handels")){
            JOptionPane.showMessageDialog(null,"Konformistiska ****");
            return false;
        }
        else{
            return false;
        }
    }


    private void initAntagningsView(String user) {
        utbModel = new DefaultTableModel(
                null,
                new String[]{"Universitet","Program","Högskolepoäng"}
        );
        utbTable.setModel(utbModel);
        antModel = new DefaultTableModel(
                null,
                new String[]{"År","Antagningspoäng"});
        antTable.setModel(antModel);

        meritLabelAntLabel.setText("Merit: "+df.format(grades.printGPA()));

    }

    private void initProfilView(String user) {
        nameLabel.setText(Database.readFirstName(user)+" "+Database.readLastName(user));
        emailLabel.setText(Database.readEmail(user));
        passwordProfilLabel.setText(Database.readPassword(user));
        meritProfilLabel.setText((df.format(grades.printGPA())));
    }

    /**
     * Sets initial courselist and merit
     * @param user target user
     */
    private void initMinaBetygView(String user, Database db) {
        listCourseModel = new DefaultListModel<>();
        listCourses.setModel(listCourseModel);
        initCourseLabel(user,db);
        meritLabel.setText("Merit: "+ df.format(grades.printGPA()));
    }

    /**
     * Reads grades for user in database, adds to listmodel.
     * @param user target user
     */
    public void initCourseLabel(String user, Database db) {
        listCourseModel.removeAllElements();
        Grades gs = db.readDatabaseGrades(user);

        for(Grade g : gs){
            System.out.println(g.kurs);
            grades.addGrade(new Grade(g.kurs, g.kurspoäng, g.lettergrade));
            courselistitem = g.kurs+" "+g.kurspoäng+ " "+ g.lettergrade;
            listCourseModel.addElement(courselistitem);

        }

    }

    private void initProfilePic(String user) {
        ImageIcon imageIcon = new ImageIcon(new ImageIcon("ProfilePictures/"+ user +".jpg").getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT));
        ImageLabel.setIcon(imageIcon);
    }


    public Component showView() {
        StuartPanel.setBackground(Color.WHITE);
        return StuartPanel;
    }

}


