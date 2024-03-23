package administratifs;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import main.ConnexionMySQL;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@SuppressWarnings("serial")
public class AdministratifsFactures extends JFrame {

    private JLabel labelTitre;
    private JLabel labelUtilisateurConnecte;
    private JTable tableFactures;

    // Constructeur
    public AdministratifsFactures() {
        // Titre de la fenêtre
        setTitle("North Health - Factures");
        // Taille de la fenêtre
        setSize(1366, 768);
        // Action fermeture de la fenêtre
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        // Gestionnaire de disposition
        setLayout(new BorderLayout());
        // Centrer la fenêtre
        setLocationRelativeTo(null);

        // Ajout d'un écouteur d'événements de fermeture de fenêtre
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                fermerApplication();
            }
        });

        // Création du header
        JPanel headerPanel = new JPanel(new BorderLayout());
        labelTitre = new JLabel("Espace administratif", SwingConstants.CENTER);
        headerPanel.add(labelTitre, BorderLayout.NORTH);
        labelUtilisateurConnecte = new JLabel("Connecté en tant que : [Nom Prénom]", SwingConstants.RIGHT);
        headerPanel.add(labelUtilisateurConnecte, BorderLayout.EAST);
        add(headerPanel, BorderLayout.NORTH);

        // Création de la colonne avec les boutons
        JPanel gauchePanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.BOTH; // Pour étendre les boutons sur toute la hauteur
        JButton agendaButton = new JButton("Agenda");
        gauchePanel.add(agendaButton, gbc);
        gbc.gridy = 1;
        JButton patientsButton = new JButton("Patients");
        gauchePanel.add(patientsButton, gbc);
        gbc.gridy = 2;
        JButton facturesButton = new JButton("Factures");
        gauchePanel.add(facturesButton, gbc);
        add(gauchePanel, BorderLayout.WEST);

        // Rendre la section Agenda active par défaut
        facturesButton.setEnabled(false);

        // Ajout d'un écouteur d'événements au bouton "Agenda"
        agendaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Fermer la fenêtre actuelle
                dispose();
                // Ouvrir la fenêtre de l'agenda
                new AdministratifsAgenda().setVisible(true);
            }
        });

        // Ajout d'un écouteur d'événements au bouton "Patients"
        patientsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Fermer la fenêtre actuelle
                dispose();
                // Ouvrir la fenêtre des patients
                new AdministratifsPatients().setVisible(true);
            }
        });

        // Création du panneau central pour afficher la liste des factures
        JPanel centralPanel = new JPanel(new BorderLayout());

        // Titre "Listes des factures"
        JLabel titreFactures = new JLabel("Liste des factures", SwingConstants.CENTER);
        centralPanel.add(titreFactures, BorderLayout.NORTH);

        // Création du tableau pour afficher les factures
        tableFactures = new JTable();
        JScrollPane scrollPane = new JScrollPane(tableFactures);
        centralPanel.add(scrollPane, BorderLayout.CENTER);

        // Création du panneau pour les boutons "Créer une facture" et "Supprimer une facture"
        JPanel boutonsPanel = new JPanel();
        JButton creerFactureButton = new JButton("Créer une facture");
        JButton supprimerFactureButton = new JButton("Supprimer une facture");
        boutonsPanel.add(creerFactureButton);
        boutonsPanel.add(supprimerFactureButton);
        centralPanel.add(boutonsPanel, BorderLayout.SOUTH);

        add(centralPanel, BorderLayout.CENTER);

        // Charger les données des factures dans le tableau
        chargerDonneesFactures();
        
        // Ajout d'un écouteur d'événements au bouton "Créer une facture"
        creerFactureButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	// Fermer la fenêtre actuelle
                dispose();
                // Ouvrir une nouvelle fenêtre de création de facture
                NouvelleFacture nouvelleFacture = new NouvelleFacture();
                nouvelleFacture.setVisible(true);
            }
        });

    }

    // Méthode pour charger les données des factures depuis la base de données dans le tableau
    private void chargerDonneesFactures() {
        Connection connexion = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            // Établir la connexion à la base de données
            connexion = ConnexionMySQL.getConnexion();
            // Créer la requête SQL pour récupérer les données de la vue Facture_View
            String requeteSQL = "SELECT * FROM Facture_View";
            statement = connexion.createStatement();
            resultSet = statement.executeQuery(requeteSQL);

            // Créer le modèle de tableau
            DefaultTableModel listeFactures = new DefaultTableModel();
            listeFactures.addColumn("ID facture");
            listeFactures.addColumn("Patient");
            listeFactures.addColumn("Date");
            listeFactures.addColumn("Montant");

            // Parcourir le résultat de la requête et ajouter les lignes au modèle de tableau
            while (resultSet.next()) {
                int idFacture = resultSet.getInt("idFacture");
                String patient = resultSet.getString("nom_prenom_patient");
                String date = resultSet.getString("date_facture");
                String montant = resultSet.getString("montant_paiement");
                listeFactures.addRow(new Object[]{idFacture, patient, date, montant});
            }

            // Définir le modèle de tableau pour la JTable
            tableFactures.setModel(listeFactures);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Fermer les ressources
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connexion != null) {
                try {
                    connexion.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // Méthode pour gérer la fermeture de l'application
    private void fermerApplication() {
        int option = JOptionPane.showConfirmDialog(this, "Voulez-vous vous déconnecter ?", "Confirmation de déconnexion", JOptionPane.YES_NO_OPTION);
        if (option == JOptionPane.YES_OPTION) {
            System.exit(0); // Fermer complètement l'application
        }
    }
}
