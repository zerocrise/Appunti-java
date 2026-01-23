package interfaces;

public interface Valutabile {
    void aggiungiValutazione(int voto);
    double getMediaValutazioni();
    int getNumeroValutazioni();
    
    default String getClassificazione() {
        double media = getMediaValutazioni();
        if (media >= 4.5) return "⭐⭐⭐⭐⭐ Eccellente";
        if (media >= 3.5) return "⭐⭐⭐⭐ Buono";
        if (media >= 2.5) return "⭐⭐⭐ Discreto";
        if (media >= 1.5) return "⭐⭐ Sufficiente";
        return "⭐ Scarso";
    }
}