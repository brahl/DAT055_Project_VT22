import javax.swing.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public class Grades implements Iterable<Grade>{
    private static List<Grade> grades;

    public Grades(){
        grades = new ArrayList<Grade>();
    }
    public Grades(int kp){
        grades = new ArrayList<Grade>(kp);
    }

    public static void addGrade(Grade g){
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
                System.out.println("Grade: " + grade.getGrade());
                System.out.println("Kurspoäng: " + grade.getKurspoäng());
                int kp = Integer.parseInt(grade.getKurspoäng());
                count += kp;
                res += grade.getGradeInt() * kp;
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

