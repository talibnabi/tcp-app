import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileUtility {
    private static void writeIntoFile(String fileName, String text, boolean append) throws Exception {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName, append));) {
            bw.write(text);
        }
    }

    public static void writeIntoFile(String fileName, String text) throws Exception {
        writeIntoFile(fileName, text, false);
    }

    public static void appendIntoFile(String fileName, String text) throws Exception {
        writeIntoFile(fileName, text, true);
    }

    public static void writeBytes(String fileName, byte[] data) throws Exception {
        FileOutputStream fop = new FileOutputStream(fileName);
        // get the content in bytes
        fop.write(data);
        fop.flush();
        fop.close();

        System.out.println("Done");
    }

    public static String read(String fileName) throws Exception {
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

    public static byte[] readBytes(String fileName) throws Exception {
        File file = new File(fileName);

        try (FileInputStream fileInputStream = new FileInputStream(file);) {

            byte[] bytesArray = new byte[(int) file.length()];

            //read file into bytes[]
            fileInputStream.read(bytesArray);
            return bytesArray;
        }
    }

    public static Object readFileDeserialize(String name) throws Exception {
        Object obj = null;

        FileInputStream fi = new FileInputStream(name);
        try (ObjectInputStream in = new ObjectInputStream(fi)) {
            obj = in.readObject();
        } finally {
            return obj;
        }
    }

    public static void writeObjectToFile(Serializable object, String name) throws Exception {
        try (FileOutputStream fout = new FileOutputStream(name);
             ObjectOutputStream oos = new ObjectOutputStream(fout);) {
            oos.writeObject(object);
        }
    }

    public static void writeBytes(byte[] data, String fileName) throws Exception {
        Path filePath = Paths.get(fileName);
        Files.write(filePath, data);
    }

    public static byte[] readBytesNio(String fileName) throws Exception {
        Path filePath = Paths.get(fileName);
        byte[] byteArray = Files.readAllBytes(filePath);
        return byteArray;
    }

    private static void download(String fromFile, String toFile) throws Exception {
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
