import java.io.*;
import java.util.ArrayList;

public class BibliotecaSerializer {

    private static final String FILE_DEFAULT = "biblioteca.ser";

    /**
     * Salva l'intera biblioteca in formato binario
     */
    public static void salva(ArrayList<Pubblicazione> pubblicazioni)
            throws IOException {
        salva(pubblicazioni, FILE_DEFAULT);
    }

    public static void salva(ArrayList<Pubblicazione> pubblicazioni, String nomeFile)
            throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(nomeFile))) {

            oos.writeObject(pubblicazioni);
            System.out.println("✅ Salvate " + pubblicazioni.size() +
                    " pubblicazioni in " + nomeFile);

        } catch (IOException e) {
            System.err.println("❌ Errore salvataggio: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Carica l'intera biblioteca da file binario
     */
    public static ArrayList<Pubblicazione> carica()
            throws IOException, ClassNotFoundException {
        return carica(FILE_DEFAULT);
    }

    public static ArrayList<Pubblicazione> carica(String nomeFile)
            throws IOException, ClassNotFoundException {
        File file = new File(nomeFile);

        if (!file.exists()) {
            System.out.println("⚠️  File non trovato, biblioteca vuota");
            return new ArrayList<>();
        }

        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream(nomeFile))) {

            @SuppressWarnings("unchecked")
            ArrayList<Pubblicazione> pubblicazioni =
                    (ArrayList<Pubblicazione>) ois.readObject();

            System.out.println("✅ Caricate " + pubblicazioni.size() +
                    " pubblicazioni da " + nomeFile);

            return pubblicazioni;

        } catch (IOException | ClassNotFoundException e) {
            System.err.println("❌ Errore caricamento: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Crea backup con timestamp
     */
    public static void creaBackup(ArrayList<Pubblicazione> pubblicazioni)
            throws IOException {
        String timestamp = new java.text.SimpleDateFormat("yyyyMMdd_HHmmss")
                .format(new java.util.Date());
        String nomeBackup = "biblioteca_backup_" + timestamp + ".ser";

        salva(pubblicazioni, nomeBackup);
        System.out.println("💾 Backup creato: " + nomeBackup);
    }

    /**
     * Esporta in formato CSV (compatibilità con Capitolo 10)
     */
    public static void esportaCSV(ArrayList<Pubblicazione> pubblicazioni,
                                  String nomeFile) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nomeFile))) {
            // Header
            writer.write("tipo,titolo,anno,pagine,prezzo,disponibile");
            writer.newLine();

            // Dati
            for (Pubblicazione pub : pubblicazioni) {
                writer.write(String.format("%s,%s,%d,%d,%.2f,%b",
                        pub.getTipo(),
                        pub.getTitolo(),
                        pub.getAnnoPubblicazione(),
                        pub.getNumeroPagine(),
                        pub.getPrezzo(),
                        pub.isDisponibile()
                ));
                writer.newLine();
            }

            System.out.println("✅ Esportate " + pubblicazioni.size() +
                    " pubblicazioni in CSV");
        }
    }

    /**
     * Verifica integrità file
     */
    public static boolean verificaIntegrita(String nomeFile) {
        try {
            carica(nomeFile);
            return true;
        } catch (Exception e) {
            System.err.println("⚠️  File corrotto o incompatibile: " + e.getMessage());
            return false;
        }
    }

    /**
     * Ottieni informazioni sul file senza caricarlo completamente
     */
    public static void mostraInfo(String nomeFile) {
        File file = new File(nomeFile);

        if (!file.exists()) {
            System.out.println("❌ File non esiste");
            return;
        }

        System.out.println("📁 Informazioni File:");
        System.out.println("   Nome: " + file.getName());
        System.out.println("   Percorso: " + file.getAbsolutePath());
        System.out.println("   Dimensione: " + file.length() + " bytes");
        System.out.println("   Ultima modifica: " +
                new java.util.Date(file.lastModified()));

        try {
            ArrayList<Pubblicazione> pubs = carica(nomeFile);
            System.out.println("   Pubblicazioni: " + pubs.size());
        } catch (Exception e) {
            System.out.println("   Stato: ⚠️  Corrotto o incompatibile");
        }
    }
}
