 class MyResource implements AutoCloseable {

    public MyResource() {
        System.out.println("Resource opened.");
    }

    public void use() {
        System.out.println("Using resource...");
    }

    @Override
    public void close() {
        System.out.println("Resource closed automatically.");
    }
}

public class AutoCloseDemo {

    public static void main(String[] args) {

        try (MyResource resource = new MyResource()) {

            resource.use();

            System.out.println("Inside try block.");

            // Creating an exception
            throw new Exception("Original error!");

        } catch (Exception e) {

            System.out.println("Caught error: " + e.getMessage());
        }

        System.out.println("Program finished.");
    }
} 
    

