public class MiniBank {

   
    interface InterestBearing {

       
        double interestRate();

      
        default double yearlyInterest(double balance) {
            return balance * interestRate() / 100;
        }
    }

   
    static class Account implements InterestBearing {

        private double balance = 5000;

        @Override
        public double interestRate() {
            return 5.0; 
        }

        public double getBalance() {
            return balance;
        }
    }

    public static void main(String[] args) {

        Account account = new Account();

        System.out.println("Balance: ₹" + account.getBalance());
        System.out.println("Interest Rate: " + account.interestRate() + "%");
        System.out.println(
            "Yearly Interest: ₹" +
            account.yearlyInterest(account.getBalance())
        );
    }
}