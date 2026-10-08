import java.util.ArrayList;
import java.util.List;

public class CounterRace {

    private static final int NUM_THREADS = 10;
    private static final int INCREMENTS_PER_THREAD = 100_000;

   
    private static long sharedCounter = 0;

    private static final Object lock = new Object();

    
    private static void incrementUnsynchronized() {
        for (int i = 0; i < INCREMENTS_PER_THREAD; i++) {
        
            sharedCounter++;
        }
    }

   
    private static void incrementSynchronized() {
        for (int i = 0; i < INCREMENTS_PER_THREAD; i++) {
            synchronized (lock) {
                sharedCounter++;
            }
        }
    }

    private static void runTest(boolean useSynchronization) {
        sharedCounter = 0;
        List<Thread> threads = new ArrayList<>();

      
        for (int i = 0; i < NUM_THREADS; i++) {
            Thread thread = new Thread(useSynchronization ? 
                CounterRace::incrementSynchronized : 
                CounterRace::incrementUnsynchronized
            );
            threads.add(thread);
        }

      
        for (Thread thread : threads) {
            thread.start();
        }

       
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        long expectedTotal = (long) NUM_THREADS * INCREMENTS_PER_THREAD;
        String mode = useSynchronization ? "[Synchronized]   " : "[Unsynchronized] ";
        System.out.printf("%s Final Count: %,d | Expected: %,d%n", mode, sharedCounter, expectedTotal);
    }

    public static void main(String[] args) {
        System.out.println("--- Running Counter Race Test ---");

       
        runTest(false);

      
        runTest(true);
    }
}