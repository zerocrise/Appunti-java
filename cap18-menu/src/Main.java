import gui.BibliotecaAppFinale;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        // Esegue l'applicazione nel thread di Swing
        SwingUtilities.invokeLater(() -> {
            try {
                // Imposta il Look and Feel di sistema per un aspetto nativo
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (ClassNotFoundException | InstantiationException | 
                     IllegalAccessException | UnsupportedLookAndFeelException e) {
                System.err.println("Impossibile impostare il Look and Feel di sistema");
                e.printStackTrace();
            }
            
            // Crea e mostra la finestra principale
            System.setProperty("awt.useSystemAAFontSettings", "on");
            System.setProperty("swing.aatext", "true");
            BibliotecaAppFinale app = new BibliotecaAppFinale();
            app.setVisible(true);
        });
    }
}