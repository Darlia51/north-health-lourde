import java.util.Date;

public class RendezVous extends Medecin {

    Date dateRdv;
    String heureRdv;
    boolean possibilite;
    String typeConsultation;
    String hospOptions;

    RendezVous (Date dateRdv, String heureRdv, boolean possibilite, String typeConsultation, String hospOptions,
                String nomEtablissement, String villeEtablissement, int codePostalEtablissement, String nomMedecin, String prenomMedecin) {
        super (nomEtablissement, villeEtablissement, codePostalEtablissement, nomMedecin, prenomMedecin);
        this.dateRdv = dateRdv;
        this.heureRdv = heureRdv;
        this.possibilite = possibilite;
        this.typeConsultation = typeConsultation;
        this.hospOptions = hospOptions;
    }

    public String getTypeConsultation() {
        return typeConsultation;
    }
    public void setTypeConsultation(String typeConsultation) {
        this.typeConsultation = typeConsultation;
    }

    public String getHospOptions() {
        return hospOptions;
    }
    public void setHospOptions(String hospOptions) {
        this.hospOptions = hospOptions;
    }

}
