package exceptions;

public class LibroNonTrovatoException extends BibliotecaException {
    private String criterioRicerca;

    public LibroNonTrovatoException(String criterio) {
        super("Nessuna pubblicazione trovata per: " + criterio);
        this.criterioRicerca = criterio;
    }

    public String getCriterioRicerca() {
        return criterioRicerca;
    }
}