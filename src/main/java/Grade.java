import javax.swing.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

class Grade {
    private String kurs;
    private String lettergrade;
    private double grade;
    private String kurspoäng;

    /**
     * Constructs a default grades object
     */
    public Grade() {
        this.kurs = "empty";
        this.lettergrade = "F";
        this.grade = 0;
        this.kurspoäng = "100";
    }

    /**
     * Constructs a grade object with specified values
     * @param k The course name
     * @param lg The grade (has to be either "A","B","C","D","E" or "F")
     * @param kp The course credits
     * @throws ArithmeticException if the grade is not as specified
     * @throws ArithmeticException if the course credits is not numeric
     */
    public Grade(String k, String lg, String kp) {
        if(!lg.equals("A") || !lg.equals("B") || !lg.equals("C") || !lg.equals("D") || !lg.equals("E") || !lg.equals("F")){
            JPanel panel = new JPanel();
            JOptionPane.showMessageDialog(panel, "The grade must be either A,B,C,D,E or F in capital letter", "Warning", JOptionPane.WARNING_MESSAGE);
            throw new ArithmeticException("Bad grade input");
        }
        try{
            Integer.parseInt(kp);
        }catch(NumberFormatException e){
            JPanel panel = new JPanel();
            JOptionPane.showMessageDialog(panel, "The course credits must be a positive number", "Warning", JOptionPane.WARNING_MESSAGE);
            throw new ArithmeticException("Bad course credits input");
        }
        this.kurs = k;
        this.lettergrade = lg;
        this.kurspoäng = kp;
        this.grade = gradeToInt(this.lettergrade);
    }

    private double gradeToInt(String grade) {
        if (Objects.equals(grade, "A")) {
            this.grade = 20;
        } else if (Objects.equals(grade, "B")) {
            this.grade = 17.5;
        } else if (Objects.equals(grade, "C")) {
            this.grade = 15;
        } else if (Objects.equals(grade, "D")) {
            this.grade = 12.5;
        } else if (Objects.equals(grade, "E")) {
            this.grade = 10;
        } else if (Objects.equals(grade, "F")) {
            this.grade = 0;
        }
        return this.grade;
    }

    /**
     * Getter for the coursename
     * @return
     */
    public String getkurs(){
        return this.kurs;
    }

    /**
     * Getter for the course grade
     * @return
     */
    public String getGrade(){
        return this.lettergrade;
    }

    /**
     * Getter for the coursepoints
     * @return
     */
    public String getKurspoäng(){
        return this.kurspoäng;
    }

    /**
     * Getter for the course grade as a double
     * @return
     */
    public Double getGradeInt() {
        return this.grade;
    }
}