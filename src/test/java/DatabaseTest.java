import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DatabaseTest {

    @Test
    void readAndUpdateEmail() {
        String x = Database.readEmail("001");
        Database.updateEmail("001",x);
        assertEquals(x, Database.readEmail("001"));

    }

    @Test
    void readAndUpdateFirstName() {
        String x = Database.readFirstName("001");
        Database.updateFirstName("001",x);
        assertEquals(x,Database.readFirstName("001"));
    }

    @Test
    void readAndUpdateLastName() {
        String x = Database.readLastName("001");
        Database.updateLastName("001",x);
        assertEquals(x,Database.readLastName("001"));
    }

    @Test
    void readAndUpdatePassword() {
        String x = Database.readPassword("001");
        Database.updatePassword("001",x);
        assertEquals(x,Database.readPassword("001"));
    }

    @Test
    void readDatabaseGrades() {
    }

    @Test
    void readDatabaseFavCourses() {
    }

    @Test
    void addUser() {
    }

    @Test
    void addGrade() {
    }

    @Test
    void addTargetEducation() {
    }

    @Test
    void removeGrade() {
    }

    @Test
    void userExists() {
    }

    @Test
    void passwordMatch() {
    }

    @Test
    void countGrades() {
    }

    @Test
    void countFavEducations() {
    }
}