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
import de.dblab.page.DataBaseService;
import de.dblab.page.TemplatePage;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import net.sf.click.extras.control.CalendarField;
//import org.springframework.stereotype.Component;

//@Component
public final class AngestelltePage extends TemplatePage {

    private static final long serialVersionUID = 1L;

    private final Table table = new Table("table");
    public static int angId = -1;
    private final ActionLink deleteLink = new ActionLink("delete", "Delete");
    private final ActionLink viewLink = new ActionLink("view", "View");
    private final PageLink editLink = new PageLink("Edit", AngestellteEditPage.class);
    
    private Column columnViewEdit;
    private Column column;
  
    private final AngestelterForm detailForm  = new AngestelterForm();
    private final DataBaseService dataBaseService = HomePage.dataBaseService;
    
    private final AngestellteSearchForm formSuchen;

    // Constructor ------------------------------------------------------------

    public AngestelltePage() {
        dataBaseService.commitChange();
        AngestellteNewForm formNewAngestellter= new AngestellteNewForm();
        addControl(formNewAngestellter);
        
        formSuchen=new AngestellteSearchForm ();
        addControl(formSuchen);
       // addControl(detailForm.getTableZeit());
        initTable();
    }
    
    public void initTable(){
        addControl(deleteLink);
        addControl(table);
        addControl(viewLink);
        addControl(editLink);
        
       deleteLink.addBehavior(new DefaultAjaxBehavior() {
            @Override
            public ActionResult onAction(Control source) {
                angId = deleteLink.getValueInteger();
                dataBaseService.removeAngestellte(angId);
                table.onProcess();
                return new ActionResult(table.toString(), ActionResult.HTML);
            }
        });
        
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
                detailForm.copyFrom(dataBaseService.getAngestellteForID(angId));
                return new ActionResult(detailForm.toString(), ActionResult.HTML);
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
        
        column = new Column("geburtsdatum");
        column.setFormat("{0,date,medium}");
        column.setWidth("90px");
        
        table.addColumn(column);
        
        table.addColumn(new Column("stelle"));

        table.addColumn(new Column("gehalt"));
        
        Checkbox checkbox = new Checkbox();
        checkbox.setDisabled(true);
        column = new FieldColumn("entlassene", checkbox);
        column.setTextAlign("center");
        column.setWidth("50px");
        table.addColumn(column);
        
        deleteLink.setAttribute("name", "Delete");
        deleteLink.setImageSrc("/images/remove.png");
        deleteLink.setTitle("Delete");
        
        viewLink.setAttribute("name", "View");
        viewLink.setImageSrc("/images/form.png");
        viewLink.setTitle("View");
        
        editLink.setAttribute("name", "Edit");
        editLink.setImageSrc("/images/table-edit.png");
        editLink.setTitle("Edit");
        editLink.setParameter("referrer", "/page/angestellte/AngestelltePage.htm");

        
        columnViewEdit = new Column("View/Edit");
        columnViewEdit.setTextAlign("center");
          // sizeSelect.setSize(8);
        AbstractLink[] links = new AbstractLink[] { viewLink, editLink, deleteLink };
        columnViewEdit.setDecorator(new LinkDecorator(table, links, "id"));
        columnViewEdit.setSortable(false);
        columnViewEdit.setWidth("auto");
        table.addColumn(columnViewEdit);
        
       
        
        table.setDataProvider(new DataProvider<Angestellte>() {
            
            private static final long serialVersionUID = 1L;
            
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
                return dataBaseService.getAngestelltes(type, value, formSuchen.firedSelect.getValue());
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
