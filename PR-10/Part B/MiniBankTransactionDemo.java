import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CyclicBarrier;

public class MiniBankTransactionDemo {
    static class Account {
        private final String accountNumber;
        private long balance;

        public Account(String accountNumber, long balance) {
            this.accountNumber = accountNumber;
            this.balance = balance;
        }

        public synchronized void deposit(long amount) {
            if (amount <= 0) {
                throw new IllegalArgumentException("Deposit amount must be positive");
            }
            balance += amount;
            System.out.println("Deposit: +" + amount + " | " + accountNumber + " => " + balance);
        }

        public synchronized boolean withdraw(long amount) {
            if (amount <= 0) {
                throw new IllegalArgumentException("Withdrawal amount must be positive");
            }
            if (amount > balance) {
                return false;
            }
            balance -= amount;
            System.out.println("Withdrawal: -" + amount + " | " + accountNumber + " => " + balance);
            return true;
        }

        public synchronized long getBalance() {
            return balance;
        }

        public String getAccountNumber() {
            return accountNumber;
        }
    }

    static class SharedTransactionBuffer {
        private final ArrayDeque<String> buffer = new ArrayDeque<>();
        private static final int CAPACITY = 3;

        public synchronized void addTransaction(String transaction) throws InterruptedException {
            while (buffer.size() >= CAPACITY) {
                wait();
            }
            buffer.addLast(transaction);
            System.out.println("Producer added: " + transaction + " | Queue: " + buffer);
            notifyAll();
        }

        public synchronized String processTransaction() throws InterruptedException {
            while (buffer.isEmpty()) {
                wait();
            }
            String transaction = buffer.removeFirst();
            System.out.println("Consumer processed: " + transaction + " | Queue: " + buffer);
            notifyAll();
            return transaction;
        }
    }

    static class TransactionTask implements Runnable {
        private final Account account;
        private final long amount;
        private final boolean deposit;

        TransactionTask(Account account, long amount, boolean deposit) {
            this.account = account;
            this.amount = amount;
            this.deposit = deposit;
        }

        @Override
        public void run() {
            if (deposit) {
                account.deposit(amount);
            } else {
                account.withdraw(amount);
            }
        }
    }

    static void transferDeadlock(Account from, Account to, long amount, CyclicBarrier startBarrier) {
        try {
            startBarrier.await();
            synchronized (from) {
                System.out.println("Thread " + Thread.currentThread().getName() + " locked " + from.getAccountNumber());
                Thread.sleep(200L);
                synchronized (to) {
                    if (from.withdraw(amount)) {
                        to.deposit(amount);
                    }
                }
            }
        } catch (InterruptedException | java.util.concurrent.BrokenBarrierException e) {
            Thread.currentThread().interrupt();
        }
    }

    static void transferFixed(Account from, Account to, long amount) {
        Account first = from.getAccountNumber().compareTo(to.getAccountNumber()) < 0 ? from : to;
        Account second = first == from ? to : from;

        synchronized (first) {
            synchronized (second) {
                if (from.withdraw(amount)) {
                    to.deposit(amount);
                }
            }
        }
    }

    public static void runDeadlockDemo() throws InterruptedException {
        Account a = new Account("A", 1000L);
        Account b = new Account("B", 500L);
        CyclicBarrier barrier = new CyclicBarrier(2);

        Thread t1 = new Thread(() -> transferDeadlock(a, b, 200L, barrier), "T1");
        Thread t2 = new Thread(() -> transferDeadlock(b, a, 150L, barrier), "T2");

        t1.start();
        t2.start();
        t1.join(2000L);
        t2.join(2000L);

        if (t1.isAlive() || t2.isAlive()) {
            System.out.println("Deadlock observed with opposite lock order.");
        } else {
            System.out.println("No deadlock observed in the short deadlock test.");
        }
    }

    public static void runFixedTransferDemo() throws InterruptedException {
        Account x = new Account("A", 1000L);
        Account y = new Account("B", 500L);

        Thread safe1 = new Thread(() -> transferFixed(x, y, 200L));
        Thread safe2 = new Thread(() -> transferFixed(y, x, 150L));

        safe1.start();
        safe2.start();
        safe1.join();
        safe2.join();

        System.out.println("Fixed transfer completed. Final balances: A=" + x.getBalance() + ", B=" + y.getBalance());
    }

    public static void main(String[] args) throws InterruptedException {
        System.out.println("=== Transaction Processor Demo ===");
        TransactionProcessor processor = new TransactionProcessor();
        Account account = new Account("A100", 1000L);

        for (int i = 1; i <= 20; i++) {
            final long amount = 50L + (i % 5) * 10L;
            boolean isDeposit = (i % 2 == 0);
            processor.submit(new TransactionTask(account, amount, isDeposit));
        }

        processor.stop();
        System.out.println("Final account balance after processor tasks: " + account.getBalance());

        System.out.println("\n=== Producer-Consumer Demo ===");
        SharedTransactionBuffer buffer = new SharedTransactionBuffer();
        List<String> expected = new ArrayList<>();

        Thread producer = new Thread(() -> {
            try {
                for (int i = 1; i <= 10; i++) {
                    String tx = "TX-" + i;
                    expected.add(tx);
                    buffer.addTransaction(tx);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        List<String> consumed = new ArrayList<>();
        Thread consumer = new Thread(() -> {
            try {
                for (int i = 0; i < 10; i++) {
                    consumed.add(buffer.processTransaction());
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        producer.start();
        consumer.start();
        producer.join();
        consumer.join();

        System.out.println("Expected: " + expected);
        System.out.println("Consumed: " + consumed);
        System.out.println("Consumer sequence matches: " + expected.equals(consumed));

        System.out.println("\n=== Fixed Transfer Demo ===");
        runFixedTransferDemo();
    }
}
