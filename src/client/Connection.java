package client;

import commonThings.API;
import commonThings.Const;
import commonThings.commonFunctions;

import java.io.*;
import java.net.Socket;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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
                                   window.clearLoginField();
                                   sendMessage(REQUEST_FOR_FILES + " " + login);
                                   break;
                                }
                            }
                        }
                        while (true) {
                            request = in.readObject();
                            if (request instanceof Vector) {
                                Vector<String> fileList = (Vector)request;
                                showFileList(fileList, window);
                            }
                        }
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

    private void showFileList(Vector<String> fileList, GUI window) {
        window.showFileList(fileList);
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
}
