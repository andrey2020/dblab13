/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.dblab.page.schaechte;

import de.dblab.domain.Angestellte;
import de.dblab.domain.Schaechte;
import de.dblab.domain.Zeit;
import de.dblab.page.HomePage;
import de.dblab.page.DataBaseService;
import de.dblab.page.angestellte.AngestelltePage;
import static de.dblab.page.angestellte.AngestelltePage.angId;
import java.util.List;
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
 * @author anuta
 */
public class SchaechteForm extends Form{
    
    private final Table tableZeit = new Table("nebenTable1");
    private final Table tableAngestellteImSchacht = new Table("nebenTable2");
    private final Table tableMoeglicheAngestellte = new Table("nebenTable3");
    
    private final ActionLink endeArbeit = new ActionLink("remove", "Remove");
    private final ActionLink beginArbeit = new ActionLink("add", "Add");
    
    private final Button closeButton = new Button("Schließen"); 
    private final FieldSet fieldSet = new FieldSet("Detail des Schaechter");
    
    private final Form componentform = new Form();
    private final DataBaseService dataBaseService = HomePage.dataBaseService;
    private final Form topForm = new Form();


    private final Form downForm = new Form();
    private FieldColumn column;
    
    public SchaechteForm(){
       
       this.setJavaScriptValidation(true); 
       this.add(fieldSet);
       fieldSet.setTextAlign("center");
       this.setButtonAlign("right");
       this.setLabelAlign("center");
       
       initFormComponent();
       initTableZeit();
       initTableAngestellteImSchacht();
       initTableMoeglicheAngestellte();
       
       this.setColumns(1);
       fieldSet.setStyle("border-color", "#a80402");
       topForm.setColumns(2);
       downForm.setColumns(2);
       fieldSet.add(topForm);
       fieldSet.add(downForm);
       
       
    }
  

    
    public Form getForm(){
        return this;
    }
    
    private void initFormComponent(){

        FieldSet subFieldSet = new FieldSet("Schaechter");
        //subFieldSet.setWidth("410px");
        componentform.setAttribute("background-color", "#f4f4f4");
        componentform.add(subFieldSet);
        subFieldSet.setStyle("background-color", "#f4f4f4");
        componentform.setButtonAlign("right");
        componentform.setJavaScriptValidation(true); 
        
        topForm.add(componentform);
        
        TextField idField = new TextField("id");
        idField.setDisabled(true);
        TextField nameField = new TextField("name");
        nameField.setDisabled(true);
        
        TextField tiefField = new TextField("tief");
        tiefField.setDisabled(true);
        TextField leiterName = new TextField("leiterVonSchaechte.name","Leiter");
        leiterName.setDisabled(true);
        TextField leiterNachname = new TextField("leiterVonSchaechte.nachname", "Leiter");
        leiterNachname.setDisabled(true);
        Checkbox istGeschlossen = new Checkbox("geschlossen");
        istGeschlossen.setDisabled(true);
        
        subFieldSet.add(idField);
        subFieldSet.add(nameField);
        subFieldSet.add(tiefField);
        subFieldSet.add(leiterName);
        subFieldSet.add(leiterNachname);
        subFieldSet.add(istGeschlossen);
        this.add(closeButton);
        closeButton.setOnClick("form.submit();");
        componentform.setColumns(1);
        
    }
    
    private void initTableZeit(){
        tableZeit.getControlLink().addBehavior(new DefaultAjaxBehavior() {
            @Override
            public ActionResult onAction(Control source) {
                tableZeit.onProcess();
                getForm().copyFrom(dataBaseService.getSchaechteForID(SchaechtePage.schachtId));
                return new ActionResult(getForm().toString(), ActionResult.HTML);
        }});
        
        tableZeit.setCaption("Zeit Tabelle");
       
        topForm.add(tableZeit);
        
        tableZeit.setPaginator(new TableInlinePaginator(tableZeit));
        tableZeit.setPaginatorAttachment(Table.PAGINATOR_INLINE);
        
        
        tableZeit.setClass(Table.CLASS_REPORT);
        tableZeit.addColumn(new Column("toAngestellte.id","ID"));
        tableZeit.addColumn(new Column("toAngestellte.name","Angestellte Name"));
        tableZeit.addColumn(new Column("toAngestellte.nachname","Angestellte Nachname"));
        Column eingangColumn=new Column("zeitEingang","Eingang");
        eingangColumn.setFormat("{0,date,dd.MM.yyyy - HH:mm:ss}");
        tableZeit.addColumn(eingangColumn);
        Column ausgangColumn=new Column("zeitAusgang","Ausgang");
        ausgangColumn.setFormat("{0,date,dd.MM.yyyy - HH:mm:ss}");
        ausgangColumn.setWidth("160px");
        eingangColumn.setWidth("160px");
        tableZeit.addColumn(ausgangColumn);
        tableZeit.setSortedColumn("zeitEingang");
        tableZeit.setSortedAscending(false);
        
        tableZeit.setShowBanner(true);
        tableZeit.setPageSize(5);
        
        tableZeit.setDataProvider(new DataProvider<Zeit>() {
            @Override
            public List<Zeit> getData() {
                //dataBaseService.getZeitFromSchaechte(SchaechtePage.angId);
                return dataBaseService.getSchaechteForID(SchaechtePage.schachtId).getZeitArray();
            }
        });
        tableZeit.setSortable(true);
        
    }
    
