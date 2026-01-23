// Classe Pubblicazione base (da capitoli precedenti)
public abstract class Pubblicazione {
    private String titolo;
    private int annoPubblicazione;

    public Pubblicazione(String titolo, int annoPubblicazione) {
        this.titolo = titolo;
        this.annoPubblicazione = annoPubblicazione;
    }

    public String getTitolo() { return titolo; }
    public int getAnnoPubblicazione() { return annoPubblicazione; }

    public abstract String getInfo();
}
