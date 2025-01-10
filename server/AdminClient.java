import java.io.*;
import java.net.*;
import java.util.List;

public class AdminClient {
    public static void main(String[] args) {
        try {
            
            Socket socket1 = new Socket("localhost", 9995);
            Socket socket2 = new Socket("localhost", 9996);
            Socket socket3 = new Socket("localhost", 9997);
            
            socket1.setSoTimeout(2000);
            socket2.setSoTimeout(2000);
            socket3.setSoTimeout(2000);

            
            ObjectOutputStream out1 = new ObjectOutputStream(socket1.getOutputStream());
            ObjectInputStream in1 = new ObjectInputStream(socket1.getInputStream());
            ObjectOutputStream out2 = new ObjectOutputStream(socket2.getOutputStream());
            ObjectInputStream in2 = new ObjectInputStream(socket2.getInputStream());
            ObjectOutputStream out3 = new ObjectOutputStream(socket3.getOutputStream());
            ObjectInputStream in3 = new ObjectInputStream(socket3.getInputStream());

            BufferedReader consoleInput = new BufferedReader(new InputStreamReader(System.in));

            System.out.println("Komutlar: SUBS, CPCTY, STRT, EXIT");

            while (true) {
                System.out.print("Komut giriniz: ");
                String command = consoleInput.readLine();

                
                if ("EXIT".equalsIgnoreCase(command)) {
                    socket1.close();
                    socket2.close();
                    socket3.close();
                    break;
                }

                try {
                    out1.writeObject(command);
                    out1.flush();
                    out2.writeObject(command);
                    out2.flush();
                    out3.writeObject(command);
                    out3.flush();

                    Object response1 = in1.readObject();
                    Object response2 = in2.readObject();
                    Object response3 = in3.readObject();
                    
                    processResponse(response1);
                    processResponse(response2);
                    processResponse(response3);
                    
                } catch (IOException | ClassNotFoundException e) {
                    System.out.println("WROND DEMAND");
                }
            }

            // Kaynakları kapat
            socket1.close();
            out1.close();
            in1.close();
            socket2.close();
            out2.close();
            in2.close();
            socket3.close();
            out3.close();
            in3.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    
    private static void processResponse(Object response) {
        if (response instanceof List) {
            @SuppressWarnings("unchecked")
            List<Subscriber> subscribers = (List<Subscriber>) response;
            for (Subscriber subscriber : subscribers) {
                System.out.println(subscriber);
            }
        } else {
            System.out.println(response);
        }
    }
}

// Subscriber Class
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

    // Getter ve setter

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
