package GUI;

import Data.Player;
import Data.Read;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static GUI.Main.*;

public class NuevoJugador extends Frame {
    public static JTextField tfieldName;
    public static JTextField tfieldIniciativa;
    private static JLabel labelError;
    private static JFrame frame;
    private static Integer ini;
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
        tfieldIniciativa.setBounds(140, 120, 50, 20);
        ini=0;
        tfieldIniciativa.setText(ini.toString());
        frame.getContentPane().add(tfieldIniciativa);
        //Botones + -
        JButton mas = new JButton("+");
        mas.setBounds(255,120,45,20);
        mas.setBackground(new Color(59, 89, 182));
        mas.setForeground(Color.WHITE);
        mas.setFocusPainted(false);
        mas.setFont(new Font("Tahoma", Font.BOLD, 12));
        mas.addActionListener(new incrementar());
        frame.getContentPane().add(mas);

        JButton menos = new JButton("-");
        menos.setBounds(200,120,45,20);
        menos.setBackground(new Color(59, 89, 182));
        menos.setForeground(Color.WHITE);
        menos.setFocusPainted(false);
        menos.setFont(new Font("Tahoma", Font.BOLD, 12));
        menos.addActionListener(new decrementar());
        frame.getContentPane().add(menos);

        //Texto Error
        labelError = new JLabel("", SwingConstants.CENTER);
        labelError.setBounds(0,30,450,25);
        frame.getContentPane().add(labelError);

        //Boton Guardar
        JButton guardar = new JButton("GUARDAR");
        guardar.setBounds(175,155,100,23);
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
            ActionListener taskPerformer = evt -> {
                if(labelError.getText().equals("Guardando...")) {
                    frame.dispose();
                    frameMain.dispose();
                    Main.createWindow();
                }
                labelError.setText("");
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

    private static class incrementar implements ActionListener{
                public void actionPerformed(ActionEvent evt) {
                    ini=Integer.valueOf(tfieldIniciativa.getText());
                    ini++;
                    tfieldIniciativa.setText(String.valueOf(ini));
                    tfieldIniciativa.update(tfieldIniciativa.getGraphics());
                }
    }
    private static class decrementar implements ActionListener{
                public void actionPerformed(ActionEvent evt) {
                    ini=Integer.valueOf(tfieldIniciativa.getText());
                    ini--;
                    tfieldIniciativa.setText(String.valueOf(ini));
                    tfieldIniciativa.update(tfieldIniciativa.getGraphics());
                }
    }
}