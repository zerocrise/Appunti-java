package gui;

import model.*;
import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

/**
 * TableModel personalizzato per visualizzare le pubblicazioni nella JTable
 */
public class BibliotecaTableModel extends AbstractTableModel {

    private ArrayList<Pubblicazione> pubblicazioni;
    private String[] columnNames = {
            "Tipo",
            "Titolo",
            "Autore/Editore",
            "Anno",
            "Pagine",
            "Prezzo",
            "Disponibile"
    };

    public BibliotecaTableModel(ArrayList<Pubblicazione> pubblicazioni) {
        this.pubblicazioni = pubblicazioni;
    }

    @Override
    public int getRowCount() {
        return pubblicazioni.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch (columnIndex) {
            case 3: // Anno
            case 4: // Pagine
                return Integer.class;
            case 5: // Prezzo
                return Double.class;
            case 6: // Disponibile
                return Boolean.class;
            default:
                return String.class;
        }
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Pubblicazione pub = pubblicazioni.get(rowIndex);

        switch (columnIndex) {
            case 0: // Tipo
                return pub.getTipo();

            case 1: // Titolo
                return pub.getTitolo();

            case 2: // Autore/Editore
                if (pub instanceof Libro) {
                    return ((Libro) pub).getAutore();
                } else if (pub instanceof Rivista) {
                    return ((Rivista) pub).getEditore();
                } else if (pub instanceof Fumetto) {
                    return ((Fumetto) pub).getAutore();
                }
                return "";

            case 3: // Anno
                return pub.getAnnoPubblicazione();

            case 4: // Pagine
                return pub.getNumeroPagine();

            case 5: // Prezzo
                return pub.getPrezzo();

            case 6: // Disponibile
                return pub.isDisponibile();

            default:
                return null;
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        // Solo la colonna "Disponibile" è editabile
        return columnIndex == 6;
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        if (columnIndex == 6) {
            Pubblicazione pub = pubblicazioni.get(rowIndex);
            pub.setDisponibile((Boolean) aValue);
            fireTableCellUpdated(rowIndex, columnIndex);
        }
    }

    /**
     * Aggiunge una pubblicazione alla tabella
     */
    public void addPubblicazione(Pubblicazione pub) {
        pubblicazioni.add(pub);
        fireTableRowsInserted(pubblicazioni.size() - 1, pubblicazioni.size() - 1);
    }

    /**
     * Rimuove una pubblicazione dalla tabella
     */
    public void removePubblicazione(int rowIndex) {
        if (rowIndex >= 0 && rowIndex < pubblicazioni.size()) {
            pubblicazioni.remove(rowIndex);
            fireTableRowsDeleted(rowIndex, rowIndex);
        }
    }

    /**
     * Ottiene la pubblicazione alla riga specificata
     */
    public Pubblicazione getPubblicazione(int rowIndex) {
        if (rowIndex >= 0 && rowIndex < pubblicazioni.size()) {
            return pubblicazioni.get(rowIndex);
        }
        return null;
    }

    /**
     * Aggiorna l'intera tabella
     */
    public void refresh() {
        fireTableDataChanged();
    }

    /**
     * Svuota la tabella
     */
    public void clear() {
        pubblicazioni.clear();
        fireTableDataChanged();
    }
}