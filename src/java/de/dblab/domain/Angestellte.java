package de.dblab.domain;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import org.apache.cayenne.CayenneDataObject;
import org.apache.cayenne.exp.Expression;
import de.dblab.service.cayenne.Property;



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
    
   
    public static final Property<List<Schaechte>> SCHAECHTEZULASSUNG = new Property<List<Schaechte>>("schaechteZulassung");
    public static final Property<Boolean> ENTLASSENE = new Property<Boolean>("entlassene");
    public static final Property<java.sql.Date> GEBURTSDATUM = new Property<java.sql.Date>("geburtsdatum","Geburtsdatum",4);
    public static final Property<Integer> GEHALT = new Property<Integer>("gehalt","Gehalt",3);
    public static final Property<Integer> ID = new Property<Integer>("id","Id",5);
    public static final Property<String> NACHNAME = new Property<String>("nachname","Nachname",1);
    public static final Property<String> NAME = new Property<String>("name","Name",0);
    public static final Property<String> STELLE = new Property<String>("stelle","Stelle",2);
    public static final Property<List<Schaechte>> SCHAECHTE_ARRAY = new Property<List<Schaechte>>("schaechteArray");
    public static final Property<List<Zeit>> ZEIT_ARRAY = new Property<List<Zeit>>("zeitArray");

    public static final Property[] visiblyColumnAngestellte = {Angestellte.NAME,
                                                           Angestellte.NACHNAME,
                                                           Angestellte.STELLE,
                                                           Angestellte.GEHALT,
                                                           Angestellte.GEBURTSDATUM,
                                                           Angestellte.ID};
    
    public void setEntlassene(Boolean entlassene) {
        writeProperty("entlassene", entlassene);
    }
    public Boolean getEntlassene() {
        return (Boolean)readProperty("entlassene");
    }

    public void setGeburtsdatum(Date geburtsdatum) {
        writeProperty("geburtsdatum", geburtsdatum);
    }
    public Date getGeburtsdatum() {
        return (Date)readProperty("geburtsdatum");
    }

    public void setGehalt(Integer gehalt) {
        writeProperty("gehalt", gehalt);
    }
    public Integer getGehalt() {
        return (Integer)readProperty("gehalt");
    }

    public void setId(Integer id) {
        writeProperty("id", id);
    }
    public Integer getId() {
        return (Integer)readProperty("id");
    }

    public void setNachname(String nachname) {
        writeProperty("nachname", nachname);
    }
    public String getNachname() {
        return (String)readProperty("nachname");
    }

    public void setName(String name) {
        writeProperty("name", name);
    }
    public String getName() {
        return (String)readProperty("name");
    }

    public void setStelle(String stelle) {
        writeProperty("stelle", stelle);
    }
    public String getStelle() {
        return (String)readProperty("stelle");
    }

    public void addToSchaechteArray(Schaechte obj) {
        addToManyTarget("schaechteArray", obj, true);
    }
    public void removeFromSchaechteArray(Schaechte obj) {
        removeToManyTarget("schaechteArray", obj, true);
    }
    @SuppressWarnings("unchecked")
    public List<Schaechte> getSchaechteArray() {
        return (List<Schaechte>)readProperty("schaechteArray");
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
        addToManyTarget("zeitArray", obj, true);
    }
    public void removeFromZeitArray(Zeit obj) {
        removeToManyTarget("zeitArray", obj, true);
    }
    @SuppressWarnings("unchecked")
    public List<Zeit> getZeitArray() {
        return (List<Zeit>)readProperty("zeitArray");
    }

}
