import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        // ✅ Avvia la GUI nel thread corretto (EDT)
        SwingUtilities.invokeLater(() -> {
            BibliotecaFrame frame = new BibliotecaFrame();
            frame.setVisible(true);

            // Test: visualizza un libro
            Libro libro = new Libro(
                    "Il Signore degli Anelli",
                    "J.R.R. Tolkien",
                    1954,
                    "Fantasy",
                    1178,
                    18.90
            );
            frame.mostraLibro(libro);
        });
    }
}
