import java.util.Scanner;

class DivideByZeroException extends Exception {

    public DivideByZeroException(String message) {
        super(message);
    }
}

public class Calculator {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        while (true) {

            try {
                System.out.print("Enter first number: ");
                double num1 = Double.parseDouble(sc.nextLine());

                System.out.print("Enter operator (+, -, *, /): ");
                char operator = sc.nextLine().charAt(0);

                System.out.print("Enter second number: ");
                double num2 = Double.parseDouble(sc.nextLine());

                double result;

                switch (operator) {

                    case '+':
                        result = num1 + num2;
                        break;

                    case '-':
                        result = num1 - num2;
                        break;

                    case '*':
                        result = num1 * num2;
                        break;

                    case '/':
                        if (num2 == 0) {
                            throw new DivideByZeroException(
                                "Error: Cannot divide by zero!"
                            );
                        }
                        result = num1 / num2;
                        break;

                    default:
                        System.out.println("Invalid operator!");
                        continue;
                }

                System.out.println("Result = " + result);

                // Stop after successful calculation
                break;

            } catch (NumberFormatException e) {

                System.out.println("Error: Please enter valid numbers.");

            } catch (DivideByZeroException e) {

                System.out.println(e.getMessage());

            } finally {

                System.out.println("Calculation attempt completed.");
                System.out.println("-----------------------------");
            }
        }

        sc.close();
    }
}