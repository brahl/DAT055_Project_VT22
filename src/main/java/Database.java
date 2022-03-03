import javax.swing.*;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;

public class Database {
    Grades gs = new Grades();
    Grade g = new Grade();
    FavEducations fes = new FavEducations();
    FavEducation fe = new FavEducation();

    public Database(){}

    //reader methods ------------------------------------------------------------------------------------------------------
    public static String readEmail(String userID){
      return readDatabase(0, userID);
    }
    public static String readFirstName(String userID){
        return readDatabase(1, userID);
    }
    public static String readLastName(String userID){
        return readDatabase(2, userID);
    }
    public static String readPassword(String userID){
        return readDatabase(3, userID);
    }

    //Update caller methods ------------------------------------------------------------------------------------------------------
    public static void updateEmail(String userID, String email){
        dBaseUpdater("email", userID, email);
        System.out.println("Email was succesfully updated");
    }
    public static void updateFirstName(String userID, String firstName){
        dBaseUpdater("fName", userID, firstName);
        System.out.println("Firstname was succesfully updated");
    }
    public static void updateLastName(String userID, String lastName){
        dBaseUpdater("lname", userID, lastName);
        System.out.println("Lastname was succesfully updated");
    }
    public static void updatePassword(String userID, String lastName){
        dBaseUpdater("password", userID, lastName);
        System.out.println("Password was succesfully updated");
    }

