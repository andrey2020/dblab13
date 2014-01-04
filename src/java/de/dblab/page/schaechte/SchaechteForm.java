/** 
 * Hochschule Offenburg, Dezember 2013
 * Databanken Labor 3, Gruppe 13
 * @author Nikolaev Andrey & Ostrovskaya Anna
 */

package de.dblab.page.schaechte;

import de.dblab.domain.Angestellte;
import de.dblab.domain.Zeit;
import de.dblab.page.HomePage;
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
import org.apache.click.extras.control.LinkDecorator;
import org.apache.click.extras.control.TableInlinePaginator;

/* 
 * Class SchaechteForm generiert HTML code auf der Seite SchaechtePage.htm
 */

public class SchaechteForm extends Form{
    
    private final Table tableZeit = new Table("nebenTable1");
    private final Table tableAngestellteImSchacht = new Table("nebenTable2");
    private final Table tableMoeglicheAngestellte = new Table("nebenTable3");
    
    private final ActionLink endeArbeit = new ActionLink("remove", "Remove");
    private final ActionLink beginArbeit = new ActionLink("add", "Add");
    
    private final Form topForm = new Form();
    private final Form downForm = new Form();
    
    public SchaechteForm(){
       this.setJavaScriptValidation(true);
       FieldSet fieldSet = new FieldSet("Detail des Schaechter");
       this.add(fieldSet);
       fieldSet.setTextAlign("center");
       this.setButtonAlign("right");
       this.setLabelAlign("center");
       
       initFormComponent();
       initTableZeit();
       initTableAngestellteImSchacht();
       initTableMoeglicheAngestellte();
       
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
        FieldSet subFieldSet = new FieldSet("Schaechte");
        topForm.add(subFieldSet);
        subFieldSet.setStyle("background-color", "#f4f4f4");
        TextField idField = new TextField("id");
        idField.setDisabled(true);
        TextField nameField = new TextField("name");
        nameField.setDisabled(true);
        TextField tiefField = new TextField("tief");
        tiefField.setDisabled(true);
        TextField leiterName = new TextField("leiterVonSchaechte.vollname","Leiter");
        leiterName.setDisabled(true);
        Checkbox istGeschlossen = new Checkbox("geschlossen");
        istGeschlossen.setDisabled(true);
        subFieldSet.add(idField);
        subFieldSet.add(nameField);
        subFieldSet.add(tiefField);
        subFieldSet.add(leiterName);
        subFieldSet.add(istGeschlossen);
        subFieldSet.setColumns(1);
    }
    
    private void initTableZeit(){
        topForm.add(tableZeit);
        tableZeit.getControlLink().addBehavior(new DefaultAjaxBehavior() {
            @Override
            public ActionResult onAction(Control source) {
                tableZeit.onProcess();
                getForm().copyFrom(HomePage.dataBaseService.getSchaechteForID(SchaechtePage.schachtId));
                return new ActionResult(getForm().toString(), ActionResult.HTML);
        }});
        
        tableZeit.setCaption("Zeit Tabelle");
        tableZeit.setPaginator(new TableInlinePaginator(tableZeit));
        tableZeit.setPaginatorAttachment(Table.PAGINATOR_INLINE);
        tableZeit.setClass(Table.CLASS_REPORT);
        tableZeit.addColumn(new Column("toAngestellte.id","ID"));
        tableZeit.addColumn(new Column("toAngestellte.vollname","Angestellte"));
        Column eingangColumn=new Column("zeitEingang","Eingang");
        eingangColumn.setFormat("{0,date,dd.MM.yyyy - HH:mm:ss}");
        tableZeit.addColumn(eingangColumn);
        Column ausgangColumn=new Column("zeitAusgang","Ausgang");
        ausgangColumn.setFormat("{0,date,dd.MM.yyyy - HH:mm:ss}");
        tableZeit.addColumn(ausgangColumn);
        ausgangColumn.setWidth("160px");
        eingangColumn.setWidth("160px");
        tableZeit.addColumn(new Column("arbeitsZeit"));
        
        tableZeit.setSortedColumn("zeitAusgang");
        tableZeit.setSortedAscending(true);
        tableZeit.setSortable(true);
        tableZeit.setShowBanner(true);
        tableZeit.setPageSize(5);
        tableZeit.setDataProvider(new DataProvider<Zeit>() {
            @Override
            public List<Zeit> getData() {
                return HomePage.dataBaseService.getSchaechteForID(SchaechtePage.schachtId).getZeitArray();
            }
        });
    }
    
