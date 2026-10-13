// Filename: MainApp.java

public class MainApp {
    public static void main(String[] args) {
        TaskManager manager = new TaskManager();

        System.out.println("=== Tiny Layered Task App ===");

        // Tasks add kar rahe hain
        manager.addTask(new Task("1", "Learn Java Collections"));
        manager.addTask(new Task("2", "Write JUnit Unit Tests"));

        System.out.println("Total Tasks in App: " + manager.getTaskCount());

        // Invalid action test karne ke liye try-catch
        try {
            System.out.println("Trying to complete a task with invalid ID...");
            manager.markTaskAsDone("999"); // Yeh ID exist nahi karti
        } catch (IllegalArgumentException e) {
            System.out.println("Caught Exception Successfully: " + e.getMessage());
        }
    }
}