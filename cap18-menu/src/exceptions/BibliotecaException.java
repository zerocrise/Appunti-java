package exceptions;

public class BibliotecaException extends Exception {
    public BibliotecaException(String messaggio) {
        super(messaggio);
    }

    public BibliotecaException(String messaggio, Throwable causa) {
        super(messaggio, causa);
    }
}