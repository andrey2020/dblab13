/** 
 * Hochschule Offenburg, Dezember 2013
 * Databanken Labor 3, Gruppe 13
 * @author Nikolaev Andrey & Ostrovskaya Anna
 */

package de.dblab.domain;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import org.apache.cayenne.CayenneDataObject;
import org.apache.cayenne.exp.Expression;
import de.dblab.service.cayenne.Property;

/* 
 * Class Angestellte beschreibt Tabelle Angestellte von Datenbank.
 */

public class Angestellte extends CayenneDataObject{
   
    private static HashMap<Integer, String> columnAngestellte;
    private static HashMap<String, Expression> stateAngestellte;
    public static final String stateAngestellteArray[] = {"Alle","Entlassente","im Betrieb"};
    
    public static HashMap<String, Expression> getStateAngestellte(){
        if (stateAngestellte == null){
            stateAngestellte = new HashMap<String, Expression>();
            stateAngestellte.put(stateAngestellteArray[0], Angestellte.ENTLASSENE.isNotNull());
            stateAngestellte.put(stateAngestellteArray[1], Angestellte.ENTLASSENE.isTrue());
            stateAngestellte.put(stateAngestellteArray[2], Angestellte.ENTLASSENE.isFalse());
        }
        return stateAngestellte;
    }
    
    public static HashMap<Integer, String> getColumnAngestellte(){
        if (columnAngestellte == null){
            columnAngestellte = new HashMap<Integer, String>(); 
            for(int i = 0; i<visiblyColumnAngestellte.length;i++){
                columnAngestellte.put(i, visiblyColumnAngestellte[i].getTitleName());
            }
        }
        return columnAngestellte;
    }
    
    public static Expression getExpressionAngestellte(int type, Object value){
        return visiblyColumnAngestellte[type].likeIgnoreCaseExp(value);
    }
    
    public static final String ID_PK_COLUMN = "ID";
    public static final Property<Boolean> ENTLASSENE = new Property<Boolean>("entlassene");
    public static final Property<java.sql.Date> GEBURTSDATUM = new Property<java.sql.Date>("geburtsdatum","Geburtsdatum",4);
    public static final Property<Integer> GEHALT = new Property<Integer>("gehalt","Gehalt",3);
    public static final Property<Integer> ID = new Property<Integer>("id","Id",5);
    public static final Property<String> NACHNAME = new Property<String>("nachname","Nachname",1);
    public static final Property<String> NAME = new Property<String>("name","Name",0);
    public static final Property<String> STELLE = new Property<String>("stelle","Stelle",2);
    public static final Property<String> VOLLNAME = new Property<String>("vollname");
    
    public static final Property<List<Schaechte>> LEITERVONSCHAECHTE = new Property<List<Schaechte>>("leiterVonSchaechte");
    public static final Property<List<Zeit>> ZEIT_ARRAY = new Property<List<Zeit>>("zeitArray");
    public static final Property<List<Schaechte>> SCHAECHTEZULASSUNG = new Property<List<Schaechte>>("schaechteZulassung");

    public static final Property[] visiblyColumnAngestellte = {Angestellte.NAME,
                                                           Angestellte.NACHNAME,
                                                           Angestellte.STELLE,
                                                           Angestellte.GEHALT,
                                                           Angestellte.GEBURTSDATUM,
                                                           Angestellte.ID};
    
    public String getVollname() {
        String s;
        s=this.getName()+" "+this.getNachname();
        return s;
    }
    
    public void setEntlassene(Boolean entlassene) {
        writeProperty(ENTLASSENE.getName(), entlassene);
    }
    public Boolean getEntlassene() {
        return (Boolean)readProperty(ENTLASSENE.getName());
    }

    public void setGeburtsdatum(Date geburtsdatum) {
        writeProperty(GEBURTSDATUM.getName(), geburtsdatum);
    }
    public Date getGeburtsdatum() {
        return (Date)readProperty(GEBURTSDATUM.getName());
    }

    public void setGehalt(Integer gehalt) {
        writeProperty(GEHALT.getName(), gehalt);
    }
    public Integer getGehalt() {
        return (Integer)readProperty(GEHALT.getName());
    }

    public void setId(Integer id) {
        writeProperty(ID.getName(), id);
    }
    public Integer getId() {
        return (Integer)readProperty(ID.getName());
    }

    public void setNachname(String nachname) {
        writeProperty(NACHNAME.getName(), nachname);
    }
    public String getNachname() {
        return (String)readProperty(NACHNAME.getName());
    }

    public void setName(String name) {
        writeProperty(NAME.getName(), name);
    }
    public String getName() {
        return (String)readProperty(NAME.getName());
    }

    public void setStelle(String stelle) {
        writeProperty(STELLE.getName(), stelle);
    }
    public String getStelle() {
        return (String)readProperty(STELLE.getName());
    }

    public void addToLeiterVonSchaechte(Schaechte obj) {
        addToManyTarget(LEITERVONSCHAECHTE.getName(), obj, true);
    }
    public void removeFromLeiterVonSchaechte(Schaechte obj) {
        removeToManyTarget(LEITERVONSCHAECHTE.getName(), obj, true);
    }
    @SuppressWarnings("unchecked")
    public List<Schaechte> getLeiterVonSchaechte() {
        return (List<Schaechte>)readProperty(LEITERVONSCHAECHTE.getName());
    }


    public void addToSchaechteZulassung(Schaechte obj) {
        addToManyTarget(SCHAECHTEZULASSUNG.getName(), obj, true);
    }
    public void removeFromSchaechteZulassung(Schaechte obj) {
        removeToManyTarget(SCHAECHTEZULASSUNG.getName(), obj, true);
    }
    @SuppressWarnings("unchecked")
    public List<Schaechte> getSchaechteZulassung() {
        return (List<Schaechte>)readProperty(SCHAECHTEZULASSUNG.getName());
    }


    public void addToZeitArray(Zeit obj) {
        addToManyTarget(ZEIT_ARRAY.getName(), obj, true);
    }
    public void removeFromZeitArray(Zeit obj) {
        removeToManyTarget(ZEIT_ARRAY.getName(), obj, true);
    }
    @SuppressWarnings("unchecked")
    public List<Zeit> getZeitArray() {
        return (List<Zeit>)readProperty(ZEIT_ARRAY.getName());
    }
 
}
