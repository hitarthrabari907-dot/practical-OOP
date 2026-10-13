import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Arrays;

public class SerializationDemo {
    private static final String FILE_NAME = "students.ser";

    static class Student implements Serializable {
        private static final long serialVersionUID = 1L;

        private final String name;
        private final int rollNumber;
        private transient String password;

        Student(String name, int rollNumber, String password) {
            this.name = name;
            this.rollNumber = rollNumber;
            this.password = password;
        }

        @Override
        public String toString() {
            return "Student{name='" + name + "', rollNumber=" + rollNumber
                    + ", password='" + password + "'}";
        }
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Student[] students = {
            new Student("Asha", 101, "asha-secret"),
            new Student("Ravi", 102, "ravi-secret")
        };

        try (ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            output.writeObject(students);
        }
        System.out.println("Saved students to " + FILE_NAME + ": " + Arrays.toString(students));

        Student[] loadedStudents;
        try (ObjectInputStream input = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            loadedStudents = (Student[]) input.readObject();
        }

        System.out.println("Loaded students: " + Arrays.toString(loadedStudents));
        System.out.println("Name and roll number were restored; transient passwords are null.");
    }
}
