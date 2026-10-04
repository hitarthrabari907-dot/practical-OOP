 public class Main {

    public static void main(String[] args) {

        System.out.println("MiniBank started!");

        long balance = 5000;

        System.out.println("Initial Balance: ₹" + balance);

        balance += 1000;
        System.out.println("After Deposit: ₹" + balance);

        balance -= 2000;
        System.out.println("After Withdrawal: ₹" + balance);

        System.out.println("MiniBank finished!");
    }
} 
    

