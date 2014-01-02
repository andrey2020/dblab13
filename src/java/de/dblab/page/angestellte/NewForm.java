/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.dblab.page.angestellte;

import de.dblab.domain.Angestellte;
import de.dblab.page.DataBaseService;
import java.util.Date;
import org.apache.click.ActionListener;
import org.apache.click.Control;
import org.apache.click.control.FieldSet;
import org.apache.click.control.Form;
import org.apache.click.control.Submit;
import org.apache.click.control.TextField;
import net.sf.click.extras.control.CalendarField; 

/**
 *
 * @author anuta
 */
public class NewForm extends Form implements ActionListener{
    private final CalendarField fieldGeburtsdatum = new CalendarField("gebutrtsdatum",true);
    private final Submit newSubmit = new Submit("new");
    private final DataBaseService dataBaseService = new DataBaseService();
    
    public NewForm(){
        super("newform");
        fieldGeburtsdatum.setDate(new Date(System.currentTimeMillis()));
        fieldGeburtsdatum.setStyle("brown");
        FieldSet paymentFieldSet = new FieldSet("Neuer Angestellter");
        paymentFieldSet.setStyle("background-color", "#f4f4f4");
        paymentFieldSet.add(new TextField("name",true));
        paymentFieldSet.add(new TextField("nachname",true));
        paymentFieldSet.add(fieldGeburtsdatum);
        paymentFieldSet.add(new TextField("stelle"));
        paymentFieldSet.add(new TextField("gehalt"));
        paymentFieldSet.add(newSubmit);
        newSubmit.setActionListener(this);
        //.setAttribute("onclick", "newAng();");
        this.add(paymentFieldSet);        
    }
    
    public boolean onAction(Control source) {
        if (this.isValid()){
            Angestellte angestellte = new Angestellte();
            this.copyTo(angestellte);
            angestellte.setGeburtsdatum(fieldGeburtsdatum.getDate());
            angestellte.setEntlassene(false);
            dataBaseService.saveObject(angestellte);
            this.clearValues();
        } 
        return true;
    }
    
}
