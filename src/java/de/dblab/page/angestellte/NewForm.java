/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.dblab.page.angestellte;

import de.dblab.domain.Angestellte;
import de.dblab.service.DataBaseService;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import org.apache.click.ActionListener;
import org.apache.click.Control;
import org.apache.click.control.FieldSet;
import org.apache.click.control.Form;
import org.apache.click.control.Submit;
import org.apache.click.control.Table;
import org.apache.click.control.TextField;

/**
 *
 * @author anuta
 */
public class NewForm extends Form implements ActionListener{
    private final TextField fieldGeburtsdatum = new TextField("gebutrtsdatum","Geburtsdatum (Year-M-D)",true);
    private final Submit newSubmit = new Submit("new");
    private final DataBaseService dataBaseService = new DataBaseService();
    Table t;
    
    public NewForm(Table table){
        super("newform");
        t=table;
        FieldSet paymentFieldSet = new FieldSet("Neuer Angestellter");
        paymentFieldSet.setStyle("background-color", "#f4f4f4");
        paymentFieldSet.add(new TextField("name","Name",true));
        paymentFieldSet.add(new TextField("nachname","Nachname",true));
        paymentFieldSet.add(fieldGeburtsdatum);
        paymentFieldSet.add(new TextField("stelle","Stelle"));
        paymentFieldSet.add(new TextField("gehalt","Gehalt"));
        paymentFieldSet.add(newSubmit);
        newSubmit.setActionListener(this);
        //.setAttribute("onclick", "newAng();");
        this.add(paymentFieldSet);
        
    }
    
    public boolean onAction(Control source) {
        if (this.isValid()){
            Angestellte angestellte = new Angestellte();
            this.copyTo(angestellte);
            SimpleDateFormat d= new  SimpleDateFormat("y-M-d");
            try {
                angestellte.setGeburtsdatum(d.parse(fieldGeburtsdatum.getValue()));
            } catch (ParseException ex) {}
            angestellte.setEntlassene(false);
            dataBaseService.saveAngestellte(angestellte);
            t.onRender();
            this.clearValues();
        } 
        return true;
    }
    
}
