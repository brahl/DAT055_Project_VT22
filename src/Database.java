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
      return readDatabase("email", userID);
    }
    public static String readFirstName(String userID){
        return readDatabase("fName", userID);
    }
    public static String readLastName(String userID){
        return readDatabase("lName", userID);
    }
    public static String readPassword(String userID){
        return readDatabase("password", userID);
    }
    /* public static void readGrades(String userID){
       String user = userID;
       System.out.println(user + "'s courses:");
       for(int i = 0; i < 10; i++){


          // if(readDatabaseGrades(user)[i][0] == null){
            //   break;
           //}

          // System.out.println("  CourseName: " + readDatabaseGrades(user)[i][0]);
          // System.out.println("        Credits: " + readDatabaseGrades(user)[i][1]);
          // System.out.println("        Grade: " + readDatabaseGrades(user)[i][2]);

       }
   }

    */
    /*public static void readTargetEducation(String userID){
       String user = userID;
       System.out.println(user + "'s target education:");
       for(int i = 0; i < 20; i++){


           if(readDatabaseFavCourses(user)[i][0] == null){
               break;
           }

           System.out.println("  ProgramName: " + readDatabaseFavCourses(user)[i][0]);
           System.out.println("        UniName: " + readDatabaseFavCourses(user)[i][1]);
           System.out.println("        ProgramCredits: " + readDatabaseFavCourses(user)[i][2]);
           System.out.println("        AdmissionPoints 2017: " + readDatabaseFavCourses(user)[i][3]);
           System.out.println("        AdmissionPoints 2018: " + readDatabaseFavCourses(user)[i][4]);
           System.out.println("        AdmissionPoints 2019: " + readDatabaseFavCourses(user)[i][5]);
           System.out.println("        AdmissionPoints 2020: " + readDatabaseFavCourses(user)[i][6]);
           System.out.println("        AdmissionPoints 2021: " + readDatabaseFavCourses(user)[i][7]);

       }
   }*/

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
        try {
            Scanner scanner = new Scanner(new File("src/dBase.txt"));
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
                        gs.addGrade(g);
                        i++;
                        j = j+2;
                    }
                }
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return gs;
    }
    public FavEducations readDatabaseFavCourses(String userID){
        try {
            Scanner scanner = new Scanner(new File("src/dBase.txt"));
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
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return fes;
    }
    public static String readDatabase(String readType, String userID){
        if(readType != "email" || readType != "fName" || readType != "lName" || readType != "password"){
            //throw new Exception("there is no readtype named specified as:" + readType);
        }
        String finalResult = "";
        int index = 0;

        if(readType == "email"){
            index = 0;
        }
        if(readType == "fName"){
            index = 1;
        }
        if(readType == "lName"){
            index = 2;
        }
        if(readType == "password"){
            index = 3;
        }
        try {
            Scanner scanner = new Scanner(new File("src/dBase.txt"));
            while (scanner.hasNextLine()) {
                if(scanner.nextLine().equals(userID)) {
                    while(index != 0) {
                        scanner.nextLine();
                        index--;
                    }
                    String result = scanner.nextLine();
                    finalResult = result.substring(result.indexOf(" "));
                    System.out.println(finalResult);
                }
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return finalResult;
    }

    //update and add model methods ------------------------------------------------------------------------------------------------------
    public static void dBaseUpdater(String updateType, String userID, String updateData){
        try {

            Scanner scanner = new Scanner(System.in);

            File originalFile = new File("src/dBase.txt");

            BufferedReader br = new BufferedReader(new FileReader(originalFile));
            // Construct the new file that will later be renamed to the original
            // filename.
            File tempFile = new File("tempfile.txt");
            PrintWriter pw = new PrintWriter(new FileWriter(tempFile));

            String line = null;
            // Read from the original file and write to the new
            // unless content matches data to be removed.
            int i = 0;
            while ((line = br.readLine()) != null) {
                if(line.contains(userID)) {
                    i++;
                }
                if (line.contains(updateType) && i == 1) {
                    String strCurrentEmail = line.substring(line.lastIndexOf(" "), line.length());
                    if (strCurrentEmail != null || !strCurrentEmail.trim().isEmpty()) {
                        System.out.println(strCurrentEmail);
                        line = line.substring(0, line.lastIndexOf(" ")) + " " + updateData;
                    }
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
    public static void addUser(String userID, String email, String fName, String lName){
        try {

            Scanner scanner = new Scanner(System.in);

            File originalFile = new File("src/dBase.txt");

            BufferedReader br = new BufferedReader(new FileReader(originalFile));
            // Construct the new file that will later be renamed to the original
            // filename.
            File tempFile = new File("tempfile.txt");
            PrintWriter pw = new PrintWriter(new FileWriter(tempFile));

            String line = null;
            // Read from the original file and write to the new
            // unless content matches data to be removed.
            int i = 0;
            while ((line = br.readLine()) != null) {

                //checks for invalid inputs
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
        try {

            Scanner scanner = new Scanner(System.in);

            File originalFile = new File("src/dBase.txt");

            BufferedReader br = new BufferedReader(new FileReader(originalFile));
            // Construct the new file that will later be renamed to the original
            // filename.
            File tempFile = new File("tempfile.txt");
            PrintWriter pw = new PrintWriter(new FileWriter(tempFile));

            String line = null;
            // Read from the original file and write to the new
            // unless content matches data to be removed.
            int i = 0;
            while ((line = br.readLine()) != null) {
                if(line.contains(userID)) {
                    i++;
                }
                if (line.contains("grades") && i == 1) {
                    String strCurrentEmail = line.substring(line.lastIndexOf("@"), line.length());
                    if (strCurrentEmail != null || !strCurrentEmail.trim().isEmpty()) {
                        System.out.println(strCurrentEmail);
                        line = line.substring(0, line.lastIndexOf("@"))+ "@" + course + ":" + credits + ":" + grade + "@";
                    }
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
        try {

            Scanner scanner = new Scanner(System.in);

            File originalFile = new File("src/dBase.txt");

            BufferedReader br = new BufferedReader(new FileReader(originalFile));
            // Construct the new file that will later be renamed to the original
            // filename.
            File tempFile = new File("tempfile.txt");
            PrintWriter pw = new PrintWriter(new FileWriter(tempFile));

            String line = null;
            // Read from the original file and write to the new
            // unless content matches data to be removed.
            int i = 0;
            while ((line = br.readLine()) != null) {
                if(line.contains(userID)) {
                    i++;
                }
                if (line.contains("fEdus") && i == 1) {
                    String strCurrentEmail = line.substring(line.lastIndexOf("@"), line.length());
                    if (strCurrentEmail != null || !strCurrentEmail.trim().isEmpty()) {
                        System.out.println(strCurrentEmail);
                        line = line.substring(0, line.lastIndexOf("@"))+ "@" + programName + ":" + university + ":" + programCredits + ":" + admission2017 + ":" + admission2018 + ":" + admission2019 + ":" + admission2020 + ":" + admission2021 + "@";
                    }
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

        //NOT IMPLEMENTED
        public static void changeGrade(String userID, String course, String grade){

    }
        public static void removeGrade(String userID, String course, String grade){

    }

    //Misc Methods ------------------------------------------------------------------------------------------------------
    public static int charIndex(String result, String charfind, int position){
        int index = -1;

        for(int i = -1; i < position; i++){
            index = result.indexOf(charfind, index+1);
            //System.out.println("Index: " + index);
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
    public static int favEducations(String userID){

        int amountOfGrades = 0;

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
}