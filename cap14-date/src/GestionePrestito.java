import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GestionePrestiti {
    private ArrayList<Prestito> prestiti;
    private HashMap<String, ArrayList<Prestito>> prestitiPerUtente;
    private HashMap<Libro, Prestito> prestitiAttivi; // Libro -> Prestito attivo

    public GestionePrestiti() {
        prestiti = new ArrayList<>();
        prestitiPerUtente = new HashMap<>();
        prestitiAttivi = new HashMap<>();
    }

    public boolean creaPrestito(Libro libro, String utente) {
        // Verifica se il libro è già in prestito
        if (prestitiAttivi.containsKey(libro)) {
            System.out.println("⚠️ Libro già in prestito!");
            return false;
        }

        // Verifica se l'utente ha prestiti scaduti
        if (haPrestitiScaduti(utente)) {
            System.out.println("⚠️ Utente ha prestiti scaduti. Restituire prima di nuovi prestiti.");
            return false;
        }

        Prestito prestito = new Prestito(libro, utente);
        prestiti.add(prestito);
        prestitiAttivi.put(libro, prestito);

        // Aggiungi all'indice per utente
        if (!prestitiPerUtente.containsKey(utente)) {
            prestitiPerUtente.put(utente, new ArrayList<>());
        }
        prestitiPerUtente.get(utente).add(prestito);

        System.out.println("✓ Prestito creato con successo!");
        System.out.println("Scadenza: " + prestito.getDataScadenza()
                .format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));

        return true;
    }

    public boolean restituisci(Libro libro) {
        Prestito prestito = prestitiAttivi.get(libro);

        if (prestito == null) {
            System.out.println("⚠️ Questo libro non risulta in prestito.");
            return false;
        }

        prestito.restituisci();
        prestitiAttivi.remove(libro);

        System.out.println("✓ Libro restituito!");

        if (prestito.isScaduto()) {
            System.out.println("⚠️ Restituzione in ritardo di " +
                    prestito.giorniRitardo() + " giorni");
            System.out.println("Multa da pagare: €" +
                    String.format("%.2f", prestito.calcolaMulta()));
        }

        return true;
    }

    public boolean haPrestitiScaduti(String utente) {
        ArrayList<Prestito> prestitiUtente = prestitiPerUtente.get(utente);
        if (prestitiUtente == null) return false;

        for (Prestito p : prestitiUtente) {
            if (p.isAttivo() && p.isScaduto()) {
                return true;
            }
        }
        return false;
    }

    public ArrayList<Prestito> getPrestitiAttivi() {
        return new ArrayList<>(prestitiAttivi.values());
    }

    public ArrayList<Prestito> getPrestitiUtente(String utente) {
        ArrayList<Prestito> lista = prestitiPerUtente.get(utente);
        return lista != null ? new ArrayList<>(lista) : new ArrayList<>();
    }

    public ArrayList<Prestito> getPrestitiScaduti() {
        ArrayList<Prestito> scaduti = new ArrayList<>();
        for (Prestito p : prestitiAttivi.values()) {
            if (p.isScaduto()) {
                scaduti.add(p);
            }
        }
        return scaduti;
    }

    public ArrayList<Prestito> getPrestitiInScadenza(int giorni) {
        ArrayList<Prestito> inScadenza = new ArrayList<>();
        for (Prestito p : prestitiAttivi.values()) {
            if (!p.isScaduto() && p.giorniRimanenti() <= giorni) {
                inScadenza.add(p);
            }
        }
        return inScadenza;
    }

    public void mostraPrestitiAttivi() {
        ArrayList<Prestito> attivi = getPrestitiAttivi();

        if (attivi.isEmpty()) {
            System.out.println("\nNessun prestito attivo.");
            return;
        }

        System.out.println("\n╔════════════════════════════════════╗");
        System.out.println("║      PRESTITI ATTIVI (" + attivi.size() + ")         ║");
        System.out.println("╚════════════════════════════════════╝\n");

        for (Prestito p : attivi) {
            System.out.println(p);
            System.out.println();
        }
    }

    public void mostraPrestitiScaduti() {
        ArrayList<Prestito> scaduti = getPrestitiScaduti();

        if (scaduti.isEmpty()) {
            System.out.println("\n✓ Nessun prestito scaduto!");
            return;
        }

        System.out.println("\n╔════════════════════════════════════╗");
        System.out.println("║   ⚠️  PRESTITI SCADUTI (" + scaduti.size() + ")  ⚠️    ║");
        System.out.println("╚════════════════════════════════════╝\n");

        double multaTotale = 0;
        for (Prestito p : scaduti) {
            System.out.println(p);
            multaTotale += p.calcolaMulta();
            System.out.println();
        }

        System.out.println("Multa totale: €" + String.format("%.2f", multaTotale));
    }

    public void mostraPromemoria() {
        ArrayList<Prestito> inScadenza = getPrestitiInScadenza(3);

        if (inScadenza.isEmpty()) {
            System.out.println("\n✓ Nessun prestito in scadenza nei prossimi 3 giorni.");
            return;
        }

        System.out.println("\n╔════════════════════════════════════╗");
        System.out.println("║    📅 PROMEMORIA SCADENZE          ║");
        System.out.println("╚════════════════════════════════════╝\n");

        for (Prestito p : inScadenza) {
            System.out.println("⏰ " + p.getLibro().getTitolo());
            System.out.println("   Utente: " + p.getUtente());
            System.out.println("   Scade tra: " + p.giorniRimanenti() + " giorni");
            System.out.println();
        }
    }

    public Map<String, Integer> getStatisticheUtenti() {
        Map<String, Integer> stats = new HashMap<>();

        for (Map.Entry<String, ArrayList<Prestito>> entry : prestitiPerUtente.entrySet()) {
            stats.put(entry.getKey(), entry.getValue().size());
        }

        return stats;
    }
}

