package server;

import java.io.*;
import java.net.Socket;

import static server.Hash.md5Custom;

public class TelnetClient {
    public static void main(String args[]) throws Exception {
        Socket socket = new Socket("localhost", 23);
        //определяем потоки
        DataInputStream in = new DataInputStream(socket.getInputStream());
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter pr;
        String command = "";
        System.out.println(in.readUTF());
        do{
            pr = new PrintWriter(socket.getOutputStream(),true);
            command = reader.readLine();
            pr.println(command);
            System.out.println(in.readUTF());
            System.out.println(md5Custom(command));
        }while (!command.equals("quit"));



//        System.out.println(in.readUTF());
//        while (!command.equals("quit")) {
//            System.out.print(">>");
//            command = reader.readLine();
//            System.out.println(command);
//            out.writeChars(command);
//            out.writeChars("\r\n");
//            out.flush();
//            if (!command.equals("quit")) {
//                System.out.println(in.readUTF());
//            }
//        }

        socket.close();
    }
}
