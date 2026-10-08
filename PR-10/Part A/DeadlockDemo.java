import java.util.concurrent.CyclicBarrier;

public class DeadlockDemo {
    static class Account {
        private final String name;
        private int balance;

        Account(String name, int balance) {
            this.name = name;
            this.balance = balance;
        }

        public synchronized void deposit(int amount) {
            balance += amount;
        }

        public synchronized void withdraw(int amount) {
            balance -= amount;
        }

        public synchronized int getBalance() {
            return balance;
        }

        public String getName() {
            return name;
        }
    }

    static void transferDeadlock(Account a, Account b, int amount, CyclicBarrier startBarrier) {
        try {
            startBarrier.await();
            synchronized (a) {
                System.out.println("Thread " + Thread.currentThread().getName() + " locked " + a.getName());
                Thread.sleep(200);
                synchronized (b) {
                    a.withdraw(amount);
                    b.deposit(amount);
                }
            }
        } catch (InterruptedException | java.util.concurrent.BrokenBarrierException e) {
            Thread.currentThread().interrupt();
        }
    }

    static void transferSafe(Account a, Account b, int amount) {
        Account first = a.getName().compareTo(b.getName()) < 0 ? a : b;
        Account second = first == a ? b : a;

        synchronized (first) {
            synchronized (second) {
                a.withdraw(amount);
                b.deposit(amount);
            }
        }
    }

    public static void runDeadlockDemo() throws InterruptedException {
        Account a = new Account("A", 1000);
        Account b = new Account("B", 1000);
        CyclicBarrier barrier = new CyclicBarrier(2);

        Thread t1 = new Thread(() -> transferDeadlock(a, b, 100, barrier), "T1");
        Thread t2 = new Thread(() -> transferDeadlock(b, a, 200, barrier), "T2");

        t1.start();
        t2.start();
        t1.join(2000);
        t2.join(2000);

        if (t1.isAlive() || t2.isAlive()) {
            System.out.println("Deadlock observed with opposite lock order.");
        } else {
            System.out.println("No deadlock detected in this short run.");
        }
    }

    public static void runSafeTransferDemo() throws InterruptedException {
        Account x = new Account("A", 1000);
        Account y = new Account("B", 1000);

        Thread safe1 = new Thread(() -> transferSafe(x, y, 100));
        Thread safe2 = new Thread(() -> transferSafe(y, x, 200));

        safe1.start();
        safe2.start();
        safe1.join();
        safe2.join();

        System.out.println("Safe transfer finished. Final balances: A=" + x.getBalance() + ", B=" + y.getBalance());
    }

    public static void main(String[] args) throws InterruptedException {
        runSafeTransferDemo();
    }
}
