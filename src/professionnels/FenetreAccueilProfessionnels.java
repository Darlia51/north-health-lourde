package professionnels;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
// JFrame
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class FenetreAccueilProfessionnels extends JFrame {

    // Constructeur
    public FenetreAccueilProfessionnels() {
        // Titre de la fenêtre
        setTitle("North Health - Accueil professionnels");
        // Taille de la fenêtre
        setSize(1366, 768);
        // Action fermeture de la fenêtre
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Gestionnaire de disposition
        setLayout(new BorderLayout());
    }
}