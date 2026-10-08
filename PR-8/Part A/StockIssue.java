class OutOfStockException extends Exception {

    private int shortfall;

    public OutOfStockException(int shortfall) {
        super("Out of stock! Shortfall = " + shortfall);
        this.shortfall = shortfall;
    }

    public int getShortfall() {
        return shortfall;
    }
}

class InvalidQuantityException extends Exception {

    public InvalidQuantityException(String message) {
        super(message);
    }
}

class Warehouse {

    private int stock = 10;

    public void issue(String item, int qty)
            throws OutOfStockException, InvalidQuantityException {

        if (qty <= 0) {
            throw new InvalidQuantityException(
                "Invalid quantity! Quantity must be greater than 0."
            );
        }

        if (qty > stock) {
            int shortfall = qty - stock;
            throw new OutOfStockException(shortfall);
        }

        stock = stock - qty;

        System.out.println(
            "Issued " + qty + " " + item + " successfully."
        );

        System.out.println("Remaining stock = " + stock);
    }
}

public class StockIssue {

    public static void main(String[] args) {

        Warehouse warehouse = new Warehouse();

        int[] requests = {3, 5, 7, 0, -2, 1};

        for (int qty : requests) {

            System.out.println("Request quantity = " + qty);

            try {

                warehouse.issue("Laptop", qty);

            } catch (OutOfStockException e) {

                System.out.println(e.getMessage());
                System.out.println(
                    "Shortfall = " + e.getShortfall()
                );

            } catch (InvalidQuantityException e) {

                System.out.println(e.getMessage());
            }

            System.out.println("-----------------------------");
        }

        System.out.println("All requests processed.");
    }
}