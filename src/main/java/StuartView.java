import org.imgscalr.Scalr;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.DecimalFormat;

import static java.nio.file.LinkOption.NOFOLLOW_LINKS;
import static java.nio.file.StandardCopyOption.COPY_ATTRIBUTES;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

public class StuartView extends JFrame{
    private JTabbedPane tabbedPane1;
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
    private JLabel sumKp;
    private JList<String> chatMessages;
    private JTextField chatField;
    private JButton sendButton;
    private JPanel chatPanel;
    private JButton redigeraProfilButton;
    private JButton doneEdit;
    private JPanel ProfileView;
    private JPanel userCard;
    private JPanel profilPanel;
    private JTable compareMeritTable;
    private DefaultTableModel cmpModel;
    private DefaultListModel<String> chatModel;
    private String courselistitem;


    JFrame frame = new JFrame("STU.ART");
    Grades grades = new Grades();
    Database db = new Database();

    private static final DecimalFormat df = new DecimalFormat("0.00");


    public StuartView(String user) throws IOException {


        //MyCellRenderer cellRenderer = new MyCellRenderer(80);
        //chatMessages.setCellRenderer(cellRenderer);
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
                        updateAntagningsView(query);
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

            private void updateAntagningsView(String query) throws IOException {

                    Scraper search = new Scraper("BI", query);
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
        chatMessages.addComponentListener(new ComponentAdapter() {
        });
        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String message = chatField.getText();
                chatModel.addElement(message);
                if(message.equals("help")){
                    chatModel.addElement("Tjena! På denna sidan kan du i denna versionen bara söka efter två utbildningar, testa sök på Chalmers eller Handels.");
                }
                else if(message.equals("hjälp")){
                    chatModel.addElement("Tjena! På denna sidan kan du i denna versionen bara söka efter två utbildningar, testa sök på Chalmers eller Handels.");
                }
                else if(!message.equals("")){
                    try {
                        int responseTime =(int) ((Math.random() * (2000 - 900)) + 900);
                        Thread.sleep(responseTime);
                        Quotes q = new Quotes();
                        chatModel.addElement(q.getRes());
                    } catch (InterruptedException | IOException ex) {
                        ex.printStackTrace();
                    }

                }



            }
        });
        redigeraProfilButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateEmail.setVisible(true);
                updatePassword.setVisible(true);
                ProfileButton.setVisible(true);
                redigeraProfilButton.setVisible(false);
                doneEdit.setVisible(true);

            }
        });
        doneEdit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateEmail.setVisible(false);
                updatePassword.setVisible(false);
                ProfileButton.setVisible(false);
                redigeraProfilButton.setVisible(true);
                doneEdit.setVisible(false);

            }
        });
    }


    private void updateCourseLabel(String user) {
        int kp = 0;
        Database db = new Database();
        courseModel.setRowCount(0);
        grades = db.readDatabaseGrades(user);
        for(Grade g : grades){
            courseModel.addRow(new Object[]{g.kurs,g.lettergrade,g.kurspoäng});
            kp += Integer.parseInt(g.kurspoäng);
        }
        sumKp.setText("Summa kurspoäng: " + kp);
        meritLabel.setText("Merit: "+ df.format(grades.printGPA()));
        meritProfilLabel.setText("Merit: "+ df.format(grades.printGPA()));
        meritLabelAntLabel.setText("Merit: "+df.format(grades.printGPA()));
    }

    private boolean searchValid(String query) {
        if(query.equals("Chalmers")){
            return true;
        }
        else if(query.equals("Handels")){
            return true;
        }
        else{
            return false;
        }
    }


    private void initAntagningsView(String user) {
        int kp = 0;
        chatModel = new DefaultListModel<>();
        chatMessages.setModel(chatModel);
        utbModel = new DefaultTableModel(
                null,
                new String[]{"Universitet","Program","Högskolepoäng"}
        );
        utbTable.setModel(utbModel);
        antModel = new DefaultTableModel(
                null,
                new String[]{"År","Antagningspoäng"});
        antTable.setModel(antModel);

        cmpModel = new DefaultTableModel(
                null,
                new String[]{"Merit jämförelse"});
        compareMeritTable.setModel(cmpModel);

        for(Grade g : grades){
            kp += Integer.parseInt(g.kurspoäng);
        }


        sumKp.setText("Summa kurspoäng: " + kp);
        meritLabelAntLabel.setText("Merit: "+df.format(grades.printGPA()));

    }

    private void initProfilView(String user) {
        nameLabel.setText(Database.readFirstName(user)+" "+Database.readLastName(user));
        emailLabel.setText(Database.readEmail(user));
        passwordProfilLabel.setText(Database.readPassword(user));
        meritProfilLabel.setText((df.format(grades.printGPA())));
        //hide edit profile options
        updateEmail.setVisible(false);
        updatePassword.setVisible(false);
        ProfileButton.setVisible(false);
        doneEdit.setVisible(false);
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

    private void initProfilePic(String user) throws IOException {
        try{

            BufferedImage master =  Scalr.resize(ImageIO.read(new File("ProfilePictures/"+ user +".jpg")),100);


            int diameter = Math.min(master.getWidth(), master.getHeight());
            BufferedImage mask = new BufferedImage(master.getWidth(), master.getHeight(), BufferedImage.TYPE_INT_ARGB);

            Graphics2D g2d = mask.createGraphics();
            applyQualityRenderingHints(g2d);
            g2d.fillOval(0, 0, diameter - 1, diameter - 1);
            g2d.dispose();

            BufferedImage masked = new BufferedImage(diameter, diameter, BufferedImage.TYPE_INT_ARGB);
            g2d = masked.createGraphics();
            applyQualityRenderingHints(g2d);
            int x = (diameter - master.getWidth()) / 2;
            int y = (diameter - master.getHeight()) / 2;
            g2d.drawImage(master, x, y, null);
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.DST_IN));
            g2d.drawImage(mask, 0, 0, null);
            g2d.dispose();


            Scalr.resize(masked,10);
            //ImageIcon imageIcon = new ImageIcon(new ImageIcon("ProfilePictures/"+ user +".jpg").getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT));
            ImageIcon imageIcon = new ImageIcon(masked);
            ImageLabel.setIcon(imageIcon);
        } catch (IOException e){
            BufferedImage master = Scalr.resize(ImageIO.read(new File("ProfilePictures/004.jpg")),100);


            int diameter = Math.min(master.getWidth(), master.getHeight());
            BufferedImage mask = new BufferedImage(master.getWidth(), master.getHeight(), BufferedImage.TYPE_INT_ARGB);

            Graphics2D g2d = mask.createGraphics();
            applyQualityRenderingHints(g2d);
            g2d.fillOval(0, 0, diameter - 1, diameter - 1);
            g2d.dispose();

            BufferedImage masked = new BufferedImage(diameter, diameter, BufferedImage.TYPE_INT_ARGB);
            g2d = masked.createGraphics();
            applyQualityRenderingHints(g2d);
            int x = (diameter - master.getWidth()) / 2;
            int y = (diameter - master.getHeight()) / 2;
            g2d.drawImage(master, x, y, null);
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.DST_IN));
            g2d.drawImage(mask, 0, 0, null);
            g2d.dispose();


            //ImageIcon imageIcon = new ImageIcon(new ImageIcon("ProfilePictures/"+ user +".jpg").getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT));

            ImageIcon imageIcon = new ImageIcon(masked);
            ImageLabel.setIcon(imageIcon);

        }

        //ImageLabel.setBorder(new RoundedBorder(Color.WHITE,7));

    }

    public static void applyQualityRenderingHints(Graphics2D g2d) {

        g2d.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
        g2d.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);

    }


    public Component showView() {
        StuartPanel.setBackground(Color.WHITE);
        return StuartPanel;
    }



}


