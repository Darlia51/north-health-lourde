package professionnels;

import javax.swing.*;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import main.ConnexionMySQL;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@SuppressWarnings("serial")
public class NouvelleIntervention extends JFrame {

	private JLabel labelNumSecuClient;
    private JTextField fieldNumSecuClient;
    private JLabel labelDateIntervention;
    private JTextField fieldDateIntervention;
    private JLabel labelHeureIntervention;
    private JTextField fieldHeureIntervention;
    private JLabel labelDescription;
    private JTextField fieldDescription;
	private JLabel labelNomEtablissement;
    private JComboBox<String> comboEtablissement;
    private JLabel labelTypeConsultation;
    private JComboBox<String> comboTypeConsultation;
    private JLabel labelProfessionnel;
    private JComboBox<String> comboProfessionnel;
    
    // Constructeur
    public NouvelleIntervention() {
        // Titre de la fenêtre
        setTitle("North Health - Nouvelle intervention");
        // Taille de la fenêtre
        setSize(600, 400);
        // Action fermeture de la fenêtre
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        // Centrer la fenêtre
        setLocationRelativeTo(null);

        // Layout pour le formulaire
        GridBagLayout layout = new GridBagLayout();
        JPanel panel = new JPanel(layout);
        getContentPane().add(panel);

        // Labels et champs de saisie
        labelNumSecuClient = new JLabel("Numéro de sécurité sociale du client :");
        fieldNumSecuClient = new JTextField();
        labelDateIntervention = new JLabel("Date des actes (YYYY-MM-DD) :");
        fieldDateIntervention = new JTextField();
        labelHeureIntervention = new JLabel("Heure des actes (HH:MM) :");
        fieldHeureIntervention = new JTextField();
        labelDescription = new JLabel("Description de l'intervention :");
        fieldDescription = new JTextField();
        labelTypeConsultation = new JLabel("Type d'intervention :");
        comboTypeConsultation = new JComboBox<>(new String[]{"---------", "Consultation générale", "IRM", "Chirurgie", "Tests urinaires", "Ammoniémie", "Endoscopie", "Echographie", "Prise de sang", "Otoplastie", "Radiographie", "Vasectomie", "Colposcopie", "Electrocardiographie", "Test ADN", "Microbiologie"});
        
        labelNomEtablissement = new JLabel("Nom de l'établissement :");
        comboEtablissement = new JComboBox<>(new String[]{"---------", "BioLaboFrance", "Clinique SantéPlus", "Laboratoire Innovatech", "PolyClinique ParisEst", "BioMédica France", "Laboratoire de Recherche Médicale Lumière", "Clinique EspritSanté", "Centre Médical du Soleil", "Laboratoire NeuroBio", "Clinique Médicale Saint-Vincent", "Centre d'Imagerie Médicale Horizon", "Laboratoire de Biotechnologie Proxima"});
        
        labelProfessionnel = new JLabel("Professionnel :");
        comboProfessionnel = new JComboBox<>(new String[]{"---------", "LAVIE Martine", "MARTIN Jacques", "DUPONT Jean", "DURAND Marie", "LEFEBVRE Julie", "GIRARD Lucie", "ROUSSEAU Nicolas", "LECLERC Mathilde", "MONCHOT Daniel", "LEBLANC Maryse", "BLANCHARD Paul", "MONCEAUX Annie"});
        
        // Ajout des composants au panneau avec GridBagConstraints
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Marges au dessus, gauche, en dessous et à droite.
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(labelNumSecuClient, gbc);
        gbc.gridx = 1;
        gbc.weightx = 1.0;  // largeur du champ
        gbc.fill = GridBagConstraints.HORIZONTAL; // Prendre toute la largeur
        panel.add(fieldNumSecuClient, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(labelDateIntervention, gbc);
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(fieldDateIntervention, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(labelHeureIntervention, gbc);
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(fieldHeureIntervention, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(labelDescription, gbc);
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(fieldDescription, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 5;
        panel.add(labelTypeConsultation, gbc);
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(comboTypeConsultation, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 6;
        panel.add(labelNomEtablissement, gbc);
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(comboEtablissement, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 7;
        panel.add(labelProfessionnel, gbc);
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(comboProfessionnel, gbc);

        // Ajout des boutons en bas de l'écran
        JButton annulerButton = new JButton("Annuler et retourner aux consultations");
        JButton validerButton = new JButton("Valider l'intervention");
        JPanel boutonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        boutonsPanel.add(annulerButton);
        boutonsPanel.add(validerButton);
        getContentPane().add(boutonsPanel, BorderLayout.SOUTH);

        // Action lorsqu'on clique sur le bouton "Annuler"
        annulerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Fermer la fenêtre actuelle
                new ProfessionnelsConsultations().setVisible(true);
            }
        });
        
        /// Action lorsqu'on clique sur le bouton "Valider"
        validerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Vérifier si un champ est vide
                if (fieldNumSecuClient.getText().isEmpty() || fieldDateIntervention.getText().isEmpty() || fieldHeureIntervention.getText().isEmpty() || fieldDescription.getText().isEmpty() || comboTypeConsultation.getSelectedItem().equals("---------") || comboEtablissement.getSelectedItem().equals("---------") || comboProfessionnel.getSelectedItem().equals("---------")) {
                    // Afficher un message d'erreur
                    JOptionPane.showMessageDialog(NouvelleIntervention.this, "Veuillez remplir tous les champs !", "Erreur", JOptionPane.ERROR_MESSAGE);
                } else {
                    // Insérer la facture dans la base de données
                    insererFichier();

                    // Afficher un message de confirmation
                    JOptionPane.showMessageDialog(NouvelleIntervention.this, "Intervention validée !");
                    dispose(); // Fermer la fenêtre actuelle
                    new ProfessionnelsConsultations().setVisible(true);
                }
            }
                
        });
    }
    
    // Méthode pour insérer la facture dans la base de données
    private void insererFichier() {
        // Établir une connexion à la base de données
        Connection connexionBdd = ConnexionMySQL.getConnexion();

        // Définir la requête SQL d'insertion
        String sql = "INSERT INTO InterventionForm (insuranceNumber, interventionDate, interventionHour, interventionDescription, idConsultationType, idEstablishment, idProfessionnal) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try {
            // Préparer la requête
            PreparedStatement statement = connexionBdd.prepareStatement(sql);

            // Remplir les paramètres de la requête
            //statement.setInt(1, idEtablissement);
            statement.setString(1, fieldNumSecuClient.getText());
            statement.setString(2, fieldDateIntervention.getText());
            statement.setString(3, fieldHeureIntervention.getText());
            statement.setString(4, fieldDescription.getText());
            statement.setInt(5, comboTypeConsultation.getSelectedIndex());
            statement.setInt(6, comboTypeConsultation.getSelectedIndex());
            statement.setInt(7, comboProfessionnel.getSelectedIndex());

            // Exécuter la requête
            statement.executeUpdate();

            // Fermer la connexion et la déclaration
            statement.close();
            connexionBdd.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    // Méthode pour gérer la fermeture de l'application
	@SuppressWarnings("unused")
	private void fermerApplication() {
    	int option = JOptionPane.showConfirmDialog(this, "Voulez-vous vous déconnecter ?", "Confirmation de déconnexion", JOptionPane.YES_NO_OPTION);
    	if (option == JOptionPane.YES_OPTION) {
    		System.exit(0); // Fermer complètement l'application
    		}
    	}
    }


