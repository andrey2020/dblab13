/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.dblab.page.schaechte;

import de.dblab.domain.Schaechte;
import org.apache.click.control.FieldSet;
import org.apache.click.control.Form;
import org.apache.click.control.Select;
import org.apache.click.control.Submit;
import org.apache.click.control.TextField;

/**
 *
 * @author anuta
 */
public class SchaechteSearchForm  extends Form{
  public final TextField searchField = new TextField("Suche","Was?");    
    public final Select sizeSelect = new Select("pageSize","Seitengröße");
    public final Select typeSelect = new Select("Typ","Wo?");
    public final Select inaktivSelect = new Select("zeigenInaktiv","");

    public SchaechteSearchForm(){
        super("form");
        Submit submit = new Submit("suchen");
    
        FieldSet paymentFieldSet = new FieldSet("Suchen");
        paymentFieldSet.setStyle("background-color", "#f4f4f4");
        this.add(paymentFieldSet);
        
        paymentFieldSet.add(searchField);
        searchField.setTabIndex(1);
        searchField.setFocus(true);
        paymentFieldSet.add(typeSelect);
        paymentFieldSet.add(inaktivSelect);
        paymentFieldSet.add(submit);
        paymentFieldSet.add(sizeSelect);
        this.setColumns(3);
        
        inaktivSelect.addAll(Schaechte.stateSchaechteArray);
        typeSelect.addAll(Schaechte.getColumnSchaechte());
        sizeSelect.addAll(new String[] {"5", "10", "15", "20", "50", "100"});
        sizeSelect.setAttribute("onchange", "form.submit();");
        typeSelect.setAttribute("onchange", "form.submit();");
        inaktivSelect.setAttribute("onchange", "form.submit();");
        //searchField.setActionListener(this);
        submit.setAttribute("onclick", "form.submit();");
        
        this.restoreState(getContext());
    }


}

