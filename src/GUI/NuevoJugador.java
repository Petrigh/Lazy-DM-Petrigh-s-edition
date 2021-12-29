package GUI;

import Data.Player;
import Data.Read;

import javax.swing.*;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.NumberFormat;

import static GUI.LazyDM.*;

public class NuevoJugador extends Frame {
    public static JTextField tfieldName;
    public static JFormattedTextField tfieldIniciativa;
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

        NumberFormat format = NumberFormat.getInstance();
        format.setGroupingUsed(false);
        NumberFormatter formatter = new NumberFormatter(format);
        formatter.setValueClass(Integer.class);
        formatter.setMinimum(Integer.MIN_VALUE);
        formatter.setMaximum(Integer.MAX_VALUE);
        formatter.setAllowsInvalid(false);
        formatter.setCommitsOnValidEdit(true);
        tfieldIniciativa = new JFormattedTextField(formatter);
        tfieldIniciativa.setBounds(140, 117, 50, 20);
        tfieldIniciativa.setText("0");
        frame.getContentPane().add(tfieldIniciativa);

        JLabel labelIniciativa = new JLabel("INICIATIVA");
        labelIniciativa.setBounds(65,120,70,14);
        frame.getContentPane().add(labelIniciativa);

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
        frame.addWindowListener(new WindowAdapter()
        {
            public void windowClosing(WindowEvent e)
            {
                frame.dispose();
                frameMain.dispose();
                LazyDM.activate();
                LazyDM.createWindow();
            }
        });
        create(frame);
        frame.setResizable(false);
        frame.setVisible(true);
    }

    private static class handler implements ActionListener{

        public void actionPerformed(ActionEvent e) {
            int delay = 3000;//ms
            ActionListener taskPerformer = evt -> labelError.setText("");
            labelError.setText("Guardando...");
            StringBuilder error = new StringBuilder();
            Read read = new Read();
            if(read.validate(tfieldName.getText(),tfieldIniciativa.getText(),error)) //imprime error
                labelError.setText(error.toString());
            new Timer(delay, taskPerformer).start(); //saca el error despues de 'delay' milliseconds

            if(labelError.getText().equals("Guardando...")) {
                Player p;
                p = new Player(tfieldName.getText(), Integer.valueOf(tfieldIniciativa.getText()));
                listPlayer.add(p);
                frame.dispose();
                frameMain.dispose();
                LazyDM.activate();
                LazyDM.createWindow();
            }
        }
    }

    private static class incrementar implements ActionListener{
                public void actionPerformed(ActionEvent evt) {
                    ini=Integer.valueOf(tfieldIniciativa.getText());
                    ini++;
                    tfieldIniciativa.setValue(ini);
                    tfieldIniciativa.update(tfieldIniciativa.getGraphics());
                }
    }
    private static class decrementar implements ActionListener{
                public void actionPerformed(ActionEvent evt) {
                    ini=Integer.valueOf(tfieldIniciativa.getText());
                    ini--;
                    tfieldIniciativa.setValue(ini);
                    tfieldIniciativa.update(tfieldIniciativa.getGraphics());
                }
    }
}