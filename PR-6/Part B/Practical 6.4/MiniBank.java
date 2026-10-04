public class MiniBank {

   
    interface Premium {
    }

   
    static class Account implements Premium {
        long balance = 5000;
    }

    public static void main(String[] args) {

        Account account = new Account();

        if (account instanceof Premium) {
            System.out.println("This is a Premium Account");
        }
    }
}