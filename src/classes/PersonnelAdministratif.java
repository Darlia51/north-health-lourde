public class PersonnelAdministratif extends ConnexionUtilisateur {

    String nomPersonnel;
    String prenomPersonnel;
    String mailPersonnel;

    PersonnelAdministratif (String identifiant, String mdp, String nomPersonnel, String prenomPersonnel, String mailPersonnel) {
        super(identifiant, mdp);
        this.nomPersonnel = nomPersonnel;
        this.prenomPersonnel = prenomPersonnel;
        this.mailPersonnel = mailPersonnel;
    }
    
    public void nomConnecte() {
    	System.out.println(nomPersonnel + " " + prenomPersonnel);
    	
    }
}
