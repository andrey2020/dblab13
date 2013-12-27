/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.dblab.page.angestellte;

import de.dblab.domain.Angestellte;
import org.apache.click.control.FieldSet;
import org.apache.click.control.Form;
import org.apache.click.control.Select;
import org.apache.click.control.Submit;
import org.apache.click.control.TextField;

/**
 *
 * @author anuta
 */
public class SearchForm extends Form{
    public final TextField searchField = new TextField("Suche","Was?");    
    public final Select sizeSelect = new Select("pageSize","Seitengröße");
    public final Select typeSelect = new Select("Typ","Wo?");
    public final Select firedSelect = new Select("zeigenEntlassente","");

    public SearchForm(){
        super("form");
        Submit submit = new Submit("suchen");
    
        FieldSet paymentFieldSet = new FieldSet("Suchen");
        paymentFieldSet.setStyle("background-color", "#f4f4f4");
        this.add(paymentFieldSet);
        
        paymentFieldSet.add(searchField);
        searchField.setTabIndex(1);
        searchField.setFocus(true);
        paymentFieldSet.add(typeSelect);
        paymentFieldSet.add(submit);
        paymentFieldSet.add(firedSelect);
        paymentFieldSet.add(sizeSelect);
        
        this.setColumns(3);
        
        firedSelect.addAll(Angestellte.stateAngestellteArray);
        typeSelect.addAll(Angestellte.getColumnAngestellte());
        sizeSelect.addAll(new String[] {"5", "10", "15", "20", "30", "40", "50", "100", "150", "200"});
        sizeSelect.setAttribute("onchange", "form.submit();");
        typeSelect.setAttribute("onchange", "form.submit();");
        firedSelect.setAttribute("onchange", "form.submit();");
        //searchField.setActionListener(this);
        submit.setAttribute("onclick", "form.submit();");
        
        this.restoreState(getContext());
    }


}
