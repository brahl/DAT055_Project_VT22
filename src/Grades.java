import javax.swing.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

class Grade {
    String kurs;
    String lettergrade;
    double grade;
    String kurspoäng;

    public Grade(){
        this.kurs= "empty";
        this.lettergrade="F";
        this.grade=0;
        this.kurspoäng="100";
    }

    public Grade(String k, String lg, String kp){
        if(Objects.equals(lg, "A")){
            this.grade=20;
        }
        else if (Objects.equals(lg, "B")){
            this.grade=17.5;
        }
        else if (Objects.equals(lg, "C")){
            this.grade=15;
        }
        else if (Objects.equals(lg, "D")){
            this.grade=12.5;
        }
        else if (Objects.equals(lg, "E")){
            this.grade=10;
        }
        else if (Objects.equals(lg, "F")){
            this.grade=0;
        }
        this.kurs= k;
        this.lettergrade=lg;
        this.kurspoäng=kp;
    }



}


public class Grades implements Iterable<Grade>{
    private List<Grade> grades;

    public Grades(){
        grades = new ArrayList<Grade>();
    }
    public Grades(int kp){
        grades = new ArrayList<Grade>(kp);
    }

    public void addGrade(Grade g){
        grades.add(g);
    }
    public void removeGrade(Grade g){
        grades.remove(g);
    }

    public int length(){
        return grades.size();
    }
    public double printGPA(){
        double count=0;
        double res=0;
        try {
            for (Grade grade : grades) {
                int kp = Integer.parseInt(grade.kurspoäng);
                count += kp;
                res += grade.grade * kp;
            }
        } catch (Exception e){
            System.out.println("Fel format på input");
        }

        return (res/count);
    }





    @Override
    public Iterator<Grade> iterator() {
        return grades.iterator();
    }
}
