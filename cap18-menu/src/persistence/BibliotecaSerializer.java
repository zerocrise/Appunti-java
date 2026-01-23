package persistence;

import model.Pubblicazione;
import model.Libro;
import model.Rivista;
import model.Fumetto;
import java.io.*;
import java.util.ArrayList;

public class BibliotecaSerializer {

    private static final String FILE_DEFAULT = "biblioteca.ser";

    public static void salva(ArrayList<Pubblicazione> pubblicazioni) throws IOException {
        salva(pubblicazioni, FILE_DEFAULT);
    }

    public static void salva(ArrayList<Pubblicazione> pubblicazioni, String nomeFile) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(nomeFile))) {
            oos.writeObject(pubblicazioni);
            System.out.println("✅ Salvate " + pubblicazioni.size() + " pubblicazioni");
        }
    }

    @SuppressWarnings("unchecked")
    public static ArrayList<Pubblicazione> carica() throws IOException, ClassNotFoundException {
        return carica(FILE_DEFAULT);
    }

    @SuppressWarnings("unchecked")
    public static ArrayList<Pubblicazione> carica(String nomeFile) throws IOException, ClassNotFoundException {
        File file = new File(nomeFile);
        if (!file.exists()) {
            return new ArrayList<>();
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(nomeFile))) {
            ArrayList<Pubblicazione> pubblicazioni = (ArrayList<Pubblicazione>) ois.readObject();
            System.out.println("✅ Caricate " + pubblicazioni.size() + " pubblicazioni");
            return pubblicazioni;
        }
    }

    public static void creaBackup(ArrayList<Pubblicazione> pubblicazioni) throws IOException {
        String timestamp = new java.text.SimpleDateFormat("yyyyMMdd_HHmmss").format(new java.util.Date());
        String nomeBackup = "biblioteca_backup_" + timestamp + ".ser";
        salva(pubblicazioni, nomeBackup);
        System.out.println("💾 Backup creato: " + nomeBackup);
    }

    public static void esportaCSV(ArrayList<Pubblicazione> pubblicazioni, String nomeFile) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nomeFile))) {
            writer.write("tipo,titolo,anno,pagine,prezzo,disponibile,autore_editore,genere_numero,serie_periodicita,volume");
            writer.newLine();

            for (Pubblicazione pub : pubblicazioni) {
                StringBuilder sb = new StringBuilder();
                sb.append(pub.getTipo()).append(",");
                sb.append(escapaCSV(pub.getTitolo())).append(",");
                sb.append(pub.getAnnoPubblicazione()).append(",");
                sb.append(pub.getNumeroPagine()).append(",");
                sb.append(String.format("%.2f", pub.getPrezzo())).append(",");
                sb.append(pub.isDisponibile()).append(",");

                if (pub instanceof Libro) {
                    Libro libro = (Libro) pub;
                    sb.append(escapaCSV(libro.getAutore())).append(",");
                    sb.append(escapaCSV(libro.getGenere())).append(",");
                    sb.append(",");
                } else if (pub instanceof Rivista) {
                    Rivista rivista = (Rivista) pub;
                    sb.append(escapaCSV(rivista.getEditore())).append(",");
                    sb.append(rivista.getNumeroEdizione()).append(",");
                    sb.append(escapaCSV(rivista.getPeriodicita())).append(",");
                } else if (pub instanceof Fumetto) {
                    Fumetto fumetto = (Fumetto) pub;
                    sb.append(escapaCSV(fumetto.getAutore())).append(",");
                    sb.append(escapaCSV(fumetto.getDisegnatore())).append(",");
                    sb.append(escapaCSV(fumetto.getSerie())).append(",");
                    sb.append(fumetto.getNumeroVolume());
                }

                writer.write(sb.toString());
                writer.newLine();
            }

            System.out.println("✅ Esportate " + pubblicazioni.size() + " pubblicazioni in CSV");
        }
    }

    private static String escapaCSV(String valore) {
        if (valore == null) return "";
        if (valore.contains(",") || valore.contains("\"") || valore.contains("\n")) {
            return "\"" + valore.replace("\"", "\"\"") + "\"";
        }
        return valore;
    }
}