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
        try(Scanner scanner = new Scanner(new File("src/dBase.txt"));) {
            //Scanner scanner = new Scanner(new File("src/dBase.txt"));
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
                        gs.addGrade(new Grade(g.kurs,g.kurspoäng,g.lettergrade));
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
        try (Scanner scanner = new Scanner(new File("src/dBase.txt"));){
            //Scanner scanner = new Scanner(new File("src/dBase.txt"));
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

        try (Scanner scanner = new Scanner(new File("src/dBase.txt"));){
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

    //update and add model methods ------------------------------------------------------------------------------------------------------
    public static void dBaseUpdater(String updateType, String userID, String updateData){
        File originalFile = new File("src/dBase.txt");
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
                pw.println(line);
                pw.flush();
            }
            //pw.close();
            //br.close();

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
    public static void addUser(String userID, String email, String fName, String lName){
        File originalFile = new File("src/dBase.txt");
        File tempFile = new File("tempfile.txt");
        try ( BufferedReader br = new BufferedReader(new FileReader(originalFile));
              PrintWriter pw = new PrintWriter(new FileWriter(tempFile));){

            String line = null;
            int i = 0;
            while ((line = br.readLine()) != null) {
                if(line.contains(userID)){
                    throw new ArithmeticException("UserID already exists");
                }
                if(line.contains(email) && email.length() == line.length()){
                    throw new ArithmeticException("Email already exists");
                }
                pw.println(line);
                pw.flush();
            }
            pw.println(userID);
            pw.println("email" + " " + email);
            pw.println("fName" + " " + fName);
            pw.println("lName" + " " + lName);
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
    public static void addGrade(String userID, String course, String credits, String grade){
        File originalFile = new File("src/dBase.txt");
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
                        throw new ArithmeticException("Coursename already exists");
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
    public static void addTargetEducation(String userID, String programName, String university, String programCredits, String admission2017, String admission2018, String admission2019, String admission2020, String admission2021){
        File originalFile = new File("src/dBase.txt");
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
                    line = line.substring(0, line.lastIndexOf("@"))+ "@" + programName + ":" + university + ":" + programCredits + ":" + admission2017 + ":" + admission2018 + ":" + admission2019 + ":" + admission2020 + ":" + admission2021 + "@";
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
        File originalFile = new File("src/dBase.txt");
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
                    line = line.substring(0,charIndex(line, course,0)-1) + line.substring(line.indexOf("@", charIndex(line, course,0)),line.lastIndexOf("@"));
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
            Scanner scanner = new Scanner(new File("src/dBase.txt"));
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
            Scanner scanner = new Scanner(new File("src/dBase.txt"));
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
}