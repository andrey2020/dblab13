/** 
 * Hochschule Offenburg, Dezember 2013
 * Databanken Labor 3, Gruppe 13
 * @author Nikolaev Andrey & Ostrovskaya Anna
 */

package de.dblab.page.angestellte;

import de.dblab.domain.Schaechte;
import de.dblab.domain.Zeit;
import de.dblab.page.HomePage;
import static de.dblab.page.angestellte.AngestelltePage.angId;
import java.util.List;
import org.apache.click.ActionResult;
import org.apache.click.Control;
import org.apache.click.ajax.DefaultAjaxBehavior;
import org.apache.click.control.ActionLink;
import org.apache.click.control.Button;
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
import org.apache.click.extras.control.TableInlinePaginator;

/* 
 * Class AngestelterForm generiert HTML code auf der Seite AngestelterPage.htm
 */

public final class AngestelterForm extends Form{

    private final Table tableZeit = new Table("nebenTable1");
    private final Table tableSchaechteZulassung = new Table("nebenTable2");
    private final Table tableVerboteneSchaechte = new Table("nebenTable3");
    
    private final ActionLink removeFromAngestellterZulassungLink = new ActionLink("remove", "Remove");
    private final ActionLink addToAngestellterZulassungLink = new ActionLink("add", "Add");

    private final Form topForm = new Form();
    private final Form downForm = new Form();
          
    public AngestelterForm(){
       this.setJavaScriptValidation(true); 
       FieldSet fieldSet = new FieldSet("Detail des Angestellter");
       this.add(fieldSet);
       fieldSet.setTextAlign("center");
       this.setButtonAlign("right");
       this.setLabelAlign("center");
       
       initFormComponent();
       initTableZeit();
       initTableSchaechteZulassung();
       initTableVerboteneSchaechte();
       
       fieldSet.setStyle("border-color", "#a80402");
       topForm.setColumns(2);
       downForm.setColumns(2);
       fieldSet.add(topForm);
       fieldSet.add(downForm);
       Button closeButton = new Button("Schließen");
       closeButton.setOnClick("form.submit();");
       this.add(closeButton);
    }
  
    
    public Form getForm(){
        return this;
    }
    
    private void initFormComponent(){

        FieldSet subFieldSet = new FieldSet("Angestellter");
        topForm.add(subFieldSet);
        subFieldSet.setAttribute("background-color", "#f4f4f4");
        TextField idField = new TextField("id");
        idField.setDisabled(true);
        TextField nameField = new TextField("name");
        nameField.setDisabled(true);
        TextField nachnameField = new TextField("nachname");
        nachnameField.setDisabled(true);
        TextField stelleField = new TextField("stelle");
        stelleField.setDisabled(true);
        TextField gehaltField = new TextField("gehalt");
        gehaltField.setDisabled(true);
        Checkbox istEntlassen = new Checkbox("entlassene");
        istEntlassen.setDisabled(true);
        DateField  geburtsDatumField = new DateField ("geburtsdatum"); 
        geburtsDatumField.setDisabled(true);
        geburtsDatumField.setFormatPattern("dd.MM.yyyy");
        subFieldSet.add(idField);
        subFieldSet.add(nameField);
        subFieldSet.add(nachnameField);
        subFieldSet.add(geburtsDatumField);
        subFieldSet.add(stelleField);
        subFieldSet.add(gehaltField);
        subFieldSet.add(istEntlassen);        
        subFieldSet.setColumns(1);
    }
    
    private void initTableZeit(){
        topForm.add(tableZeit);
        tableZeit.getControlLink().addBehavior(new DefaultAjaxBehavior() {
            @Override
            public ActionResult onAction(Control source) {

                tableZeit.onProcess();
                getForm().copyFrom(HomePage.dataBaseService.getAngestellteForID(angId));
                return new ActionResult(getForm().toString(), ActionResult.HTML);
        }});
        tableZeit.setCaption("Zeit Tabelle");
        tableZeit.setPaginator(new TableInlinePaginator(tableZeit));
        tableZeit.setPaginatorAttachment(Table.PAGINATOR_INLINE);
        tableZeit.setClass(Table.CLASS_REPORT);
        tableZeit.addColumn(new Column("toSchaechte.id","ID"));
        tableZeit.addColumn(new Column("toSchaechte.name","Schacht Name"));
        Column eingangColumn=new Column("zeitEingang","Eingang");
        eingangColumn.setFormat("{0,date,dd.MM.yyyy - HH:mm:ss}");
        tableZeit.addColumn(eingangColumn);
        Column ausgangColumn=new Column("zeitAusgang","Ausgang");
        ausgangColumn.setFormat("{0,date,dd.MM.yyyy - HH:mm:ss}");
        ausgangColumn.setWidth("160px");
        eingangColumn.setWidth("160px");
        tableZeit.addColumn(ausgangColumn);
        tableZeit.addColumn(new Column("arbeitsZeit"));
        tableZeit.setSortedColumn("zeitEingang");
        tableZeit.setSortedAscending(false);
        tableZeit.setSortable(true);
        tableZeit.setShowBanner(true);
        tableZeit.setPageSize(7);
        tableZeit.setDataProvider(new DataProvider<Zeit>() {
            @Override
            public List<Zeit> getData() {
                return HomePage.dataBaseService.getAngestellteForID(AngestelltePage.angId).getZeitArray();
            }
        });        
    }
    
