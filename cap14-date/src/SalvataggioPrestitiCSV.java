import java.io.*;
import java.time.*;
import java.time.format.*;

public class SalvataggioPrestitiCSV {

    public static void salvaPrestiti(ArrayList<Prestito> prestiti, String filename)
            throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            // Header
            writer.println("ISBN,Utente,DataInizio,DataScadenza,DataRestituzione");

            for (Prestito p : prestiti) {
                writer.print(p.getLibro().getIsbn() + ",");
                writer.print(p.getUtente() + ",");
                writer.print(p.getDataInizio() + ","); // Formato ISO automatico
                writer.print(p.getDataScadenza() + ",");

                if (p.getDataRestituzione() != null) {
                    writer.println(p.getDataRestituzione());
                } else {
                    writer.println(""); // Campo vuoto
                }
            }
        }
    }

    public static ArrayList<Prestito> caricaPrestiti(String filename,
                                                     HashMap<String, Libro> catalogoISBN) throws IOException {
        ArrayList<Prestito> prestiti = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            reader.readLine(); // Salta header

            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] campi = linea.split(",");

                String isbn = campi[0];
                String utente = campi[1];
                LocalDate dataInizio = LocalDate.parse(campi[2]); // Parse ISO
                LocalDate dataScadenza = LocalDate.parse(campi[3]);

                Libro libro = catalogoISBN.get(isbn);
                if (libro == null) continue;

                Prestito p = new Prestito(libro, utente, dataInizio);

                // Se c'è data restituzione
                if (campi.length > 4 && !campi[4].isEmpty()) {
                    LocalDate dataRest = LocalDate.parse(campi[4]);
                    // Imposta data restituzione manualmente
                    // (richiederebbe un setter o costruttore apposito)
                }

                prestiti.add(p);
            }
        }

        return prestiti;
    }
}
