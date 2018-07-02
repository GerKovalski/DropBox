package commonThings;

import java.io.ObjectOutputStream;

public class commonFunctions {
    public void sendMessage(String message, ObjectOutputStream out) {
        try {
            out.writeObject(message);
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}