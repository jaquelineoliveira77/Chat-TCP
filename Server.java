/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

/**
 *
 * @author Jaqueline Oliveira
 */
public class Server {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        try {
            ServerSocket theServer = new ServerSocket(1235);
            System.out.println(" Servidor on!");
            while (true) {
                Socket socket = theServer.accept();
                ReadMsg rm = new ReadMsg(socket);
                SendMsg sn = new SendMsg(socket);
                rm.start();
                sn.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

//send mess thread
class SendMsg extends Thread {

    public Socket socket;

    public SendMsg(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        Scanner input = new Scanner(System.in);
        PrintStream printStream = null;
        while (true) {
            String msg = input.nextLine();
            try {
                printStream = new PrintStream(socket.getOutputStream());
                printStream.println(msg);
                if (msg.equals("Sair")) {
                    System.exit(0);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }
}

//read mess thread
class ReadMsg extends Thread {

    public Socket socket;

    public ReadMsg(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            while (true) {
                String msg = bufferedReader.readLine();
                System.out.println("Client: " + msg);
                if (msg.equals("Sair")) {
                    System.out.println("Exit Program!");
                    socket.close();
                    System.exit(0);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
