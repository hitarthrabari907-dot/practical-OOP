public class AccountWorker implements Runnable {
    private final Account account;
    private final int times;
    private final long amount;

    public AccountWorker(Account account, int times, long amount) {
        if (account == null) {
            throw new IllegalArgumentException("Account cannot be null");
        }
        if (times < 0) {
            throw new IllegalArgumentException("Times cannot be negative");
        }
        this.account = account;
        this.times = times;
        this.amount = amount;
    }

    @Override
    public void run() {
        String threadName = Thread.currentThread().getName();
        System.out.println(threadName + " started");
        for (int i = 0; i < times; i++) {
            account.deposit(amount);
        }
        System.out.println(threadName + " finished");
    }
}
