package main;
import java.sql.*;
//import java.awt.EventQueue;
// Dates

public class ConnexionMySQL {

    public static Connection getConnexion() {
        String url = "jdbc:mysql://cg3iti.stackhero-network.com/NorthHealthBdd?useSSL=true&requireSSL=true";
        String user = "salarie";
        String password = "S40IuedsNkuwq@08S6JA";

        Connection connexionBdd = null;
        try {
            // Charger le pilote JDBC
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Établir la connexion à la base de données
            connexionBdd = DriverManager.getConnection(url, user, password);

            if (connexionBdd != null) {
                System.out.println("Connexion à la base de données réussie !");
            } else {
                System.out.println("Échec de la connexion à la base de données !");
            }
        } catch (ClassNotFoundException e) {
            System.out.println("Erreur: Pilote JDBC introuvable !");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Erreur: Impossible d'établir une connexion à la base de données !");
            e.printStackTrace();
        }
        
        return connexionBdd;
    }
}