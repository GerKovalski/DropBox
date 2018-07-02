package server;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Collections;
import java.util.Vector;

import commonThings.*;

public class Clients implements API {
    private ServerCore server;
    private Socket socket;
    ObjectOutputStream out;
    ObjectInputStream in;
    commonFunctions commonFunctions;
    private String login;
    private Vector<File> filesList;

    public Clients(ServerCore server, Socket socket) {
        try {
            this.server = server;
            this.socket = socket;
            this.in = new ObjectInputStream(socket.getInputStream());
            this.out = new ObjectOutputStream(socket.getOutputStream());
            this.commonFunctions = new commonFunctions();
            this.filesList = new Vector<>();
        } catch (IOException e) {
            e.printStackTrace();
        }
        new Thread(() -> {
            Object request = null;
            while (true) {
                try {
                    request = in.readObject();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                if (request instanceof String) {
                    String msg = request.toString();
                    if (msg.startsWith(AUTH)) {
                        String[] elements = msg.split(" ");
                        String tmp = server.getAuthService().checkEntryInfo(elements[1], elements[2]);
                        if (tmp != null) {
                            System.out.println("Клиент " + elements[1] + " прошел аутентификацию!");
                            commonFunctions.sendMessage(AUTH_SUCCESSFUL, out);
                            break;
                        }
                    }
                }
            }
            while (true) {
                try {
                    request = in.readObject();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                if (request instanceof String) {
                    String msg = request.toString();
                    if (msg.startsWith(REQUEST_FOR_FILES) || msg.startsWith(DELETE_FILE)) {
                        login = msg.split(" ")[1];
                        if (msg.startsWith(DELETE_FILE)) {
                            new File(PATH + login + "/" + msg.split(" ")[2]).delete();
                        }
                        File userDir = new File(PATH + login);
                        filesList.clear();
                        for (File file : userDir.listFiles()) {
                            filesList.add(file);
                        }
                        Collections.sort(filesList);
                        sendFiles(filesList, out);
                    }
                }
            }
        }).start();
    }

    public void sendFiles(Vector<File> files_list, ObjectOutputStream out) {
        Vector<String> names = new Vector<>();
        for (File file : files_list) {
            names.add(file.getName());
        }
        try {
            out.writeObject(names);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
