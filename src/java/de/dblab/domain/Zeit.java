package de.dblab.domain;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.cayenne.CayenneDataObject;
import org.apache.cayenne.exp.Property;


/**
 * Class _Zeit was generated by Cayenne.
 * It is probably a good idea to avoid changing this class manually,
 * since it may be overwritten next time code is regenerated.
 * If you need to make any customizations, please use subclass.
 */
public class Zeit extends CayenneDataObject {

    //public static final String ID_PK_COLUMN = "ID";

    public static final Property<Date> ZEIT_AUSGANG = new Property<Date>("zeitAusgang");
    public static final Property<Date> ZEIT_EINGANG = new Property<Date>("zeitEingang");
    public static final Property<Angestellte> TO_ANGESTELLTE = new Property<Angestellte>("toAngestellte");
    public static final Property<Schaechte> TO_SCHAECHTE = new Property<Schaechte>("toSchaechte");
    public static final Property<String> ARBEITSZEIT = new Property<String>("arbeitsZeit");
    
    public String getArbeitsZeit() {
        String returnZeit;
        Date ausGang;
        if (getZeitAusgang() == null){
            ausGang = new Date(System.currentTimeMillis());
        }else{
           ausGang = getZeitAusgang();
           
        }
        
        Calendar calen = Calendar.getInstance();
        
        SimpleDateFormat dateJava = new SimpleDateFormat("hh:mm:ss");
        long diff = ausGang.getTime()-this.getZeitEingang().getTime();
        long h = diff / (1000*60*60);
        long m = (diff / (1000*60)) - (h*60);
        long s = (diff / (1000)) - (h*60*60) - (m*60);
        if (diff < 0) {
            returnZeit = "ERROR";
        } else {
          returnZeit = ((h<10) ? ("0" + String.valueOf(h)) : String.valueOf(h)) + 
                ":" + ((m<10) ? ("0" + String.valueOf(m)) : String.valueOf(m)) +
                ":" + ((s<10) ? ("0" + String.valueOf(s)) : String.valueOf(s));        
        }
        return returnZeit; 
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