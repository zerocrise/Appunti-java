import java.util.*;

public class BibliotecaAvanzata {
    // Collezione principale
    private ArrayList<Libro> catalogo;

    // Indici per ricerche veloci
    private HashMap<String, Libro> indiceISBN;
    private HashMap<String, ArrayList<Libro>> indiceAutore;
    private TreeMap<Integer, ArrayList<Libro>> indiceAnno;
    private HashSet<String> isbnRegistrati; // Per evitare duplicati

    public BibliotecaAvanzata() {
        catalogo = new ArrayList<>();
        indiceISBN = new HashMap<>();
        indiceAutore = new HashMap<>();
        indiceAnno = new TreeMap<>();
        isbnRegistrati = new HashSet<>();
    }

    // Aggiunge un libro aggiornando tutti gli indici
    public boolean aggiungiLibro(Libro libro) {
        String isbn = libro.getIsbn();

        // Controlla duplicati
        if (isbnRegistrati.contains(isbn)) {
            System.out.println("⚠️ Libro già presente: " + isbn);
            return false;
        }

        // Aggiungi alla collezione principale
        catalogo.add(libro);
        isbnRegistrati.add(isbn);

        // Aggiorna indice ISBN
        indiceISBN.put(isbn, libro);

        // Aggiorna indice autore
        String autore = libro.getAutore();
        if (!indiceAutore.containsKey(autore)) {
            indiceAutore.put(autore, new ArrayList<>());
        }
        indiceAutore.get(autore).add(libro);

        // Aggiorna indice anno
        int anno = libro.getAnnoPubblicazione();
        if (!indiceAnno.containsKey(anno)) {
            indiceAnno.put(anno, new ArrayList<>());
        }
        indiceAnno.get(anno).add(libro);

        return true;
    }

    // Ricerca veloce per ISBN: O(1)
    public Libro cercaPerISBN(String isbn) {
        return indiceISBN.get(isbn);
    }

    // Ricerca per autore: O(1) + O(n) dove n = libri dell'autore
    public ArrayList<Libro> cercaPerAutore(String autore) {
        ArrayList<Libro> risultati = indiceAutore.get(autore);
        return risultati != null ? new ArrayList<>(risultati) : new ArrayList<>();
    }

    // Ricerca per anno: O(log k) + O(m) dove k = anni diversi, m = libri dell'anno
    public ArrayList<Libro> cercaPerAnno(int anno) {
        ArrayList<Libro> risultati = indiceAnno.get(anno);
        return risultati != null ? new ArrayList<>(risultati) : new ArrayList<>();
    }

    // Ricerca per intervallo di anni
    public ArrayList<Libro> cercaPerIntervalloAnni(int annoDa, int annoA) {
        ArrayList<Libro> risultati = new ArrayList<>();

        // TreeMap è ordinata, possiamo usare subMap
        for (Map.Entry<Integer, ArrayList<Libro>> entry :
                indiceAnno.subMap(annoDa, true, annoA, true).entrySet()) {
            risultati.addAll(entry.getValue());
        }

        return risultati;
    }

    // Ricerca per titolo (meno efficiente, O(n))
    public ArrayList<Libro> cercaPerTitolo(String parolaChiave) {
        ArrayList<Libro> risultati = new ArrayList<>();
        String keyword = parolaChiave.toLowerCase();

        for (Libro libro : catalogo) {
            if (libro.getTitolo().toLowerCase().contains(keyword)) {
                risultati.add(libro);
            }
        }

        return risultati;
    }

    // Rimuove libro aggiornando tutti gli indici
    public boolean rimuoviLibro(String isbn) {
        Libro libro = indiceISBN.get(isbn);
        if (libro == null) return false;

        // Rimuovi dalla collezione principale
        catalogo.remove(libro);
        isbnRegistrati.remove(isbn);

        // Rimuovi da indice ISBN
        indiceISBN.remove(isbn);

        // Rimuovi da indice autore
        ArrayList<Libro> libriAutore = indiceAutore.get(libro.getAutore());
        libriAutore.remove(libro);
        if (libriAutore.isEmpty()) {
            indiceAutore.remove(libro.getAutore());
        }

        // Rimuovi da indice anno
        ArrayList<Libro> libriAnno = indiceAnno.get(libro.getAnnoPubblicazione());
        libriAnno.remove(libro);
        if (libriAnno.isEmpty()) {
            indiceAnno.remove(libro.getAnnoPubblicazione());
        }

        return true;
    }

    // Statistiche
    public void mostraStatistiche() {
        System.out.println("\n╔══════════════════════════════════╗");
        System.out.println("║     STATISTICHE BIBLIOTECA       ║");
        System.out.println("╚══════════════════════════════════╝");
        System.out.println("Totale libri: " + catalogo.size());
        System.out.println("Autori diversi: " + indiceAutore.size());
        System.out.println("Anni rappresentati: " + indiceAnno.size());

        if (!indiceAnno.isEmpty()) {
            System.out.println("Anno più antico: " + indiceAnno.firstKey());
            System.out.println("Anno più recente: " + indiceAnno.lastKey());
        }

        // Trova l'autore più prolifico
        String autorePiu = null;
        int maxLibri = 0;
        for (Map.Entry<String, ArrayList<Libro>> entry : indiceAutore.entrySet()) {
            if (entry.getValue().size() > maxLibri) {
                maxLibri = entry.getValue().size();
                autorePiu = entry.getKey();
            }
        }

        if (autorePiu != null) {
            System.out.println("Autore più prolifico: " + autorePiu +
                    " (" + maxLibri + " libri)");
        }
    }

    // Mostra tutti i libri ordinati
    public void mostraCatalogo(Comparator<Libro> ordinamento) {
        ArrayList<Libro> copia = new ArrayList<>(catalogo);
        copia.sort(ordinamento);

        System.out.println("\n═══ CATALOGO BIBLIOTECA ═══");
        for (int i = 0; i < copia.size(); i++) {
            System.out.println((i+1) + ". " + copia.get(i).getInfoCompleta());
        }
    }
}


