import server.Server;

public class Main {
    public static void main(String[] args) {

            Server server = new Server();

            try{
                server.chooseAlgorithm();
                server.createServer();
            }catch (Exception e){
                System.out.println("Error creating a server! Please check you Noob Coder!");
            }


    }
};