    //reader model methods ------------------------------------------------------------------------------------------------------
    public Grades readDatabaseGrades(String userID){
        try(Scanner scanner = new Scanner(new File("src/main/java/dBase.txt"));) {
            //Scanner scanner = new Scanner(new File("src/main/java/dBase.txt"));
            while (scanner.hasNextLine()) {
                if(scanner.nextLine().equals(userID)) {
                    for(int i = 0; i < 4; i++){
                        scanner.nextLine();;
                    }
                    String result = scanner.nextLine();
                    int amountOfGrades = 0;
                    for (int i = 0; i < result.length(); i++) {
                        if (result.charAt(i) == '@') {
                            amountOfGrades++;
                        }
                    }
                    int i = 0;
                    int j = 0;
                    while(i <= amountOfGrades-2){
                        g.kurs = result.substring(charIndex(result, "@", i)+1, charIndex(result, ":", j));
                        g.kurspoäng = result.substring(charIndex(result, ":", j)+1, charIndex(result, ":", j+1));
                        g.lettergrade = result.substring(charIndex(result, ":", j+1)+1, charIndex(result, "@", i+1));
                        gs.addGrade(new Grade(g.kurs,g.lettergrade,g.kurspoäng));
                        i++;
                        j = j+2;
                    }
                }
            }
            //scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return gs;
    }
    public FavEducations readDatabaseFavCourses(String userID){
        try (Scanner scanner = new Scanner(new File("src/main/java/dBase.txt"));){
            while (scanner.hasNextLine()) {
                if(scanner.nextLine().equals(userID)) {
                    for(int i = 0; i < 5; i++){
                        scanner.nextLine();;
                    }
                    String result = scanner.nextLine();
                    int amountOfGrades = 0;
                    for (int i = 0; i < result.length(); i++) {
                        if (result.charAt(i) == '@') {
                            amountOfGrades++;
                        }
                    }
                    int i = 0;
                    int j = 0;
                    while(i <= amountOfGrades-2){
                        fe.pName = result.substring(charIndex(result, "@", i)+1, charIndex(result, ":", j));
                        fe.uni = result.substring(charIndex(result, ":", j)+1, charIndex(result, ":", j+1));
                        fe.credits = result.substring(charIndex(result, ":", j+1)+1, charIndex(result, ":", j+2));
                        fe.admissionM5 = result.substring(charIndex(result, ":", j+2)+1, charIndex(result, ":", j+3));
                        fe.admissionM4 = result.substring(charIndex(result, ":", j+3)+1, charIndex(result, ":", j+4));
                        fe.admissionM3 = result.substring(charIndex(result, ":", j+4)+1, charIndex(result, ":", j+5));
                        fe.admissionM2 = result.substring(charIndex(result, ":", j+5)+1, charIndex(result, ":", j+6));
                        fe.admissionM1 = result.substring(charIndex(result, ":", j+6)+1, charIndex(result, "@", i+1));
                        fes.addFavEducations(fe);
                        i++;
                        j = j+8;
                    }
                }
            }
            //scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return fes;
    }
    public static String readDatabase(int readType, String userID){
        int index = 0;
        if(readType == 0 || readType == 1 || readType == 2 || readType == 3){
            index = readType;
        }else{
            throw new ArithmeticException("The readtype you have specified does not exist");
        }
        String finalResult = "";

        try (Scanner scanner = new Scanner(new File("src/main/java/dBase.txt"));){
            while (scanner.hasNextLine()) {
                if(scanner.nextLine().equals(userID)) {
                    while(index != 0) {
                        scanner.nextLine();
                        index--;
                    }
                    String result = scanner.nextLine();
                    finalResult = result.substring(result.indexOf(" "));
                }
            }
            //scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return finalResult;
    }

    //update, add and remove model methods ------------------------------------------------------------------------------------------------------
    public static void dBaseUpdater(String updateType, String userID, String updateData){
        if(updateData.equals("")){
            JPanel panel = new JPanel();
            JOptionPane.showMessageDialog(panel, "You can't update your " + updateType + " to nothing :(", "Warning", JOptionPane.WARNING_MESSAGE);
            throw new ArithmeticException("updateData can't be null");
        }

        File originalFile = new File("src/main/java/dBase.txt");
        File tempFile = new File("tempfile.txt");
        try (BufferedReader br = new BufferedReader(new FileReader(originalFile));
             PrintWriter pw = new PrintWriter(new FileWriter(tempFile));){

            String line = null;
            int i = 0;
            while ((line = br.readLine()) != null) {
                if(line.contains(userID)) {
                    i++;
                }
                if (line.contains(updateType) && i == 1) {
                    line = line.substring(0, line.lastIndexOf(" ")) + " " + updateData;
                    i++;
                }
                System.out.println(line);
                pw.println(line);
                pw.flush();
            }
            pw.close();
            br.close();

            // Delete the original file
            if (!originalFile.delete()) {
                System.out.println("Could not delete file");
                return;
            }

            // Rename the new file to the filename the original file had.
            if (!tempFile.renameTo(originalFile))
                System.out.println("Could not rename file");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void addUser(String email, String fName, String lName, String password){
        File originalFile = new File("src/main/java/dBase.txt");
        File tempFile = new File("tempfile.txt");
        try ( BufferedReader br = new BufferedReader(new FileReader(originalFile));
              PrintWriter pw = new PrintWriter(new FileWriter(tempFile));){

            String line = null;
            int i = 0;
            while ((line = br.readLine()) != null) {
                if(line.contains(highestUserID())){
                    throw new ArithmeticException("UserID already exists");
                }
                if(line.contains(email) && email.length() == line.length()-6){
                    throw new ArithmeticException("Email already exists");
                }
                pw.println(line);
                pw.flush();
            }
            pw.println(highestUserID());
            pw.println("email" + " " + email);
            pw.println("fName" + " " + fName);
            pw.println("lName" + " " + lName);
            pw.println("password" + " " + password);
            pw.println("grades" + " " + "@");
            pw.println("fEdus" + " " + "@");
            pw.print("\n");
            pw.close();
            br.close();

            // Delete the original file
            if (!originalFile.delete()) {
                System.out.println("Could not delete file");
                return;
            }
            // Rename the new file to the filename the original file had.
            if (!tempFile.renameTo(originalFile))
                System.out.println("Could not rename file");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static boolean addGrade(String userID, String course, String credits, String grade){
        if(course.contains("@") || course.contains(":") ||
           credits.contains("@") || credits.contains(":") ||
           grade.contains("@") || grade.contains(":")){
            JPanel panel = new JPanel();
            JOptionPane.showMessageDialog(panel, "@ and : are forbidden characters", "Warning", JOptionPane.WARNING_MESSAGE);
            return false;
            //throw new ArithmeticException("Coursename already exists");
        }
        File originalFile = new File("src/main/java/dBase.txt");
        File tempFile = new File("tempfile.txt");

        try ( BufferedReader br = new BufferedReader(new FileReader(originalFile));
              PrintWriter pw = new PrintWriter(new FileWriter(tempFile));){

            String line = null;
            int i = 0;
            while ((line = br.readLine()) != null) {
                if(line.contains(userID)) {
                    i++;
                }
                if (line.contains("grades") && i == 1) {
                    if(line.contains(course)){
                        JPanel panel = new JPanel();
                        JOptionPane.showMessageDialog(panel, "You already have a grade for the course: " + course + " :)", "Warning", JOptionPane.WARNING_MESSAGE);
                        return false;
                        //throw new ArithmeticException("Coursename already exists");

                    }
                    line = line.substring(0, line.lastIndexOf("@"))+ "@" + course + ":" + credits + ":" + grade + "@";
                    i++;
                }
                pw.println(line);
                pw.flush();
            }
            pw.close();
            br.close();

            // Delete the original file
            if (!originalFile.delete()) {
                System.out.println("Could not delete file");
                //here was empty
                return false;
            }

            // Rename the new file to the filename the original file had.
            if (!tempFile.renameTo(originalFile))
                System.out.println("Could not rename file");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }
    public static void addTargetEducation(String userID, String programName, String university, String programCredits, String admissionM5, String admissionM4, String admissionM3, String admissionM2, String admissionM1){
        if(programName.contains("@") || programName.contains(":") ||
           university.contains("@") || university.contains(":") ||
           programCredits.contains("@") || programCredits.contains(":") ||
           admissionM5.contains("@") || admissionM5.contains(":") ||
           admissionM4.contains("@") || admissionM4.contains(":") ||
           admissionM3.contains("@") || admissionM3.contains(":") ||
           admissionM2.contains("@") || admissionM2.contains(":") ||
           admissionM1.contains("@") || admissionM1.contains(":")){
            JPanel panel = new JPanel();
            JOptionPane.showMessageDialog(panel, "@ and : are forbidden characters", "Warning", JOptionPane.WARNING_MESSAGE);
            throw new ArithmeticException("Coursename already exists");
        }

        File originalFile = new File("src/main/java/dBase.txt");
        File tempFile = new File("tempfile.txt");
        try (BufferedReader br = new BufferedReader(new FileReader(originalFile));
             PrintWriter pw = new PrintWriter(new FileWriter(tempFile));){

            String line = null;
            int i = 0;
            while ((line = br.readLine()) != null) {
                if(line.contains(userID)) {
                    i++;
                }
                if (line.contains("fEdus") && i == 1) {
                    line = line.substring(0, line.lastIndexOf("@"))+ "@" + programName + ":" + university + ":" + programCredits + ":" + admissionM5 + ":" + admissionM4 + ":" + admissionM3 + ":" + admissionM2 + ":" + admissionM1 + "@";
                    i++;
                }
                pw.println(line);
                pw.flush();
            }
            pw.close();
            br.close();

            // Delete the original file
            if (!originalFile.delete()) {
                System.out.println("Could not delete file");
                return;
            }
            // Rename the new file to the filename the original file had.
            if (!tempFile.renameTo(originalFile))
                System.out.println("Could not rename file");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void removeGrade(String userID, String course){
        File originalFile = new File("src/main/java/dBase.txt");
        File tempFile = new File("tempfile.txt");
        try (BufferedReader br = new BufferedReader(new FileReader(originalFile));
             PrintWriter pw = new PrintWriter(new FileWriter(tempFile));){

            String line = null;
            int i = 0;
            while ((line = br.readLine()) != null) {
                if(line.contains(userID)) {
                    i++;
                }
                if (line.contains("grades") && i == 1) {
                    if(!line.contains(course)){
                        JPanel panel = new JPanel();
                        JOptionPane.showMessageDialog(panel, "You have not taken this the course: " + course + " yet so you can't remove it :(", "Warning", JOptionPane.WARNING_MESSAGE);
                        throw new ArithmeticException("Coursename does not exist");
                    }

                    line = line.substring(0,charIndex(line, course,0)-1) + line.substring(line.indexOf("@", charIndex(line, course,0)),line.lastIndexOf("@")) + "@";

                    i++;
                }
                pw.println(line);
                pw.flush();
            }
            pw.close();
            br.close();
            // Delete the original file
            if (!originalFile.delete()) {
                System.out.println("Could not delete file");
                return;
            }
            // Rename the new file to the filename the original file had.
            if (!tempFile.renameTo(originalFile))
                System.out.println("Could not rename file");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Login
    public static String userExists(String email){
        String userID = "";
        String temp = "";
        try (Scanner scanner = new Scanner(new File("src/main/java/dBase.txt"));){
            while (scanner.hasNextLine()) {
                String store = scanner.nextLine();
                if(scanner.hasNextLine()){
                    temp = scanner.nextLine();
                }

                if(temp.contains(email) && temp.length()-6 == email.length()) {
                    userID = store;
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        if(userID.equals("")){
            JPanel panel = new JPanel();
            JOptionPane.showMessageDialog(panel, "No user with this email exists", "Warning", JOptionPane.WARNING_MESSAGE);
            throw new ArithmeticException("Wrong email");
        }
        return userID;
    }
    public static boolean passwordMatch(String email, String password){
        try (Scanner scanner = new Scanner(new File("src/main/java/dBase.txt"));){
            while (scanner.hasNextLine()) {
                if(scanner.nextLine().contains(email)) {
                    scanner.nextLine();
                    scanner.nextLine();
                    String result = scanner.nextLine();
                    if(result.contains(password) && result.length()-9 == password.length()){
                        return true;
                    }
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    //Misc Methods ------------------------------------------------------------------------------------------------------
    private static int charIndex(String result, String charfind, int position){
        int index = -1;

        for(int i = -1; i < position; i++){
            index = result.indexOf(charfind, index+1);
        }
        return index;
    }
    public static int countGrades(String userID){

        int amountOfGrades = 0;

        try {
            Scanner scanner = new Scanner(new File("src/main/java/dBase.txt"));
            while (scanner.hasNextLine()) {
                if(scanner.nextLine().equals(userID)) {
                    for(int i = 0; i < 4; i++){
                        scanner.nextLine();;
                    }
                    String result = scanner.nextLine();

                    for (int i = 0; i < result.length(); i++) {
                        if (result.charAt(i) == '@') {
                            amountOfGrades++;
                        }
                    }
                }
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return amountOfGrades-2;
    }
    public static int countFavEducations(String userID){

        int amountOfFavEdu = 0;

        try {
            Scanner scanner = new Scanner(new File("src/main/java/dBase.txt"));
            while (scanner.hasNextLine()) {
                if(scanner.nextLine().equals(userID)) {
                    for(int i = 0; i < 5; i++){
                        scanner.nextLine();;
                    }
                    String result = scanner.nextLine();

                    for (int i = 0; i < result.length(); i++) {
                        if (result.charAt(i) == '@') {
                            amountOfFavEdu++;
                        }
                    }
                }
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return amountOfFavEdu-2;
    }
    public static String highestUserID(){
        int highestID = 0;
        String highestIDString = "000";
        try (Scanner scanner = new Scanner(new File("src/main/java/dBase.txt"));){
        while (scanner.hasNextLine()) {
            String result = scanner.nextLine();

            if(result.length() == 3 && scanner.nextLine().contains("email")) {
                if(Integer.parseInt(result) > highestID){
                    highestID = Integer.parseInt(result);
                    if(result.contains("00") && !result.contains("9")){
                        highestIDString = "00" + (highestID+1);
                    }
                    if(!result.contains("00") && result.contains("0") && !result.contains("99")){
                        highestIDString = "0" + (highestID+1);
                    }
                    if(!result.contains("0")){
                        highestIDString = Integer.toString(highestID+1);
                    }

                }
            }
        }
    } catch (FileNotFoundException e) {
        e.printStackTrace();
    }
        return highestIDString;
    }
}

