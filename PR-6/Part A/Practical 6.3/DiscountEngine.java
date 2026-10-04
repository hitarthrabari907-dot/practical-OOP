import java.util.*;

public class DiscountEngine {

    @FunctionalInterface
    interface DiscountRule {
        double apply(double price);
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        List<Double> prices = Arrays.asList(
            1000.0, 2000.0, 3000.0, 5000.0
        );

        System.out.println("Choose Discount:");
        System.out.println("1. 10% Discount");
        System.out.println("2. 20% Discount");
        System.out.println("3. ₹500 Discount");

        System.out.print("Enter choice: ");
        int choice = sc.nextInt();

        DiscountRule rule;

        if (choice == 1) {
            rule = price -> price * 0.90;
        } 
        else if (choice == 2) {
            rule = price -> price * 0.80;
        } 
        else if (choice == 3) {
            rule = price -> price - 500;
        } 
        else {
            System.out.println("Invalid choice");
            sc.close();
            return;
        }

        System.out.println("\nFinal Prices:");

        for (double price : prices) {
            double finalPrice = rule.apply(price);
            System.out.println("₹" + price + " -> ₹" + finalPrice);
        }

        sc.close();
    }
}