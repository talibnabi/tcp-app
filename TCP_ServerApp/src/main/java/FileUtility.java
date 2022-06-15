import lombok.SneakyThrows;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileUtility {
    @SneakyThrows
    private static void writeIntoFile(String fileName, String text, boolean append) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName, append));) {
            bw.write(text);
        }
    }

    @SneakyThrows
    public static void writeIntoFile(String fileName, String text) {
        writeIntoFile(fileName, text, false);
    }

    @SneakyThrows
    public static void appendIntoFile(String fileName, String text) {
        writeIntoFile(fileName, text, true);
    }

    @SneakyThrows
    public static void writeBytes(String fileName, byte[] data) {
        FileOutputStream fop = new FileOutputStream(fileName);
        // get the content in bytes
        fop.write(data);
        fop.flush();
        fop.close();

        System.out.println("Done");
    }

    @SneakyThrows
    public static String read(String fileName) {
        try (InputStream in = new FileInputStream(fileName);
             InputStreamReader r = new InputStreamReader(in);
             BufferedReader reader = new BufferedReader(r);) {
            String line = null;
            String result = "";
            while ((line = reader.readLine()) != null) {
                result += line + "\n";
            }
            return result;
        }
    }

    @SneakyThrows
    public static byte[] readBytes(String fileName) {
        File file = new File(fileName);

        try (FileInputStream fileInputStream = new FileInputStream(file);) {

            byte[] bytesArray = new byte[(int) file.length()];

            //read file into bytes[]
            fileInputStream.read(bytesArray);
            return bytesArray;
        }
    }

    @SneakyThrows
    public static Object readFileDeserialize(String name) {
        Object obj = null;

        FileInputStream fi = new FileInputStream(name);
        try (ObjectInputStream in = new ObjectInputStream(fi)) {
            obj = in.readObject();
        } finally {
            return obj;
        }
    }

    @SneakyThrows
    public static void writeObjectToFile(Serializable object, String name) {
        try (FileOutputStream fout = new FileOutputStream(name);
             ObjectOutputStream oos = new ObjectOutputStream(fout);) {
            oos.writeObject(object);
        }
    }

    @SneakyThrows
    public static void writeBytes(byte[] data, String fileName) {
        Path filePath = Paths.get(fileName);
        Files.write(filePath, data);
    }

    @SneakyThrows
    public static byte[] readBytesNio(String fileName) {
        Path filePath = Paths.get(fileName);
        byte[] byteArray = Files.readAllBytes(filePath);
        return byteArray;
    }

    @SneakyThrows
    private static void download(String fromFile, String toFile) {
        URL website = new URL(fromFile);

        URL url = new URL(fromFile);
        URLConnection con = url.openConnection();
        con.setConnectTimeout(10000);
        con.setReadTimeout(10000);
        InputStream in = con.getInputStream();

        ReadableByteChannel rbc = Channels.newChannel(in);

        FileOutputStream fos = new FileOutputStream(toFile);
        fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
        fos.close();
        rbc.close();
    }
}
