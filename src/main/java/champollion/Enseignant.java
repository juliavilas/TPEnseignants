package champollion;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Enseignant extends Personne {

    private String nom;
    private String email;
    private HashMap<UE, ServicePrevu> myServicesPrevus = new HashMap<>();
    private List<Intervention> myInterventions = new LinkedList<>();

    public Enseignant(String nom, String email) {
        super(nom, email);
    }

    /**
     * Calcule le nombre total d'heures prévues pour cet enseignant en "heures
     * équivalent TD" Pour le calcul : 1 heure de cours magistral vaut 1,5 h
     * "équivalent TD" 1 heure de TD vaut 1h "équivalent TD" 1 heure de TP vaut
     * 0,75h "équivalent TD"
     *
     * @return le nombre total d'heures "équivalent TD" prévues pour cet
     * enseignant, arrondi à l'entier le plus proche
     *
     */
    public int heuresPrevues() {
        int heuresPrevues = 0;
        Iterator<Map.Entry<UE, ServicePrevu>> iterateur = myServicesPrevus.entrySet().iterator();
        while (iterateur.hasNext()) {
            Map.Entry entree = iterateur.next();
            heuresPrevues = heuresPrevues + heuresPrevuesPourUE((UE) entree.getKey());
        }
        return heuresPrevues;
    }

    /**
     * Calcule le nombre total d'heures prévues pour cet enseignant dans l'UE
     * spécifiée en "heures équivalent TD" Pour le calcul : 1 heure de cours
     * magistral vaut 1,5 h "équivalent TD" 1 heure de TD vaut 1h "équivalent
     * TD" 1 heure de TP vaut 0,75h "équivalent TD"
     *
     * @param ue l'UE concernée
     * @return le nombre total d'heures "équivalent TD" prévues pour cet
     * enseignant, arrondi à l'entier le plus proche
     *
     */
    public int heuresPrevuesPourUE(UE ue) {
        int CMEquivalentTD = (3/2) * myServicesPrevus.get(ue).getVolumeCM();
        int TPEquivalentTD = (3/4) * myServicesPrevus.get(ue).getVolumeTP();
        int TD = myServicesPrevus.get(ue).getVolumeTD();
        int heuresPrevuesPourUE = CMEquivalentTD + TPEquivalentTD + TD;
        return heuresPrevuesPourUE;
    }

    /**
     * Ajoute un enseignement au service prévu pour cet enseignant
     *
     * @param ue l'UE concernée
     * @param volumeCM le volume d'heures de cours magitral
     * @param volumeTD le volume d'heures de TD
     * @param volumeTP le volume d'heures de TP
     */
    public void ajouteEnseignement(UE ue, int volumeCM, int volumeTD, int volumeTP) {
        // TODO: Implémenter cette méthode
        //throw new UnsupportedOperationException("Pas encore implémenté");
        if(myServicesPrevus.containsKey(ue)){
            int nouveauVolumeCM = myServicesPrevus.get(ue).getVolumeCM() + volumeCM;
            int nouveauVolumeTD = myServicesPrevus.get(ue).getVolumeTD() + volumeTD;
            int nouveauVolumeTP = myServicesPrevus.get(ue).getVolumeTP() + volumeTP;
            ServicePrevu serviceAjoute = new ServicePrevu(nouveauVolumeCM, nouveauVolumeTD, nouveauVolumeTP);
            myServicesPrevus.replace(ue, serviceAjoute);
        }else{
        ServicePrevu serviceAjoute = new ServicePrevu(volumeCM, volumeTD, volumeTP);
        myServicesPrevus.put(ue, serviceAjoute);
    }
    }

    public void ajouteIntervention(Intervention e) {
        myInterventions.add(e);
        System.out.println("L'intervention " + e + " a été ajoutée aux inteventions de l'enseignant.");
    }

    public int heuresPlanifiees() {
        int heuresPlanifiees = 0;
        int CMEquivalentTD = 0;
        int heuresTD = 0;
        int TPEquivalentTD = 0;
        for (int i = 0; i < myInterventions.size(); i++) {
            //Comment trier par type d'intervention ?
            if (myInterventions.get(i).getType().equals(TypeIntervention.CM)) {
                CMEquivalentTD = (3/2) * myInterventions.get(i).getDuree();
            }
            if (myInterventions.get(i).getType().equals(TypeIntervention.TD)) {
                heuresTD = myInterventions.get(i).getDuree();
            }
            if (myInterventions.get(i).getType().equals(TypeIntervention.TP)) {
                TPEquivalentTD = (3/4) * myInterventions.get(i).getDuree();
            }
            heuresPlanifiees = CMEquivalentTD + heuresTD + TPEquivalentTD;
        }
        return heuresPlanifiees;
    }

    public boolean enSousService() {
        return this.heuresPlanifiees() < this.heuresPrevues();
    }

}
