package server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

import static server.Hash.md5Custom;

public class TelnetServer {

    public static void main(String[] args) {
        Server server = new Server();
        server.start();
    }

    static class Server extends Thread {

        public void run() {
            Socket clientSocket = null;

            int port = 23;

            try {
                ServerSocket serverSocket = new ServerSocket(port);
                clientSocket = serverSocket.accept();
            } catch (IOException e) {
                System.out.println("Port: " + port + " - connection error!");
                System.exit(-1);
            }

            InputStream inClientStream = null;
            try {
                inClientStream = clientSocket.getInputStream();
                sendResponse(clientSocket, "Welcome to server\n");
            } catch (IOException e) {
                System.out.println("Can't get stream: " + e.getMessage());
                System.exit(-1);
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(inClientStream));
            String readClientStream = null;
            try {
                do{
                    readClientStream = reader.readLine();
                    System.out.println("Client message: " + readClientStream);
                    System.out.flush();
                    sendResponse(clientSocket, md5Custom(readClientStream));
                }while(!readClientStream.equals("END"));
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Can't read message: " + e.getMessage());
                System.exit(-1);
            }

        }

        //ответка
        private void sendResponse(Socket clientSocket, String message) throws IOException {
            DataOutputStream outDataClientStream = new DataOutputStream(clientSocket.getOutputStream());
            try {
                outDataClientStream.writeUTF(message);
            } catch (IOException e) {
                System.out.println("Can't send mesage: " + e.getMessage());
            }
        }
    }
}
