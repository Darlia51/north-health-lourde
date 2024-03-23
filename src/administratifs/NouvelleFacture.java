package administratifs;

import javax.swing.*;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@SuppressWarnings("serial")
public class NouvelleFacture extends JFrame {

	private JLabel labelNomEtablissement;
    private JTextField fieldNomEtablissement;
    private JLabel labelDateFacture;
    private JTextField fieldDateFacture;
    private JLabel labelNumSecuClient;
    private JTextField fieldNumSecuClient;
    private JLabel labelNomPrenomClient;
    private JTextField fieldNomPrenomClient;
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
        fieldNomEtablissement = new JTextField();
        labelDateFacture = new JLabel("Date de la facture (YYYY-MM-DD):");
        fieldDateFacture = new JTextField();
        labelNumSecuClient = new JLabel("Numéro de sécurité sociale du client:");
        fieldNumSecuClient = new JTextField();
        labelNomPrenomClient = new JLabel("Nom et prénom du client:");
        fieldNomPrenomClient = new JTextField();
        labelMontantFacture = new JLabel("Montant de la facture:");
        fieldMontantFacture = new JTextField();
        labelModePaiement = new JLabel("Mode de paiement:");
        comboModePaiement = new JComboBox<>(new String[]{"CarteBancaire", "Cheque", "Espece"});

     // Ajout des composants au panneau avec GridBagConstraints appropriés
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(labelNomEtablissement, gbc);
        gbc.gridx = 1;
        gbc.weightx = 1.0; // Élargir le champ de saisie
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(fieldNomEtablissement, gbc);
        gbc.weightx = 0.0; // Réinitialiser weightx pour les autres composants

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(labelDateFacture, gbc);
        gbc.gridx = 1;
        gbc.weightx = 1.0; // largeur du champ
        panel.add(fieldDateFacture, gbc);
        gbc.weightx = 0.0;

        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(labelNumSecuClient, gbc);
        gbc.gridx = 1;
        gbc.weightx = 1.0; // largeur du champ
        panel.add(fieldNumSecuClient, gbc);
        gbc.weightx = 0.0;

        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(labelNomPrenomClient, gbc);
        gbc.gridx = 1;
        gbc.weightx = 1.0; // largeur du champ
        panel.add(fieldNomPrenomClient, gbc);
        gbc.weightx = 0.0;

        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(labelMontantFacture, gbc);
        gbc.gridx = 1;
        gbc.weightx = 1.0; // largeur du champ
        panel.add(fieldMontantFacture, gbc);
        gbc.weightx = 0.0;

        gbc.gridx = 0;
        gbc.gridy = 5;
        panel.add(labelModePaiement, gbc);
        gbc.gridx = 1;
        gbc.weightx = 1.0; // largeur du champ
        panel.add(comboModePaiement, gbc);
        gbc.weightx = 0.0;

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
            }
        });

     // Action lorsqu'on clique sur le bouton "Valider"
        validerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Vérifier si un champ est vide
                if (fieldNumSecuClient.getText().isEmpty() || fieldNomPrenomClient.getText().isEmpty() || fieldNomEtablissement.getText().isEmpty() ||
                        fieldDateFacture.getText().isEmpty() || fieldMontantFacture.getText().isEmpty()) {
                    // Afficher un message d'erreur
                    JOptionPane.showMessageDialog(NouvelleFacture.this, "Veuillez remplir tous les champs !", "Erreur", JOptionPane.ERROR_MESSAGE);
                } else {
                    // Insérer le code pour valider la facture ici
                    JOptionPane.showMessageDialog(NouvelleFacture.this, "Facture validée !");
                    dispose(); // Fermer la fenêtre actuelle
                }
            }
        });

    }

     // Méthode pour gérer la fermeture de l'application
    @SuppressWarnings("unused")
	private void fermerApplication() {
    	int option = JOptionPane.showConfirmDialog(this, "Voulez-vous vous déconnecter ?", "Confirmation de déconnexion", JOptionPane.YES_NO_OPTION);
    	if (option == JOptionPane.YES_OPTION) {
    		System.exit(0); // Fermer complètement l'application
    		}
    	}
    
    public static void main(String[] args) {
        // Création et affichage de la fenêtre
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new NouvelleFacture().setVisible(true);
            }
        });
    }
};