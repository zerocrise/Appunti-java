import java.time.*;
import java.time.format.*;
import java.util.*;

public class Prestito {
    private Libro libro;
    private String utente;
    private LocalDate dataInizio;
    private LocalDate dataScadenza;
    private LocalDate dataRestituzione;
    private static final int GIORNI_PRESTITO = 30;
    private static final double MULTA_GIORNO = 0.50; // Euro

    public Prestito(Libro libro, String utente) {
        this.libro = libro;
        this.utente = utente;
        this.dataInizio = LocalDate.now();
        this.dataScadenza = dataInizio.plusDays(GIORNI_PRESTITO);
        this.dataRestituzione = null;
    }

    // Costruttore per test con data personalizzata
    public Prestito(Libro libro, String utente, LocalDate dataInizio) {
        this.libro = libro;
        this.utente = utente;
        this.dataInizio = dataInizio;
        this.dataScadenza = dataInizio.plusDays(GIORNI_PRESTITO);
        this.dataRestituzione = null;
    }

    public void restituisci() {
        this.dataRestituzione = LocalDate.now();
    }

    public boolean isScaduto() {
        LocalDate riferimento = dataRestituzione != null ?
                dataRestituzione : LocalDate.now();
        return riferimento.isAfter(dataScadenza);
    }

    public boolean isAttivo() {
        return dataRestituzione == null;
    }

    public long giorniRitardo() {
        if (!isScaduto()) return 0;

        LocalDate riferimento = dataRestituzione != null ?
                dataRestituzione : LocalDate.now();
        return ChronoUnit.DAYS.between(dataScadenza, riferimento);
    }

    public double calcolaMulta() {
        return giorniRitardo() * MULTA_GIORNO;
    }

    public long giorniRimanenti() {
        if (!isAttivo()) return 0;

        long giorni = ChronoUnit.DAYS.between(LocalDate.now(), dataScadenza);
        return Math.max(0, giorni);
    }

    public String getStatoPrestito() {
        if (!isAttivo()) {
            return isScaduto() ? "RESTITUITO IN RITARDO" : "RESTITUITO";
        }

        long giorniRim = giorniRimanenti();
        if (giorniRim == 0) {
            return "SCADE OGGI!";
        } else if (giorniRim <= 3) {
            return "IN SCADENZA (" + giorniRim + " giorni)";
        } else if (isScaduto()) {
            return "SCADUTO (" + giorniRitardo() + " giorni di ritardo)";
        } else {
            return "ATTIVO (" + giorniRim + " giorni rimanenti)";
        }
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        StringBuilder sb = new StringBuilder();

        sb.append("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n");
        sb.append("Libro: ").append(libro.getTitolo()).append("\n");
        sb.append("Utente: ").append(utente).append("\n");
        sb.append("Data inizio: ").append(dataInizio.format(formatter)).append("\n");
        sb.append("Scadenza: ").append(dataScadenza.format(formatter)).append("\n");

        if (dataRestituzione != null) {
            sb.append("Restituito: ").append(dataRestituzione.format(formatter)).append("\n");
        }

        sb.append("Stato: ").append(getStatoPrestito()).append("\n");

        if (isScaduto()) {
            sb.append("⚠️ Multa: €").append(String.format("%.2f", calcolaMulta())).append("\n");
        }

        sb.append("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");

        return sb.toString();
    }

    // Getters
    public Libro getLibro() { return libro; }
    public String getUtente() { return utente; }
    public LocalDate getDataInizio() { return dataInizio; }
    public LocalDate getDataScadenza() { return dataScadenza; }
    public LocalDate getDataRestituzione() { return dataRestituzione; }
}

