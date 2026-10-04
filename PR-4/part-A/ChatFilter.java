import java.util.Locale;

public class ChatFilter {
    public static int appendMatches(String[] lines, String keyword, StringBuilder report) {
        int matchCount = 0;
        String searchTerm = keyword.toLowerCase(Locale.ROOT);

        for (String line : lines) {
            String[] parts = line.split(" ", 3);
            if (parts.length < 3) {
                continue;
            }

            String message = parts[2];
            if (message.toLowerCase(Locale.ROOT).contains(searchTerm)) {
                if (matchCount > 0) {
                    report.append(System.lineSeparator());
                }
                report.append(parts[0]).append(' ').append(parts[1])
                        .append(": ").append(message);
                matchCount++;
            }
        }

        return matchCount;
    }
}