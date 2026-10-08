public class MiniBank {
    private static final int THREAD_COUNT = 10;
    private static final int DEPOSITS_PER_THREAD = 100_000;
    private static final long DEPOSIT_AMOUNT = 1;

    public static void main(String[] args) throws InterruptedException {
        Account account = new Account(0);
        Thread[] threads = new Thread[THREAD_COUNT];
        long expectedBalance = (long) THREAD_COUNT * DEPOSITS_PER_THREAD * DEPOSIT_AMOUNT;

        for (int i = 0; i < THREAD_COUNT; i++) {
            threads[i] = new Thread(
                    new AccountWorker(account, DEPOSITS_PER_THREAD, DEPOSIT_AMOUNT),
                    "BankWorker-" + (i + 1)
            );
        }

        for (Thread thread : threads) {
            System.out.println("Starting " + thread.getName());
            thread.start();
        }

        for (Thread thread : threads) {
            thread.join();
        }

        System.out.println("Final balance: " + account.getBalance());
        System.out.println("Expected balance: " + expectedBalance);
    }
}
