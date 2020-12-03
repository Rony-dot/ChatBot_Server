import  java.io.*;
import java.net.*;

public class Server {
    ServerSocket server;
    Socket socket;
    BufferedReader br;
    PrintWriter out;

    public Server() {
        try{
            server = new ServerSocket(7777);
            System.out.println("Server is Ready to accept connection");
            System.out.println("Waiting...");
            socket = server.accept();
            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream());
            
            startReading();
            startWriting();

        }catch (Exception e){
            e.printStackTrace();
        }
    }


    private void startReading() {
        Runnable r1 = ()->{
            System.out.println("Reader started...");
            try{
            while(true){
                    String msg = br.readLine().trim();
                    if(msg.equals("exit")){
                        System.out.println("Client Terminated The program");
                        socket.close();
                        break;
                    }
                    System.out.println("Clinet: "+msg);

            }
            }
            catch (Exception e){
                //System.out.println("Reading prblm");
                System.out.println("Connection closed");
            }
        };
        new Thread(r1).start();
    }
    private void startWriting() {
        Runnable r2=()-> {
            System.out.println("Writer started...");
            try {
            while (!socket.isClosed()) {
                    BufferedReader br1 = new BufferedReader(new InputStreamReader(System.in));
                    String content = br1.readLine();
                    out.println(content);
                    out.flush();
                    if(content.equals("exit")){
                        socket.close();
                        break;
                    }

            }
            } catch (Exception e) {
                System.out.println("Connection closed");
            }
        };
        new Thread(r2).start();
    }

    public static void main(String[] args){
        System.out.println("Server is Running....");
       new Server();
    }
}
