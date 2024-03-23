package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@SuppressWarnings("serial")
public class Inscription extends JFrame {

    private JTextField champNom;
    private JTextField champPrenom;
    private JTextField champIdentifiant;
    private JPasswordField champMotDePasse;

    // Constructeur
    public Inscription() {
        // Titre de la fenêtre
        setTitle("North Health - Inscription");
        // Taille de la fenêtre
        setSize(400, 250);
        // Action fermeture de la fenêtre
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Gestionnaire de disposition
        setLayout(new BorderLayout());
        // Centrer la fenêtre
        setLocationRelativeTo(null);

        // Composants pour les boutons radio
        JRadioButton administratifRadioButton = new JRadioButton("Administratif");
        JRadioButton professionnelRadioButton = new JRadioButton("Professionnel");
        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(administratifRadioButton);
        buttonGroup.add(professionnelRadioButton);

        // Créer un panneau pour les boutons radio
        JPanel radioPanel = new JPanel();
        radioPanel.setLayout(new FlowLayout());
        radioPanel.add(administratifRadioButton);
        radioPanel.add(professionnelRadioButton);

        // Créer un panneau pour les champs d'entrée
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(4, 2, 10, 10)); // lignes, colonnes, espace entre les composants

        JLabel labelNom = new JLabel("Nom:");
        champNom = new JTextField(10);
        JLabel labelPrenom = new JLabel("Prénom:");
        champPrenom = new JTextField(10);
        JLabel labelIdentifiant = new JLabel("Identifiant:");
        champIdentifiant = new JTextField(10);
        JLabel labelMotDePasse = new JLabel("Mot de passe:");
        champMotDePasse = new JPasswordField(10);

        inputPanel.add(labelNom);
        inputPanel.add(champNom);
        inputPanel.add(labelPrenom);
        inputPanel.add(champPrenom);
        inputPanel.add(labelIdentifiant);
        inputPanel.add(champIdentifiant);
        inputPanel.add(labelMotDePasse);
        inputPanel.add(champMotDePasse);

        // Créer les boutons
        JButton boutonCreationCompte = new JButton("Créer le compte");
        JButton boutonAnnuler = new JButton("Annuler");

     // ActionListener de la création de compte
        boutonCreationCompte.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Vérification des champs vides
                if (champNom.getText().isEmpty() || champPrenom.getText().isEmpty() || champIdentifiant.getText().isEmpty() || champMotDePasse.getPassword().length == 0) {
                    JOptionPane.showMessageDialog(null, "Veuillez remplir tous les champs.", "Erreur", JOptionPane.ERROR_MESSAGE);
                } else if (!administratifRadioButton.isSelected() && !professionnelRadioButton.isSelected()) {
                    JOptionPane.showMessageDialog(null, "Veuillez sélectionner un type de compte.", "Erreur", JOptionPane.ERROR_MESSAGE);
                } else {
                    // Récupérer les données saisies
                    String nom = champNom.getText();
                    String prenom = champPrenom.getText();
                    String identifiant = champIdentifiant.getText();
                    String motDePasse = new String(champMotDePasse.getPassword());

                   // Connexion à la bdd
                    Connection connexion = ConnexionMySQL.getConnexion();
                    if (connexion != null) {
                        try {
                            Statement statement = connexion.createStatement();
                            // Vérifier si l'identifiant existe déjà
                            ResultSet result = statement.executeQuery("SELECT * FROM AdministrativeStaff WHERE login = '" + identifiant + "'");
                            if (result.next()) {
                                JOptionPane.showMessageDialog(null, "L'identifiant existe déjà. Veuillez en utiliser un autre.", "Erreur d'identifiant", JOptionPane.ERROR_MESSAGE);
                            } else {
                                // Insérer les données saisies dans la base de données
                                if (administratifRadioButton.isSelected()) {
                                    String query = "INSERT INTO AdministrativeStaff (lastName, firstName, login, password) VALUES ('" + nom + "', '" + prenom + "', '" + identifiant + "', '" + motDePasse + "')";
                                    statement.executeUpdate(query);
                                    JOptionPane.showMessageDialog(null, "Compte administratif créé avec succès !", "Succès", JOptionPane.INFORMATION_MESSAGE);
                                } else if (professionnelRadioButton.isSelected()) {
                                    // Ajoutez ici la logique pour insérer les données dans la table Professionnals
                                    // Vérifiez également pour les professionnels si l'identifiant existe déjà dans leur table
                                    String query = "INSERT INTO Professionnals (lastName, firstName, login, password, idEstablishment) VALUES ('" + nom + "', '" + prenom + "', '" + identifiant + "', '" + motDePasse + "', 'id_de_votre_etablissement')";
                                    statement.executeUpdate(query);
                                    JOptionPane.showMessageDialog(null, "Compte professionnel créé avec succès !", "Succès", JOptionPane.INFORMATION_MESSAGE);
                                    // Femer la fenêtre et rouvrir la fenêtre d'identification
                                }
                                dispose(); // Fermer la fenêtre de création
                                Identification identification = new Identification();
                                identification.setVisible(true); // Ouvrir la fenêtre d'identification
                            }
                            result.close();
                            statement.close();
                            connexion.close();
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                        }
                    }
                }
            }
        });

        // ActionListener du bouton "Annuler"
        boutonAnnuler.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Fermer la fenêtre d'inscription
                dispose();
                // Ouvrir la fenêtre de connexion
                Identification identification = new Identification();
                identification.setVisible(true);
            }
        });

        // Créer un panneau pour les boutons
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(boutonAnnuler);
        buttonPanel.add(boutonCreationCompte);
       

        // Ajouter les composants à la fenêtre
        add(radioPanel, BorderLayout.NORTH);
        add(inputPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

}
