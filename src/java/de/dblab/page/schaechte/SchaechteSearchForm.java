/** 
 * Hochschule Offenburg, Dezember 2013
 * Databanken Labor 3, Gruppe 13
 * @author Nikolaev Andrey & Ostrovskaya Anna
 */

package de.dblab.page.schaechte;

import de.dblab.domain.Schaechte;
import org.apache.click.control.FieldSet;
import org.apache.click.control.Form;
import org.apache.click.control.Select;
import org.apache.click.control.Submit;
import org.apache.click.control.TextField;

/* 
 * Class SchaechteSearchForm generiert HTML code auf der Seite SchaechtePage.htm
 */

public class SchaechteSearchForm  extends Form{
    
    protected final TextField searchField = new TextField("Suche","Was?");    
    protected final Select sizeSelect = new Select("pageSize","Seitengröße");
    protected final Select typeSelect = new Select("Typ","Wo?");
    protected final Select inaktivSelect = new Select("zeigenInaktiv","");

    public SchaechteSearchForm(){
        super("form");
        Submit submit = new Submit("suchen");
        FieldSet searchFieldSet = new FieldSet("Suchen");
        searchFieldSet.setStyle("background-color", "#f4f4f4");
        this.add(searchFieldSet);
        
        searchFieldSet.add(searchField);
        searchFieldSet.add(typeSelect);
        searchFieldSet.add(inaktivSelect);
        searchFieldSet.add(submit);
        searchFieldSet.add(sizeSelect);
        this.setColumns(3);
        
        searchField.setFocus(true);
        inaktivSelect.addAll(Schaechte.stateSchaechteArray);
        typeSelect.addAll(Schaechte.getColumnSchaechte());
        sizeSelect.addAll(new String[] {"10", "15", "30", "50", "100", "150", "200"});
        sizeSelect.setAttribute("onchange", "form.submit();");
        typeSelect.setAttribute("onchange", "form.submit();");
        inaktivSelect.setAttribute("onchange", "form.submit();");
        submit.setAttribute("onclick", "form.submit();");
        this.restoreState(getContext());
    }
}

