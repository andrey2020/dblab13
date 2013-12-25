package de.dblab.page.schaechte;



import org.apache.click.control.Table;
import de.dblab.page.TemplatePage;
//import org.springframework.stereotype.Component;


public class SchaechtePage extends TemplatePage {

    private static final long serialVersionUID = 1L;

    private Table table = new Table("table");


    public SchaechtePage() {
    
        addControl(table);
    }
 
}
