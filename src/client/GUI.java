package client;

import javax.swing.*;
import java.awt.*;
import java.util.Vector;

import static commonThings.API.DELETE_FILE;

public class GUI extends JFrame {
    private JTree fileList;
    private JScrollPane centerScrollPane;
    private JPanel center;
    private JButton upload;
    private JButton delete;
    private JPanel down;
    private JTextField jTextField;
    private JPanel up;
    private JTextField loginField;
    private JPasswordField passwordField;
    private JButton signIn;
    private Connection connection;
    private String login;

    public GUI() {
        connection = new Connection();
        connection.init(this);

        setTitle("DropBox");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(400,400);
        setLocationRelativeTo(null);
        setResizable(false);

        up = new JPanel(new GridLayout(1,3));
        loginField = new JTextField();
        passwordField = new JPasswordField();
        signIn = new JButton("Login");
        up.add(loginField);
        up.add(passwordField);
        up.add(signIn);
        up.setVisible(true);
        add(up, BorderLayout.NORTH);

        down = new JPanel(new GridLayout(1,2));
        delete = new JButton("Delete");
        upload = new JButton("Upload");
        down.add(delete);
        down.add(upload);
        down.setVisible(true);
        add(down, BorderLayout.SOUTH);

        center = new JPanel(new GridLayout(1,1));
        center.setVisible(false);
        add(center, BorderLayout.CENTER);

        setVisible(true);

        signIn.addActionListener(e -> sendAuthData());

        delete.addActionListener(e -> sendMessage(DELETE_FILE + " " + login + " " + fileList.getSelectionPath().getLastPathComponent().toString()));
    }
    public void sendAuthData() {
        login = loginField.getText();
        connection.auth(login, String.valueOf(passwordField.getPassword()));
        loginField.setText("");
        passwordField.setText("");
    }

    public void showFileList(Vector<String> files) {
        center.setVisible(false);
        center.removeAll();
        fileList = new JTree(files);
        center.add(new JScrollPane(fileList, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED));
        center.setVisible(true);
    }

    public void clearLoginField() {
        up.removeAll();
        jTextField = new JTextField("Ваша директория на сервере");
        jTextField.setEditable(false);
        jTextField.setHorizontalAlignment(SwingConstants.CENTER);
        up.add(jTextField);
    }

    public void sendMessage(String msg) {
        connection.sendMessage(msg);
    }
}
