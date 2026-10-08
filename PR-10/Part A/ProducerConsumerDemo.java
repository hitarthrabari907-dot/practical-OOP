import java.util.ArrayList;
import java.util.List;

public class ProducerConsumerDemo {
    private static final int CAPACITY = 3;
    private static final int TOTAL_ITEMS = 10;

    static class SharedBuffer {
        private final List<Integer> buffer = new ArrayList<>();

        public synchronized void produce(int value) throws InterruptedException {
            while (buffer.size() >= CAPACITY) {
                wait();
            }
            buffer.add(value);
            System.out.println("Produced: " + value + " | Buffer: " + buffer);
            notifyAll();
        }

        public synchronized int consume() throws InterruptedException {
            while (buffer.isEmpty()) {
                wait();
            }
            int value = buffer.remove(0);
            System.out.println("Consumed: " + value + " | Buffer: " + buffer);
            notifyAll();
            return value;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        SharedBuffer buffer = new SharedBuffer();
        List<Integer> produced = new ArrayList<>();
        List<Integer> consumed = new ArrayList<>();

        Thread producer = new Thread(() -> {
            try {
                for (int i = 1; i <= TOTAL_ITEMS; i++) {
                    produced.add(i);
                    buffer.produce(i);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        Thread consumer = new Thread(() -> {
            try {
                for (int i = 0; i < TOTAL_ITEMS; i++) {
                    consumed.add(buffer.consume());
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        producer.start();
        consumer.start();
        producer.join();
        consumer.join();

        System.out.println("Produced: " + produced);
        System.out.println("Consumed: " + consumed);

        if (produced.equals(consumed)) {
            System.out.println("No items were lost or reordered.");
        } else {
            System.out.println("Mismatch detected! Producer and consumer lists differ.");
        }
    }
}
