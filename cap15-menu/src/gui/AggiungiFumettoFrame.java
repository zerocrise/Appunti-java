package gui;

import model.Biblioteca;
import model.Fumetto;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * Frame per aggiungere un nuovo fumetto alla biblioteca
 */
public class AggiungiFumettoFrame extends JFrame {

    // Componenti
    private JTextField textFieldTitolo;
    private JTextField textFieldAutore;
    private JTextField textFieldDisegnatore;
    private JTextField textFieldSerie;
    private JTextField textFieldAnno;
    private JTextField textFieldPagine;
    private JTextField textFieldPrezzo;
    private JTextField textFieldNumeroVolume;
    private JCheckBox checkBoxAColori;
    private JButton buttonSalva;
    private JButton buttonAnnulla;

    // Model
    private Biblioteca biblioteca;

    public AggiungiFumettoFrame(Biblioteca biblioteca) {
        this.biblioteca = biblioteca;

        setTitle("Aggiungi Fumetto");
        setSize(580, 520);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        inizializzaComponenti();
        assemblaInterfaccia();
    }

    private void inizializzaComponenti() {
        textFieldTitolo = new JTextField(25);
        textFieldAutore = new JTextField(25);
        textFieldDisegnatore = new JTextField(25);
        textFieldSerie = new JTextField(25);
        textFieldAnno = new JTextField(25);
        textFieldPagine = new JTextField(25);
        textFieldPrezzo = new JTextField(25);
        textFieldNumeroVolume = new JTextField(25);
        checkBoxAColori = new JCheckBox("Sì");

        buttonSalva = new JButton("💾 Salva");
        buttonSalva.setBackground(new Color(46, 204, 113));
        buttonSalva.setForeground(Color.WHITE);
        buttonSalva.setFont(new Font("Arial", Font.BOLD, 14));
        buttonSalva.setFocusPainted(false);
        buttonSalva.setCursor(new Cursor(Cursor.HAND_CURSOR));
        buttonSalva.addActionListener(this::salvaFumetto);

        buttonAnnulla = new JButton("❌ Annulla");
        buttonAnnulla.setBackground(new Color(231, 76, 60));
        buttonAnnulla.setForeground(Color.WHITE);
        buttonAnnulla.setFont(new Font("Arial", Font.BOLD, 14));
        buttonAnnulla.setFocusPainted(false);
        buttonAnnulla.setCursor(new Cursor(Cursor.HAND_CURSOR));
        buttonAnnulla.addActionListener(e -> dispose());
    }

    private void assemblaInterfaccia() {
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(new Color(236, 240, 241));

        // Header
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(155, 89, 182)); // Viola per differenziare dai libri
        headerPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

