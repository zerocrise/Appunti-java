package gui;

import model.*;
import javax.swing.*;
import java.awt.*;

/**
 * Frame per aggiungere una nuova rivista alla biblioteca
 */
public class AggiungiRivistaFrame extends JFrame {

    private JTextField textFieldTitolo;
    private JTextField textFieldEditore;
    private JTextField textFieldAnno;
    private JTextField textFieldNumero;
    private JComboBox<String> comboPeriodicita;
    private JTextField textFieldPagine;
    private JTextField textFieldPrezzo;
    private JButton buttonSalva;
    private JButton buttonAnnulla;

    private Biblioteca biblioteca;

    public AggiungiRivistaFrame(Biblioteca biblioteca) {
        this.biblioteca = biblioteca;

        setTitle("Aggiungi Rivista");
        setSize(550, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        inizializzaComponenti();
        assemblaInterfaccia();
    }

    private void inizializzaComponenti() {
        textFieldTitolo = new JTextField(25);
        textFieldEditore = new JTextField(25);
        textFieldAnno = new JTextField(25);
        textFieldNumero = new JTextField(25);

        String[] periodicita = {"Settimanale", "Bisettimanale", "Mensile", "Bimestrale", "Trimestrale", "Annuale"};
        comboPeriodicita = new JComboBox<>(periodicita);
        comboPeriodicita.setSelectedIndex(2); // Default: Mensile

        textFieldPagine = new JTextField(25);
        textFieldPrezzo = new JTextField(25);

        buttonSalva = new JButton("💾 Salva");
        buttonSalva.setBackground(new Color(46, 204, 113));
        buttonSalva.setForeground(Color.WHITE);
        buttonSalva.setFont(new Font("Arial", Font.BOLD, 14));
        buttonSalva.setFocusPainted(false);
        buttonSalva.setCursor(new Cursor(Cursor.HAND_CURSOR));
        buttonSalva.addActionListener(e -> salvaRivista());

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
        headerPanel.setBackground(new Color(142, 68, 173));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

        JLabel titleLabel = new JLabel("📰 Aggiungi Nuova Rivista");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 26));
        titleLabel.setForeground(Color.WHITE);
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
        formPanel.add(textFieldTitolo, gbc);
        row++;

        // Editore
        gbc.gridx = 0; gbc.gridy = row; gbc.weightx = 0;
        formPanel.add(createLabel("Editore:"), gbc);
        gbc.gridx = 1; gbc.weightx = 1;
        formPanel.add(textFieldEditore, gbc);
        row++;

        // Anno
        gbc.gridx = 0; gbc.gridy = row; gbc.weightx = 0;
        formPanel.add(createLabel("Anno:"), gbc);
        gbc.gridx = 1; gbc.weightx = 1;
        formPanel.add(textFieldAnno, gbc);
        row++;

        // Numero Edizione
        gbc.gridx = 0; gbc.gridy = row; gbc.weightx = 0;
        formPanel.add(createLabel("N° Edizione:"), gbc);
        gbc.gridx = 1; gbc.weightx = 1;
        formPanel.add(textFieldNumero, gbc);
        row++;

        // Periodicità
        gbc.gridx = 0; gbc.gridy = row; gbc.weightx = 0;
        formPanel.add(createLabel("Periodicità:"), gbc);
        gbc.gridx = 1; gbc.weightx = 1;
        formPanel.add(comboPeriodicita, gbc);
        row++;

        // Pagine
        gbc.gridx = 0; gbc.gridy = row; gbc.weightx = 0;
        formPanel.add(createLabel("Pagine:"), gbc);
        gbc.gridx = 1; gbc.weightx = 1;
        formPanel.add(textFieldPagine, gbc);
        row++;

        // Prezzo
        gbc.gridx = 0; gbc.gridy = row; gbc.weightx = 0;
        formPanel.add(createLabel("Prezzo (€):"), gbc);
        gbc.gridx = 1; gbc.weightx = 1;
        formPanel.add(textFieldPrezzo, gbc);

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 15));
        buttonPanel.setBackground(new Color(236, 240, 241));
        buttonPanel.add(buttonSalva);
        buttonPanel.add(buttonAnnulla);

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

    private void salvaRivista() {
        if (!validaInput()) {
            return;
        }

        try {
            String titolo = textFieldTitolo.getText().trim();
            String editore = textFieldEditore.getText().trim();
            int anno = Integer.parseInt(textFieldAnno.getText().trim());
            int numero = Integer.parseInt(textFieldNumero.getText().trim());
            String periodicita = (String) comboPeriodicita.getSelectedItem();
            int pagine = Integer.parseInt(textFieldPagine.getText().trim());
            double prezzo = Double.parseDouble(textFieldPrezzo.getText().trim().replace(",", "."));

            Rivista rivista = new Rivista(titolo, editore, anno, numero, periodicita, pagine, prezzo);
            biblioteca.aggiungi(rivista);

            JOptionPane.showMessageDialog(this,
                    "✅ Rivista aggiunta con successo!\n\n" +
                            "Titolo: " + titolo + "\n" +
                            "Editore: " + editore + "\n" +
                            "N° " + numero,
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
        if (textFieldTitolo.getText().trim().isEmpty()) {
            mostraErrore("Il titolo non può essere vuoto");
            textFieldTitolo.requestFocus();
            return false;
        }

        if (textFieldEditore.getText().trim().isEmpty()) {
            mostraErrore("L'editore non può essere vuoto");
            textFieldEditore.requestFocus();
            return false;
        }

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

        try {
            int numero = Integer.parseInt(textFieldNumero.getText().trim());
            if (numero <= 0) {
                mostraErrore("Numero edizione deve essere maggiore di 0");
                textFieldNumero.requestFocus();
                return false;
            }
        } catch (NumberFormatException e) {
            mostraErrore("Numero edizione deve essere un numero valido");
            textFieldNumero.requestFocus();
            return false;
        }

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

        try {
            double prezzo = Double.parseDouble(textFieldPrezzo.getText().trim().replace(",", "."));
            if (prezzo < 0) {
                mostraErrore("Prezzo non può essere negativo");
                textFieldPrezzo.requestFocus();
                return false;
            }
        } catch (NumberFormatException e) {
            mostraErrore("Prezzo deve essere un numero valido");
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
        textFieldEditore.setText("");
        textFieldAnno.setText("");
        textFieldNumero.setText("");
        comboPeriodicita.setSelectedIndex(2);
        textFieldPagine.setText("");
        textFieldPrezzo.setText("");
    }
}