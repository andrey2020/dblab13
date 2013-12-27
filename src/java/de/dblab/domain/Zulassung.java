package de.dblab.domain;


import org.apache.cayenne.CayenneDataObject;
import org.apache.cayenne.exp.Property;

public class Zulassung extends CayenneDataObject  {

    

    public static final Property<Integer> ANGESTELLTEID = new Property<Integer>("angestellteId");
    public static final Property<Integer> SCHAECHTEID = new Property<Integer>("schaechteId");


    public void setAngestellteId(Integer angestellteId) {
        writeProperty(ANGESTELLTEID.getName(), angestellteId);
    }
    public Integer getAngestellteId() {
        return (Integer)readProperty(ANGESTELLTEID.getName());
    }

    public void setSchaechteId(Integer schaechteId) {
        writeProperty(SCHAECHTEID.getName(), schaechteId);
    }
    public Integer getsetSchaechteId() {
        return (Integer)readProperty(SCHAECHTEID.getName());
    }



}