    private void initTableMoeglicheAngestellte(){
        tableMoeglicheAngestellte.getControlLink().addBehavior(new DefaultAjaxBehavior() {
            @Override
            public ActionResult onAction(Control source) {
                tableMoeglicheAngestellte.onProcess();
                getForm().copyFrom(dataBaseService.getSchaechteForID(SchaechtePage.schachtId));
                return new ActionResult(getForm().toString(), ActionResult.HTML);
        }}); 
           
       downForm.add(tableMoeglicheAngestellte);
       
       tableMoeglicheAngestellte.setCaption("Mögliche Angestellte");
       tableMoeglicheAngestellte.setClass(Table.CLASS_REPORT);
       
       beginArbeit.setParent(this);
       beginArbeit.addBehavior(new DefaultAjaxBehavior() {
            @Override
            public ActionResult onAction(Control source) {
                int id=beginArbeit.getValueInteger();
                //dataBaseService.addZullassung(AngestelltePage.angId,id);
                 dataBaseService.beginArbeit(beginArbeit.getValueInteger(),SchaechtePage.schachtId);
                         //getAngestellteForID(AngestelltePage.angId).addToSchaechteZulassung(
                                //dataBaseService.getSchaechteForID(id));
                getForm().copyFrom(dataBaseService.getSchaechteForID(SchaechtePage.schachtId));
                return new ActionResult(getForm().toString(), ActionResult.HTML);
            }
        });
        
       tableMoeglicheAngestellte.setDataProvider(new DataProvider<Angestellte>() {
            @Override
            public List<Angestellte> getData() {
                return dataBaseService.getAngestellteMoeglich(SchaechtePage.schachtId);
                        //getVerboteneSchaechte(AngestelltePage.angId);
            }
        });
        
                
        beginArbeit.setImageSrc("/images/add.png");
        beginArbeit.setTitle("Begin Arbeit");
        // removeFromAngestellterZulassungLink.setAttribute("onclick", "return window.confirm('Are you sure you want to delete this record?');");        
        AbstractLink[] links = new AbstractLink[] { beginArbeit };
        Column columnRemove=new Column("Action");
        columnRemove.setDecorator(new LinkDecorator(tableMoeglicheAngestellte, links, "id"));
        columnRemove.setTextAlign("center");
        columnRemove.setSortable(false);
        columnRemove.setWidth("auto");
        tableMoeglicheAngestellte.addColumn(columnRemove);
        
        beginArbeit.setAttribute("name", "linkAction");
       // tableMoeglicheAngestellte.setName("tableSchaechteZulassung");
       
        tableMoeglicheAngestellte.addColumn(new Column("id","Id"));
        Column nameColumn = new Column("name","Name");
        nameColumn.setWidth("400px");
        tableMoeglicheAngestellte.addColumn(nameColumn);
        tableMoeglicheAngestellte.addColumn(new Column("nachname","Nachname"));
                
        tableMoeglicheAngestellte.setSortable(true);

        tableMoeglicheAngestellte.setWidth("555px");
    }
    
    private void initTableAngestellteImSchacht() {
       downForm.add(tableAngestellteImSchacht); 
       //subFieldSet.setStyle("background-color", "#f4f4f4");
       tableAngestellteImSchacht.setClass(Table.CLASS_REPORT);
       tableAngestellteImSchacht.setWidth("440px");
       endeArbeit.setParent(this);
       endeArbeit.addBehavior(new DefaultAjaxBehavior() {
            @Override
            public ActionResult onAction(Control source) {
                int id=endeArbeit.getValueInteger();
                //dataBaseService.getAngestellteForID(AngestelltePage.angId).removeFromSchaechteZulassung(dataBaseService.getSchaechteForID(id));
                dataBaseService.endeArbeit(endeArbeit.getValueInteger());
                dataBaseService.commitChange();
                getForm().copyFrom(dataBaseService.getSchaechteForID(SchaechtePage.schachtId));
                return new ActionResult(getForm().toString(), ActionResult.HTML);
            }
        });
       tableAngestellteImSchacht.setCaption("Angestellte im Schacht");
       tableAngestellteImSchacht.getControlLink().addBehavior(new DefaultAjaxBehavior() {
            @Override
            public ActionResult onAction(Control source) {
                tableAngestellteImSchacht.onProcess();
                getForm().copyFrom(dataBaseService.getSchaechteForID(SchaechtePage.schachtId));
                return new ActionResult(getForm().toString(), ActionResult.HTML);
        }});
         
       
       tableAngestellteImSchacht.setDataProvider(new DataProvider<Zeit>() {
            @Override
            public List<Zeit> getData() {
                return dataBaseService.getAngestellteImSchacht(SchaechtePage.schachtId);
            }
        });
       
        endeArbeit.setAttribute("name", "linkAction");
       // tableAngestellteImSchacht.setName("tableSchaechteZulassung");
        tableAngestellteImSchacht.addColumn(new Column("toAngestellte.id","Id"));
        tableAngestellteImSchacht.addColumn(new Column("toAngestellte.name","Name"));
        tableAngestellteImSchacht.addColumn(new Column("toAngestellte.nachname","Nachname"));
        
        tableAngestellteImSchacht.setSortable(true);
        
        endeArbeit.setImageSrc("/images/delete.png");
        endeArbeit.setTitle("Ende der Arbeit");
        // removeFromAngestellterZulassungLink.setAttribute("onclick", "return window.confirm('Are you sure you want to delete this record?');");        
        AbstractLink[] links = new AbstractLink[] { endeArbeit };
        Column columnRemove=new Column("Action");
        columnRemove.setDecorator(new LinkDecorator(tableAngestellteImSchacht, links, "toAngestellte.id"));
        columnRemove.setTextAlign("center");
        columnRemove.setSortable(true);
        columnRemove.setWidth("auto");
        tableAngestellteImSchacht.addColumn(columnRemove);
        
    }
    
    public Table getTableZeit(){
        return tableZeit;
    }
}
