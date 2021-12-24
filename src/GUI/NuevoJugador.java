package GUI;

import Data.Player;
import Data.Read;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import static GUI.Main.*;

public class NuevoJugador extends Frame {
    public static JTextField tfieldName;
    public static JTextField tfieldIniciativa;
    private static JLabel labelError;
    private static JButton guardar;
    private static JFrame frame;

    public static void create(JFrame frame) {
        //Nombre
        tfieldName = new JTextField();
        tfieldName.setBounds(140,77,250,20);
        frame.getContentPane().add(tfieldName);
        JLabel labelName = new JLabel("NOMBRE");
        labelName.setBounds(65,80,70,14);
        frame.getContentPane().add(labelName);

        //Inciativa
        JLabel labelIniciativa = new JLabel("INICIATIVA");
        labelIniciativa.setBounds(65,120,70,14);
        frame.getContentPane().add(labelIniciativa);
        tfieldIniciativa = new JTextField();
        tfieldIniciativa.setBounds(140, 117, 250, 20);
        frame.getContentPane().add(tfieldIniciativa);

        //Texto Error
        labelError = new JLabel("", SwingConstants.CENTER);
        labelError.setBounds(0,30,450,25);
        frame.getContentPane().add(labelError);

        //Boton Guardar
        guardar = new JButton("GUARDAR");
        guardar.setBounds(175,150,100,23);
        guardar.setBackground(new Color(59, 89, 182));
        guardar.setForeground(Color.WHITE);
        guardar.setFocusPainted(false);
        guardar.setFont(new Font("Tahoma", Font.BOLD, 12));
        guardar.addActionListener( new handler());
        frame.getRootPane().setDefaultButton(guardar);
        frame.getContentPane().add(guardar);
    }
    public static void createWindow() {
        frame = new JFrame("LAZY DM - NUEVO JUGADOR");
        frame.setBounds(100, 100, 450, 250);
        frame.getContentPane().setLayout(null);
        create(frame);
        frame.setResizable(false);
        frame.setVisible(true);
    }

    private static class handler implements ActionListener{

        public void actionPerformed(ActionEvent e) {
            int delay = 1000; //milliseconds
            ActionListener taskPerformer = new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    if(labelError.getText().equals("Guardando...")) {
                        frame.dispose();
                        frameMain.dispose();
                        Main.createWindow();
                    }
                    labelError.setText("");
                }
            };
            StringBuilder error = new StringBuilder();
            if(Read.validate(tfieldName.getText(),tfieldIniciativa.getText(),error)){
                labelError.setText(error.toString());
            }else {
                Player p;
                p = new Player(tfieldName.getText(), Integer.valueOf(tfieldIniciativa.getText()));
                listPlayer.add(p);
                labelError.setText("Guardando...");
            }
            new Timer(delay, taskPerformer).start();
        }
    }

}