    private void initTableMoeglicheAngestellte(){
        downForm.add(tableMoeglicheAngestellte);
        tableMoeglicheAngestellte.getControlLink().addBehavior(new DefaultAjaxBehavior() {
            @Override
            public ActionResult onAction(Control source) {
                tableMoeglicheAngestellte.onProcess();
                getForm().copyFrom(HomePage.dataBaseService.getSchaechteForID(SchaechtePage.schachtId));
                return new ActionResult(getForm().toString(), ActionResult.HTML);
        }}); 
       tableMoeglicheAngestellte.setCaption("Mögliche Angestellte");
       tableMoeglicheAngestellte.setClass(Table.CLASS_REPORT);
       beginArbeit.setParent(this);
       beginArbeit.addBehavior(new DefaultAjaxBehavior() {
            @Override
            public ActionResult onAction(Control source) {
                HomePage.dataBaseService.beginArbeit(beginArbeit.getValueInteger(),SchaechtePage.schachtId);
                getForm().copyFrom(HomePage.dataBaseService.getSchaechteForID(SchaechtePage.schachtId));
                return new ActionResult(getForm().toString(), ActionResult.HTML);
            }
        });
        beginArbeit.setImageSrc("/images/add.png");
        beginArbeit.setTitle("Begin Arbeit");
        beginArbeit.setAttribute("name", "linkAction");
        Column columnAdd=new Column("Action");
        columnAdd.setDecorator(new LinkDecorator(tableMoeglicheAngestellte, beginArbeit, "id"));
        columnAdd.setTextAlign("center");
        columnAdd.setSortable(false);
        columnAdd.setWidth("auto");
        tableMoeglicheAngestellte.addColumn(columnAdd);
        tableMoeglicheAngestellte.addColumn(new Column("id","Id"));
        tableMoeglicheAngestellte.addColumn(new Column("vollname","Name"));
        tableMoeglicheAngestellte.setSortable(true);
        tableMoeglicheAngestellte.setWidth("450px");
        tableMoeglicheAngestellte.setDataProvider(new DataProvider<Angestellte>() {
            @Override
            public List<Angestellte> getData() {
                return HomePage.dataBaseService.getAngestellteMoeglich(SchaechtePage.schachtId);
            }
        });
    }
    
    private void initTableAngestellteImSchacht() {
       downForm.add(tableAngestellteImSchacht);
       tableAngestellteImSchacht.getControlLink().addBehavior(new DefaultAjaxBehavior() {
            @Override
            public ActionResult onAction(Control source) {
                tableAngestellteImSchacht.onProcess();
                getForm().copyFrom(HomePage.dataBaseService.getSchaechteForID(SchaechtePage.schachtId));
                return new ActionResult(getForm().toString(), ActionResult.HTML);
        }});
       tableAngestellteImSchacht.setCaption("Angestellte im Schacht");
       tableAngestellteImSchacht.setClass(Table.CLASS_REPORT);
       endeArbeit.setParent(this);
       endeArbeit.addBehavior(new DefaultAjaxBehavior() {
            @Override
            public ActionResult onAction(Control source) {
                int id=endeArbeit.getValueInteger();
                HomePage.dataBaseService.endeArbeit(endeArbeit.getValueInteger());
                getForm().copyFrom(HomePage.dataBaseService.getSchaechteForID(SchaechtePage.schachtId));
                return new ActionResult(getForm().toString(), ActionResult.HTML);
            }
        });
        endeArbeit.setImageSrc("/images/delete.png");
        endeArbeit.setTitle("Ende der Arbeit");
        endeArbeit.setAttribute("name", "linkAction");
        tableAngestellteImSchacht.addColumn(new Column("toAngestellte.id","Id"));
        tableAngestellteImSchacht.addColumn(new Column("toAngestellte.vollname","Name"));
        Column columnRemove=new Column("Action");
        columnRemove.setDecorator(new LinkDecorator(tableAngestellteImSchacht, endeArbeit, "toAngestellte.id"));
        columnRemove.setTextAlign("center");
        columnRemove.setSortable(false);
        columnRemove.setWidth("auto");
        tableAngestellteImSchacht.addColumn(columnRemove);
        tableAngestellteImSchacht.setSortable(true);
        tableAngestellteImSchacht.setWidth("450px");
        tableAngestellteImSchacht.setDataProvider(new DataProvider<Zeit>() {
            @Override
            public List<Zeit> getData() {
                return HomePage.dataBaseService.getAngestellteImSchacht(SchaechtePage.schachtId);
            }
        });
    }
}
