package client;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FileChooser extends JFrame {
    private JButton saveFile = null;
    private JButton openDir = null;
    private JFileChooser fileChooser = null;

    public FileChooser() {
//        super("Выбер файла");
//        setDefaultCloseOperation(EXIT_ON_CLOSE);
//        openDir = new JButton("Открыть директорию");
//        saveFile = new JButton("Сохранить файл");

        fileChooser = new JFileChooser();

//        addFileChooserListeners();
//
//        JPanel contents = new JPanel();
//        contents.add(openDir);
//        contents.add(saveFile);
//        setContentPane(contents);
//        setSize(360, 110);
//        setVisible(true);
        fileChooser.setDialogTitle("Выберите файл");
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        int result = fileChooser.showSaveDialog(FileChooser.this);
        if (result == JFileChooser.APPROVE_OPTION) {
            System.out.println(fileChooser.getSelectedFile().toString());
//            JOptionPane.showMessageDialog(FileChooser.this,
//                    "Файл " + fileChooser.getSelectedFile() + " сохранен");
        }
    }

    private void addFileChooserListeners() {
        saveFile.addActionListener(e -> {
            fileChooser.setDialogTitle("Сохранение файла");
             fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
             int result = fileChooser.showSaveDialog(FileChooser.this);
             if (result == JFileChooser.APPROVE_OPTION) {
                 JOptionPane.showMessageDialog(FileChooser.this,
                         "Файл " + fileChooser.getSelectedFile() + " сохранен");
             }
        });
    }

    public static void main(String[] args) {
        //UIManager.put("FileChooser.saveButtonText", "Сохранить");
        //UIManager.put("FileChooser.cancelButtonText", "Отмена");
        //UIManager.put("FileChooser.fileNameLabelText", "Наименование файла");
        //UIManager.put("FileChooser.filesOfTypeLabelText", "наименование файла");
        //UIManager.put("FileChooser.lookInLabelText", "Директория");
        //UIManager.put("FileChooser.saveInLabelText", "Сохранить в директорию");
        //UIManager.put("FileChooser.folderNameLabelText", "Путь директории");
        new FileChooser();

    }
}
