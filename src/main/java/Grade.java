import javax.swing.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
/**
 * @author Johan Birgersson
 * @version 1
 *
 */

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
     * @param lg The grade
     * @param kp The course credits
     */
    public Grade(String k, String lg, String kp) {
        this.kurs = k;
        this.lettergrade = lg;
        this.kurspoäng = kp;
        this.grade = gradeToInt(this.lettergrade);
    }

    private double gradeToInt(String grade) {
        switch(grade){
            case "A":
                this.grade = 20;
                break;
            case "B":
                this.grade = 17.5;
                break;
            case "C":
                this.grade = 15;
                break;
            case "D":
                this.grade = 12.5;
                break;
            case "E":
                this.grade = 10;
                break;
            case "F":
                this.grade = 0;
                break;
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