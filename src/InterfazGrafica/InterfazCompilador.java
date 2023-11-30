package InterfazGrafica;

import com.sun.tools.javac.Main;
import lexico.MainLexico;
import lexico.tablaDeSimbolos.ManejadorErrores;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;

public class InterfazCompilador {
    private JPanel panel;
    private JTextArea codigoFuente;
    private JButton cargarArchivo;
    private JButton GuardarArchivo;
    private JButton Compilar;
    private JTextArea Terminal;
    private JLabel estatusGuardado;
    private String filePath;
    private ManejadorErrores manejadorErrores;

    public InterfazCompilador(){
        manejadorErrores = ManejadorErrores.obtenerInstancia();
        cargarArchivo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int seleccion = fileChooser.showOpenDialog(panel);
                if(seleccion == JFileChooser.APPROVE_OPTION){
                    File selectedFile = fileChooser.getSelectedFile();

                    String contenido = null;
                    try {
                        filePath = selectedFile.getAbsolutePath();

                        estatusGuardado.setText("Archivo guardado");
                        contenido = new String(Files.readAllBytes(selectedFile.toPath()));
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }

                    codigoFuente.setText(contenido);                }
            }
        });
        GuardarArchivo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int seleccion = fileChooser.showSaveDialog(panel);
                if(seleccion == JFileChooser.APPROVE_OPTION){
                    File selectedFile = fileChooser.getSelectedFile();
                    try {
                        selectedFile = new File(selectedFile.getAbsolutePath());
                        filePath = selectedFile.getAbsolutePath();
                        Files.write(selectedFile.toPath(), codigoFuente.getText().getBytes());
                        estatusGuardado.setText("Archivo guardado");

                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });

        Compilar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Terminal.setText("");
                manejadorErrores.setErrores(new ArrayList<>());
                try {
                    MainLexico lexico =  new MainLexico(filePath);
                    for(String error : manejadorErrores.getErrores()) {
                        Terminal.append(error + "\n");
                    }
                    if (manejadorErrores.getCantidadErrores() == 0) {
                        Terminal.append("Compilación exitosa");
                    }

                } catch (Exception ex) {

                    throw new RuntimeException(ex);

                }

            }
        });

        codigoFuente.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                super.keyTyped(e);

                estatusGuardado.setText("Archivo no guardado");
            }
        });
    }


    public static void main(String[] args) {
        JFrame frame = new JFrame("InterfazCompilador");
        frame.setContentPane(new InterfazCompilador().panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
//        llama al main de otro paquete



    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
