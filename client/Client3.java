import java.io.*;
import java.net.*;
import java.util.*;

public class Client1 {
    public static void main(String[] args) throws IOException {
     
        Subscriber sub1 = new Subscriber(8, "Ahmet Bal", 1631020, 163204, Arrays.asList("Programming", "Reading", "Gaming"), false);
        Subscriber sub2 = new Subscriber(9, "Selim Sal", 16310202, 163200, Arrays.asList("Watching Movies", "Reading", "Hiking"), true);
        Subscriber sub3 = new Subscriber(10, null, 0, 0, null, false);
        Subscriber sub4 = new Subscriber(11,"Toprak Kose",16310202,163200,Arrays.asList("Surfing Net", "Coding" , "Tennis"),true
        );
        @SuppressWarnings("unchecked")
        List<Subscriber> subscribers = Arrays.asList(sub1, sub2 , sub3 ,sub4);
   
        Socket socket = new Socket("localhost", 9993);
        System.out.println("Connected!");
  
        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
        out.writeObject(subscribers);

        socket.close();
    }
}

class Subscriber implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private String nameSurname;
    private int startDate;
    private int lastAccessed;
    private List<String> interests;
    private boolean isOnline;

    // Constructor
    public Subscriber(int id, String nameSurname, int startDate, int lastAccessed, List<String> interests, boolean isOnline) {
        this.id = id;
        this.nameSurname = nameSurname;
        this.startDate = startDate;
        this.lastAccessed = lastAccessed;
        this.interests = interests;
        this.isOnline = isOnline;
    }

    // Getter ve setter'lar

    @Override
    public String toString() {
        return "ID: " + id +
               "\nName and Surname: " + nameSurname +
               "\nStart Date: " + startDate +
               "\nLast Accessed: " + lastAccessed +
               "\nInterests: " + interests +
               "\nOnline Status: " + (isOnline ? "Online" : "Offline");
    }
}