package GUI;

import Data.Player;
import Data.Read;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static GUI.Main.frameMain;
import static GUI.Main.listPlayer;

public class EditarJugadorForm {

    private JPanel panel1;
    private JButton guardarButton;
    private JButton button1;
    private JButton button2;
    private JTextField IniciativanTfieldEdit;
    private JTextField NameTfieldEdit;
    private Player player;
    private JLabel labelErrorEdit;
    private JFrame frame;
    private Integer ini;

    public EditarJugadorForm(String n, Integer p) {
        player = new Player(n, p);
        initializeListeners();
        initializeLabels();
    }

    private void initializeLabels() {
        NameTfieldEdit.setText(player.getName());
        IniciativanTfieldEdit.setText(player.getInitiative().toString());
    }

    private void initializeListeners() {

        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                ini = Integer.valueOf(IniciativanTfieldEdit.getText());
                ini++;
                IniciativanTfieldEdit.setText(String.valueOf(ini));
                IniciativanTfieldEdit.update(IniciativanTfieldEdit.getGraphics());
            }
        });
        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                ini = Integer.valueOf(IniciativanTfieldEdit.getText());
                ini--;
                IniciativanTfieldEdit.setText(String.valueOf(ini));
                IniciativanTfieldEdit.update(IniciativanTfieldEdit.getGraphics());
            }
        });

        guardarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                int delay = 3000;//ms
                ActionListener taskPerformer = evt -> labelErrorEdit.setText("");
                labelErrorEdit.setText("Guardando...");
                StringBuilder error = new StringBuilder();
                Read read = new Read();
                if (read.validate(NameTfieldEdit.getText(), IniciativanTfieldEdit.getText(), error)) //imprime error
                    labelErrorEdit.setText(error.toString());
                new Timer(delay, taskPerformer).start(); //saca el error despues de 'delay' milliseconds

                if (labelErrorEdit.getText().equals("Guardando...")) {
                    for (Player play : listPlayer) { //busca el jugador y lo edita
                        if ((play.getName().equals(player.getName())) && (play.getInitiative().equals(player.getInitiative()))) {
                            int index = listPlayer.indexOf(play);
                            Player aux;
                            aux = new Player(NameTfieldEdit.getText(), Integer.valueOf(IniciativanTfieldEdit.getText()));
                            listPlayer.set(index, aux);
                            break;
                        }
                    }
                    frame.dispose();
                    frameMain.dispose();
                    Main.createWindow();
                }
            }
        });
    }

    public void createWindow() {

        frame = new JFrame("LAZY DM - EDITAR JUGADOR");

        frame.setBounds(100,100,320,150);

        frame.getRootPane().setDefaultButton(guardarButton);

        frame.setResizable(false);

        frame.setVisible(true);

        frame.setContentPane(this.panel1);

    }

}
