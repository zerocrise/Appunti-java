public class Rivista {
    private String titolo;
    private int numero;



    public Rivista(String titolo, int numero){
        this.titolo=titolo;
        this.numero=numero;
    }




    public void stampaInfo() {
        System.out.println("═══════════════════════════════════");
        System.out.println("📚 Rivista");
        System.out.println("Titolo: " + titolo);
        System.out.println("Numero: " + numero);
        System.out.println("═══════════════════════════════════");
    }
}
