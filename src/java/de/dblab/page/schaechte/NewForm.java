package de.dblab.page.schaechte;

import de.dblab.domain.Schaechte;
import de.dblab.service.DataBaseService;
import org.apache.click.ActionListener;
import org.apache.click.Control;
import org.apache.click.control.FieldSet;
import org.apache.click.control.Form;
import org.apache.click.control.Submit;
import org.apache.click.control.TextField;

/**
 *
 * @author anuta
 */
public class NewForm extends Form implements ActionListener{
    private final Submit newSubmit = new Submit("new");
    private final DataBaseService dataBaseService = new DataBaseService();
    
    public NewForm(){
        super("newform");
        FieldSet paymentFieldSet = new FieldSet("Neuer Angestellter");
        paymentFieldSet.setStyle("background-color", "#f4f4f4");
        paymentFieldSet.add(new TextField("name",true));
        paymentFieldSet.add(new TextField("tief"));
        paymentFieldSet.add(new TextField("leiter_id"));
        paymentFieldSet.add(newSubmit);
        newSubmit.setActionListener(this);
        //.setAttribute("onclick", "newAng();");
        this.add(paymentFieldSet);        
    }
    
    public boolean onAction(Control source) {
        if (this.isValid()){
            Schaechte schaechte = new Schaechte();
            this.copyTo(schaechte);
            schaechte.setGeschlossen(false);
            dataBaseService.saveObject(schaechte);
            this.clearValues();
        } 
        return true;
    }
    
}
