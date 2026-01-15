import java.io.Serializable;
import java.util.ArrayList;

public class Libro extends Pubblicazione implements Valutabile, Serializable {
    private static final long serialVersionUID = 1L;

    private String autore;
    private String genere;
    private ArrayList<Integer> valutazioni;

    public Libro(String titolo, String autore, int anno, String genere,
                 int pagine, double prezzo) {
        super(titolo, anno, pagine, prezzo);
        setAutore(autore);
        setGenere(genere);
        this.valutazioni = new ArrayList<>();
    }

    // Metodi come prima...
}
