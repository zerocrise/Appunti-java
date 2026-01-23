package gui;

import persistence.BibliotecaSerializer;
import model.*;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.ArrayList;

public class BibliotecaAppFinale extends JFrame {

    private Biblioteca biblioteca;
    private JTable table;
    private BibliotecaTableModel tableModel;
    private File fileCorrente;
    private boolean modificato;

    // Componenti menu
    private JMenuBar menuBar;
    private JMenu menuFile, menuModifica, menuVisualizza, menuAiuto;

    // Componenti UI
    private JLabel statusLabel;

    public BibliotecaAppFinale() {
        biblioteca = new Biblioteca();
        modificato = false;

        setTitle("Biblioteca Personale");
        setSize(1200, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                esci();
            }
        });

        inizializzaComponenti();
        creaMenu();
        assemblaInterfaccia();
        caricaDatiDefault();
    }

    private void inizializzaComponenti() {
        tableModel = new BibliotecaTableModel(biblioteca.getPubblicazioni());
        table = new JTable(tableModel);

        table.setRowHeight(28);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setAutoCreateRowSorter(true);
        table.setFont(new Font("Arial", Font.PLAIN, 12));

        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Arial", Font.BOLD, 12));
        header.setBackground(new Color(52, 73, 94));
        header.setForeground(Color.WHITE);

        // Assicurati che il modello abbia 7 colonne: Tipo, Titolo, Autore/Disegnatore/etc., Anno, Pagine, Prezzo, Disponibile
        if (table.getColumnCount() >= 7) {
            table.getColumnModel().getColumn(0).setPreferredWidth(80);   // Tipo
            table.getColumnModel().getColumn(1).setPreferredWidth(300);  // Titolo
            table.getColumnModel().getColumn(2).setPreferredWidth(180);  // Info specifica (autore, disegnatore, editore...)
            table.getColumnModel().getColumn(3).setPreferredWidth(80);   // Anno
            table.getColumnModel().getColumn(4).setPreferredWidth(80);   // Pagine
            table.getColumnModel().getColumn(5).setPreferredWidth(100);  // Prezzo
            table.getColumnModel().getColumn(6).setPreferredWidth(100);  // Disponibile
        }

        // Renderer prezzo
        table.getColumnModel().getColumn(5).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                                                           boolean isSelected, boolean hasFocus, int row, int column) {
                if (value instanceof Double) {
                    value = String.format("€%.2f", value);
                }
                Component c = super.getTableCellRendererComponent(table, value,
                        isSelected, hasFocus, row, column);
                ((JLabel) c).setHorizontalAlignment(SwingConstants.RIGHT);
                return c;
            }
        });

        // Renderer disponibilità
        table.getColumnModel().getColumn(6).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                                                           boolean isSelected, boolean hasFocus, int row, int column) {
                JLabel label = new JLabel();
                label.setHorizontalAlignment(SwingConstants.CENTER);
                label.setOpaque(true);

                if (value instanceof Boolean) {
                    if ((Boolean) value) {
                        label.setText("✅ Sì");
                        if (!isSelected) label.setBackground(new Color(212, 237, 218));
                    } else {
                        label.setText("❌ No");
                        if (!isSelected) label.setBackground(new Color(248, 215, 218));
                    }
                }

                if (isSelected) {
                    label.setBackground(table.getSelectionBackground());
                    label.setForeground(table.getSelectionForeground());
                }

                return label;
            }
        });

        statusLabel = new JLabel("Pronto");
        statusLabel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
    }

    private void creaMenu() {
        menuBar = new JMenuBar();

        // Menu File (invariato)
        menuFile = new JMenu("File");
        menuFile.setMnemonic(KeyEvent.VK_F);

        JMenuItem itemNuovo = new JMenuItem("Nuova Biblioteca");
        itemNuovo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_DOWN_MASK));
        itemNuovo.addActionListener(e -> nuovaBiblioteca());

        JMenuItem itemApri = new JMenuItem("Apri...");
        itemApri.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_DOWN_MASK));
        itemApri.addActionListener(e -> apriFile());

        JMenuItem itemSalva = new JMenuItem("Salva");
        itemSalva.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK));
        itemSalva.addActionListener(e -> salva());

        JMenuItem itemSalvaCome = new JMenuItem("Salva Come...");
        itemSalvaCome.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,
                InputEvent.CTRL_DOWN_MASK | InputEvent.SHIFT_DOWN_MASK));
        itemSalvaCome.addActionListener(e -> salvaCome());

        JMenu menuEsporta = new JMenu("Esporta");
        JMenuItem itemEsportaCSV = new JMenuItem("Esporta CSV...");
        itemEsportaCSV.addActionListener(e -> esportaCSV());
        menuEsporta.add(itemEsportaCSV);

        JMenuItem itemEsci = new JMenuItem("Esci");
        itemEsci.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, InputEvent.CTRL_DOWN_MASK));
        itemEsci.addActionListener(e -> esci());

        menuFile.add(itemNuovo);
        menuFile.add(itemApri);
        menuFile.addSeparator();
        menuFile.add(itemSalva);
        menuFile.add(itemSalvaCome);
        menuFile.add(menuEsporta);
        menuFile.addSeparator();
        menuFile.add(itemEsci);

        // Menu Modifica - AGGIORNATO
        menuModifica = new JMenu("Modifica");
        menuModifica.setMnemonic(KeyEvent.VK_M);

        JMenuItem itemAggiungiLibro = new JMenuItem("Aggiungi Libro...");
        itemAggiungiLibro.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, InputEvent.CTRL_DOWN_MASK));
        itemAggiungiLibro.addActionListener(e -> aggiungiLibro());

        JMenuItem itemAggiungiFumetto = new JMenuItem("Aggiungi Fumetto...");
        itemAggiungiFumetto.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_DOWN_MASK)); // C per "Comic"
        itemAggiungiFumetto.addActionListener(e -> aggiungiFumetto());

        JMenuItem itemAggiungiRivista = new JMenuItem("Aggiungi Rivista...");
        itemAggiungiRivista.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, InputEvent.CTRL_DOWN_MASK));
        itemAggiungiRivista.addActionListener(e -> aggiungiRivista());

        JMenuItem itemRimuovi = new JMenuItem("Rimuovi Selezionato");
        itemRimuovi.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0));
        itemRimuovi.addActionListener(e -> rimuoviSelezionato());

        JMenuItem itemPresta = new JMenuItem("Presta/Restituisci");
        itemPresta.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, InputEvent.CTRL_DOWN_MASK));
        itemPresta.addActionListener(e -> togglePrestito());

        menuModifica.add(itemAggiungiLibro);
        menuModifica.add(itemAggiungiFumetto);
        menuModifica.add(itemAggiungiRivista);
        menuModifica.addSeparator();
        menuModifica.add(itemRimuovi);
        menuModifica.add(itemPresta);

        // Menu Visualizza (invariato)
        menuVisualizza = new JMenu("Visualizza");
        menuVisualizza.setMnemonic(KeyEvent.VK_V);

        JMenuItem itemRicerca = new JMenuItem("Ricerca Avanzata...");
        itemRicerca.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, InputEvent.CTRL_DOWN_MASK));
        itemRicerca.addActionListener(e -> mostraRicerca());

        JMenuItem itemStatistiche = new JMenuItem("Statistiche");
        itemStatistiche.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_T, InputEvent.CTRL_DOWN_MASK));
        itemStatistiche.addActionListener(e -> mostraStatistiche());

        JMenuItem itemDettagli = new JMenuItem("Dettagli Selezionato");
        itemDettagli.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D, InputEvent.CTRL_DOWN_MASK));
        itemDettagli.addActionListener(e -> mostraDettagli());

        menuVisualizza.add(itemRicerca);
        menuVisualizza.add(itemStatistiche);
        menuVisualizza.addSeparator();
        menuVisualizza.add(itemDettagli);

        // Menu Aiuto (invariato)
        menuAiuto = new JMenu("Aiuto");
        menuAiuto.setMnemonic(KeyEvent.VK_A);

        JMenuItem itemGuida = new JMenuItem("Guida Rapida");
        itemGuida.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0));
        itemGuida.addActionListener(e -> mostraGuida());

        JMenuItem itemAbout = new JMenuItem("Informazioni");
        itemAbout.addActionListener(e -> mostraAbout());

        menuAiuto.add(itemGuida);
        menuAiuto.addSeparator();
        menuAiuto.add(itemAbout);

        setJMenuBar(menuBar);
        menuBar.add(menuFile);
        menuBar.add(menuModifica);
        menuBar.add(menuVisualizza);
        menuBar.add(menuAiuto);
    }

    private void assemblaInterfaccia() {
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JToolBar toolBar = creaToolBar();
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        JPanel statusBar = new JPanel(new BorderLayout());
        statusBar.setBorder(BorderFactory.createEtchedBorder());
        statusBar.add(statusLabel, BorderLayout.WEST);

        mainPanel.add(toolBar, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(statusBar, BorderLayout.SOUTH);

        add(mainPanel);
        aggiornaStatus();
    }

    private JToolBar creaToolBar() {
        JToolBar toolBar = new JToolBar();
        toolBar.setFloatable(false);

        JButton btnNuovo = new JButton("🆕 Nuovo");
        btnNuovo.setToolTipText("Nuova biblioteca (Ctrl+N)");
        btnNuovo.addActionListener(e -> nuovaBiblioteca());

        JButton btnApri = new JButton("📂 Apri");
        btnApri.setToolTipText("Apri file (Ctrl+O)");
        btnApri.addActionListener(e -> apriFile());

        JButton btnSalva = new JButton("💾 Salva");
        btnSalva.setToolTipText("Salva (Ctrl+S)");
        btnSalva.addActionListener(e -> salva());

        toolBar.add(btnNuovo);
        toolBar.add(btnApri);
        toolBar.add(btnSalva);
        toolBar.addSeparator();

        JButton btnAggiungiLibro = new JButton("📚 Libro");
        btnAggiungiLibro.setToolTipText("Aggiungi libro (Ctrl+L)");
        btnAggiungiLibro.addActionListener(e -> aggiungiLibro());

        JButton btnAggiungiFumetto = new JButton("🎨 Fumetto");
        btnAggiungiFumetto.setToolTipText("Aggiungi fumetto (Ctrl+C)");
        btnAggiungiFumetto.addActionListener(e -> aggiungiFumetto());

        JButton btnAggiungiRivista = new JButton("📰 Rivista");
        btnAggiungiRivista.setToolTipText("Aggiungi rivista (Ctrl+R)");
        btnAggiungiRivista.addActionListener(e -> aggiungiRivista());

        JButton btnRimuovi = new JButton("🗑️ Rimuovi");
        btnRimuovi.setToolTipText("Rimuovi selezionato (Canc)");
        btnRimuovi.addActionListener(e -> rimuoviSelezionato());

        toolBar.add(btnAggiungiLibro);
        toolBar.add(btnAggiungiFumetto);
        toolBar.add(btnAggiungiRivista);
        toolBar.add(btnRimuovi);
        toolBar.addSeparator();

        JButton btnRicerca = new JButton("🔍 Cerca");
        btnRicerca.setToolTipText("Ricerca avanzata (Ctrl+F)");
        btnRicerca.addActionListener(e -> mostraRicerca());

        JButton btnStatistiche = new JButton("📊 Statistiche");
        btnStatistiche.setToolTipText("Mostra statistiche (Ctrl+T)");
        btnStatistiche.addActionListener(e -> mostraStatistiche());

        toolBar.add(btnRicerca);
        toolBar.add(btnStatistiche);

        return toolBar;
    }

    // === METODI PER AGGIUNTA PUBBLICAZIONI ===

    private void aggiungiLibro() {
        AggiungiLibroFrame frame = new AggiungiLibroFrame(biblioteca);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                aggiornaTabella();
            }
        });
        frame.setVisible(true);
    }

    private void aggiungiFumetto() {
        AggiungiFumettoFrame frame = new AggiungiFumettoFrame(biblioteca);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                aggiornaTabella();
            }
        });
        frame.setVisible(true);
    }

    private void aggiungiRivista() {
        AggiungiRivistaFrame frame = new AggiungiRivistaFrame(biblioteca);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                aggiornaTabella();
            }
        });
        frame.setVisible(true);
    }

    private void aggiornaTabella() {
        tableModel = new BibliotecaTableModel(biblioteca.getPubblicazioni());
        table.setModel(tableModel);
        modificato = true;
        aggiornaStatus();
    }

    // === METODI ESISTENTI (senza modifiche sostanziali) ===

    private void nuovaBiblioteca() {
        if (modificato) {
            int risposta = JOptionPane.showConfirmDialog(this,
                    "Ci sono modifiche non salvate. Salvare prima di continuare?",
                    "Conferma",
                    JOptionPane.YES_NO_CANCEL_OPTION);

            if (risposta == JOptionPane.YES_OPTION) {
                salva();
            } else if (risposta == JOptionPane.CANCEL_OPTION) {
                return;
            }
        }

        biblioteca = new Biblioteca();
        tableModel = new BibliotecaTableModel(biblioteca.getPubblicazioni());
        table.setModel(tableModel);
        fileCorrente = null;
        modificato = false;
        aggiornaStatus();
        setTitle("Biblioteca Personale - Nuova Biblioteca");
    }

    private void apriFile() {
        if (modificato) {
            int risposta = JOptionPane.showConfirmDialog(this,
                    "Ci sono modifiche non salvate. Salvare prima di continuare?",
                    "Conferma",
                    JOptionPane.YES_NO_CANCEL_OPTION);

            if (risposta == JOptionPane.YES_OPTION) {
                salva();
            } else if (risposta == JOptionPane.CANCEL_OPTION) {
                return;
            }
        }

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter(
                "File Biblioteca (*.ser)", "ser"));
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));

        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            fileCorrente = fileChooser.getSelectedFile();

            try {
                ArrayList<Pubblicazione> pubs = BibliotecaSerializer.carica(fileCorrente.getAbsolutePath());
                biblioteca = new Biblioteca();
                for (Pubblicazione pub : pubs) {
                    biblioteca.aggiungi(pub);
                }

                tableModel = new BibliotecaTableModel(biblioteca.getPubblicazioni());
                table.setModel(tableModel);
                modificato = false;
                aggiornaStatus();
                setTitle("Biblioteca Personale - " + fileCorrente.getName());

                JOptionPane.showMessageDialog(this,
                        "Biblioteca caricata con successo!\n" + pubs.size() + " pubblicazioni caricate.",
                        "Successo",
                        JOptionPane.INFORMATION_MESSAGE);

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this,
                        "Errore caricamento: " + ex.getMessage(),
                        "Errore",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void salva() {
        if (fileCorrente == null) {
            salvaCome();
        } else {
            try {
                BibliotecaSerializer.salva(biblioteca.getPubblicazioni(),
                        fileCorrente.getAbsolutePath());
                modificato = false;
                aggiornaStatus();
                statusLabel.setText("Salvato: " + fileCorrente.getName());
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this,
                        "Errore salvataggio: " + ex.getMessage(),
                        "Errore",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void salvaCome() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter(
                "File Biblioteca (*.ser)", "ser"));
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));

        if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            fileCorrente = fileChooser.getSelectedFile();

            if (!fileCorrente.getName().endsWith(".ser")) {
                fileCorrente = new File(fileCorrente.getAbsolutePath() + ".ser");
            }

            if (fileCorrente.exists()) {
                int conferma = JOptionPane.showConfirmDialog(this,
                        "Il file esiste già. Sovrascrivere?",
                        "Conferma",
                        JOptionPane.YES_NO_OPTION);

                if (conferma != JOptionPane.YES_OPTION) {
                    return;
                }
            }

            salva();
            setTitle("Biblioteca Personale - " + fileCorrente.getName());
        }
    }

    private void esportaCSV() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter(
                "File CSV (*.csv)", "csv"));

        if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();

            if (!file.getName().endsWith(".csv")) {
                file = new File(file.getAbsolutePath() + ".csv");
            }

            try {
                BibliotecaSerializer.esportaCSV(biblioteca.getPubblicazioni(),
                        file.getAbsolutePath());

                JOptionPane.showMessageDialog(this,
                        "Esportazione CSV completata!",
                        "Successo",
                        JOptionPane.INFORMATION_MESSAGE);

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this,
                        "Errore esportazione: " + ex.getMessage(),
                        "Errore",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void esci() {
        if (modificato) {
            int risposta = JOptionPane.showConfirmDialog(this,
                    "Ci sono modifiche non salvate. Salvare prima di uscire?",
                    "Conferma Uscita",
                    JOptionPane.YES_NO_CANCEL_OPTION);

            if (risposta == JOptionPane.YES_OPTION) {
                salva();
                System.exit(0);
            } else if (risposta == JOptionPane.NO_OPTION) {
                System.exit(0);
            }
            // CANCEL_OPTION: non fare nulla
        } else {
            System.exit(0);
        }
    }
/*
    private void aggiungiLibro() {
        AggiungiLibroFrame frame = new AggiungiLibroFrame(biblioteca);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                tableModel = new BibliotecaTableModel(biblioteca.getPubblicazioni());
                table.setModel(tableModel);
                modificato = true;
                aggiornaStatus();
            }
        });
        frame.setVisible(true);
    }*/

    private void rimuoviSelezionato() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this,
                    "Seleziona una pubblicazione da rimuovere",
                    "Nessuna selezione",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        int modelRow = table.convertRowIndexToModel(selectedRow);
        Pubblicazione pub = tableModel.getPubblicazione(modelRow);

        int conferma = JOptionPane.showConfirmDialog(this,
                "Vuoi davvero rimuovere \"" + pub.getTitolo() + "\"?",
                "Conferma eliminazione",
                JOptionPane.YES_NO_OPTION);

        if (conferma == JOptionPane.YES_OPTION) {
            tableModel.removePubblicazione(modelRow);
            modificato = true;
            aggiornaStatus();
        }
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
        modificato = true;
        aggiornaStatus();
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

        JDialog dettagliDialog = new JDialog(this, "Dettagli - " + pub.getTitolo(), true);
        dettagliDialog.setSize(500, 450);
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
        } else if (pub instanceof Fumetto) {
            Fumetto fumetto = (Fumetto) pub;
            sb.append("Autore: ").append(fumetto.getAutore()).append("\n");
            sb.append("Disegnatore: ").append(fumetto.getDisegnatore()).append("\n");
            sb.append("Serie: ").append(fumetto.getSerie()).append("\n");
            sb.append("Volume: ").append(fumetto.getNumeroVolume()).append("\n");
            sb.append("A colori: ").append(fumetto.isAColori() ? "Sì" : "No").append("\n");
        } else if (pub instanceof Rivista) {
            Rivista rivista = (Rivista) pub;
            sb.append("Editore: ").append(rivista.getEditore()).append("\n");
            sb.append("Periodicità: ").append(rivista.getPeriodicita()).append("\n");
            sb.append("Numero: ").append(rivista.getNumeroEdizione()).append("\n");
        }

        sb.append("Anno: ").append(pub.getAnnoPubblicazione()).append("\n");
        sb.append("Pagine: ").append(pub.getNumeroPagine()).append("\n");
        sb.append(String.format("Prezzo: €%.2f\n", pub.getPrezzo()));
        sb.append("Disponibile: ").append(pub.isDisponibile() ? "Sì" : "No").append("\n");

        textArea.setText(sb.toString());
        dettagliDialog.add(new JScrollPane(textArea));
        dettagliDialog.setVisible(true);
    }

    private void mostraRicerca() {
        // Implementa come in sezione 15.4.2 (può filtrare per tipo)
    }

    private void mostraStatistiche() {
        // Implementa come in sezione 15.5
    }

    private void mostraGuida() {
        String guida =
                "GUIDA RAPIDA - Biblioteca Personale\n\n" +
                        "SCORCIATOIE:\n" +
                        "Ctrl+N - Nuova biblioteca\n" +
                        "Ctrl+O - Apri\n" +
                        "Ctrl+S - Salva\n" +
                        "Ctrl+L - Aggiungi Libro\n" +
                        "Ctrl+C - Aggiungi Fumetto\n" +
                        "Ctrl+R - Aggiungi Rivista\n" +
                        "Canc - Rimuovi\n" +
                        "Ctrl+F - Ricerca\n" +
                        "Ctrl+T - Statistiche\n" +
                        "Ctrl+Q - Esci\n\n" +
                        "Supporta: Libri, Fumetti, Riviste";

        JOptionPane.showMessageDialog(this, guida, "Guida Rapida",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void mostraAbout() {
        JOptionPane.showMessageDialog(this,
                "Biblioteca Personale v2.0\n" +
                        "Gestione completa di libri, fumetti e riviste.\n" +
                        "© 2026 - Progetto Java Swing",
                "Informazioni",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void caricaDatiDefault() { /* ... */ }
    private void aggiornaStatus() { /* ... */ }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {
            BibliotecaAppFinale app = new BibliotecaAppFinale();
            app.setVisible(true);
        });
    }
}