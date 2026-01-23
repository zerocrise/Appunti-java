import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Biblioteca biblioteca = new Biblioteca();
            AggiungiLibroFrame frame = new AggiungiLibroFrame(biblioteca);
            frame.setVisible(true);
        });
    }
}
