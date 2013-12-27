/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package de.dblab.page.angestellte;

import de.dblab.domain.Schaechte;
import de.dblab.domain.Zeit;
import de.dblab.page.angestellte.AngestelltePage;
import static de.dblab.page.angestellte.AngestelltePage.angId;
import static de.dblab.page.angestellte.AngestelltePage.dataBaseService;
import de.dblab.service.DataBaseService;
import java.util.List;
import net.sf.click.extras.control.CalendarField;
import org.apache.click.ActionResult;
import org.apache.click.Control;
import org.apache.click.ajax.DefaultAjaxBehavior;
import org.apache.click.control.AbstractLink;
import org.apache.click.control.ActionLink;
import org.apache.click.control.Checkbox;
import org.apache.click.control.Column;
import org.apache.click.control.FieldSet;
import org.apache.click.control.Form;
import org.apache.click.control.Table;
import org.apache.click.control.TextField;
import org.apache.click.dataprovider.DataProvider;
import org.apache.click.extras.control.DateField;
import org.apache.click.extras.control.FieldColumn;
import org.apache.click.extras.control.LinkDecorator;

/**
 *
 * @author andrey
 */
public final class AngestelterForm extends Form{
   
    private final FieldSet fieldSet = new FieldSet("Detail des Angestellter");
    private final Table tableSchaechteZulassung = new Table("tableSchaechteZulassung");
    private final Table tableVerboteneSchaechte = new Table("tableVerboteneSchaechte");
    private final Table tableZeit = new Table("Zeit");
    private final ActionLink removeFromAngestellterZulassungLink = new ActionLink("remove", "Remove");
    private final ActionLink addToAngestellterZulassungLink = new ActionLink("add", "Add");
    private final DataBaseService dataBaseService = new DataBaseService();
    private FieldColumn column;
    
    public AngestelterForm(){
       
       fieldSet.setStyle("background-color", "#f4f4f4");
       this.add(fieldSet);
     
       initFormComponent();
       initTableZeit();
       initTableVerboteneSchaechte();
       initTableSchaechteZulassung();
    }
    
  
    public Table getZulassungTable(){
        return tableSchaechteZulassung;
    }
    
    private Form getForm(){
        return this;
    }
    
    private void initFormComponent(){
        
        TextField idField = new TextField("id");
        idField.setDisabled(true);
        TextField nameField = new TextField("name", true);
        TextField nachnameField = new TextField("nachname", true);
        CalendarField  gebutsDatumField = new CalendarField ("geburtsdatum", true);
        TextField stelleField = new TextField("stelle", true);
        TextField gehaltField = new TextField("gehalt", true);
        Checkbox istEntlassen = new Checkbox("entlassene");
        gebutsDatumField.setStyle("brown");
        fieldSet.add(idField);
        fieldSet.add(nameField);
        fieldSet.add(nachnameField);
        fieldSet.add(gebutsDatumField);
        fieldSet.add(stelleField);
        fieldSet.add(gehaltField);
        fieldSet.add(istEntlassen);
    }
    
    private void initTableZeit(){
        fieldSet.add(tableZeit);
        
        tableZeit.setClass(Table.CLASS_ITS);
        tableZeit.addColumn(new Column("toSchaechte.id","Schacht ID"));
        tableZeit.addColumn(new Column("toSchaechte.name","Schacht Name"));
        tableZeit.addColumn(new Column("zeitEingang","Eingang"));
        tableZeit.addColumn(new Column("zeitAusgang","Ausgang"));
        tableZeit.setSortedColumn("zeitAusgang");
        
        tableZeit.setDataProvider(new DataProvider<Zeit>() {
            @Override
            public List<Zeit> getData() {
                return dataBaseService.getAngestellteForID(AngestelltePage.angId).getZeitArray();
            }
        });
        
    }
    
