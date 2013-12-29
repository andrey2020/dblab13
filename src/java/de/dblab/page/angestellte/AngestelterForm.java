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
import de.dblab.service.DataBaseService;
import java.util.ArrayList;
import java.util.List;
import net.sf.click.extras.control.CalendarField;
import org.apache.click.ActionResult;
import org.apache.click.Control;
import org.apache.click.ajax.DefaultAjaxBehavior;
import org.apache.click.control.AbstractLink;
import org.apache.click.control.ActionLink;
import org.apache.click.control.Button;
import org.apache.click.control.Checkbox;
import org.apache.click.control.Column;
import org.apache.click.control.FieldSet;
import org.apache.click.control.Form;
import org.apache.click.control.Table;
import org.apache.click.control.TextField;
import org.apache.click.dataprovider.DataProvider;
import org.apache.click.extras.control.FieldColumn;
import org.apache.click.extras.control.LinkDecorator;
import org.apache.click.extras.control.TableInlinePaginator;

/**
 *
 * @author andrey
 */
public final class AngestelterForm extends Form{
    private final Button speichernButton = new Button("Speichern"); 
    private final Button deleteButton = new Button("Entfernen"); 
    private final FieldSet fieldSet = new FieldSet("Detail des Angestellter");
    private final Table tableSchaechteZulassung = new Table("tableSchaechteZulassung");
    private final Table tableVerboteneSchaechte = new Table("tableVerboteneSchaechte");
    private final Table tableZeit = new Table("Zeit");
    private final Form componentform = new Form();
    private final ActionLink removeFromAngestellterZulassungLink = new ActionLink("remove", "Remove");
    private final ActionLink addToAngestellterZulassungLink = new ActionLink("add", "Add");
    private final DataBaseService dataBaseService = AngestelltePage.dataBaseService;// new DataBaseService();
    private final Form topForm = new Form();
    private final Form downForm = new Form();
    private FieldColumn column;

    CalendarField  geburtsDatumField = new CalendarField ("geburtsdatum", true);       
    public AngestelterForm(){
       
       this.setJavaScriptValidation(true); 
       this.add(fieldSet);
       fieldSet.setTextAlign("center");
       //this.setLsetLabelAlign("left");
       this.setButtonAlign("right");
       this.setLabelAlign("center");
       
       //deleteButton.setAttribute("border-color", "red");
       initFormComponent();
       initTableZeit();
       initTableSchaechteZulassung();
       initTableVerboteneSchaechte();
       this.setColumns(1);
       fieldSet.setStyle("border-color", "#a80402");
       topForm.setColumns(2);
       downForm.setColumns(2);
       fieldSet.add(topForm);
       fieldSet.add(downForm);
       this.remove(speichernButton);
       
    }
  
    public Table getZulassungTable(){
        return tableSchaechteZulassung;
    }
    
    public Form getForm(){
        return this;
    }
    
    private void initFormComponent(){

        FieldSet subFieldSet = new FieldSet("Angestellter");
        componentform.setAttribute("background-color", "#f4f4f4");
        componentform.add(subFieldSet);
        subFieldSet.setStyle("background-color", "#f4f4f4");
        componentform.setButtonAlign("right");
        componentform.setJavaScriptValidation(true); 
        
        topForm.add(componentform);
        
        TextField idField = new TextField("id");
        idField.setDisabled(true);
        TextField nameField = new TextField("name", true);
        nameField.setWidth("250px");
        TextField nachnameField = new TextField("nachname", true);
        nachnameField.setWidth("250px");
        
        TextField stelleField = new TextField("stelle", true);
        TextField gehaltField = new TextField("gehalt", true);
        Checkbox istEntlassen = new Checkbox("entlassene");
        geburtsDatumField.setStyle("brown");
        subFieldSet.add(idField);
        subFieldSet.add(nameField);
        subFieldSet.add(nachnameField);
        subFieldSet.add(geburtsDatumField);
        subFieldSet.add(stelleField);
        subFieldSet.add(gehaltField);
        subFieldSet.add(istEntlassen);
        subFieldSet.add(speichernButton);
        subFieldSet.add(deleteButton);
        deleteButton.setParent(componentform);
        componentform.setColumns(1);
        
    }
    
