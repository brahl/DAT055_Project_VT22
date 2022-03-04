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

    public Grade() {
        this.kurs = "empty";
        this.lettergrade = "F";
        this.grade = 0;
        this.kurspoäng = "100";
    }

    public Grade(String k, String lg, String kp) {
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

    public String getkurs(){
        return this.kurs;
    }
    public String getGrade(){
        return this.lettergrade;
    }
    public String getKurspoäng(){
        return this.kurspoäng;
    }
    public Double getGradeInt() {
        return this.grade;
    }
}