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
import org.apache.click.control.ActionLink;
import org.apache.click.control.Checkbox;
import org.apache.click.control.Column;
import org.apache.click.control.PageLink;
import org.apache.click.control.Table;
import org.apache.click.extras.control.FieldColumn;
import org.apache.click.extras.control.LinkDecorator;


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
    private final DataBaseService dataBaseService = new DataBaseService();
    private final Table table = new Table("AngestellteZulassungSchaechte");
    private final Table table2 = new Table("Zeit");
    Angestellte angestellte;
        // Constructor -----------------------------------------------------------
    private AbstractLink viewLink;
    private AbstractLink editLink;
    private FieldColumn column;
    ActionLink removeLink = new ActionLink("Remove",this, "onRemoveClick");    
   
    

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
        form.add(table2);
        
    }
    
     public void initTable(){
        //addControl(table);
        table.setClass(Table.CLASS_ITS);
        table.addColumn(new Column("id","Id"));
        table.addColumn(new Column("name","Name"));
        table.addColumn(new Column("tief","Tief"));
        Checkbox checkbox = new Checkbox();
        checkbox.setDisabled(true);
        column = new FieldColumn("geschlossen","Ist geschlossen", checkbox);
        column.setTextAlign("center");
        column.setWidth("50px");
        table.addColumn(column);
        
        addControl(removeLink);
        removeLink.setImageSrc("/images/delete.gif");
        removeLink.setTitle("Remove Schacht Zulassung");
        removeLink.setAttribute("onclick", "return window.confirm('Are you sure you want to delete this record?');");        
        AbstractLink[] links = new AbstractLink[] { removeLink };
        Column columnRemove=new Column("Action");
        columnRemove.setDecorator(new LinkDecorator(table, links, "id"));
        columnRemove.setTextAlign("center");
        columnRemove.setSortable(false);
        columnRemove.setWidth("auto");
        table.addColumn(columnRemove);
        
        
        
        table2.setClass(Table.CLASS_ITS);
        table2.addColumn(new Column("toSchaechte.id","Schacht ID"));
        table2.addColumn(new Column("toSchaechte.name","Schacht Name"));
        table2.addColumn(new Column("zeitEingang","Eingang"));
        table2.addColumn(new Column("zeitAusgang","Ausgang"));
        table2.setSortedColumn("zeitAusgang");

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
                
                initTable();
                table.setRowList(angestellte.getSchaechteZulassung());
                table2.setRowList(angestellte.getZeitArray());
                form.copyFrom(angestellte);
            }
            
            
            
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

    public boolean onRemoveClick(){
        int id=removeLink.getValueInteger();
        angestellte.removeFromSchaechteZulassung(dataBaseService.getSchaechteForID(id));
        
        
        return true;
    }
    
}
