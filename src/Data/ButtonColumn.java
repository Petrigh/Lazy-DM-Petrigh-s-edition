package Data;

import GUI.EditarJugador;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import static GUI.Main.listPlayer;

/**
 *  The ButtonColumn class provides a renderer and an editor that looks like a
 *  JButton. The renderer and editor will then be used for a specified column
 *  in the table. The TableModel will contain the String to be displayed on
 *  the button.
 *
 *  The button can be invoked by a mouse click or by pressing the space bar
 *  when the cell has focus. Optionally a mnemonic can be set to invoke the
 *  button. When the button is invoked the provided Action is invoked. The
 *  source of the Action will be the table. The action command will contain
 *  the model row number of the button that was clicked.
 *
 */
public class ButtonColumn extends AbstractCellEditor
        implements TableCellRenderer, TableCellEditor, ActionListener, MouseListener
{
    private JTable table;
    private Action action;
    private int mnemonic;
    private Border originalBorder;
    private Border focusBorder;

    private JButton renderButton;
    private JButton editButton;
    private Object editorValue;
    private boolean isButtonColumnEditor;

    private Action delete = new AbstractAction(){
        public void actionPerformed(ActionEvent e) {
            int confirmWindow = JOptionPane.showConfirmDialog(null, "¿Desea eliminar a este jugador?", "Advertencia", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
            if(confirmWindow == 0) {
                JTable table = (JTable)e.getSource();
                int row = table.getSelectedRow();
                Player p;
                p = new Player(table.getValueAt(row, 0).toString(), Integer.valueOf(table.getValueAt(row, 1).toString()));
                for (Player play : listPlayer) {
                    if ((play.getName().equals(p.getName())) && (play.getInitiative().equals(p.getInitiative()))) {
                        int index = listPlayer.indexOf(play);
                        listPlayer.remove(index);
                        break;
                    }
                }
                int modelRow = Integer.valueOf( e.getActionCommand() );
                ((DefaultTableModel)table.getModel()).removeRow(modelRow);
            }
        }
    };
    private Action edit = new AbstractAction(){
        public void actionPerformed(ActionEvent e) {
            JTable table = (JTable)e.getSource();
            int row = table.getSelectedRow();
            EditarJugador.createWindow(table.getValueAt(row, 0).toString(),Integer.valueOf(table.getValueAt(row, 1).toString()));
        }
    };

    public ButtonColumn(JTable table, int action, int column)
    {
        this.table = table;
        if(action == 0) {
            this.action = edit;
        }else{
             this.action = delete;
         }

        renderButton = new JButton();
        editButton = new JButton();
        editButton.setFocusPainted( false );
        editButton.addActionListener( this );
        originalBorder = editButton.getBorder();
        setFocusBorder( new LineBorder(Color.BLUE) );

        TableColumnModel columnModel = table.getColumnModel();
        columnModel.getColumn(column).setCellRenderer( this );
        columnModel.getColumn(column).setCellEditor( this );
        table.addMouseListener( this );
    }


    public Border getFocusBorder()
    {
        return focusBorder;
    }

    public void setFocusBorder(Border focusBorder)
    {
        this.focusBorder = focusBorder;
        editButton.setBorder( focusBorder );
    }

    public int getMnemonic()
    {
        return mnemonic;
    }

    public void setMnemonic(int mnemonic)
    {
        this.mnemonic = mnemonic;
        renderButton.setMnemonic(mnemonic);
        editButton.setMnemonic(mnemonic);
    }

    private void checkObjectValueToSetButtonText(Object value, JButton button) {

        if (value == null)
        {
            button.setText( "" );
            button.setIcon( null );
        }
        else if (value instanceof Icon)
        {
            button.setText( "" );
            button.setIcon( (Icon)value );
        }
        else
        {
            button.setText( value.toString() );
            button.setIcon( null );
        }

    }

    @Override
    public Component getTableCellEditorComponent(
            JTable table, Object value, boolean isSelected, int row, int column)
    {
        checkObjectValueToSetButtonText(value, editButton);

        this.editorValue = value;
        return editButton;
    }

    @Override
    public Object getCellEditorValue()
    {
        return editorValue;
    }

    public Component getTableCellRendererComponent(
            JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
    {
        if (isSelected)
        {
            renderButton.setForeground(table.getSelectionForeground());
            renderButton.setBackground(table.getSelectionBackground());
        }
        else
        {
            renderButton.setForeground(table.getForeground());
            renderButton.setBackground(UIManager.getColor("Button.background"));
        }

        if (hasFocus)
        {
            renderButton.setBorder( focusBorder );
        }
        else
        {
            renderButton.setBorder( originalBorder );
        }

        checkObjectValueToSetButtonText(value, renderButton);

        return renderButton;
    }

    public void actionPerformed(ActionEvent e)
    {
        int row = table.convertRowIndexToModel( table.getEditingRow() );
        fireEditingStopped();

        //  Invoke the Action

        ActionEvent event = new ActionEvent(
                table,
                ActionEvent.ACTION_PERFORMED,
                "" + row);
        action.actionPerformed(event);
    }

    public void mousePressed(MouseEvent e)
    {
        if (table.isEditing()
                &&  table.getCellEditor() == this)
            isButtonColumnEditor = true;
    }

    public void mouseReleased(MouseEvent e)
    {
        if (isButtonColumnEditor
                &&  table.isEditing())
            table.getCellEditor().stopCellEditing();

        isButtonColumnEditor = false;
    }

    public void mouseClicked(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
}
