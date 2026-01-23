import javax.swing.*;
import java.awt.*;

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
        setSize(500, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        inizializzaComponenti();
        assemblaInterfaccia();
    }

    private void inizializzaComponenti() {
        textFieldTitolo = new JTextField(20);
        textFieldAutore = new JTextField(20);
        textFieldAnno = new JTextField(20);
        textFieldGenere = new JTextField(20);
        textFieldPagine = new JTextField(20);
        textFieldPrezzo = new JTextField(20);

        buttonSalva = new JButton("Salva");
        buttonSalva.setBackground(new Color(46, 204, 113));
        buttonSalva.setForeground(Color.red);
        buttonSalva.setFont(new Font("Arial", Font.BOLD, 14));
        buttonSalva.addActionListener(e -> salvaLibro());

        buttonAnnulla = new JButton("Annulla");
        buttonAnnulla.setBackground(new Color(231, 76, 60));
        buttonAnnulla.setForeground(Color.red);
        buttonAnnulla.setFont(new Font("Arial", Font.BOLD, 14));
        buttonAnnulla.addActionListener(e -> dispose());
    }

    private void assemblaInterfaccia() {
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Header
        JPanel headerPanel = new JPanel();
        //headerPanel.setOpaque(true);
        headerPanel.setBackground(new Color(52, 152, 219));
        JLabel titleLabel = new JLabel("📚 Aggiungi Nuovo Libro");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        //titleLabel.setOpaque(true);
        //titleLabel.setForeground(Color.black);
        headerPanel.add(titleLabel);

        // Form panel
        JPanel formPanel = new JPanel(new GridLayout(6, 2, 10, 15));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

        formPanel.add(new JLabel("Titolo:"));
        formPanel.add(textFieldTitolo);

        formPanel.add(new JLabel("Autore:"));
        formPanel.add(textFieldAutore);

        formPanel.add(new JLabel("Anno:"));
        formPanel.add(textFieldAnno);

        formPanel.add(new JLabel("Genere:"));
        formPanel.add(textFieldGenere);

        formPanel.add(new JLabel("Pagine:"));
        formPanel.add(textFieldPagine);

        formPanel.add(new JLabel("Prezzo (€):"));
        formPanel.add(textFieldPrezzo);

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.add(buttonSalva);
        buttonPanel.add(buttonAnnulla);

        // Assembla
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(formPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
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
                    "Libro aggiunto con successo!",
                    "Successo",
                    JOptionPane.INFORMATION_MESSAGE);

            pulisciCampi();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Errore: " + ex.getMessage(),
                    "Errore",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private boolean validaInput() {
        if (textFieldTitolo.getText().trim().isEmpty()) {
            mostraErrore("Il titolo non può essere vuoto");
            textFieldTitolo.requestFocus();
            return false;
        }

        if (textFieldAutore.getText().trim().isEmpty()) {
            mostraErrore("L'autore non può essere vuoto");
            textFieldAutore.requestFocus();
            return false;
        }

        try {
            int anno = Integer.parseInt(textFieldAnno.getText().trim());
            if (anno < 0 || anno > 2024) {
                mostraErrore("Anno deve essere tra 0 e 2024");
                textFieldAnno.requestFocus();
                return false;
            }
        } catch (NumberFormatException e) {
            mostraErrore("Anno deve essere un numero");
            textFieldAnno.requestFocus();
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
            mostraErrore("Pagine deve essere un numero");
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
            mostraErrore("Prezzo deve essere un numero");
            textFieldPrezzo.requestFocus();
            return false;
        }

        return true;
    }

    private void mostraErrore(String messaggio) {
        JOptionPane.showMessageDialog(this, messaggio, "Errore", JOptionPane.ERROR_MESSAGE);
    }

    private void pulisciCampi() {
        textFieldTitolo.setText("");
        textFieldAutore.setText("");
        textFieldAnno.setText("");
        textFieldGenere.setText("");
        textFieldPagine.setText("");
        textFieldPrezzo.setText("");
        textFieldTitolo.requestFocus();
    }
}
