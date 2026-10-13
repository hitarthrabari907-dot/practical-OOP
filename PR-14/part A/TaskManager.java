// Filename: TaskManager.java

import java.util.ArrayList;
import java.util.List;

public class TaskManager {
    private List<Task> taskList = new ArrayList<>();

    // Task add karne ka method
    public void addTask(Task task) {
        if (task == null) {
            throw new IllegalArgumentException("Task cannot be null!");
        }
        taskList.add(task);
    }

    // Total tasks kitne hain woh batata hai
    public int getTaskCount() {
        return taskList.size();
    }

    // Task ko complete mark karna (agar ID na mile toh Exception throw karega)
    public void markTaskAsDone(String id) {
        for (Task t : taskList) {
            if (t.getId().equals(id)) {
                t.setDone(true);
                return;
            }
        }
        throw new IllegalArgumentException("Error: Task with ID " + id + " not found!");
    }

    public List<Task> getAllTasks() {
        return taskList;
    }
}