import lombok.SneakyThrows;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        clientSocket();
    }

    @SneakyThrows
    public static void clientSocket() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter file path: ");
        String path = scanner.next();
        byte[] bytes = FileUtility.readBytes(path);

        System.out.println("Enter ip and port ( like this --> ip:port): ");
        String ipAndPort = scanner.next();
        String[] arrIpAndPort = ipAndPort.split(":");
        String ip = arrIpAndPort[0];
        String port = arrIpAndPort[1];
        Socket socket = new Socket(ip, Integer.parseInt(port));
        //Socket socket = new Socket("192.168.1.114", 6789);
        DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
        dataOutputStream.writeInt(bytes.length);
        dataOutputStream.write(bytes);
        socket.close();
    }
}
//