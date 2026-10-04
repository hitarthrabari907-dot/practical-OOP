import java.util.Locale;

public class CommandParser {
    private CommandParser() {
    }

    public static Command parse(String line) {
        if (line == null || line.isBlank()) {
            throw new IllegalArgumentException("Command cannot be empty.");
        }

        String[] parts = line.trim().split("\\s+");
        if (parts.length != 3) {
            throw new IllegalArgumentException("Command must contain a type, account number, and amount.");
        }

        TransactionType type;
        try {
            type = TransactionType.valueOf(parts[0].toUpperCase(Locale.ROOT));
        } catch (IllegalArgumentException exception) {
            throw new IllegalArgumentException("Unknown transaction type: " + parts[0], exception);
        }

        long amount;
        try {
            amount = Long.parseLong(parts[2]);
        } catch (NumberFormatException exception) {
            throw new IllegalArgumentException("Amount must be a whole number.", exception);
        }

        return new Command(type, parts[1], amount);
    }
}