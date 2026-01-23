public class PubblicazioneNonDisponibileException extends BibliotecaException {
    private Pubblicazione pubblicazione;

    public PubblicazioneNonDisponibileException(Pubblicazione pub) {
        super("La pubblicazione \"" + pub.getTitolo() + "\" non è disponibile");
        this.pubblicazione = pub;
    }

    public Pubblicazione getPubblicazione() {
        return pubblicazione;
    }
}