    private void initTableVerboteneSchaechte(){
       fieldSet.add(tableVerboteneSchaechte);
       addToAngestellterZulassungLink.setParent(this);
       addToAngestellterZulassungLink.addBehavior(new DefaultAjaxBehavior() {
            @Override
            public ActionResult onAction(Control source) {
                int id=addToAngestellterZulassungLink.getValueInteger();
                dataBaseService.addZullassung(AngestelltePage.angId,id);
                Form thisForm = (Form) source.getParent();
                thisForm.copyFrom(dataBaseService.getAngestellteForID(angId));
                //AngestelltePage.detailForm.copyFrom(dataBaseService.getAngestellteForID(AngestelltePage.angId));
                return new ActionResult(getForm().toString(), ActionResult.HTML);
            }
        });
        
       tableVerboteneSchaechte.getControlLink().addBehavior(new DefaultAjaxBehavior() {
            @Override
            public ActionResult onAction(Control source) {

                return new ActionResult(source.getParent().toString(), ActionResult.HTML);
        }});
        
        
       tableVerboteneSchaechte.setDataProvider(new DataProvider<Schaechte>() {
            @Override
            public List<Schaechte> getData() {
                return dataBaseService.getVerboteneSchaechte(AngestelltePage.angId);
            }
        });
       
        addToAngestellterZulassungLink.setAttribute("name", "SchaechteZulassung");
        tableVerboteneSchaechte.setName("tableSchaechteZulassung");
        tableVerboteneSchaechte.setClass(Table.CLASS_ITS);
        tableVerboteneSchaechte.addColumn(new Column("id","Id"));
        tableVerboteneSchaechte.addColumn(new Column("name","Name"));
        tableVerboteneSchaechte.addColumn(new Column("tief","Tief"));
        Checkbox checkbox = new Checkbox();
        checkbox.setDisabled(true);
        column = new FieldColumn("geschlossen","Ist geschlossen", checkbox);
        column.setTextAlign("center");
        column.setWidth("50px");
        tableVerboteneSchaechte.addColumn(column);
        
        tableVerboteneSchaechte.setSortable(false);
        
        addToAngestellterZulassungLink.setImageSrc("/images/add.png");
        addToAngestellterZulassungLink.setTitle("Schacht Zulassung");
        // removeFromAngestellterZulassungLink.setAttribute("onclick", "return window.confirm('Are you sure you want to delete this record?');");        
        AbstractLink[] links = new AbstractLink[] { addToAngestellterZulassungLink };
        Column columnRemove=new Column("Action");
        columnRemove.setDecorator(new LinkDecorator(tableVerboteneSchaechte, links, "id"));
        columnRemove.setTextAlign("center");
        columnRemove.setSortable(false);
        columnRemove.setWidth("auto");
        tableVerboteneSchaechte.addColumn(columnRemove);        
    }
    
    private void initTableSchaechteZulassung() {
       fieldSet.add(tableSchaechteZulassung);
       removeFromAngestellterZulassungLink.setParent(this);
       removeFromAngestellterZulassungLink.addBehavior(new DefaultAjaxBehavior() {
            @Override
            public ActionResult onAction(Control source) {
                int id=removeFromAngestellterZulassungLink.getValueInteger();
                dataBaseService.getAngestellteForID(AngestelltePage.angId).removeFromSchaechteZulassung(dataBaseService.getSchaechteForID(id));
                dataBaseService.commitChange();
                Form thisForm = (Form) source.getParent();
                thisForm.copyFrom(dataBaseService.getAngestellteForID(angId));
                return new ActionResult(getForm().toString(), ActionResult.HTML);
            }
        });
       
       tableSchaechteZulassung.getControlLink().addBehavior(new DefaultAjaxBehavior() {
            @Override
            public ActionResult onAction(Control source) {

                return new ActionResult(source.getParent().toString(), ActionResult.HTML);
        }});
         
       
       tableSchaechteZulassung.setDataProvider(new DataProvider<Schaechte>() {
            @Override
            public List<Schaechte> getData() {
                return dataBaseService.getAngestellteForID(AngestelltePage.angId).getSchaechteZulassung();
            }
        });
       
        removeFromAngestellterZulassungLink.setAttribute("name", "SchaechteZulassung");
        tableSchaechteZulassung.setName("tableSchaechteZulassung");
        tableSchaechteZulassung.setClass(Table.CLASS_ITS);
        tableSchaechteZulassung.addColumn(new Column("id","Id"));
        tableSchaechteZulassung.addColumn(new Column("name","Name"));
        tableSchaechteZulassung.addColumn(new Column("tief","Tief"));
        Checkbox checkbox = new Checkbox();
        checkbox.setDisabled(true);
        column = new FieldColumn("geschlossen","Ist geschlossen", checkbox);
        column.setTextAlign("center");
        column.setWidth("50px");
        tableSchaechteZulassung.addColumn(column);
        
        tableSchaechteZulassung.setSortable(false);
        
        removeFromAngestellterZulassungLink.setImageSrc("/images/delete.png");
        removeFromAngestellterZulassungLink.setTitle("Remove Schacht Zulassung");
        // removeFromAngestellterZulassungLink.setAttribute("onclick", "return window.confirm('Are you sure you want to delete this record?');");        
        AbstractLink[] links = new AbstractLink[] { removeFromAngestellterZulassungLink };
        Column columnRemove=new Column("Action");
        columnRemove.setDecorator(new LinkDecorator(tableSchaechteZulassung, links, "id"));
        columnRemove.setTextAlign("center");
        columnRemove.setSortable(false);
        columnRemove.setWidth("auto");
        tableSchaechteZulassung.addColumn(columnRemove);
        
    }
    
}
