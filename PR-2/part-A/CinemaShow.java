public class CinemaShow { 
 
    private String title; 
    private int seatsAvailable; 
    private final int capacity; 
 
    private static int totalBooked = 0; 
 
    // Constructor with title and capacity 
    public CinemaShow(String title, int capacity) { 
        this.title = title; 
        this.capacity = capacity; 
        this.seatsAvailable = capacity; 
    } 
 
    // Constructor with title 
    public CinemaShow(String title) { 
        this(title, 100); 
    } 
    
    public boolean book(int n){ 
 
        if(n<=seatsAvailable){ 
 
            seatsAvailable -= n; 
            totalBooked += n; 
            return true; 
        } 
        else{ 
            return false; 
        } 
    } 
 
    public void  cancel(int n){ 
 
        seatsAvailable += n; 
 
         if (seatsAvailable > capacity) { 
            seatsAvailable = capacity; 
        } 
    } 
 
    public int getSeatsAvailable(){ 
 
        return seatsAvailable; 
 
    } 
 
    public static int getTotalBooked(){ 
 
        return totalBooked; 
    } 
 
 
 
  public static void main(String[] args) { 
 
        CinemaShow show = new CinemaShow("Avengers", 50); 
 
        System.out.println("Book 20 seats: " + show.book(20)); 
        System.out.println("Seats available: " + show.getSeatsAvailable()); 
 
        System.out.println("Book 25 seats: " + show.book(25)); 
        System.out.println("Seats available: " + show.getSeatsAvailable()); 
 
        System.out.println("Book 10 seats: " + show.book(10)); 
        System.out.println("Seats available: " + show.getSeatsAvailable()); 
 
        show.cancel(15); 
        System.out.println("After cancelling 15 seats:"); 
        System.out.println("Seats available: " + show.getSeatsAvailable()); 
 
        System.out.println("Book 10 seats: " + show.book(10)); 
        System.out.println("Seats available: " + show.getSeatsAvailable()); 
 
        System.out.println("Total booked seats: " + CinemaShow.getTotalBooked()); 
    } 
 
} 
