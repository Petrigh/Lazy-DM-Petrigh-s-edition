package GUI;

import javax.swing.table.DefaultTableModel;

public class CustomTableModel extends DefaultTableModel implements Reorderable{

    @Override
    public void reorder(int fromIndex, int toIndex) {
        /*
            aca va el codigo para intercambiar los lugares
            esto funciona mas o menos xd
            https://stackoverflow.com/questions/638807/how-do-i-drag-and-drop-a-row-in-a-jtable
            lo saque de aca.
            la implementacion de este metodo me la acabo de inventar
            quiza el moveRow funcione pero me dio paja

         */

        try {
            Object jugadorFrom =  this.dataVector.get(fromIndex);

            Object jugadorTo =  this.dataVector.get(toIndex);

            this.dataVector.set(toIndex , jugadorFrom);

            this.dataVector.set(fromIndex, jugadorTo);

        } catch (ArrayIndexOutOfBoundsException e) {

            Object jugadorFrom =  this.dataVector.get(fromIndex);

            Object jugadorTo =  this.dataVector.get(toIndex - 1);

            this.dataVector.set(toIndex - 1, jugadorFrom);

            this.dataVector.set(fromIndex, jugadorTo);

        } finally {

            this.fireTableDataChanged();
        }



    }
}
