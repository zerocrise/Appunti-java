// Scaffale generico con capacità limitata
public class Scaffale<T extends Pubblicazione> {
    private ArrayList<T> contenuto;
    private int capacitaMassima;
    private String categoria;

    public Scaffale(String categoria, int capacitaMassima) {
        this.categoria = categoria;
        this.capacitaMassima = capacitaMassima;
        this.contenuto = new ArrayList<>();
    }

    public boolean aggiungi(T item) {
        if (contenuto.size() < capacitaMassima) {
            contenuto.add(item);
            return true;
        }
        return false;
    }

    public T rimuovi(int indice) {
        if (indice >= 0 && indice < contenuto.size()) {
            return contenuto.remove(indice);
        }
        return null;
    }

    public ArrayList<T> cerca(String parolaChiave) {
        ArrayList<T> risultati = new ArrayList<>();
        for (T item : contenuto) {
            if (item.getTitolo().toLowerCase().contains(parolaChiave.toLowerCase())) {
                risultati.add(item);
            }
        }
        return risultati;
    }

    public void mostraContenuto() {
        System.out.println("\n=== Scaffale: " + categoria + " ===");
        System.out.println("Occupazione: " + contenuto.size() + "/" + capacitaMassima);
        for (int i = 0; i < contenuto.size(); i++) {
            System.out.println((i+1) + ". " + contenuto.get(i).getInfo());
        }
    }

    public ArrayList<T> getContenuto() {
        return new ArrayList<>(contenuto); // Copia difensiva
    }

    public boolean isPieno() {
        return contenuto.size() >= capacitaMassima;
    }
}


