// Filename: Task.java

public class Task {
    private String id;
    private String title;
    private boolean isDone;

    public Task(String id, String title) {
        this.id = id;
        this.title = title;
        this.isDone = false;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }
}