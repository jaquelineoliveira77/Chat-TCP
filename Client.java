/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

/**
 *
 * @author Jaqueline Oliveira
 */
public class Client {

    public static void main(String[] args) {

        try {
            Socket clientSocket = new Socket("localhost", 1235);
            System.out.println("Connected!");
            ReadMsgClient rmClient = new ReadMsgClient(clientSocket);
            SendMsgClient snClient = new SendMsgClient(clientSocket);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

//send mess thread
class SendMsgClient extends Thread {

    public Socket socket;

    public SendMsgClient(Socket socket) {
        this.socket = socket;
        this.start();
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
                    System.out.println("Desconectou! ");
                    System.exit(0);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }
}

//read mess thread
class ReadMsgClient extends Thread {

    public Socket socket;

    public ReadMsgClient(Socket socket) {
        this.socket = socket;
        this.start();
    }

    @Override
    public void run() {
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            while (true) {
                String msg = bufferedReader.readLine();
                System.out.println("Server: " + msg);
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
