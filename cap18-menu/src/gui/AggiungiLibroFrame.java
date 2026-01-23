package gui;

import model.*;
import javax.swing.*;
import java.awt.*;

/**
 * Frame per aggiungere un nuovo libro alla biblioteca
 */
public class AggiungiLibroFrame extends JFrame {

    // Componenti
    private JTextField textFieldTitolo;
    private JTextField textFieldAutore;
    private JTextField textFieldAnno;
    private JTextField textFieldGenere;
    private JTextField textFieldPagine;
    private JTextField textFieldPrezzo;
    private JButton buttonSalva;
    private JButton buttonAnnulla;

    // Model
    private Biblioteca biblioteca;

    public AggiungiLibroFrame(Biblioteca biblioteca) {
        this.biblioteca = biblioteca;

        setTitle("Aggiungi Libro");
        setSize(550, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        inizializzaComponenti();
        assemblaInterfaccia();
    }

    private void inizializzaComponenti() {
        textFieldTitolo = new JTextField(25);
        textFieldAutore = new JTextField(25);
        textFieldAnno = new JTextField(25);
        textFieldGenere = new JTextField(25);
        textFieldPagine = new JTextField(25);
        textFieldPrezzo = new JTextField(25);

        buttonSalva = new JButton("💾 Salva");
        buttonSalva.setBackground(new Color(46, 204, 113));
        buttonSalva.setForeground(Color.WHITE);
        buttonSalva.setFont(new Font("Arial", Font.BOLD, 14));
        buttonSalva.setFocusPainted(false);
        buttonSalva.setCursor(new Cursor(Cursor.HAND_CURSOR));
        buttonSalva.addActionListener(e -> salvaLibro());

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
        headerPanel.setBackground(new Color(52, 152, 219));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

        JLabel titleLabel = new JLabel("📚 Aggiungi Nuovo Libro");
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

        // Titolo
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0;
        formPanel.add(createLabel("Titolo:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1;
        formPanel.add(textFieldTitolo, gbc);

        // Autore
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0;
        formPanel.add(createLabel("Autore:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1;
        formPanel.add(textFieldAutore, gbc);

        // Anno
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0;
        formPanel.add(createLabel("Anno:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1;
        formPanel.add(textFieldAnno, gbc);

        // Genere
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 0;
        formPanel.add(createLabel("Genere:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1;
        formPanel.add(textFieldGenere, gbc);

        // Pagine
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.weightx = 0;
        formPanel.add(createLabel("Pagine:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1;
        formPanel.add(textFieldPagine, gbc);

        // Prezzo
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.weightx = 0;
        formPanel.add(createLabel("Prezzo (€):"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1;
        formPanel.add(textFieldPrezzo, gbc);

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

    private void salvaLibro() {
        if (!validaInput()) {
            return;
        }

        try {
            String titolo = textFieldTitolo.getText().trim();
            String autore = textFieldAutore.getText().trim();
            int anno = Integer.parseInt(textFieldAnno.getText().trim());
            String genere = textFieldGenere.getText().trim();
            int pagine = Integer.parseInt(textFieldPagine.getText().trim());
            double prezzo = Double.parseDouble(textFieldPrezzo.getText().trim().replace(",", "."));

            Libro libro = new Libro(titolo, autore, anno, genere, pagine, prezzo);
            biblioteca.aggiungi(libro);

            JOptionPane.showMessageDialog(this,
                    "✅ Libro aggiunto con successo!\n\n" +
                            "Titolo: " + titolo + "\n" +
                            "Autore: " + autore,
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
        textFieldAnno.setText("");
        textFieldGenere.setText("");
        textFieldPagine.setText("");
        textFieldPrezzo.setText("");
    }
}