    private void initTableZeit(){
        tableZeit.getControlLink().addBehavior(new DefaultAjaxBehavior() {
            @Override
            public ActionResult onAction(Control source) {
               // tableZeit.saveState(getContext());
               // tableZeit.onProcess();
               // Form thisForm = (Form) source.getParent();
               // thisForm.copyFrom(dataBaseService.getAngestellteForID(angId));
                tableZeit.onProcess();
                getForm().copyFrom(dataBaseService.getAngestellteForID(angId));
                return new ActionResult(getForm().toString(), ActionResult.HTML);
        }});
        tableZeit.setCaption("Zeit Tabelle");
       
        topForm.add(tableZeit);
        
        tableZeit.setPaginator(new TableInlinePaginator(tableZeit));
        tableZeit.setPaginatorAttachment(Table.PAGINATOR_INLINE);
        
        //tableZeit.getPaginator()
        

        tableZeit.setClass(Table.CLASS_REPORT);
        tableZeit.addColumn(new Column("toSchaechte.id","ID"));
        tableZeit.addColumn(new Column("toSchaechte.name","Schacht Name"));
        Column eingangColumn=new Column("zeitEingang","Eingang");
        eingangColumn.setFormat("{0,date,dd.MM.yyyy - HH:mm:ss}");
        tableZeit.addColumn(eingangColumn);
        Column ausgangColumn=new Column("zeitAusgang","Ausgang");
        ausgangColumn.setFormat("{0,date,dd.MM.yyyy - HH:mm:ss}");
        //ausgangColumn.setWidth("210px");
        //eingangColumn.setWidth("210px");
        tableZeit.addColumn(ausgangColumn);
        tableZeit.setSortedColumn("zeitEingang");
        tableZeit.setSortedColumn("zeitAusgang");
        tableZeit.setShowBanner(true);
        tableZeit.setPageSize(4);

        tableZeit.setWidth("555px");
        
                

        
        tableZeit.setDataProvider(new DataProvider<Zeit>() {
            @Override
            public List<Zeit> getData() {
                //dataBaseService.getZeitFromAngestellte(AngestelltePage.angId);
                return dataBaseService.getAngestellteForID(AngestelltePage.angId).getZeitArray();
            }
        });
        tableZeit.setSortable(true);
        
    }
    
    private void initTableVerboteneSchaechte(){
        tableVerboteneSchaechte.getControlLink().addBehavior(new DefaultAjaxBehavior() {
            @Override
            public ActionResult onAction(Control source) {

                return new ActionResult(source.getParent().toString(), ActionResult.HTML);
        }}); 
           
       
       downForm.add(tableVerboteneSchaechte);
       
       tableVerboteneSchaechte.setCaption("Verbotene Schachte");
       tableVerboteneSchaechte.setClass(Table.CLASS_REPORT);
       
       addToAngestellterZulassungLink.setParent(this);
       addToAngestellterZulassungLink.addBehavior(new DefaultAjaxBehavior() {
            @Override
            public ActionResult onAction(Control source) {
                int id=addToAngestellterZulassungLink.getValueInteger();
                //dataBaseService.addZullassung(AngestelltePage.angId,id);
                 dataBaseService.getAngestellteForID(AngestelltePage.angId).addToSchaechteZulassung(
                                dataBaseService.getSchaechteForID(id));
                Form thisForm = (Form) source.getParent();
                thisForm.copyFrom(dataBaseService.getAngestellteForID(angId));
                return new ActionResult(getForm().toString(), ActionResult.HTML);
            }
        });
        

        
        
       tableVerboteneSchaechte.setDataProvider(new DataProvider<Schaechte>() {
            @Override
            public List<Schaechte> getData() {
                return dataBaseService.getVerboteneSchaechte(AngestelltePage.angId);
            }
        });
        
                
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
        
        addToAngestellterZulassungLink.setAttribute("name", "SchaechteZulassung");
        tableVerboteneSchaechte.setName("tableSchaechteZulassung");
       
        tableVerboteneSchaechte.addColumn(new Column("id","Id"));
        Column nameColumn = new Column("name","Name");
        nameColumn.setWidth("400px");
        tableVerboteneSchaechte.addColumn(nameColumn);
        tableVerboteneSchaechte.addColumn(new Column("tief","Tief"));
        Checkbox checkbox = new Checkbox();
        checkbox.setDisabled(true);
        column = new FieldColumn("geschlossen","Inaktiv", checkbox);
        column.setTextAlign("center");
        column.setWidth("50px");
        tableVerboteneSchaechte.addColumn(column);
        
        tableVerboteneSchaechte.setSortable(true);

        tableVerboteneSchaechte.setWidth("555px");
    }
    
    private void initTableSchaechteZulassung() {
           
     
       downForm.add(tableSchaechteZulassung); 
       //subFieldSet.setStyle("background-color", "#f4f4f4");
       tableSchaechteZulassung.setClass(Table.CLASS_REPORT);
       tableSchaechteZulassung.setWidth("410px");
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
       tableSchaechteZulassung.setCaption("Zugelassene Schachte");
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
        tableSchaechteZulassung.addColumn(new Column("id","Id"));
        tableSchaechteZulassung.addColumn(new Column("name","Name"));
        tableSchaechteZulassung.addColumn(new Column("tief","Tief"));
        Checkbox checkbox = new Checkbox();
        checkbox.setDisabled(true);
        column = new FieldColumn("geschlossen","Inaktiv", checkbox);
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
    
    public Table getTableZeit(){
        return tableZeit;
    }
    

    
}
