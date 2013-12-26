package de.dblab.domain;

import java.util.Date;

import org.apache.cayenne.CayenneDataObject;
import org.apache.cayenne.exp.Property;

public class Zeit extends CayenneDataObject  {

    public static final String ID_PK_COLUMN = "id";

    public static final Property<Date> ZEIT_AUSGANG = new Property<Date>("zeitAusgang");
    public static final Property<Date> ZEIT_EINGANG = new Property<Date>("zeitEingang");
    public static final Property<Angestellte> TO_ANGESTELLTE = new Property<Angestellte>("toAngestellte");
    public static final Property<Schaechte> TO_SCHAECHTE = new Property<Schaechte>("toSchaechte");

    public void setZeitAusgang(Date zeitAusgang) {
        writeProperty(ZEIT_AUSGANG.getName(), zeitAusgang);
    }
    public Date getZeitAusgang() {
        return (Date)readProperty(ZEIT_AUSGANG.getName());
    }

    public void setZeitEingang(Date zeitEingang) {
        writeProperty(ZEIT_EINGANG.getName(), zeitEingang);
    }
    public Date getZeitEingang() {
        return (Date)readProperty(ZEIT_EINGANG.getName());
    }

    public void setToAngestellte(Angestellte toAngestellte) {
        setToOneTarget(TO_ANGESTELLTE.getName(), toAngestellte, true);
    }

    public Angestellte getToAngestellte() {
        return (Angestellte)readProperty(TO_ANGESTELLTE.getName());
    }


    public void setToSchaechte(Schaechte toSchaechte) {
        setToOneTarget(TO_SCHAECHTE.getName(), toSchaechte, true);
    }

    public Schaechte getToSchaechte() {
        return (Schaechte)readProperty(TO_SCHAECHTE.getName());
    }


}
