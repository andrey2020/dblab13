package de.dblab.domain;

import java.util.List;
import org.apache.cayenne.CayenneDataObject;
import de.dblab.service.cayenne.Property;
import java.util.HashMap;
import org.apache.cayenne.exp.Expression;

public class Schaechte extends CayenneDataObject {

    
    private static HashMap<Integer, String> columnSchaechte;
    private static HashMap<String, Expression> stateSchaechte;
    public static final String stateSchaechteArray[] = {"Alle","Aktiv","Inaktiv"};
    
    public static HashMap<String, Expression> getStateSchaechte(){
        if (stateSchaechte == null){
            stateSchaechte = new HashMap<String, Expression>();
            stateSchaechte.put(stateSchaechteArray[0], Schaechte.GESCHLOSSEN.isNotNull());
            stateSchaechte.put(stateSchaechteArray[1], Schaechte.GESCHLOSSEN.isFalse());
            stateSchaechte.put(stateSchaechteArray[2], Schaechte.GESCHLOSSEN.isTrue());
        }
        return stateSchaechte;
    }
    
    public static HashMap<Integer, String> getColumnSchaechte(){
        if (columnSchaechte == null){
            columnSchaechte = new HashMap<Integer, String>(); 
            for(int i = 0; i<visiblyColumnSchaechte.length;i++){
                columnSchaechte.put(i, visiblyColumnSchaechte[i].getTitleName());
            }
        }
        return columnSchaechte;
    }
    
    public static Expression getExpressionSchaechte(int type, Object value){
        return visiblyColumnSchaechte[type].likeIgnoreCaseExp(value);
    }
    
    
    public static final String ID_PK_COLUMN = "ID";
    public static final Property<Boolean> GESCHLOSSEN = new Property<Boolean>("geschlossen");
    public static final Property<Integer> ID = new Property<Integer>("id","ID",2);
    public static final Property<Integer> LEITER_ID = new Property<Integer>("leiter_id");
    public static final Property<String> NAME = new Property<String>("name","Name",0);
    public static final Property<Integer> TIEF = new Property<Integer>("tief","Tief",1);
    public static final Property<List<Angestellte>> ANGESTELLTEZULASSUNG = new Property<List<Angestellte>>("angestellteZulassung");
    public static final Property<Angestellte> LEITERVONSCHAECHTE = new Property<Angestellte>("leiterVonSchaechte");
    public static final Property<List<Zeit>> ZEIT_ARRAY = new Property<List<Zeit>>("zeitArray");
    public static final Property[] visiblyColumnSchaechte = {Schaechte.NAME,
                                                           Schaechte.TIEF,
                                                           Schaechte.ID};
    
    public void setGeschlossen(Boolean geschlossen) {
        writeProperty(GESCHLOSSEN.getName(), geschlossen);
    }
    public Boolean getGeschlossen() {
        return (Boolean)readProperty(GESCHLOSSEN.getName());
    }

    public void setId(Integer id) {
        writeProperty(ID.getName(), id);
    }
    public Integer getId() {
        return (Integer)readProperty(ID.getName());
    }

    public void setLeiter_id(Integer leiter_id) {
        writeProperty(LEITER_ID.getName(), leiter_id);
    }
    public Integer getLeiter_id() {
        return (Integer)readProperty(LEITER_ID.getName());
    }

    public void setName(String name) {
        writeProperty(NAME.getName(), name);
    }
    public String getName() {
        return (String)readProperty(NAME.getName());
    }

    public void setTief(Integer tief) {
        writeProperty(TIEF.getName(), tief);
    }
    public Integer getTief() {
        return (Integer)readProperty(TIEF.getName());
    }

    public void addToAngestellteZulassung(Angestellte obj) {
        addToManyTarget(ANGESTELLTEZULASSUNG.getName(), obj, true);
    }
    public void removeFromAngestellteZulassung(Angestellte obj) {
        removeToManyTarget(ANGESTELLTEZULASSUNG.getName(), obj, true);
    }
    @SuppressWarnings("unchecked")
    public List<Angestellte> getAngestellteZulassung() {
        return (List<Angestellte>)readProperty(ANGESTELLTEZULASSUNG.getName());
    }


    public void setLeiterVonSchaechte(Angestellte toAngestellte) {
        setToOneTarget(LEITERVONSCHAECHTE.getName(), toAngestellte, true);
    }

    public Angestellte getLeiterVonSchaechte() {
        return (Angestellte)readProperty(LEITERVONSCHAECHTE.getName());
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
