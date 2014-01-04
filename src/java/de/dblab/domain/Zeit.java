/** 
 * Hochschule Offenburg, Dezember 2013
 * Databanken Labor 3, Gruppe 13
 * @author Nikolaev Andrey & Ostrovskaya Anna
 */

package de.dblab.domain;

import java.util.Date;
import org.apache.cayenne.CayenneDataObject;
import org.apache.cayenne.exp.Property;

/* 
 * Class Zeit beschreibt Tabelle Zeit von Datenbank.
 */

public class Zeit extends CayenneDataObject {

    public static final String ID_PK_COLUMN = "ID";

    public static final Property<Date> ZEIT_AUSGANG = new Property<Date>("zeitAusgang");
    public static final Property<Date> ZEIT_EINGANG = new Property<Date>("zeitEingang");
    public static final Property<Angestellte> TO_ANGESTELLTE = new Property<Angestellte>("toAngestellte");
    public static final Property<Schaechte> TO_SCHAECHTE = new Property<Schaechte>("toSchaechte");
    public static final Property<String> ARBEITSZEIT = new Property<String>("arbeitsZeit");
    
    public String getArbeitsZeit() {
        long diff;
        if (getZeitAusgang() == null){
            diff = System.currentTimeMillis()-this.getZeitEingang().getTime();
        }else{
            diff = getZeitAusgang().getTime()-this.getZeitEingang().getTime();
        }
        if (diff < 0) {
            return "ERROR";
        } else {
            long h = diff / (1000*60*60);
            long m = (diff / (1000*60)) - (h*60);
            long s = (diff / (1000)) - (h*60*60) - (m*60);
            return ((h<10) ? ("0" + String.valueOf(h)) : String.valueOf(h)) + 
                ":" + ((m<10) ? ("0" + String.valueOf(m)) : String.valueOf(m)) +
                ":" + ((s<10) ? ("0" + String.valueOf(s)) : String.valueOf(s));        
        }
    }
        
    public void setZeitAusgang(Date zeitAusgang) {
        writeProperty("zeitAusgang", zeitAusgang);
    }
    public Date getZeitAusgang() {
        return (Date)readProperty("zeitAusgang");
    }

    public void setZeitEingang(Date zeitEingang) {
        writeProperty("zeitEingang", zeitEingang);
    }
    public Date getZeitEingang() {
        return (Date)readProperty("zeitEingang");
    }

    public void setToAngestellte(Angestellte toAngestellte) {
        setToOneTarget("toAngestellte", toAngestellte, true);
    }

    public Angestellte getToAngestellte() {
        return (Angestellte)readProperty("toAngestellte");
    }


    public void setToSchaechte(Schaechte toSchaechte) {
        setToOneTarget("toSchaechte", toSchaechte, true);
    }

    public Schaechte getToSchaechte() {
        return (Schaechte)readProperty("toSchaechte");
    }


}