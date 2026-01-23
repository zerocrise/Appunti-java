import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;

public class BibliotecaVistaFrame extends JFrame {

    private JTable table;
    private BibliotecaTableModel tableModel;
    private Biblioteca biblioteca;

    private JButton buttonAggiungi;
    private JButton buttonRimuovi;
    private JButton buttonDettagli;
    private JButton buttonPresta;

    public BibliotecaVistaFrame(Biblioteca biblioteca) {
        this.biblioteca = biblioteca;

        setTitle("Biblioteca Personale - Vista Completa");
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        inizializzaComponenti();
        assemblaInterfaccia();
    }

    private void inizializzaComponenti() {
        // Table model e table
        tableModel = new BibliotecaTableModel(biblioteca.getPubblicazioni());
        table = new JTable(tableModel);

        // Personalizza tabella
        table.setRowHeight(25);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setAutoCreateRowSorter(true);

        // Larghezza colonne
        table.getColumnModel().getColumn(0).setPreferredWidth(80);   // Tipo
        table.getColumnModel().getColumn(1).setPreferredWidth(250);  // Titolo
        table.getColumnModel().getColumn(2).setPreferredWidth(150);  // Autore
        table.getColumnModel().getColumn(3).setPreferredWidth(60);   // Anno
        table.getColumnModel().getColumn(4).setPreferredWidth(70);   // Pagine
        table.getColumnModel().getColumn(5).setPreferredWidth(80);   // Prezzo
        table.getColumnModel().getColumn(6).setPreferredWidth(100);  // Disponibile

        // Renderer per prezzo (formato €)
        table.getColumnModel().getColumn(5).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                                                           boolean isSelected, boolean hasFocus, int row, int column) {
                if (value instanceof Double) {
                    value = String.format("€%.2f", value);
                }
                return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            }
        });

        // Pulsanti
        buttonAggiungi = new JButton("➕ Aggiungi");
        buttonAggiungi.setBackground(new Color(46, 204, 113));
        buttonAggiungi.setForeground(Color.WHITE);
        buttonAggiungi.setFont(new Font("Arial", Font.BOLD, 14));
        buttonAggiungi.addActionListener(e -> aggiungiLibro());

        buttonRimuovi = new JButton("🗑️ Rimuovi");
        buttonRimuovi.setBackground(new Color(231, 76, 60));
        buttonRimuovi.setForeground(Color.WHITE);
        buttonRimuovi.setFont(new Font("Arial", Font.BOLD, 14));
        buttonRimuovi.addActionListener(e -> rimuoviSelezionato());

        buttonDettagli = new JButton("📖 Dettagli");
        buttonDettagli.setBackground(new Color(52, 152, 219));
        buttonDettagli.setForeground(Color.WHITE);
        buttonDettagli.setFont(new Font("Arial", Font.BOLD, 14));
        buttonDettagli.addActionListener(e -> mostraDettagli());

        buttonPresta = new JButton("📤 Presta/Restituisci");
        buttonPresta.setBackground(new Color(155, 89, 182));
        buttonPresta.setForeground(Color.WHITE);
        buttonPresta.setFont(new Font("Arial", Font.BOLD, 14));
        buttonPresta.addActionListener(e -> togglePrestito());
    }

    private void assemblaInterfaccia() {
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Header
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(44, 62, 80));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0));

        JLabel titleLabel = new JLabel("📚 BIBLIOTECA PERSONALE");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setForeground(Color.WHITE);
        headerPanel.add(titleLabel);

        // Tabella con scroll
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));

        // Panel info
        JPanel infoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel infoLabel = new JLabel("Totale pubblicazioni: " + tableModel.getRowCount());
        infoLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        infoPanel.add(infoLabel);

        // Panel pulsanti
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        buttonPanel.add(buttonAggiungi);
        buttonPanel.add(buttonRimuovi);
        buttonPanel.add(buttonDettagli);
        buttonPanel.add(buttonPresta);

        // Assembla
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(infoPanel, BorderLayout.NORTH);
        bottomPanel.add(buttonPanel, BorderLayout.CENTER);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private void aggiungiLibro() {
        AggiungiLibroFrame aggiungiFrame = new AggiungiLibroFrame(biblioteca);
        aggiungiFrame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent e) {
                // Ricarica tabella quando chiudi la finestra aggiungi
                tableModel = new BibliotecaTableModel(biblioteca.getPubblicazioni());
                table.setModel(tableModel);
            }
        });
        aggiungiFrame.setVisible(true);
    }

    private void rimuoviSelezionato() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this,
                    "Seleziona una pubblicazione da rimuovere",
                    "Nessuna selezione",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Converti indice vista → indice model (importante con ordinamento!)
        int modelRow = table.convertRowIndexToModel(selectedRow);
        Pubblicazione pub = tableModel.getPubblicazione(modelRow);

        int conferma = JOptionPane.showConfirmDialog(this,
                "Vuoi davvero rimuovere \"" + pub.getTitolo() + "\"?",
                "Conferma eliminazione",
                JOptionPane.YES_NO_OPTION);

        if (conferma == JOptionPane.YES_OPTION) {
            tableModel.removePubblicazione(modelRow);
            JOptionPane.showMessageDialog(this,
                    "Pubblicazione rimossa con successo",
                    "Successo",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void mostraDettagli() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this,
                    "Seleziona una pubblicazione",
                    "Nessuna selezione",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        int modelRow = table.convertRowIndexToModel(selectedRow);
        Pubblicazione pub = tableModel.getPubblicazione(modelRow);

        // Crea finestra dettagli
        JDialog dettagliDialog = new JDialog(this, "Dettagli - " + pub.getTitolo(), true);
        dettagliDialog.setSize(500, 400);
        dettagliDialog.setLocationRelativeTo(this);

        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        textArea.setMargin(new Insets(10, 10, 10, 10));

        StringBuilder sb = new StringBuilder();
        sb.append("═══════════════════════════════════════\n");
        sb.append("  DETTAGLI PUBBLICAZIONE\n");
        sb.append("═══════════════════════════════════════\n\n");
        sb.append("Tipo: ").append(pub.getTipo()).append("\n");
        sb.append("Titolo: ").append(pub.getTitolo()).append("\n");

        if (pub instanceof Libro) {
            Libro libro = (Libro) pub;
            sb.append("Autore: ").append(libro.getAutore()).append("\n");
            sb.append("Genere: ").append(libro.getGenere()).append("\n");
        } else if (pub instanceof Rivista) {
            Rivista rivista = (Rivista) pub;
            sb.append("Editore: ").append(rivista.getEditore()).append("\n");
            sb.append("Numero: ").append(rivista.getNumeroEdizione()).append("\n");
            sb.append("Periodicità: ").append(rivista.getPeriodicita()).append("\n");
        }

        sb.append("Anno: ").append(pub.getAnnoPubblicazione()).append("\n");
        sb.append("Pagine: ").append(pub.getNumeroPagine()).append("\n");
        sb.append(String.format("Prezzo: €%.2f\n", pub.getPrezzo()));
        sb.append(String.format("Prezzo con IVA: €%.2f\n", pub.getPrezzoConIVA()));
        sb.append("Disponibile: ").append(pub.isDisponibile() ? "Sì" : "No").append("\n");
        sb.append("\n─────────────────────────────────────\n");
        sb.append("Età: ").append(pub.calcolaEta()).append(" anni\n");
        sb.append(String.format("Tempo lettura: %.1f ore\n", pub.calcolaTempoLettura()));
        sb.append(String.format("Sconto: €%.2f\n", pub.calcolaSconto()));

        textArea.setText(sb.toString());

        dettagliDialog.add(new JScrollPane(textArea));
        dettagliDialog.setVisible(true);
    }

    private void togglePrestito() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this,
                    "Seleziona una pubblicazione",
                    "Nessuna selezione",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        int modelRow = table.convertRowIndexToModel(selectedRow);
        Pubblicazione pub = tableModel.getPubblicazione(modelRow);

        if (pub.isDisponibile()) {
            pub.presta();
        } else {
            pub.restituisci();
        }

        tableModel.refresh();
    }
}
