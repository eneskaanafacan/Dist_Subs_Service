import java.io.*;
import java.net.*;
import java.util.*;

public class Server2 {
    public static void main(String[] args){
            // Create Server Socket
        try {
            //Client server socket
            ServerSocket clientserverSocket = new ServerSocket(9993);
            System.out.println("Merhaba Server3");
            
            // Admin server Socket
            List<Subscriber> subscribers = new ArrayList<>();
            ServerSocket adminserverSocket = new ServerSocket(9997);

            ServerStatus status = new ServerStatus();
            Capacity capacity = new Capacity();
            
            Demand demand = new Demand(status, subscribers, capacity);
            Thread adminThread = new Thread(new AdminListener(adminserverSocket, capacity, subscribers, demand));
            adminThread.start();  
            Thread clientThread = new Thread(new ClientListener(clientserverSocket, capacity, subscribers , status));
            clientThread.start();
             

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
    //     ADMIN
 class AdminListener implements Runnable {
    private ServerSocket serverSocket;
    private Capacity capacity;
    private List<Subscriber> subscribers; 
    private Demand demand;

    public AdminListener(ServerSocket serverSocket, Capacity capacity, List<Subscriber> subscribers, Demand demand) {
        this.serverSocket = serverSocket;
        this.capacity = capacity;
        this.subscribers = subscribers;
        this.demand = demand;
    }
    

    public void run() {
        try {
            while (true) {
                Socket adminSocket = serverSocket.accept();
                Thread adminThread = new Thread(new AdminHandler(adminSocket , demand));
                adminThread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (serverSocket != null) {
                    serverSocket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

class AdminHandler implements Runnable {
    private Socket socket;
    private Demand demand;

    public AdminHandler(Socket socket , Demand demand) {
        this.socket = socket;
        this.demand = demand;
    }

    @Override
public void run() {
    try {
        ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());

        while (true) {
            Object commandObj = in.readObject();  
            String command = (String) commandObj;
            String response = demand.processcommand(command, in, out);
            System.out.println("Admin Command :" + command + "\n  Answer : " + response);
        }
    } catch (IOException | ClassNotFoundException e) {
        e.printStackTrace();
    } finally {
        try {
            if (socket != null) {
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

}

    //          CLIENT
class ClientListener implements Runnable {
    private ServerSocket serverSocket;
    private Capacity capacity;
    private List<Subscriber> subscribers;
    private ServerStatus status;
    
    public ClientListener(ServerSocket serverSocket, Capacity capacity, List<Subscriber> subscribers , ServerStatus status) {
        this.serverSocket = serverSocket;
        this.capacity = capacity;
        this.subscribers = subscribers;
        this.status = status;
    }

    public void run() {
        try {

            status.waitForStart();

            while (true) {
                Socket clientSocket = serverSocket.accept();
                Thread clientThread = new Thread(new ClientHandler(clientSocket, subscribers , status));
                clientThread.start();
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        } finally {
            try {
                if (serverSocket != null) {
                    serverSocket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

class ClientHandler implements Runnable {
    private Socket socket;
    private List<Subscriber> subscribers;
    private ServerStatus status;
    
    public ClientHandler(Socket socket, List<Subscriber> subscribers , ServerStatus status) {
        this.socket = socket;
        this.subscribers = subscribers;
        this.status = status;
    }
    
    @Override
    public void run() {
        try {
            status.waitForStart();

            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
      
            @SuppressWarnings("unchecked")
            List<Subscriber> receivedSubscribers = (List<Subscriber>) in.readObject();                 

            synchronized (subscribers) {
                for (Subscriber subscriber : receivedSubscribers) {
                    Capacity.increase();
                }
                subscribers.addAll(receivedSubscribers);
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } catch(InterruptedException e){
            e.printStackTrace();
        }finally {
            try {
                    if (socket != null) {
                    socket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}



    //Capacityy Class
class Capacity {
    private static int count;
    
    public synchronized static void increase() {
        count++;
    }
    
    public synchronized static int showCapacity() {
        if(count == 0){
            count = 0;
            return count;
        }
        else    
            return count;
    }
}
    

    //Demand Class
class Demand {
    private ServerStatus status;
    private List<Subscriber> subscribers;
    private Capacity capacity;
    
    public Demand(ServerStatus status, List<Subscriber> subscribers, Capacity capacity) {
        this.status = status;
        this.subscribers = subscribers;
        this.capacity = capacity;
    }
        
    public String processcommand(String command , ObjectInputStream in, ObjectOutputStream out) throws IOException  {
        switch(command){
            case "SUBS" : return sendSubs(out);
            case "CPCTY" : return sendCpcty(out);
            case "STRT" : return sendStart(out);
            default : return "WRONG DEMAND";
        }
    }
    private String sendStart(ObjectOutputStream out){
        try { 
            status.startServer();
            out.writeObject("YEP");
            return "YEP";
        }catch (IOException e){
            try{
            out.writeObject("NOP");
            }catch(IOException ex){
                ex.printStackTrace();
            } return "Server can't start";
        }
    }
    
    private String sendSubs(ObjectOutputStream out) throws IOException {
        out.writeObject(subscribers);
        return "SUBS command executed";
    }
    
    private String sendCpcty(ObjectOutputStream out) throws IOException {
        if(capacity.showCapacity() == 0){
            out.writeObject("server3_status : "+ capacity.showCapacity() + "\ntimestamp : " + System.currentTimeMillis() );
            return "null";
        }
        else
            out.writeObject("server3_status : " + capacity.showCapacity() + "\ntimestamp : " + System.currentTimeMillis() );
            return "CPCTY command executed";
    }
    
}

    //   Server Status
class ServerStatus {
    private volatile boolean isStarted = false;
    
   
    public synchronized void startServer(){
        isStarted = true;
        notifyAll();
    }
    

    public synchronized void waitForStart() throws InterruptedException{
        while (!isStarted) {
            wait(); 
        }
    }

    public synchronized boolean isStarted(){ 
        return isStarted; 
    }
}

    

    //Subscirber Class
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

    // Getter methods
    public int getId(){ 
        return id; 
    }
    public String getNameSurname(){
         return nameSurname; 
    }
    public List<String> getInterests(){ 
        return interests;
    }
    public boolean isOnline(){ 
        return isOnline;
    }

    @Override
    public String toString() {
        return "ID: " + id + "\n Name and Surname: " + nameSurname + "\n Start Date: " + startDate +
               "\n Last Accessed: " + lastAccessed + "\n Interests: " + String.join(", ", interests) +
               "\n Online Status: " + (isOnline ? "Online" : "Offline");
    }
}
