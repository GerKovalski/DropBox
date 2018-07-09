package commonThings;

import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.file.Files;

public class commonFunctions {
    public void sendMessage(String message, ObjectOutputStream out) {
        try {
            out.writeObject(message);
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendFile(File file, ObjectOutputStream out) {
        try {
            Object[] objects = new Object[2];
            objects[0] = file.getName();
            byte[] content = Files.readAllBytes(file.toPath());
            objects[1] = content;
            out.writeObject(objects);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}