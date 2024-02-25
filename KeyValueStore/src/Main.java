import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class Main {
    public static void main(String[] args) throws IOException {


        //server which will listen at  port 80
        ServerSocket server = new ServerSocket(80);


        while (true) {
            Socket clientConnection = server.accept();
            System.out.println("Client " + clientConnection.getInetAddress() + "  Port = " + clientConnection.getPort());


            //handling every client using new Thread
            Thread thread = new ClientThread(clientConnection);
            thread.start();
        }
    }
}



