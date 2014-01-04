/** 
 * Hochschule Offenburg, Dezember 2013
 * Databanken Labor 3, Gruppe 13
 * @author Nikolaev Andrey & Ostrovskaya Anna
 */

package de.dblab.page.schaechte;

import de.dblab.domain.Schaechte;
import de.dblab.page.HomePage;
import org.apache.click.ActionListener;
import org.apache.click.Control;
import org.apache.click.control.FieldSet;
import org.apache.click.control.Form;
import org.apache.click.control.Select;
import org.apache.click.control.Submit;
import org.apache.click.control.TextField;
import org.apache.click.extras.control.IntegerField;

/* 
 * Class SchaechteNewForm generiert HTML code auf der Seite SchaechtePage.htm
 */

public class SchaechteNewForm extends Form implements ActionListener{
    public SchaechteNewForm(){
        super("newform");
        TextField nameField = new TextField("name",true);
        IntegerField tiefField = new IntegerField("tief");
        Select leiterId = new Select("leiter_id","Leiter");
        Submit newSubmit = new Submit("create");
        
        nameField.setMaxLength(45);
        tiefField.setMaxLength(10);
        leiterId.addAll(HomePage.dataBaseService.getAngestellteName());
        newSubmit.setActionListener(this);
        
        FieldSet createFieldSet = new FieldSet("Neuer Angestellter");
        createFieldSet.setStyle("background-color", "#f4f4f4");
        createFieldSet.add(nameField);
        createFieldSet.add(tiefField);
        createFieldSet.add(leiterId);
        createFieldSet.add(newSubmit);
        this.add(createFieldSet);        
    }
    
    public boolean onAction(Control source) {
        if (this.isValid()){
            Schaechte schaechte = new Schaechte();
            this.copyTo(schaechte);
            schaechte.setGeschlossen(false);
            HomePage.dataBaseService.saveObject(schaechte);
            this.clearValues();
        } 
        return true;
    }
    
}
