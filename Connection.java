import java.io.*;
import java.net.*;

public class Connection {
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;



    public Connection(String ip, String port) {
        connectToHost(ip,port);
    }

    public Connection(String ip, String port, boolean isServer) {
        try {
            startHosting(ip, port);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void connectToHost(String ip, String port) {
        try {
            Socket socket = new Socket(ip, Integer.parseInt(port));
            Main.log("Connected to host " + ip +" " + port);

            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            Main.setOutputStream(out);

            // Thread to listen to new messages
            new Thread(() -> listenForMessages(in, "Client")).start();

            Main.setInputStream(in);

            // Thread that sends messages
            new Thread(() -> sendMessage(out)).start();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
    private void listenForMessages(BufferedReader in, String senderLabel) {
        try  {
            String message;
            while((message = in.readLine()) != null) {
                Main.log(senderLabel + " Received: " + message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendMessage(PrintWriter out) {
        try (BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in))) {
            String message;
            while((message = userInput.readLine()) != null) {
                out.println(message);
                Main.log("Sent: " + message);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void startHosting(String ip, String port){

        try {
            ServerSocket serverSocket = new ServerSocket(Integer.parseInt(port));
            Main.log("Server is running...");

            Socket clientSocket = serverSocket.accept();
            Main.log("Client connected");

            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

            Main.setOutputStream(out);

            new Thread(() -> listenForMessages(in, "Server")).start();

            Main.setInputStream(in);

            new Thread(() -> sendMessage(out)).start();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