    private void initTableVerboteneSchaechte(){
        downForm.add(tableVerboteneSchaechte);
        tableVerboteneSchaechte.getControlLink().addBehavior(new DefaultAjaxBehavior() {
            @Override
            public ActionResult onAction(Control source) {
                tableVerboteneSchaechte.onProcess();
                getForm().copyFrom(HomePage.dataBaseService.getAngestellteForID(angId));
                return new ActionResult(getForm().toString(), ActionResult.HTML);
                
        }}); 
       tableVerboteneSchaechte.setCaption("Verbotene Schachte");
       tableVerboteneSchaechte.setClass(Table.CLASS_REPORT);
       addToAngestellterZulassungLink.setParent(this);
       addToAngestellterZulassungLink.addBehavior(new DefaultAjaxBehavior() {
            @Override
            public ActionResult onAction(Control source) {
                int id=addToAngestellterZulassungLink.getValueInteger();
                HomePage.dataBaseService.getAngestellteForID(AngestelltePage.angId).addToSchaechteZulassung(HomePage.dataBaseService.getSchaechteForID(id));
                HomePage.dataBaseService.commitChange();
      //          Form thisForm = (Form) source.getParent();
                getForm().copyFrom(HomePage.dataBaseService.getAngestellteForID(angId));
                return new ActionResult(getForm().toString(), ActionResult.HTML);
            }
        });  
        addToAngestellterZulassungLink.setImageSrc("/images/add.png");
        addToAngestellterZulassungLink.setTitle("Schacht Zulassung");
        addToAngestellterZulassungLink.setAttribute("name", "linkAction");
        Column columnRemove=new Column("Action");
        columnRemove.setDecorator(new LinkDecorator(tableVerboteneSchaechte, addToAngestellterZulassungLink, "id"));
        columnRemove.setTextAlign("center");
        columnRemove.setSortable(false);
        tableVerboteneSchaechte.addColumn(columnRemove);
        tableVerboteneSchaechte.addColumn(new Column("id","Id"));
        Column column = new Column("name","Name");
        tableVerboteneSchaechte.addColumn(column);
        tableVerboteneSchaechte.addColumn(new Column("tief","Tief"));
        Checkbox checkbox = new Checkbox();
        checkbox.setDisabled(true);
        column = new FieldColumn("geschlossen","Inaktiv", checkbox);
        column.setTextAlign("center");
        column.setWidth("50px");
        tableVerboteneSchaechte.addColumn(column);
        tableVerboteneSchaechte.setSortable(true);
        tableVerboteneSchaechte.setWidth("500px");
         tableVerboteneSchaechte.setDataProvider(new DataProvider<Schaechte>() {
            @Override
            public List<Schaechte> getData() {
                return HomePage.dataBaseService.getVerboteneSchaechte(AngestelltePage.angId);
            }
        });
    }
    
    private void initTableSchaechteZulassung() {
       downForm.add(tableSchaechteZulassung);
       tableSchaechteZulassung.getControlLink().addBehavior(new DefaultAjaxBehavior() {
            @Override
            public ActionResult onAction(Control source) {
                tableSchaechteZulassung.onProcess();
                getForm().copyFrom(HomePage.dataBaseService.getAngestellteForID(angId));
                return new ActionResult(getForm().toString(), ActionResult.HTML);
        }});
       tableSchaechteZulassung.setCaption("Zugelassene Schachte");
       tableSchaechteZulassung.setClass(Table.CLASS_REPORT);
       removeFromAngestellterZulassungLink.setParent(this);
       removeFromAngestellterZulassungLink.addBehavior(new DefaultAjaxBehavior() {
            @Override
            public ActionResult onAction(Control source) {
                int id=removeFromAngestellterZulassungLink.getValueInteger();
                HomePage.dataBaseService.getAngestellteForID(AngestelltePage.angId).removeFromSchaechteZulassung(HomePage.dataBaseService.getSchaechteForID(id));
                HomePage.dataBaseService.commitChange();
           //     Form thisForm = (Form) source.getParent();
                getForm().copyFrom(HomePage.dataBaseService.getAngestellteForID(angId));
                return new ActionResult(getForm().toString(), ActionResult.HTML);
            }
        });
        removeFromAngestellterZulassungLink.setImageSrc("/images/delete.png");
        removeFromAngestellterZulassungLink.setTitle("Remove Schacht Zulassung");
        removeFromAngestellterZulassungLink.setAttribute("name", "linkAction");
        tableSchaechteZulassung.addColumn(new Column("id","Id"));
        tableSchaechteZulassung.addColumn(new Column("name","Name"));
        tableSchaechteZulassung.addColumn(new Column("tief","Tief"));
        
        Checkbox checkbox = new Checkbox();
        checkbox.setDisabled(true);
        Column column = new FieldColumn("geschlossen","Inaktiv", checkbox);
        column.setTextAlign("center");
        column.setWidth("50px");
        tableSchaechteZulassung.addColumn(column);
        
        column=new Column("Action");
        column.setDecorator(new LinkDecorator(tableSchaechteZulassung, removeFromAngestellterZulassungLink, "id"));
        column.setTextAlign("center");
        column.setSortable(false);
        tableSchaechteZulassung.addColumn(column);
        
        tableSchaechteZulassung.setSortable(true);
        tableSchaechteZulassung.setWidth("500px");
        tableSchaechteZulassung.setDataProvider(new DataProvider<Schaechte>() {
            @Override
            public List<Schaechte> getData() {
                return HomePage.dataBaseService.getAngestellteForID(AngestelltePage.angId).getSchaechteZulassung();
            }
        });
        
    }
}
