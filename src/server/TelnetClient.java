package server;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;

import static server.Hash.md5Custom;

public class TelnetClient {
    public static void main(String args[]) throws Exception {
        Socket socket = new Socket("localhost", 23);
        //определяем потоки
        DataInputStream in = new DataInputStream(socket.getInputStream());
        DataOutputStream out = new DataOutputStream(socket.getOutputStream());
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        String command = "";
        System.out.println(in.readUTF());
        while (!command.equals("quit")) {
            System.out.print(">>");
            command = reader.readLine();
            System.out.println(command);
            out.writeUTF(command);
            out.writeUTF("\r\n");
            out.flush();
            if (!command.equals("quit")) {
                System.out.println(in.readUTF());
            }
        }

        socket.close();
    }
}
