package administratifs;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import main.ConnexionMySQL;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@SuppressWarnings("serial")
public class AdministratifsAgenda extends JFrame {

    private JLabel labelTitre;
    private JLabel labelUtilisateurConnecte;
    private JTable tableRendezVous;

    // Constructeur
    public AdministratifsAgenda() {
        // Titre de la fenêtre
        setTitle("North Health - Agenda");
        // Taille de la fenêtre
        setSize(1366, 768);
        // Action fermeture de la fenêtre
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        // Gestionnaire de disposition
        setLayout(new BorderLayout());
        // Centrer la fenêtre
        setLocationRelativeTo(null);

        // Ajout d'un écouteur d'événements de fermeture de fenêtre
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
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

        // Création de la JTable pour afficher les rendez-vous
        tableRendezVous = new JTable();
        JScrollPane scrollPane = new JScrollPane(tableRendezVous);
        add(scrollPane, BorderLayout.CENTER);

        // Rendre la section Agenda active par défaut
        agendaButton.setEnabled(false);

        // Charger les données dans le tableau
        chargerDonneesRendezVous();

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
        
        // Ajout d'un écouteur d'événements au bouton "Factures"
        facturesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Fermer la fenêtre actuelle
                dispose();
                // Ouvrir la fenêtre des factures
                new AdministratifsFactures().setVisible(true);
            }
        });
    }

    // Méthode pour charger les données des rendez-vous depuis la base de données dans le tableau
    private void chargerDonneesRendezVous() {
        Connection connexion = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            // Établir la connexion à la base de données
            connexion = ConnexionMySQL.getConnexion();
            // Créer la requête SQL pour récupérer les données de la vue
            String requeteSQL = "SELECT * FROM appointment_administrative_view";
            statement = connexion.createStatement();
            resultSet = statement.executeQuery(requeteSQL);

            // Créer le modèle de tableau
            DefaultTableModel listeAgenda = new DefaultTableModel();
            listeAgenda.addColumn("Date");
            listeAgenda.addColumn("Heure");
            listeAgenda.addColumn("Patient");
            listeAgenda.addColumn("Professionnel");
            listeAgenda.addColumn("Établissement");

            // Parcourir le résultat de la requête et ajouter les lignes au modèle de tableau
            while (resultSet.next()) {
                String date = resultSet.getString("appointmentDate");
                String heure = resultSet.getString("timeSlot");
                String patient = resultSet.getString("patientName");
                String professionnel = resultSet.getString("fullName");
                String etablissement = resultSet.getString("establishmentName");
                listeAgenda.addRow(new Object[]{date, heure, patient, professionnel, etablissement});
            }

            // Définir le modèle de tableau pour la JTable
            tableRendezVous.setModel(listeAgenda);

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