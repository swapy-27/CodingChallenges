package server;

import limiting_algorithms.FixedWindow;
import limiting_algorithms.Limiter;
import limiting_algorithms.SlidingWindow;
import limiting_algorithms.TokenBucket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {

    private Limiter limiter;


    public void createServer() throws IOException {
        //server which will listen at  port 80
        ServerSocket server = new ServerSocket(80);

        while (true) {
            Socket clientConnection = server.accept();
            System.out.println("Client " + clientConnection.getInetAddress() + "  Port = " + clientConnection.getPort());

            //handling every client using new Thread
            Thread thread = new ClientThread(clientConnection, limiter);
            thread.start();

        }
    }

    public void chooseAlgorithm() throws Exception {

        Scanner sc = new Scanner(System.in);

        System.out.println("Choose Your favourite Rate Limiting Algorithm!");
        System.out.println("=================================================");
        System.out.println("options available - ");
        System.out.println("1- Fixed-Window");
        System.out.println("2- Sliding-Window");
        System.out.println("3- Token-Bucket");
        System.out.println("=================================================");


        String input = sc.next();


        switch (input) {
            case "1":
                limiter = new FixedWindow();
                break;
            case "2":
                limiter = new SlidingWindow();
                break;
            case "3":
                limiter = new TokenBucket();
                break;
            default:
                throw new Exception("Invalid input!");

        }

        System.out.println("Limiter created successfully!");

    }
}