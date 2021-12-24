package GUI;

import java.awt.*;
import java.awt.event.KeyEvent;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.Collections;

import Data.ButtonColumn;
import Data.Player;

public class Main extends DefaultTableModel {
    public static JFrame frameMain;
    public static ArrayList<Player> listPlayer = new ArrayList<>();

    public static void create(JFrame frame) {

        //Genero Botones

        //Boton Nuevo
        JButton nuevo = new JButton("+ NUEVO");
        nuevo.setBounds(147,20,100,23);
        nuevo.setBackground(new Color(59, 89, 182));
        nuevo.setForeground(Color.WHITE);
        nuevo.setFocusPainted(false);
        nuevo.setFont(new Font("Tahoma", Font.BOLD, 12));
        nuevo.addActionListener(e -> NuevoJugador.createWindow());
        frame.getRootPane().setDefaultButton(nuevo);
        frame.getContentPane().add(nuevo);

        //Genero la tabla
        JTable tabla = new JTable();
        listPlayer.sort(Collections.reverseOrder());
        DefaultTableModel model=new DefaultTableModel();
        model.addColumn("NOMBRE");
        model.addColumn("INICIATIVA");
        model.addColumn("");
        model.addColumn("");
        for (Player player : listPlayer)
            model.addRow(new Object[]{
                    player.getName(),
                    player.getInitiative(),
                    "EDITAR",
                    "ELIMINAR"
            });
        tabla.setModel(model);
        tabla.getColumnModel().getColumn(0).setMaxWidth(120);
        tabla.getColumnModel().getColumn(1).setMaxWidth(30);
        tabla.getColumnModel().getColumn(2).setMaxWidth(95);
        tabla.getColumnModel().getColumn(3).setMaxWidth(95);
        tabla.getTableHeader().setReorderingAllowed(false);
        tabla.setTableHeader(null);
        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBounds(20,70,340,300);
        frame.getContentPane().add(scroll);

        //botones en columnas
        ButtonColumn buttonColumnEdit = new ButtonColumn(tabla, 0, 2);
        buttonColumnEdit.setMnemonic(KeyEvent.VK_D);
        ButtonColumn buttonColumnDelete = new ButtonColumn(tabla, 1, 3);
        buttonColumnDelete.setMnemonic(KeyEvent.VK_D);
    }
    public static void createWindow() {
        frameMain = new JFrame("LAZY DM");
        frameMain.setBounds(100, 100, 395, 420);
        frameMain.getContentPane().setLayout(null);
        create(frameMain);
        frameMain.setResizable(false);
        frameMain.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frameMain.setVisible(true);
    }
    public static void main(String[] args) {
        createWindow();
    }
}