import java.io.*;
import java.util.ArrayList;

public class BibliotecaFileManager {

    private static final String SEPARATORE = ",";
    private static final String FILE_DEFAULT = "biblioteca.csv";

    // Salva biblioteca
    public static void salva(ArrayList<Pubblicazione> pubblicazioni)
            throws IOException {
        salva(pubblicazioni, FILE_DEFAULT);
    }

    public static void salva(ArrayList<Pubblicazione> pubblicazioni, String nomeFile)
            throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nomeFile))) {
            // Header
            writer.write("tipo,titolo,anno,pagine,prezzo,disponibile,campo1,campo2,campo3,campo4");
            writer.newLine();

            // Dati
            for (Pubblicazione pub : pubblicazioni) {
                writer.write(convertiInRiga(pub));
                writer.newLine();
            }

            System.out.println("✅ Salvate " + pubblicazioni.size() +
                    " pubblicazioni in " + nomeFile);
        }
    }

    // Carica biblioteca
    public static ArrayList<Pubblicazione> carica() throws IOException {
        return carica(FILE_DEFAULT);
    }

    public static ArrayList<Pubblicazione> carica(String nomeFile) throws IOException {
        File file = new File(nomeFile);
        if (!file.exists()) {
            System.out.println("⚠️  File non trovato, biblioteca vuota");
            return new ArrayList<>();
        }

        ArrayList<Pubblicazione> pubblicazioni = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(nomeFile))) {
            reader.readLine();  // Salta header

            String riga;
            int numeroRiga = 1;
            int errori = 0;

            while ((riga = reader.readLine()) != null) {
                numeroRiga++;

                try {
                    Pubblicazione pub = parseRiga(riga);
                    pubblicazioni.add(pub);
                } catch (Exception e) {
                    System.err.println("⚠️  Errore riga " + numeroRiga + ": " +
                            e.getMessage());
                    errori++;
                }
            }

            System.out.println("✅ Caricate " + pubblicazioni.size() +
                    " pubblicazioni da " + nomeFile);
            if (errori > 0) {
                System.out.println("⚠️  " + errori + " righe ignorate per errori");
            }
        }

        return pubblicazioni;
    }

    // Backup
    public static void creaBackup(ArrayList<Pubblicazione> pubblicazioni)
            throws IOException {
        String timestamp = new java.text.SimpleDateFormat("yyyyMMdd_HHmmss")
                .format(new java.util.Date());
        String nomeBackup = "biblioteca_backup_" + timestamp + ".csv";
        salva(pubblicazioni, nomeBackup);
        System.out.println("💾 Backup creato: " + nomeBackup);
    }

    // Metodi privati di utilità

    private static String convertiInRiga(Pubblicazione pub) {
        StringBuilder sb = new StringBuilder();

        sb.append(pub.getTipo()).append(SEPARATORE);
        sb.append(escapaCSV(pub.getTitolo())).append(SEPARATORE);
        sb.append(pub.getAnnoPubblicazione()).append(SEPARATORE);
        sb.append(pub.getNumeroPagine()).append(SEPARATORE);
        sb.append(String.format("%.2f", pub.getPrezzo())).append(SEPARATORE);
        sb.append(pub.isDisponibile()).append(SEPARATORE);

        if (pub instanceof Libro) {
            Libro libro = (Libro) pub;
            sb.append(escapaCSV(libro.getAutore())).append(SEPARATORE);
            sb.append(escapaCSV(libro.getGenere())).append(SEPARATORE);
            sb.append(SEPARATORE);
            sb.append("");

        } else if (pub instanceof Rivista) {
            Rivista rivista = (Rivista) pub;
            sb.append(escapaCSV(rivista.getEditore())).append(SEPARATORE);
            sb.append(rivista.getNumeroEdizione()).append(SEPARATORE);
            sb.append(escapaCSV(rivista.getPeriodicita())).append(SEPARATORE);
            sb.append("");

        } else if (pub instanceof Fumetto) {
            Fumetto fumetto = (Fumetto) pub;
            sb.append(escapaCSV(fumetto.getAutore())).append(SEPARATORE);
            sb.append(escapaCSV(fumetto.getDisegnatore())).append(SEPARATORE);
            sb.append(escapaCSV(fumetto.getSerie())).append(SEPARATORE);
            sb.append(fumetto.getNumeroVolume());
        }

        return sb.toString();
    }

    private static Pubblicazione parseRiga(String riga) throws Exception {
        String[] campi = parseCSV(riga);

        if (campi.length < 10) {
            throw new IllegalArgumentException("Riga incompleta");
        }

        String tipo = campi[0];
        String titolo = campi[1];
        int anno = Integer.parseInt(campi[2]);
        int pagine = Integer.parseInt(campi[3]);
        double prezzo = Double.parseDouble(campi[4].replace(",", "."));
        boolean disponibile = Boolean.parseBoolean(campi[5]);

        Pubblicazione pub;

        switch (tipo) {
            case "LIBRO":
            case "Libro":
                pub = new Libro(titolo, campi[6], anno, campi[7], pagine, prezzo);
                break;
            case "RIVISTA":
            case "Rivista":
                pub = new Rivista(titolo, campi[6], anno,
                        Integer.parseInt(campi[7]), campi[8], pagine, prezzo);
                break;
            case "FUMETTO":
            case "Fumetto":
                pub = new Fumetto(titolo, campi[6], campi[7], anno,
                        campi[8], Integer.parseInt(campi[9]),
                        pagine, prezzo, false);
                break;
            default:
                throw new IllegalArgumentException("Tipo sconosciuto: " + tipo);
        }

        pub.setDisponibile(disponibile);
        return pub;
    }

    private static String escapaCSV(String valore) {
        if (valore == null) return "";
        if (valore.contains(",") || valore.contains("\"") || valore.contains("\n")) {
            return "\"" + valore.replace("\"", "\"\"") + "\"";
        }
        return valore;
    }

    private static String[] parseCSV(String riga) {
        ArrayList<String> campi = new ArrayList<>();
        StringBuilder campo = new StringBuilder();
        boolean inVirgolette = false;

        for (int i = 0; i < riga.length(); i++) {
            char c = riga.charAt(i);

            if (c == '"') {
                inVirgolette = !inVirgolette;
            } else if (c == ',' && !inVirgolette) {
                campi.add(campo.toString().trim());
                campo = new StringBuilder();
            } else {
                campo.append(c);
            }
        }
        campi.add(campo.toString().trim());

        return campi.toArray(new String[0]);
    }
}