import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

class ClientThread extends Thread {

    private Socket client;
    String responseHtml =
            "<!DOCTYPE html>\n" +
                    "<html lang=\"en\">\n" +
                    "  <head>\n" +
                    "    <title>Simple Web Page</title>\n" +
                    "  </head>\n" +
                    "  <body>\n" +
                    "    <h1>Test Web Page</h1>\n" +
                    "    <p>My web server served this page!</p>\n" +
                    "  </body>\n" +
                    "</html>";

    ClientThread(Socket client) {
        this.client = client;
    }


    @Override
    public void run() {
        System.out.println("Current Thread = " + Thread.currentThread());


        try {
            //print request information
            BufferedReader reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
            String requestLine = reader.readLine();

            String requestedResource = null;

            // Check if the request line is not null and is not empty
            if (requestLine != null && !requestLine.isEmpty()) {
                // Split the request line into parts
                String[] requestParts = requestLine.split(" ");
                if (requestParts.length >= 3) {
                    // Extract HTTP method and protocol
                    String httpMethod = requestParts[0];
                    requestedResource = requestParts[1];
                    String httpProtocol = requestParts[2];

                    // Print the parsed information
                    System.out.println("HTTP Method: " + httpMethod + " " + "Requested Resource: " + requestedResource + " " + "HTTP Protocol: " + httpProtocol);
                }
            }


            DataOutputStream dout = new DataOutputStream(client.getOutputStream());

            if (null != requestedResource && requestedResource.equals("/")) {
                String httpResponse = "HTTP/1.1 " + 200 + " " + "OK" + "\r\n\r\n";
                dout.write(httpResponse.getBytes(StandardCharsets.UTF_8));
                dout.write(responseHtml.getBytes());
            } else if (null != requestedResource && requestedResource.equals("/favicon.ico")) {
                String httpResponse = "HTTP/1.1 " + 204 + " " + "OK" + "\r\n\r\n";
                dout.write(httpResponse.getBytes(StandardCharsets.UTF_8));
            } else {

                String httpResponse = "HTTP/1.1 " + 404 + " " + "NOT_FOUND" + "\r\n\r\n";
                dout.write(httpResponse.getBytes(StandardCharsets.UTF_8));
            }


            // close all open IO connections
            dout.flush();
            dout.close();
            reader.close();
            client.close();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}