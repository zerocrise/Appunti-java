import java.io.Serializable;

public abstract class Pubblicazione implements Prestabile, Scontabile, Serializable {
    private static final long serialVersionUID = 1L;

    protected String titolo;
    protected int annoPubblicazione;
    protected int numeroPagine;
    protected boolean disponibile;
    protected double prezzo;

    // Costruttore, getter, setter, metodi come prima...
}
