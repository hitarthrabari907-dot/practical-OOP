 import java.lang.annotation.*;
import java.lang.reflect.Method;

public class MiniTestRunner {

    
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    @interface Run {
    }

   
    static class MyTests {

        @Run
        public void testLogin() {
            System.out.println("Test Login executed");
        }

        @Run
        public void testSignup() {
            System.out.println("Test Signup executed");
        }

        public void normalMethod() {
            System.out.println("This method should NOT run");
        }

        @Run
        public void testCalculation() {
            System.out.println("Test Calculation executed");
        }
    }

    public static void main(String[] args) throws Exception {

        MyTests tests = new MyTests();

        int count = 0;

       
        for (Method method : MyTests.class.getDeclaredMethods()) {

           
            if (method.isAnnotationPresent(Run.class)
                    && method.getParameterCount() == 0) {

                method.invoke(tests);
                count++;
            }
        }

        System.out.println("\nTotal tests ran: " + count);
    }
} 