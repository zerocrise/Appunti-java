

public class Libro  {
 private String titolo;
    private String autore;
public Libro(String titolo, String autore){
        this.titolo=titolo;
        this.autore=autore;
    }
 public void stampaInfo() {
        System.out.println("═══════════════════════════════════");
        System.out.println("📚 LIBRO");
        System.out.println("Titolo: " + titolo);
        System.out.println("Autore: " + autore);
        System.out.println("═══════════════════════════════════");
    }
}
