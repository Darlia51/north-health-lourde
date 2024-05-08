package administratifs;

import javax.swing.*;

import main.ConnexionMySQL;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@SuppressWarnings("serial")
public class NouvelleFacture extends JFrame {

	private JLabel labelNomEtablissement;
    private JComboBox<String> comboEtablissement;
    private JLabel labelDateFacture;
    private JTextField fieldDateFacture;
    private JLabel labelNumSecuClient;
    private JTextField fieldNumSecuClient;
    private JLabel labelMontantFacture;
    private JTextField fieldMontantFacture;
    private JLabel labelModePaiement;
    private JComboBox<String> comboModePaiement;
    
    // Constructeur
    public NouvelleFacture() {
        // Titre de la fenêtre
        setTitle("North Health - Nouvelle facture");
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
        labelNomEtablissement = new JLabel("Nom de l'établissement:");
        comboEtablissement = new JComboBox<>(new String[]{"---------", "BioLaboFrance", "Clinique SantéPlus", "Laboratoire Innovatech", "PolyClinique ParisEst", "BioMédica France", "Laboratoire de Recherche Médicale Lumière", "Clinique EspritSanté", "Centre Médical du Soleil", "Laboratoire NeuroBio", "Clinique Médicale Saint-Vincent", "Centre d'Imagerie Médicale Horizon", "Laboratoire de Biotechnologie Proxima"});
        labelDateFacture = new JLabel("Date de la facture (YYYY-MM-DD):");
        fieldDateFacture = new JTextField();
        labelNumSecuClient = new JLabel("Numéro de sécurité sociale du client:");
        fieldNumSecuClient = new JTextField();
        labelMontantFacture = new JLabel("Montant de la facture:");
        fieldMontantFacture = new JTextField();
        labelModePaiement = new JLabel("Mode de paiement:");
        comboModePaiement = new JComboBox<>(new String[]{"---------", "Carte bancaire", "Chèque", "Espèces"});
        
        // Ajout des composants au panneau avec GridBagConstraints
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(labelNomEtablissement, gbc);
        gbc.gridx = 1;
        gbc.weightx = 1.0; // largeur du champ
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(comboEtablissement, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(labelDateFacture, gbc);
        gbc.gridx = 1;
        gbc.weightx = 1.0; 
        panel.add(fieldDateFacture, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(labelNumSecuClient, gbc);
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        panel.add(fieldNumSecuClient, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(labelMontantFacture, gbc);
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        panel.add(fieldMontantFacture, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(labelModePaiement, gbc);
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        panel.add(comboModePaiement, gbc);

        // Ajout des boutons en bas de l'écran
        JButton annulerButton = new JButton("Annuler et retourner aux factures");
        JButton validerButton = new JButton("Valider la facture");
        JPanel boutonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        boutonsPanel.add(annulerButton);
        boutonsPanel.add(validerButton);
        getContentPane().add(boutonsPanel, BorderLayout.SOUTH);

        // Action lorsqu'on clique sur le bouton "Annuler"
        annulerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Fermer la fenêtre actuelle
                new AdministratifsFactures().setVisible(true);
            }
        });
        
        /// Action lorsqu'on clique sur le bouton "Valider"
        validerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Vérifier si un champ est vide
                if (comboEtablissement.getSelectedItem().equals("---------") || fieldNumSecuClient.getText().isEmpty() || fieldDateFacture.getText().isEmpty() || fieldMontantFacture.getText().isEmpty() || comboModePaiement.getSelectedItem().equals("---------")) {
                    // Afficher un message d'erreur
                    JOptionPane.showMessageDialog(NouvelleFacture.this, "Veuillez remplir tous les champs !", "Erreur", JOptionPane.ERROR_MESSAGE);
                    } else {
                        // Vérifier si le montant est valide
                        String montantTexte = fieldMontantFacture.getText();
                        if (!montantTexte.contains(".")) {
                            // Afficher un message d'erreur si le montant ne contient pas de point
                            JOptionPane.showMessageDialog(NouvelleFacture.this, "Veuillez utiliser un point (.) pour les décimales du montant.", "Erreur", JOptionPane.ERROR_MESSAGE);
                        } else {
                    // Insérer la facture dans la base de données
                    insererFacture();

                    // Afficher un message de confirmation
                    JOptionPane.showMessageDialog(NouvelleFacture.this, "Facture validée !");
                    dispose(); // Fermer la fenêtre actuelle
                    new AdministratifsFactures().setVisible(true);
                }
            }
                }
        });
    }
    
    // Méthode pour insérer la facture dans la base de données
    private void insererFacture() {
        // Établir une connexion à la base de données
        Connection connexionBdd = ConnexionMySQL.getConnexion();

        // Définir la requête SQL d'insertion
        String sql = "INSERT INTO Bill (idEstablishment, billDate, insuranceNumber, amount, idPayment) VALUES (?, ?, ?, ?, ?)";

        try {
            // Préparer la requête
            PreparedStatement statement = connexionBdd.prepareStatement(sql);

            // Remplir les paramètres de la requête
            statement.setInt(1, comboEtablissement.getSelectedIndex());
            statement.setString(2, fieldDateFacture.getText());
            statement.setString(3, fieldNumSecuClient.getText());
            statement.setBigDecimal(4, new BigDecimal(fieldMontantFacture.getText()));
            statement.setInt(5, comboModePaiement.getSelectedIndex());

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
//	public static void main(String[] args) {
//        // Création et affichage de la fenêtre
//        SwingUtilities.invokeLater(new Runnable() {
//            @Override
//            public void run() {
//                new NouvelleFacture().setVisible(true);
//            }
//        });
//    }
}
