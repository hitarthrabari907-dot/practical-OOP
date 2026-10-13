// Filename: TaskManagerTest.java

public class TaskManagerTest {
    private TaskManager manager;

    public void setUp() {
        manager = new TaskManager();
    }

    public static void main(String[] args) {
        TaskManagerTest test = new TaskManagerTest();
        test.setUp();
        test.testAddIncreasesCount();
        test.setUp();
        test.testInvalidActionThrowsException();
    }

    // Test 1: Check karo ki task add karne se count badhta hai ya nahi
    public void testAddIncreasesCount() {
        assertEquals(0, manager.getTaskCount(), "Initial count should be 0");

        Task newTask = new Task("T1", "Complete Lab Assignment");
        manager.addTask(newTask);

        assertEquals(1, manager.getTaskCount(), "Count should be 1 after adding a task");
    }

    // Test 2: Check karo ki galat ID dene par exception throw hoti hai ya nahi
    public void testInvalidActionThrowsException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            manager.markTaskAsDone("INVALID_ID_123");
        });

        assertTrue(exception.getMessage().contains("not found"), "Exception message should mention task not found");
    }

    private static void assertEquals(int expected, int actual, String message) {
        if (expected != actual) {
            throw new AssertionError(message + ": expected " + expected + ", but was " + actual);
        }
    }

    private static void assertTrue(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }

    private static <T extends Throwable> T assertThrows(Class<T> expectedType, Runnable action) {
        try {
            action.run();
        } catch (Throwable exception) {
            if (expectedType.isInstance(exception)) {
                return expectedType.cast(exception);
            }
            throw new AssertionError("Expected " + expectedType.getName() + " but caught " + exception, exception);
        }
        throw new AssertionError("Expected " + expectedType.getName() + " to be thrown");
    }
}