package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

// JFrame
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import administratifs.AdministratifsAgenda;
import professionnels.FenetreAccueilProfessionnels;

@SuppressWarnings("serial")
public class Identification extends JFrame {

    @SuppressWarnings("unused")
	private JLabel labelIdentifiant;
    private JTextField champIdentifiant;
    private JPasswordField fieldMotDePasse;
    private JButton boutonConnexion;
    private JButton boutonCreationCompte;
    
    // Constructeur
    public Identification() {
        // Titre de la fenêtre
        setTitle("North Health - Identification");
        // Taille de la fenêtre
        setSize(400, 250);
        // Action fermeture de la fenêtre
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Gestionnaire de disposition
        setLayout(new BorderLayout());
     // Centrer la fenêtre
        setLocationRelativeTo(null);

        // Composants
        JLabel labelIdentifiant = new JLabel("Identifiant:");
        champIdentifiant = new JTextField(10); // Taille du champ de texte pour l'identifiant
        champIdentifiant.setPreferredSize(new Dimension(200, champIdentifiant.getPreferredSize().height));

        JLabel labelMotDePasse = new JLabel("Mot de passe:");
        fieldMotDePasse = new JPasswordField(10); // Taille du champ de texte pour le mot de passe
        fieldMotDePasse.setPreferredSize(new Dimension(200, fieldMotDePasse.getPreferredSize().height));

        // Créer un panneau pour les champs d'entrée
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(2, 1, 0, 0)); // lignes, colonnes,vertical et horizontal

        JPanel identifiantPanel = new JPanel();
        identifiantPanel.add(labelIdentifiant);
        identifiantPanel.add(champIdentifiant);
        inputPanel.add(identifiantPanel);

        JPanel motDePassePanel = new JPanel();
        motDePassePanel.add(labelMotDePasse);
        motDePassePanel.add(fieldMotDePasse);
        inputPanel.add(motDePassePanel);

        // Créer les boutons
        boutonConnexion = new JButton("Connexion");
        boutonCreationCompte = new JButton("Créer un compte");
        
        // ActionListener de la connexion
        boutonConnexion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String identifiant = champIdentifiant.getText();
                String motDePasse = new String(fieldMotDePasse.getPassword());

                // Vérification des informations d'identification
                if (identifiant.isEmpty() || motDePasse.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Informations manquantes et/ou incorrectes.", "Erreur d'identification", JOptionPane.ERROR_MESSAGE);
                } else {
                    // Logique de connexion
                    try {
                        Connection connexion = ConnexionMySQL.getConnexion();
                        PreparedStatement statement = connexion.prepareStatement("SELECT * FROM AdministrativeStaff WHERE login = ? AND password = ?");
                        statement.setString(1, identifiant);
                        statement.setString(2, motDePasse);
                        ResultSet resultSet = statement.executeQuery();

                        if (resultSet.next()) {
                            // Ouverture de la fenêtre correspondante
                        	AdministratifsAgenda fenetreAgenda = new AdministratifsAgenda();
                            fenetreAgenda.setVisible(true);
                            dispose(); // Fermeture de la fenêtre actuelle
                        } else {
                            statement = connexion.prepareStatement("SELECT * FROM Professionnals WHERE login = ? AND password = ?");
                            statement.setString(1, identifiant);
                            statement.setString(2, motDePasse);
                            resultSet = statement.executeQuery();

                            if (resultSet.next()) {
                                // Ouverture de la fenêtre correspondante
                                FenetreAccueilProfessionnels fenetreAccueil = new FenetreAccueilProfessionnels();
                                fenetreAccueil.setVisible(true);
                                dispose(); // Fermeture de la fenêtre actuelle
                            } else {
                                JOptionPane.showMessageDialog(null, "Identifiant ou mot de passe incorrect !");
                            }
                        }

                        resultSet.close();
                        statement.close();
                        connexion.close();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(null, "Erreur lors de la connexion à la base de données !");
                    }
                }
            }
        });
        
        // ActionListener de la création de compte
        boutonCreationCompte.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Ouvrir la fenêtre d'inscription
                Inscription fenetreInscription = new Inscription();
                fenetreInscription.setVisible(true);
                // Fermer la fenêtre d'identification
                dispose();
            }
        });

        // Créer un panneau pour les boutons
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(boutonConnexion);
        buttonPanel.add(boutonCreationCompte);

        // Ajouter les composants à la fenêtre
        add(inputPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }
}