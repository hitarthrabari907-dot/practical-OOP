import java.util.*;

public class WordFrequencyAnalyzer {
    public static void main(String[] args) {
        String paragraph = "Java is a powerful programming language. Java is widely used for backend development, and java is fun to learn.";
        
        // Paragraph ko lowercase karke punctuation hata kar words mein split karna
        String[] words = paragraph.toLowerCase().replaceAll("[^a-zA-Z ]", "").split("\\s+");
        
        // Step 1: HashMap mein frequencies count karna
        Map<String, Integer> freqMap = new HashMap<>();
        for (String word : words) {
            if (!word.isEmpty()) {
                freqMap.put(word, freqMap.getOrDefault(word, 0) + 1);
            }
        }
        
        // Step 2: Map entries ko List mein convert karke Comparator se sort karna (highest frequency pehle)
        List<Map.Entry<String, Integer>> entryList = new ArrayList<>(freqMap.entrySet());
        
        entryList.sort(new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(Map.Entry<String, Integer> e1, Map.Entry<String, Integer> e2) {
                // Descending order of frequency
                int freqCompare = e2.getValue().compareTo(e1.getValue());
                if (freqCompare != 0) {
                    return freqCompare;
                }
                // Agar frequency same ho toh alphabetical order mein sort karo
                return e1.getKey().compareTo(e2.getKey());
            }
        });
        
        // Step 3: Sorted results print karna
        System.out.println("=== Word Frequency Report ===");
        for (Map.Entry<String, Integer> entry : entryList) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
    }
}