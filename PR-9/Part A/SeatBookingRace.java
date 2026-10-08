import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class SeatBookingRace {
    private static final int TOTAL_SEATS = 5;
    private static final int THREAD_COUNT = 10;
    private static final long BOOKING_DELAY_MS = 10L;

    static class SeatBookingSystem {
        private int seatsLeft;

        public SeatBookingSystem(int seatsLeft) {
            this.seatsLeft = seatsLeft;
        }

        public boolean bookSeatUnsynchronized() {
            String threadName = Thread.currentThread().getName();
            System.out.println(threadName + " checking seatsLeft = " + seatsLeft);

            if (seatsLeft > 0) {
                try {
                    Thread.sleep(BOOKING_DELAY_MS);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }

                seatsLeft--;
                System.out.println(threadName + " booked successfully. Seats left: " + seatsLeft);
                return true;
            }

            System.out.println(threadName + " could not book. Seats left: " + seatsLeft);
            return false;
        }

        public synchronized boolean bookSeatSynchronized() {
            String threadName = Thread.currentThread().getName();
            System.out.println(threadName + " checking seatsLeft = " + seatsLeft);

            if (seatsLeft > 0) {
                seatsLeft--;
                System.out.println(threadName + " booked successfully. Seats left: " + seatsLeft);
                return true;
            }

            System.out.println(threadName + " could not book. Seats left: " + seatsLeft);
            return false;
        }

        public int getSeatsLeft() {
            return seatsLeft;
        }
    }

    static class BookingWorker implements Runnable {
        private final SeatBookingSystem bookingSystem;
        private final boolean synchronizedBooking;
        private final AtomicInteger successfulBookings;

        public BookingWorker(SeatBookingSystem bookingSystem, boolean synchronizedBooking, AtomicInteger successfulBookings) {
            this.bookingSystem = bookingSystem;
            this.synchronizedBooking = synchronizedBooking;
            this.successfulBookings = successfulBookings;
        }

        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName() + " started");
            boolean booked = synchronizedBooking
                    ? bookingSystem.bookSeatSynchronized()
                    : bookingSystem.bookSeatUnsynchronized();

            if (booked) {
                successfulBookings.incrementAndGet();
            }

            System.out.println(Thread.currentThread().getName() + " finished");
        }
    }

    private static int runBookingRace(boolean synchronizedBooking) throws InterruptedException {
        SeatBookingSystem bookingSystem = new SeatBookingSystem(TOTAL_SEATS);
        AtomicInteger successfulBookings = new AtomicInteger();
        List<Thread> threads = new ArrayList<>();

        for (int i = 0; i < THREAD_COUNT; i++) {
            Thread thread = new Thread(new BookingWorker(bookingSystem, synchronizedBooking, successfulBookings), "Passenger-" + (i + 1));
            threads.add(thread);
        }

        for (Thread thread : threads) {
            thread.start();
        }

        for (Thread thread : threads) {
            thread.join();
        }

        System.out.println("Successful bookings: " + successfulBookings.get());
        System.out.println("Seats left: " + bookingSystem.getSeatsLeft());
        System.out.println();
        return successfulBookings.get();
    }

    public static void main(String[] args) throws InterruptedException {
        System.out.println("=== Without synchronization ===");
        int unsynchronizedSuccessfulBookings = runBookingRace(false);

        System.out.println("=== With synchronization ===");
        int synchronizedSuccessfulBookings = runBookingRace(true);

        System.out.println("Unsynchronized result: " + unsynchronizedSuccessfulBookings + " bookings for 5 seats");
        System.out.println("Synchronized result: " + synchronizedSuccessfulBookings + " bookings for 5 seats");
    }
}
