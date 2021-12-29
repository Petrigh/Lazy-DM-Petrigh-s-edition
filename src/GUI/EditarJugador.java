package GUI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.NumberFormat;

import javax.swing.*;
import javax.swing.text.NumberFormatter;

import Data.Player;
import Data.Read;

import static GUI.LazyDM.*;

public class EditarJugador {
    public static JTextField NameTfieldEdit;
    public static JFormattedTextField IniciativanTfieldEdit;
    private static JLabel labelErrorEdit;
    private static JFrame frame;
    private static Player player;
    private static Integer ini;


    public static void create(JFrame frame) {
        //Nombre
        NameTfieldEdit = new JTextField();
        NameTfieldEdit.setBounds(140,77,250,20);
        NameTfieldEdit.setText(player.getName());
        frame.getContentPane().add(NameTfieldEdit);
        JLabel labelNombreEdit = new JLabel("NOMBRE");
        labelNombreEdit.setBounds(65,80,70,14);
        frame.getContentPane().add(labelNombreEdit);

        //Inciativa
        JLabel labelIniciativaEdit = new JLabel("INICIATIVA");
        labelIniciativaEdit.setBounds(65,120,70,14);
        frame.getContentPane().add(labelIniciativaEdit);
        NumberFormat format = NumberFormat.getInstance();
        format.setGroupingUsed(false);
        NumberFormatter formatter = new NumberFormatter(format);
        formatter.setValueClass(Integer.class);
        formatter.setMinimum(Integer.MIN_VALUE);
        formatter.setMaximum(Integer.MAX_VALUE);
        formatter.setAllowsInvalid(false);
        formatter.setCommitsOnValidEdit(true);

        IniciativanTfieldEdit = new JFormattedTextField(formatter);
        IniciativanTfieldEdit.setBounds(140, 117, 50, 20);
        IniciativanTfieldEdit.setText(String.valueOf(player.getInitiative()));
        frame.getContentPane().add(IniciativanTfieldEdit);

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
        labelErrorEdit = new JLabel("", SwingConstants.CENTER);
        labelErrorEdit.setBounds(0,30,450,25);
        frame.getContentPane().add(labelErrorEdit);

        //Boton Editar
        JButton editarEdit = new JButton("EDITAR");
        editarEdit.setBounds(180,150,90,23);
        editarEdit.setBackground(new Color(59, 89, 182));
        editarEdit.setForeground(Color.WHITE);
        editarEdit.setFocusPainted(false);
        editarEdit.setFont(new Font("Tahoma", Font.BOLD, 12));
        editarEdit.addActionListener(new handler());
        frame.getRootPane().setDefaultButton(editarEdit);
        frame.getContentPane().add(editarEdit);
    }
    public static void createWindow(String n, Integer p) {
        player = new Player(n,p);
        frame = new JFrame("LAZY DM - EDITAR JUGADOR");
        frame.setBounds(100, 100, 450, 250);
        frame.getContentPane().setLayout(null);
        frame.addWindowListener(new WindowAdapter()
        {
            public void windowClosing(WindowEvent e)
            {
                frame.dispose();
                frameMain.dispose();
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
            ActionListener taskPerformer = evt -> labelErrorEdit.setText("");
            labelErrorEdit.setText("Guardando...");
            StringBuilder error = new StringBuilder();
            Read read = new Read();
            if(read.validate(NameTfieldEdit.getText(),IniciativanTfieldEdit.getText(),error)) //imprime error
                labelErrorEdit.setText(error.toString());
            new Timer(delay, taskPerformer).start(); //saca el error despues de 'delay' milliseconds

            if(labelErrorEdit.getText().equals("Guardando...")) {
                for (Player play : listPlayer) { //busca el jugador y lo edita
                    if ((play.getName().equals(player.getName())) && (play.getInitiative().equals(player.getInitiative()))) {
                        int index = listPlayer.indexOf(play);
                        Player aux;
                        aux = new Player(NameTfieldEdit.getText(), Integer.valueOf(IniciativanTfieldEdit.getText()));
                        listPlayer.set(index,aux);
                        break;
                    }
                }
                frame.dispose();
                frameMain.dispose();
                LazyDM.createWindow();
            }
        }
    }
    private static class incrementar implements ActionListener{
                public void actionPerformed(ActionEvent evt) {
                    ini=Integer.valueOf(IniciativanTfieldEdit.getText());
                    ini++;
                    IniciativanTfieldEdit.setValue(ini);
                    IniciativanTfieldEdit.update(IniciativanTfieldEdit.getGraphics());
                }
    }

    private static class decrementar implements ActionListener{
                public void actionPerformed(ActionEvent evt) {
                    ini=Integer.valueOf(IniciativanTfieldEdit.getText());
                    ini--;
                    IniciativanTfieldEdit.setValue(ini);
                    IniciativanTfieldEdit.update(IniciativanTfieldEdit.getGraphics());
                }
    }
}