        JLabel titleLabel = new JLabel("🎨 Aggiungi Nuovo Fumetto");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 26));
        titleLabel.setForeground(Color.WHITE);
       // titleLabel.setOpaque(true); // Rende visibile lo sfondo
        //titleLabel.setBackground(new Color(155, 89, 182));
        //titleLabel.setBackground(Color.BLUE);
        headerPanel.add(titleLabel);

        // Form panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(189, 195, 199), 1),
                BorderFactory.createEmptyBorder(25, 25, 25, 25)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(8, 8, 8, 8);

        int row = 0;

        // Titolo
        gbc.gridx = 0; gbc.gridy = row; gbc.weightx = 0;
        formPanel.add(createLabel("Titolo:"), gbc);
        gbc.gridx = 1; gbc.weightx = 1;
        formPanel.add(textFieldTitolo, gbc); row++;

        // Autore
        gbc.gridx = 0; gbc.gridy = row; gbc.weightx = 0;
        formPanel.add(createLabel("Autore:"), gbc);
        gbc.gridx = 1; gbc.weightx = 1;
        formPanel.add(textFieldAutore, gbc); row++;

        // Disegnatore
        gbc.gridx = 0; gbc.gridy = row; gbc.weightx = 0;
        formPanel.add(createLabel("Disegnatore:"), gbc);
        gbc.gridx = 1; gbc.weightx = 1;
        formPanel.add(textFieldDisegnatore, gbc); row++;

        // Serie
        gbc.gridx = 0; gbc.gridy = row; gbc.weightx = 0;
        formPanel.add(createLabel("Serie:"), gbc);
        gbc.gridx = 1; gbc.weightx = 1;
        formPanel.add(textFieldSerie, gbc); row++;

        // Numero Volume
        gbc.gridx = 0; gbc.gridy = row; gbc.weightx = 0;
        formPanel.add(createLabel("N. Volume:"), gbc);
        gbc.gridx = 1; gbc.weightx = 1;
        formPanel.add(textFieldNumeroVolume, gbc); row++;

        // Anno
        gbc.gridx = 0; gbc.gridy = row; gbc.weightx = 0;
        formPanel.add(createLabel("Anno:"), gbc);
        gbc.gridx = 1; gbc.weightx = 1;
        formPanel.add(textFieldAnno, gbc); row++;

        // Pagine
        gbc.gridx = 0; gbc.gridy = row; gbc.weightx = 0;
        formPanel.add(createLabel("Pagine:"), gbc);
        gbc.gridx = 1; gbc.weightx = 1;
        formPanel.add(textFieldPagine, gbc); row++;

        // Prezzo
        gbc.gridx = 0; gbc.gridy = row; gbc.weightx = 0;
        formPanel.add(createLabel("Prezzo (€):"), gbc);
        gbc.gridx = 1; gbc.weightx = 1;
        formPanel.add(textFieldPrezzo, gbc); row++;

        // A colori
        gbc.gridx = 0; gbc.gridy = row; gbc.weightx = 0;
        formPanel.add(createLabel("A colori?"), gbc);
        gbc.gridx = 1; gbc.weightx = 1;
        formPanel.add(checkBoxAColori, gbc); row++;

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 15));
        buttonPanel.setBackground(new Color(236, 240, 241));
        buttonPanel.add(buttonSalva);
        buttonPanel.add(buttonAnnulla);

        // Assembla
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(formPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.BOLD, 13));
        label.setForeground(new Color(52, 73, 94));
        return label;
    }

    private void salvaFumetto(ActionEvent e) {
        if (!validaInput()) {
            return;
        }

        try {
            String titolo = textFieldTitolo.getText().trim();
            String autore = textFieldAutore.getText().trim();
            String disegnatore = textFieldDisegnatore.getText().trim();
            String serie = textFieldSerie.getText().trim();
            int numeroVolume = Integer.parseInt(textFieldNumeroVolume.getText().trim());
            int anno = Integer.parseInt(textFieldAnno.getText().trim());
            int pagine = Integer.parseInt(textFieldPagine.getText().trim());
            double prezzo = Double.parseDouble(textFieldPrezzo.getText().trim().replace(",", "."));
            boolean aColori = checkBoxAColori.isSelected();

            Fumetto fumetto=new Fumetto(titolo,  autore,  disegnatore, anno,serie,numeroVolume,pagine,prezzo,  aColori);
            biblioteca.aggiungi(fumetto);

            JOptionPane.showMessageDialog(this,
                    "✅ Fumetto aggiunto con successo!\n\n" +
                            "Titolo: " + titolo + "\n" +
                            "Serie: " + serie + " Vol. " + numeroVolume,
                    "Successo",
                    JOptionPane.INFORMATION_MESSAGE);

            pulisciCampi();
            textFieldTitolo.requestFocus();

        } catch (IllegalArgumentException ex) {
            mostraErrore("Dati non validi: " + ex.getMessage());
        } catch (Exception ex) {
            mostraErrore("Errore imprevisto: " + ex.getMessage());
        }
    }

    private boolean validaInput() {
        // Titolo
        if (textFieldTitolo.getText().trim().isEmpty()) {
            mostraErrore("Il titolo non può essere vuoto");
            textFieldTitolo.requestFocus();
            return false;
        }

        // Autore
        if (textFieldAutore.getText().trim().isEmpty()) {
            mostraErrore("L'autore non può essere vuoto");
            textFieldAutore.requestFocus();
            return false;
        }

        // Disegnatore
        if (textFieldDisegnatore.getText().trim().isEmpty()) {
            mostraErrore("Il disegnatore non può essere vuoto");
            textFieldDisegnatore.requestFocus();
            return false;
        }

        // Serie
        if (textFieldSerie.getText().trim().isEmpty()) {
            mostraErrore("La serie non può essere vuota");
            textFieldSerie.requestFocus();
            return false;
        }

        // Numero Volume
        try {
            int numVol = Integer.parseInt(textFieldNumeroVolume.getText().trim());
            if (numVol <= 0) {
                mostraErrore("Il numero del volume deve essere > 0");
                textFieldNumeroVolume.requestFocus();
                return false;
            }
        } catch (NumberFormatException e) {
            mostraErrore("Numero volume deve essere un intero positivo");
            textFieldNumeroVolume.requestFocus();
            return false;
        }

        // Anno
        try {
            int anno = Integer.parseInt(textFieldAnno.getText().trim());
            if (anno < 0 || anno > 2025) {
                mostraErrore("Anno deve essere tra 0 e 2025");
                textFieldAnno.requestFocus();
                return false;
            }
        } catch (NumberFormatException e) {
            mostraErrore("Anno deve essere un numero valido");
            textFieldAnno.requestFocus();
            return false;
        }

        // Pagine
        try {
            int pagine = Integer.parseInt(textFieldPagine.getText().trim());
            if (pagine <= 0) {
                mostraErrore("Pagine deve essere maggiore di 0");
                textFieldPagine.requestFocus();
                return false;
            }
        } catch (NumberFormatException e) {
            mostraErrore("Pagine deve essere un numero valido");
            textFieldPagine.requestFocus();
            return false;
        }

        // Prezzo
        try {
            double prezzo = Double.parseDouble(textFieldPrezzo.getText().trim().replace(",", "."));
            if (prezzo < 0) {
                mostraErrore("Prezzo non può essere negativo");
                textFieldPrezzo.requestFocus();
                return false;
            }
        } catch (NumberFormatException e) {
            mostraErrore("Prezzo deve essere un numero valido (usa . o , per i decimali)");
            textFieldPrezzo.requestFocus();
            return false;
        }

        return true;
    }

    private void mostraErrore(String messaggio) {
        JOptionPane.showMessageDialog(this,
                messaggio,
                "Errore di Validazione",
                JOptionPane.ERROR_MESSAGE);
    }

    private void pulisciCampi() {
        textFieldTitolo.setText("");
        textFieldAutore.setText("");
        textFieldDisegnatore.setText("");
        textFieldSerie.setText("");
        textFieldNumeroVolume.setText("");
        textFieldAnno.setText("");
        textFieldPagine.setText("");
        textFieldPrezzo.setText("");
        checkBoxAColori.setSelected(false);
    }
}