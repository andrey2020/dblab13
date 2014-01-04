/** 
 * Hochschule Offenburg, Dezember 2013
 * Databanken Labor 3, Gruppe 13
 * @author Nikolaev Andrey & Ostrovskaya Anna
 */

package de.dblab.page.angestellte;


import java.util.HashMap;
import java.util.List;
import org.apache.click.ActionResult;
import org.apache.click.Control;
import org.apache.click.ajax.DefaultAjaxBehavior;
import org.apache.click.control.AbstractLink;
import org.apache.click.control.*;
import org.apache.click.control.Table;
import org.apache.click.dataprovider.DataProvider;
import org.apache.click.element.Element;
import org.apache.click.element.JsImport;
import org.apache.click.element.JsScript;
import org.apache.click.extras.control.FieldColumn;
import org.apache.click.extras.control.LinkDecorator;
import de.dblab.domain.Angestellte;
import de.dblab.page.HomePage;
import de.dblab.page.TemplatePage;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/* 
 * Class AngestelltePage generiert HTML code auf der Seite AngestelltePage.htm
 */

public final class AngestelltePage extends TemplatePage {
    
    public static int angId = -1;
    private final Table table = new Table("table");
    private final AngestelterForm detailForm  = new AngestelterForm();
    private final AngestellteSearchForm formSuchen;
    AngestellteNewForm formNewAngestellter;

    private final ActionLink deleteLink = new ActionLink("delete", "Delete");
    private final ActionLink viewLink = new ActionLink("view", "View");
    private final PageLink editLink = new PageLink("Edit", AngestellteEditPage.class);
   
    public AngestelltePage() {
        HomePage.dataBaseService.commitChange();
        formNewAngestellter= new AngestellteNewForm();
        addControl(formNewAngestellter);
        formSuchen=new AngestellteSearchForm ();
        addControl(formSuchen);
        initTable();
    }
    
    public void initTable(){
        addControl(table);
        table.getControlLink().addBehavior(new DefaultAjaxBehavior() {
            @Override
            public ActionResult onAction(Control source) {
                table.saveState(getContext());
                table.onProcess();
                return new ActionResult(table.toString(), ActionResult.HTML);
        }});
        
        viewLink.addBehavior(new DefaultAjaxBehavior() {
            @Override
            public ActionResult onAction(Control source) {
                angId = viewLink.getValueInteger();
                detailForm.copyFrom( HomePage.dataBaseService.getAngestellteForID(angId));
                return new ActionResult(detailForm.toString(), ActionResult.HTML);
            }
        });
        
        deleteLink.addBehavior(new DefaultAjaxBehavior() {
            @Override
            public ActionResult onAction(Control source) {
                angId = deleteLink.getValueInteger();
                 HomePage.dataBaseService.removeAngestellte(angId);
                table.onProcess();
                return new ActionResult(table.toString(), ActionResult.HTML);
            }
        });
        table.setClass(Table.CLASS_ISI);
        table.setPageSize(Integer.valueOf(formSuchen.sizeSelect.getValue()));
        table.setShowBanner(true);
        table.setBannerPosition(Table.POSITION_TOP);
        table.setSortable(true);
        table.setWidth("100%");
        table.addColumn(new Column("id"));
        table.addColumn(new Column("name"));
        table.addColumn(new Column("nachname"));
        
        Column column = new Column("geburtsdatum");
        column.setFormat("{0,date,medium}");
        table.addColumn(column);
        
        table.addColumn(new Column("stelle"));
        table.addColumn(new Column("gehalt"));
        
        Checkbox checkbox = new Checkbox();
        checkbox.setDisabled(true);
        column = new FieldColumn("entlassene", checkbox);
        column.setTextAlign("center");
        table.addColumn(column);
        
        viewLink.setAttribute("name", "View");
        viewLink.setImageSrc("/images/form.png");
        viewLink.setTitle("View");
        editLink.setImageSrc("/images/table-edit.png");
        editLink.setTitle("Edit");
        editLink.setParameter("referrer", "/page/angestellte/AngestelltePage.htm");
        deleteLink.setAttribute("name", "Delete");
        deleteLink.setImageSrc("/images/remove.png");
        deleteLink.setTitle("Delete");        
        column = new Column("View - Edit - Delete");
        column.setTextAlign("center");
        AbstractLink l = new ActionLink(" ");
        l.setDisabled(true);
        AbstractLink[] links = new AbstractLink[] { viewLink, l, l, editLink, l, l, deleteLink };
        
        column.setDecorator(new LinkDecorator(table, links, "id"));
        column.setSortable(false);
        column.setWidth("auto");
        table.addColumn(column);
        
        table.setDataProvider(new DataProvider<Angestellte>() {            
            @Override
            public List<Angestellte> getData() {
                int type = Integer.valueOf(formSuchen.typeSelect.getValue());
                Object value = formSuchen.searchField.getValue();
                if (type == Angestellte.ID.getId() || type == Angestellte.GEHALT.getId()) {
                    try{
                        Integer.parseInt(value.toString());
                    } catch (NumberFormatException e) {
                        formSuchen.searchField.setValue("");
                        type=1;
                        value = "%";
                    }
                }else if (type == Angestellte.GEBURTSDATUM.getId()) {
                    try{
                        SimpleDateFormat dateJava = new SimpleDateFormat("dd.MM.yyyy");
                        Calendar calendar = Calendar.getInstance(); 
                        calendar.setTime(dateJava.parse(value.toString()));
                        value = calendar;
                    } catch (ParseException ex) {
                        formSuchen.searchField.setValue("");
                        type=1;
                        value = "%";
                    } 
                }else{
                    value = "%" + value + "%";
                }
                return  HomePage.dataBaseService.getAngestelltes(type, value, formSuchen.firedSelect.getValue());
            }
        });
        table.restoreState(getContext());
    }
    
    @Override
    public void onPost() {
        formSuchen.saveState(getContext());
        table.saveState(getContext());
    }
    
    @Override
    public void onRender() {
        table.setPageSize(Integer.valueOf(formSuchen.sizeSelect.getValue()));
    }
    
    @Override
    public List<Element> getHeadElements() {
        if (headElements == null) { 
            headElements = super.getHeadElements();
            headElements.add(new JsImport("/script/js/jquery-1.4.2.js"));
            headElements.add(new JsScript("/script/table-ajax.js", new HashMap()));
        }
        return headElements;
    }
}
