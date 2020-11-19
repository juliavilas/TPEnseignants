/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package champollion;

import java.util.Date;




/**
 *
 * @author Juju Vilas
 */
public class Intervention {
    private Date debut;
    private int duree;
    private boolean annulee;
    private TypeIntervention type;

    public Intervention(Salle s, UE u,Enseignant e,Date debut, int duree, TypeIntervention type) {
        this.debut = debut;
        this.duree = duree;
        this.type=type;
    }

    public int getDuree() {
        return duree;
    }

    public TypeIntervention getType() {
        return type;
    }

    @Override
    public String toString() {
        return "Intervention{" + "debut=" + debut + ", duree=" + duree + ", annulee=" + annulee + ", type=" + type + '}';
    }
    
    
}
