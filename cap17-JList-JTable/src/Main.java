import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Crea biblioteca con dati di esempio
            Biblioteca biblioteca = new Biblioteca();

            try {
                biblioteca.aggiungi(new Libro("1984", "George Orwell",
                        1949, "Distopia", 328, 12.99));
                biblioteca.aggiungi(new Libro("Il Nome della Rosa", "Umberto Eco",
                        1980, "Giallo", 503, 15.50));
                biblioteca.aggiungi(new Libro("Il Signore degli Anelli", "J.R.R. Tolkien",
                        1954, "Fantasy", 1178, 18.90));
                //biblioteca.aggiungi(new Rivista("Focus", "Mondadori",2024, 450, "Mensile", 120, 4.50));
               // biblioteca.aggiungi(new Fumetto("One Piece", "Eiichiro Oda", "Eiichiro Oda",1997, "One Piece", 1, 200, 5.50, false));
            } catch (Exception e) {
                e.printStackTrace();
            }

            BibliotecaVistaFrame frame = new BibliotecaVistaFrame(biblioteca);
            frame.setVisible(true);
        });
    }
}