package server;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

import static server.Hash.md5Custom;

public class TelnetServer {

    public TelnetServer() {

    }

    public static void main(String[] args) {
        Server server = new Server();
        server.start();
    }

    static class Server extends Thread {

        public void run() {

            int port = 23;

            Socket clientSocket = null;
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
                while ((readClientStream = reader.readLine()) != null) {
                    System.out.println("Client message: " + readClientStream);

                    sendResponse(clientSocket, "Ok!\n");
                }
            } catch (IOException e) {
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
