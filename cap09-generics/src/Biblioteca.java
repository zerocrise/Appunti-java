


// Biblioteca con scaffali multipli
public class Biblioteca<T extends Pubblicazione> {
    private ArrayList<Scaffale<T>> scaffali;
    private String nome;

    public Biblioteca(String nome) {
        this.nome = nome;
        this.scaffali = new ArrayList<>();
    }

    public void aggiungiScaffale(Scaffale<T> scaffale) {
        scaffali.add(scaffale);
    }

    public boolean aggiungiPubblicazione(T item) {
        for (Scaffale<T> scaffale : scaffali) {
            if (!scaffale.isPieno()) {
                return scaffale.aggiungi(item);
            }
        }
        return false;
    }

    public ArrayList<T> cercaGlobale(String parolaChiave) {
        ArrayList<T> risultati = new ArrayList<>();
        for (Scaffale<T> scaffale : scaffali) {
            risultati.addAll(scaffale.cerca(parolaChiave));
        }
        return risultati;
    }

    public void mostraTutto() {
        System.out.println("\n╔════════════════════════════════════╗");
        System.out.println("║  " + nome.toUpperCase());
        System.out.println("╚════════════════════════════════════╝");
        for (Scaffale<T> scaffale : scaffali) {
            scaffale.mostraContenuto();
        }
    }
}
