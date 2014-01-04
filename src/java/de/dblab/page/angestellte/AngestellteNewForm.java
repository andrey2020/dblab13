/** 
 * Hochschule Offenburg, Dezember 2013
 * Databanken Labor 3, Gruppe 13
 * @author Nikolaev Andrey & Ostrovskaya Anna
 */

package de.dblab.page.angestellte;

import de.dblab.domain.Angestellte;
import de.dblab.page.HomePage;
import java.util.Date;
import org.apache.click.ActionListener;
import org.apache.click.Control;
import org.apache.click.control.FieldSet;
import org.apache.click.control.Form;
import org.apache.click.control.Submit;
import org.apache.click.control.TextField;
import net.sf.click.extras.control.CalendarField; 
import org.apache.click.extras.control.IntegerField;

/* 
 * Class AngestellteNewForm generiert HTML code auf der Seite AngestelltePage.htm
 */

public class AngestellteNewForm extends Form implements ActionListener{
    private final CalendarField fieldGeburtsdatum = new CalendarField("gebutrtsdatum",true);
    
    public AngestellteNewForm(){
        super("newform");
        TextField nameField = new TextField("name",true);
        TextField nachnameField = new TextField("nachname",true);
        TextField stelleField = new TextField("stelle");
        IntegerField gehaltField = new IntegerField("gehalt");
        Submit newSubmit = new Submit("create");
        
        nameField.setMaxLength(45);
        nachnameField.setMaxLength(45);
        stelleField.setMaxLength(45);
        gehaltField.setMaxLength(10);
        fieldGeburtsdatum.setDate(new Date(System.currentTimeMillis()));
        fieldGeburtsdatum.setStyle("brown");
        newSubmit.setActionListener(this);
        
        FieldSet createFieldSet = new FieldSet("Neuer Angestellter");
        createFieldSet.setStyle("background-color", "#f4f4f4");
        createFieldSet.add(nameField);
        createFieldSet.add(nachnameField);
        createFieldSet.add(fieldGeburtsdatum);
        createFieldSet.add(stelleField);
        createFieldSet.add(gehaltField);
        createFieldSet.add(newSubmit);
        this.add(createFieldSet);        
    }
    
    public boolean onAction(Control source) {
        if (this.isValid()){
            Angestellte angestellte = new Angestellte();
            this.copyTo(angestellte);
            angestellte.setGeburtsdatum(fieldGeburtsdatum.getDate());
            angestellte.setEntlassene(false);
            HomePage.dataBaseService.saveObject(angestellte);
            this.clearValues();
        } 
        return true;
    }
    
}
