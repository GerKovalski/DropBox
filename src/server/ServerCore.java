package server;

import commonThings.API;
import commonThings.Const;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collections;
import java.util.List;
import java.util.Vector;

public class ServerCore implements API, Const {
    private List clients;
    private AuthService authService;

    public AuthService getAuthService() {
        return authService;
    }

    public ServerCore() {
        clients = Collections.synchronizedList(new Vector<Clients>());
        Socket socket = null;
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(PORT);
            authService = new AuthService();
            System.out.println("Ожидание подключений!");
            while (true) {
                socket = serverSocket.accept();
                clients.add(new Clients(this, socket));
                System.out.println("Клиент подключен!");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
