/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package de.dblab.page.angestellte;


import org.apache.click.control.FieldSet;
import org.apache.click.control.Form;
import org.apache.click.control.HiddenField;
import org.apache.click.control.Submit;
import org.apache.click.control.TextField;
import org.apache.click.util.Bindable;
import de.dblab.domain.Angestellte;
import de.dblab.domain.Schaechte;
import de.dblab.domain.Zeit;
import de.dblab.page.HomePage;
import de.dblab.page.TemplatePage;
import de.dblab.service.DataBaseService;
import java.util.List;
import org.apache.click.control.AbstractLink;
import org.apache.click.control.Column;
import org.apache.click.control.Table;


/**
 *
 * @author andrey
 */
public class ViewAngestellte extends TemplatePage{
    private static final long serialVersionUID = 1L;

    private final Form form = new Form("form");
    private final HiddenField referrerField = new HiddenField("referrer", String.class);
    private final HiddenField idField = new HiddenField("id", Integer.class);

    // Bindable variables can automatically have their value set by request parameters
    @Bindable protected Integer id;
    @Bindable protected String referrer;
    List<Schaechte> temp; 
    List<Zeit> temp2; 
    private final DataBaseService dataBaseService = new DataBaseService();
    private final Table table = new Table("AngestellteZulassungSchaechte");
    private final Table table2 = new Table("Zeit");
    Angestellte angestellte;
    Zeit zeit;
        // Constructor -----------------------------------------------------------
    private AbstractLink viewLink;
    private AbstractLink editLink;

    public ViewAngestellte() {
        addControl(form);

        form.add(referrerField);

        form.add(idField);

        FieldSet fieldSet = new FieldSet("angestellter");
        form.add(fieldSet);

        TextField nameField = new TextField("name", true);
        
        TextField nachnameField = new TextField("nachname", true);
        nameField.setMinLength(5);
        nameField.setFocus(true);
        fieldSet.add(nameField);
        fieldSet.add(nachnameField);

        


        form.add(new Submit("ok", "  OK  ", this, "onOkClick"));
        form.add(new Submit("cancel", this, "onCancelClick"));
        form.add(table);
        
    }
    
     public void initTable(){
        //addControl(table);
        table.setClass(Table.CLASS_ITS);
        table.addColumn(new Column("id","Id"));
        table.addColumn(new Column("name","Name"));
        table.addColumn(new Column("tief","Tief"));
        table.addColumn(new Column("geschlossen","Ist geschlossen"));
        //table.addColumn(new Column("id"));
        table.setRowList(temp);
        
        table2.setClass(Table.CLASS_ITS);
        table2.addColumn(new Column("id","Id"));
        table2.addColumn(new Column("name","Name"));
        table2.addColumn(new Column("tief","Tief"));
        table2.addColumn(new Column("geschlossen","Ist geschlossen"));
        table2.setRowList(temp);

       // table.restoreState(getContext());
    }
    
        /**
     * When page is first displayed on the GET request.
     *
     * @see Page#onGet()
     */
    @Override
    public void onGet() {
        if (id != null) {
            angestellte = dataBaseService.getAngestellteForID(id);
            
            //AngestellteZulassungSchaechte.TO_SCHAECHTE
            if (angestellte != null) {
                // Copy angestellte data to form. The idField value will be set by
                // this call
                temp = angestellte.getSchaechteZulassung();
                
                initTable();
                
                form.copyFrom(angestellte);
            }
           // zeit=dataBaseService.getZeitForID(id);
            
            
        }

        
        if (referrer != null) {
            // Set HiddenField to bound referrer field
            referrerField.setValue(referrer);
        }
    }

    public boolean onOkClick() {
        if (form.isValid()) {
            Integer id = (Integer) idField.getValueObject();
            angestellte  = dataBaseService.getAngestellteForID(id);

            if (angestellte == null) {
                angestellte = new Angestellte();
            }
            form.copyTo(angestellte);

            dataBaseService.saveAngestellte(angestellte);

            String referrer = referrerField.getValue();
            if (referrer != null) {
                setRedirect(referrer);
            } else {
                setRedirect(HomePage.class);
            }

            return true;

        } else {
            return true;
        }
    }

    public boolean onCancelClick() {
        String referrer1 = referrerField.getValue();
        if (referrer1 != null) {
            setRedirect(referrer1);
        } else {
            setRedirect(HomePage.class);
        }
        return true;
    }

}
