import javax.sound.midi.SysexMessage;
import javax.swing.*;
import java.io.*;
import java.net.*;

public class Connection {
    public Socket socket;
    public BufferedReader in;
    public PrintWriter out;
    public ServerSocket serverSocket;



    public Connection(String ip, String port) {
        try {
            this.socket = new Socket(ip, Integer.parseInt(port));
            this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.out = new PrintWriter(socket.getOutputStream(), true);

        } catch (IOException e) {
            e.printStackTrace();
        }

        connectToHost(ip,port);
    }

    public Connection(String ip, String port, boolean isServer) {
        if(isServer) {
            startHosting(ip,port);
        }
    }


    public void connectToHost(String ip, String port) {

            Main.log("Connected to host " + ip +" " + port);

            Main.setOutputStream(out);
            Main.setInputStream(in);

            // Thread to listen to new messages
            new Thread(() -> listenForMessages(in, "Server")).start();

            // Thread that sends messages
            new Thread(() -> sendMessage(out)).start();



    }
    private void listenForMessages(BufferedReader in, String senderLabel) {
        try  {
            String message;
            while((message = in.readLine()) != null) {
                if(message.equals(SystemMessage.SysMessage.CONNECTION_SUCCESS.getMessage())) {
                    String finalmessage = SystemMessage.SysMessage.CONNECTION_SUCCESS.getMessage();
                    SwingUtilities.invokeLater(() -> Main.displayMessage(finalmessage, true));
                } else {
                    String finalMessage = senderLabel + " Sent: " + message;
                    Main.log(senderLabel + " Received: " + message);
                    SwingUtilities.invokeLater(() -> Main.displayMessage(finalMessage,false));
                }


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
            this.serverSocket = new ServerSocket(Integer.parseInt(port));
            this.socket = serverSocket.accept();
            this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.out = new PrintWriter(socket.getOutputStream(),true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Main.log("Server is running...");

            Main.log("Client connected");

            Main.setOutputStream(out);
            Main.setInputStream(in);

            new Thread(() -> listenForMessages(in, "Client")).start();

            new Thread(() -> sendMessage(out)).start();


    }
}
