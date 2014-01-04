/** 
 * Hochschule Offenburg, Dezember 2013
 * Databanken Labor 3, Gruppe 13
 * @author Nikolaev Andrey & Ostrovskaya Anna
 */

package de.dblab.page.angestellte;

import de.dblab.domain.Angestellte;
import org.apache.click.control.FieldSet;
import org.apache.click.control.Form;
import org.apache.click.control.Select;
import org.apache.click.control.Submit;
import org.apache.click.control.TextField;

/* 
 * Class AngestellteSearchForm generiert HTML code auf der Seite AngestelltePage.htm
 */

public class AngestellteSearchForm extends Form{
    
    protected final TextField searchField = new TextField("Suche","Was?");    
    protected final Select sizeSelect = new Select("pageSize","Seitengröße");
    protected final Select typeSelect = new Select("Typ","Wo?");
    protected final Select firedSelect = new Select("zeigenEntlassente","");

    public AngestellteSearchForm(){
        super("form");
        Submit submit = new Submit("suchen");
        FieldSet searchFieldSet = new FieldSet("Suchen");
        searchFieldSet.setStyle("background-color", "#f4f4f4");
        this.add(searchFieldSet);
        
        searchFieldSet.add(searchField);
        searchFieldSet.add(typeSelect);
        searchFieldSet.add(firedSelect);
        searchFieldSet.add(sizeSelect);
        searchFieldSet.add(submit);
        this.setColumns(3);
        
        searchField.setFocus(true);
        firedSelect.addAll(Angestellte.stateAngestellteArray);
        typeSelect.addAll(Angestellte.getColumnAngestellte());
        sizeSelect.addAll(new String[] {"10", "15", "30", "50", "100", "150", "200"});
        sizeSelect.setAttribute("onchange", "form.submit();");
        typeSelect.setAttribute("onchange", "form.submit();");
        firedSelect.setAttribute("onchange", "form.submit();");
        submit.setAttribute("onclick", "form.submit();");        
        this.restoreState(getContext());
    }


}
