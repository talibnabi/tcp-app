import lombok.SneakyThrows;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        menu();
    }

    private static void menu() {
        System.out.println("Enter name: ");
        Scanner scanner = new Scanner(System.in);
        String name = scanner.next();
        System.out.println("Enter surname: ");
        String surname = scanner.next();
        if (name.equals("Talib") && surname.equals("Nabiyev")) {
            readAsByte();
        } else {
            System.exit(0);
        }
    }

    @SneakyThrows
    private static void readAsByte() {
        ServerSocket serverSocket = new ServerSocket(3454);
        while (true) {
            Socket socket = serverSocket.accept();
            DataInputStream dataStream = new DataInputStream(socket.getInputStream());

            byte[] arr = readMessage(dataStream);
            Random random = new Random();
            String text = random.toString();
            FileUtility.writeBytes(text + ".jpg", arr);
        }
    }

    @SneakyThrows
    private static String readAsString() {
        ServerSocket serverSocket = new ServerSocket(3454);
        String line = "";
        while (true) {
            Socket socket = serverSocket.accept();
            InputStreamReader inputStreamReader = new InputStreamReader(socket.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            line = bufferedReader.readLine();
            return line;
        }
    }

    @SneakyThrows
    private static byte[] readMessage(DataInputStream din) {
        int msgLen = din.readInt();//1
        //System.out.println("message length1=" + msgLen);//500
        byte[] msg = new byte[msgLen];

        din.readFully(msg);
        return msg;
    }

    @SneakyThrows
    private static void readAsByteX() {
        ServerSocket serverSocket = new ServerSocket(9765);
        while (true) {
            Socket socket = serverSocket.accept();
            DataInputStream dataStream = new DataInputStream(socket.getInputStream());
            String result = readRequest(dataStream);
            System.out.println("Result: " + result);

            OutputStream outputStream = socket.getOutputStream();
            DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
            byte[] bytes = "Text message: ".getBytes();
            dataOutputStream.writeInt(bytes.length);
            dataOutputStream.write(bytes);
            dataOutputStream.flush();
            dataOutputStream.close();
        }
    }

    @SneakyThrows
    private static String readRequest(InputStream in) {
        InputStreamReader isr = new InputStreamReader(in);
        BufferedReader br = new BufferedReader(isr);
        String msg = "";
        while (true) {
            String s = br.readLine();
            if (s == null && s.trim().length() == 0) {
                break;
            } else {
                msg = msg + s + "\r\n";
            }
            System.out.printf("Server request: " + msg);
        }
        return msg;
    }

}
