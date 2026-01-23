public class DatiNonValidiException extends BibliotecaException {
    private String campo;
    private String motivazione;

    public DatiNonValidiException(String campo, String motivazione) {
        super("Campo '" + campo + "': " + motivazione);
        this.campo = campo;
        this.motivazione = motivazione;
    }

    public String getCampo() {
        return campo;
    }

    public String getMotivazione() {
        return motivazione;
    }
}