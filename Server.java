import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public Server(String ip, String port) {
        try(ServerSocket serverSocket = new ServerSocket(Integer.parseInt(port))){
            Main.log("Server is running on port " + port);

            Socket clientSocket = serverSocket.accept();
            Main.log("Client connected");

            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

            String message;
            while((message = in.readLine()) != null) {
                Main.log("Recieved: "+ message);
                out.println("Server received: " + message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
