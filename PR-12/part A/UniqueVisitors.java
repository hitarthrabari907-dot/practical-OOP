import java.util.*;

public class UniqueVisitors {
    public static void main(String[] args) {
        // Repeated login names ki sample list
        List<String> logins = Arrays.asList(
            "rahul123", "priya99", "amit_k", "rahul123", 
            "neha_sharma", "priya99", "rohit_m", "amit_k", "pooja_s"
        );

        System.out.println("All Logins List: " + logins);

        // 1. HashSet ka use karke unique visitors count karna
        Set<String> uniqueSet = new HashSet<>(logins);
        System.out.println("\nTotal Unique Visitors Count: " + uniqueSet.size());

        // 2. LinkedHashSet ka use karke first-seen order maintain karna
        Set<String> firstSeenSet = new LinkedHashSet<>(logins);

        // 3. Pehle 3 distinct names print karna
        System.out.println("\nFirst 3 distinct visitors (in order):");
        int count = 0;
        for (String visitor : firstSeenSet) {
            if (count < 3) {
                System.out.println((count + 1) + ". " + visitor);
                count++;
            } else {
                break;
            }
        }
    }
}