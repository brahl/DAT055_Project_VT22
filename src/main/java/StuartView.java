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
    private JButton minaBetygBtn;
    private JButton updatePassword;
    private JButton updateEmail;
    private JLabel nameLabel;
    private JLabel meritProfilLabel;
    private JLabel passwordProfilLabel;
    private JLabel emailLabel;

    private DefaultListModel<String> listCourseModel;

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
    private DefaultTableModel courseModel;
    private JTable courseTable;
    private JButton tabortButton;

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
                boolean valid = Database.addGrade(user,kursField.getText(),pointsField.getText(),betygField.getText());
                if(valid){
                    grades.addGrade(new Grade(kursField.getText(),betygField.getText(),pointsField.getText()));
                }


                //empty input fields
                kursField.setText("");
                betygField.setText("");
                pointsField.setText("");
                //updateJlist
                updateCourseLabel(user);
            }


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
                    if(!newPic.substring(newPic.length()-3).equals("jpg")){
                        System.out.println(newPic.substring(newPic.length()-3));
                            JPanel panel = new JPanel();
                            JOptionPane.showMessageDialog(panel, "Image format has to be .jpg", "Warning", JOptionPane.WARNING_MESSAGE);
                            throw new ArithmeticException("Not jpeg");
                    }
                    Path bytes = Files.copy(new java.io.File(newPic).toPath(),
                            new java.io.File("ProfilePictures/"+user+".jpg").toPath(),
                            REPLACE_EXISTING,
                            COPY_ATTRIBUTES,
                            NOFOLLOW_LINKS);
                    initProfilePic(user);
                } catch (IOException d) {
                    d.printStackTrace();
                }
            }
        });
        tabortButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int column = 0;
                int row = courseTable.getSelectedRow();
                String value = courseTable.getModel().getValueAt(row, column).toString();
                Database.removeGrade(user,value);
                updateCourseLabel(user);
            }
        });
    }

    private void updateCourseLabel(String user) {
        Database db = new Database();
        courseModel.setRowCount(0);
        grades = db.readDatabaseGrades(user);
        for(Grade g : grades){
            courseModel.addRow(new Object[]{g.kurs,g.lettergrade,g.kurspoäng});
        }
        meritLabel.setText("Merit: "+ df.format(grades.printGPA()));
        meritProfilLabel.setText("Merit: "+ df.format(grades.printGPA()));
        meritLabelAntLabel.setText("Merit: "+df.format(grades.printGPA()));
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
        courseModel = new DefaultTableModel(
                null,
                new String[]{"Kurs","Betyg","Kurspoäng"}
        );
        courseTable.setModel(courseModel);
        initCourseLabel(user,db);
        meritLabel.setText("Merit: "+ df.format(grades.printGPA()));
    }

    /**
     * Reads grades for user in database, adds to listmodel.
     * @param user target user
     */
    public void initCourseLabel(String user, Database db) {
        courseModel.setRowCount(0);
        Grades gs = db.readDatabaseGrades(user);

        for(Grade g : gs){
            System.out.println(g.kurs);
            grades.addGrade(new Grade(g.kurs, g.lettergrade, g.kurspoäng));
            courseModel.addRow(new Object[]{g.kurs,g.lettergrade,g.kurspoäng});
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


