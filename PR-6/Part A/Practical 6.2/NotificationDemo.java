interface Notifier {
    void send(String message);
}


interface Urgent {
}

class EmailSender implements Notifier, Urgent {
    public void send(String message) {
        System.out.println("Email: " + message);
    }
}


class SMSSender implements Notifier {
    public void send(String message) {
        System.out.println("SMS: " + message);
    }
}

public class NotificationDemo {

    public static void main(String[] args) {

     
        Notifier email = message ->
                System.out.println("Email: " + message);

        Notifier sms = message ->
                System.out.println("SMS: " + message);

      
        Notifier[] senders = { email, sms };

        String message = "Your bank transaction is complete.";

      
        System.out.println("Normal Message:");

        for (Notifier sender : senders) {
            sender.send(message);
        }

       
        System.out.println("\nUrgent Message:");

        Notifier urgentSender = new EmailSender();

        if (urgentSender instanceof Urgent) {
            urgentSender.send(message);
            urgentSender.send(message);
        }
    }
}