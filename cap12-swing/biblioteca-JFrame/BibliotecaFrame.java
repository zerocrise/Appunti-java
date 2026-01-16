import javax.swing.*;
import java.awt.*;

public class BibliotecaFrame extends JFrame {

    // Componenti dell'interfaccia
    private JPanel mainPanel;
    private JPanel headerPanel;
    private JPanel infoPanel;
    private JPanel footerPanel;

    public BibliotecaFrame() {
        // Configurazione della finestra
        setTitle("Biblioteca Personale");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centra la finestra

        // Inizializzazione e assemblaggio
        inizializzaComponenti();
        assemblaInterfaccia();
    }

    private void inizializzaComponenti() {
        mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBackground(new Color(240, 240, 240));

        headerPanel = creaHeader();
        infoPanel = creaInfoPanel();
        footerPanel = creaFooter();
    }

    private JPanel creaHeader() {
        JPanel panel = new JPanel();
        panel.setBackground(new Color(51, 102, 153));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0));

        JLabel label = new JLabel("📚 BIBLIOTECA PERSONALE");
        label.setFont(new Font("Arial", Font.BOLD, 28));
        label.setForeground(Color.WHITE);
        panel.add(label);

        return panel;
    }

    private JPanel creaInfoPanel() {
        JPanel panel = new JPanel(new GridLayout(6, 2, 10, 15));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Dati di esempio
        aggiungiRigaInfo(panel, "Titolo:", "1984");
        aggiungiRigaInfo(panel, "Autore:", "George Orwell");
        aggiungiRigaInfo(panel, "Anno:", "1949");
        aggiungiRigaInfo(panel, "Genere:", "Distopia");
        aggiungiRigaInfo(panel, "Pagine:", "328");
        aggiungiRigaInfo(panel, "Prezzo:", "€12.99");

        return panel;
    }

    private JPanel creaFooter() {
        JPanel panel = new JPanel();
        panel.setBackground(new Color(51, 102, 153));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        JLabel label = new JLabel("Versione 12.0 - GUI con Swing");
        label.setForeground(Color.WHITE);
        panel.add(label);

        return panel;
    }

    private void aggiungiRigaInfo(JPanel panel, String etichetta, String valore) {
        JLabel labelComp = new JLabel(etichetta);
        labelComp.setFont(new Font("Arial", Font.BOLD, 14));

        JLabel valueComp = new JLabel(valore);
        valueComp.setFont(new Font("Arial", Font.PLAIN, 14));

        panel.add(labelComp);
        panel.add(valueComp);
    }

    private void assemblaInterfaccia() {
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(infoPanel, BorderLayout.CENTER);
        mainPanel.add(footerPanel, BorderLayout.SOUTH);
        add(mainPanel);
    }

    /**
     * Aggiorna l'interfaccia con i dati di un libro specifico.
     */
    public void mostraLibro(Libro libro) {
        infoPanel.removeAll();

        aggiungiRigaInfo(infoPanel, "Titolo:", libro.getTitolo());
        aggiungiRigaInfo(infoPanel, "Autore:", libro.getAutore());
        aggiungiRigaInfo(infoPanel, "Anno:", String.valueOf(libro.getAnnoPubblicazione()));
        aggiungiRigaInfo(infoPanel, "Genere:", libro.getGenere());
        aggiungiRigaInfo(infoPanel, "Pagine:", String.valueOf(libro.getNumeroPagine()));
        aggiungiRigaInfo(infoPanel, "Prezzo:", String.format("€%.2f", libro.getPrezzo()));

        infoPanel.revalidate(); // Notifica il layout manager dei cambiamenti
        infoPanel.repaint();    // Richiede il ridisegno del pannello
    }
}