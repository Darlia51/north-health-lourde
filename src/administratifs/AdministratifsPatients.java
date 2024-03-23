package administratifs;

import javax.swing.*;
import main.ConnexionMySQL;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.table.DefaultTableModel;

@SuppressWarnings("serial")
public class AdministratifsPatients extends JFrame {

    private JLabel labelTitre;
    private JLabel labelUtilisateurConnecte;
    private JTextField rechercheField;
    private JTable resultTable = new JTable(); // Initialisation de resultTable
    private DefaultTableModel resultTableModel = new DefaultTableModel(); // Initialisation de resultTableModel

    // Constructeur
    public AdministratifsPatients() {
        // Titre de la fenêtre
        setTitle("North Health - Patients");
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

        // Ajout de la barre de recherche
        JPanel centralPanel = new JPanel(new BorderLayout());
        JLabel titreRecherche = new JLabel("Recherche d'un patient", SwingConstants.CENTER);
        JLabel sousTitreRecherche = new JLabel("Numéro de sécurité sociale ou nom de famille", SwingConstants.CENTER);
        rechercheField = new JTextField();

        // Création du panneau pour le titre et le sous-titre
        JPanel titrePanel = new JPanel(new BorderLayout());
        titrePanel.add(titreRecherche, BorderLayout.NORTH);
        titrePanel.add(sousTitreRecherche, BorderLayout.CENTER);

        // Utiliser une disposition BorderLayout pour aligner le titre et le champ de recherche en haut
        JPanel recherchePanel = new JPanel(new BorderLayout());
        recherchePanel.add(titrePanel, BorderLayout.NORTH);
        recherchePanel.add(rechercheField, BorderLayout.CENTER);

        centralPanel.add(recherchePanel, BorderLayout.NORTH);

        // Ajout du bouton Rechercher
        JButton rechercherButton = new JButton("Rechercher");
        rechercherButton.addActionListener(e -> rechercherPatients()); // Appel à la méthode rechercherPatients()

        // Création d'un composant Box pour centrer le bouton Rechercher
        Box centerBox = Box.createHorizontalBox();
        centerBox.add(Box.createHorizontalGlue()); // Ajoute un espace flexible à gauche du bouton
        centerBox.add(rechercherButton); // Ajoute le bouton Rechercher
        centerBox.add(Box.createHorizontalGlue()); // Ajoute un espace flexible à droite du bouton

        // Ajoute le composant Box au panneau central
        centralPanel.add(centerBox, BorderLayout.CENTER);

        // Création d'un modèle de tableau pour afficher les résultats de la recherche
        resultTableModel.addColumn("Numéro de sécurité sociale");
        resultTableModel.addColumn("Nom");
        resultTableModel.addColumn("Prénom");
        resultTable.setModel(resultTableModel);
        JScrollPane resultScrollPane = new JScrollPane(resultTable);

        // Ajout du JScrollPane contenant la JTable des résultats
        centralPanel.add(resultScrollPane, BorderLayout.SOUTH);

        add(centralPanel, BorderLayout.CENTER);

        // Rendre la section Patients active par défaut
        patientsButton.setEnabled(false);

        // Ajout d'un écouteur d'événements au bouton "Agenda"
        agendaButton.addActionListener(e -> {
            // Fermer la fenêtre actuelle
            dispose();
            // Ouvrir la fenêtre de l'agenda
            new AdministratifsAgenda().setVisible(true);
        });

        // Ajout d'un écouteur d'événements au bouton "Factures"
        facturesButton.addActionListener(e -> {
            // Fermer la fenêtre actuelle
            dispose();
            // Ouvrir la fenêtre des factures
            new AdministratifsFactures().setVisible(true);
        });
    }

    // Méthode pour rechercher les patients dans la base de données
    private void rechercherPatients() {
        String recherche = rechercheField.getText().trim();
        if (!recherche.isEmpty()) {
            Connection connexion = null;
            Statement statement = null;
            ResultSet resultSet = null;
            try {
                connexion = ConnexionMySQL.getConnexion();
                statement = connexion.createStatement();
                String requeteSQL = "SELECT insuranceNumber, lastName, firstName FROM Patients WHERE insuranceNumber LIKE '%" + recherche + "%' OR lastName LIKE '%" + recherche + "%'";
                resultSet = statement.executeQuery(requeteSQL);
                resultTableModel.setRowCount(0); // Réinitialiser le modèle de tableau des résultats
                boolean found = false; // Variable pour suivre si des résultats ont été trouvés
                while (resultSet.next()) {
                    found = true; // Au moins un résultat a été trouvé
                    String insuranceNumber = resultSet.getString("insuranceNumber");
                    String lastName = resultSet.getString("lastName");
                    String firstName = resultSet.getString("firstName");
                    resultTableModel.addRow(new Object[]{insuranceNumber, lastName, firstName});
                }
                if (!found) {
                    JOptionPane.showMessageDialog(this, "Aucune donnée trouvée.", "Recherche sans résultats", JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                // Fermer les ressources
                try {
                    if (resultSet != null) resultSet.close();
                    if (statement != null) statement.close();
                    if (connexion != null) connexion.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Veuillez saisir un numéro ou un nom pour effectuer une recherche.", "Erreur de recherche", JOptionPane.ERROR_MESSAGE);
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
