package client;

import commonThings.API;
import commonThings.Const;
import commonThings.commonFunctions;

import javax.swing.*;
import java.io.*;
import java.net.Socket;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.util.Vector;


public class Connection implements API, Const {
    Socket socket;
    ObjectInputStream in;
    ObjectOutputStream out;
    commonFunctions commonFunctions;
    String login;

    public void init(GUI window) {
        try {
            this.socket = new Socket(URL, PORT);
            this.out = new ObjectOutputStream(socket.getOutputStream());
            this.in = new ObjectInputStream(socket.getInputStream());
            this.commonFunctions = new commonFunctions();

            new Thread(() -> {
                try {
                    Object request = null;
                    try {
                        while (true) {
                            request = in.readObject();
                            if (request instanceof String) {
                                String msg = request.toString();
                               if (msg.startsWith(AUTH_SUCCESSFUL)) {
                                   window.changePaneToMain();
                                   sendMessage(REQUEST_FOR_FILES + " " + login);
                                   break;
                                }
                            }
                        }
                        downloadFile(window);
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                } catch(IOException e){
                    e.printStackTrace();
                }
            }).start();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void auth(String login, String password) {
        try {
            this.login = login;
            out.writeObject(AUTH + " " + login + " " + password);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(String msg) {
        commonFunctions.sendMessage(msg, out);
    }

    public void sendFile(File file) {
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

    public void downloadFile(GUI window) {
        Object request = null;
        while (true) {
            try {
                request = in.readObject();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            if (request instanceof Object[]) {
                Object[] objects = (Object[])request;
                String fileName = objects[0].toString();
                byte[] content = (byte[])objects[1];
                try {
                    JFileChooser fileChooser = new JFileChooser();
                    fileChooser.setDialogTitle("Выберите файл");
                    fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                    int result = fileChooser.showSaveDialog(window);
                    if (result == JFileChooser.APPROVE_OPTION) {
                        File file = new File(fileChooser.getCurrentDirectory() + "/" + fileName);
                        file.createNewFile();
                        Files.write(file.toPath(), content);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (request instanceof Vector) {
                Vector<String> fileList = (Vector)request;
                window.showFileList(fileList);
            }
        }
    }
}
