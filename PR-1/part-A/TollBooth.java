import java.util.Scanner; 
 
record Vehicle(String number, String type) {} 
 
public class TollBooth 
{ 
     public static void main(String[] args)
{ 
Scanner sc = new Scanner(System.in); 
 int totalToll = 0;
 int bike = 0;
 int car = 0; 
 int truck = 0; 
 
        while (true) 
        { 
            System.out.print("Enter Vehicle Number (or done): "); 
            String number = sc.next(); 
 
            if (number.equalsIgnoreCase("done")) 
            { 
                break; 
            } 
 
            System.out.print("Enter Vehicle Type (bike/car/truck): "); 
            String type = sc.next().toLowerCase(); 
 
            Vehicle v = new Vehicle(number, type);      
 
            int toll = switch (v.type()) 
            { 
                case "bike" -> 20; 
                case "car" -> 50; 
                case "truck" -> 150; 
                default -> 0; 
            }; 
 
           totalToll += toll; 
 
            switch (v.type()) 
            { 
                case "bike": 
                    bike++; 
                    break; 
 
                case "car": 
                    car++; 
                    break; 
 
                case "truck": 
                    truck++; 
                    break; 
            } 
        } 
 
        String most = "bike"; 
 
        if (car >= bike && car >= truck) 
        { 
            most = "car"; 
        } 
        else if (truck >= bike && truck >= car) 
        { 
            most = "truck"; 
        } 
 
        System.out.println("Total toll: " + totalToll); 
        System.out.println("Most frequent: " + most); 
 
        sc.close(); 
    } 
} 
