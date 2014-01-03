/** 
 * Hochschule Offenburg, Dezember 2013
 * Databanken Labor 3, Gruppe 13
 * @author Nikolaev Andrey & Ostrovskaya Anna
 */

package de.dblab.page.schaechte;

import de.dblab.domain.Schaechte;
import de.dblab.page.HomePage;
import org.apache.click.control.Table;
import de.dblab.page.TemplatePage;
import java.util.HashMap;
import java.util.List;
import org.apache.click.ActionResult;
import org.apache.click.Control;
import org.apache.click.ajax.DefaultAjaxBehavior;
import org.apache.click.control.AbstractLink;
import org.apache.click.control.ActionLink;
import org.apache.click.control.Checkbox;
import org.apache.click.control.Column;
import org.apache.click.control.PageLink;
import org.apache.click.dataprovider.DataProvider;
import org.apache.click.element.Element;
import org.apache.click.element.JsImport;
import org.apache.click.element.JsScript;
import org.apache.click.extras.control.FieldColumn;
import org.apache.click.extras.control.LinkDecorator;

/* 
 * Class SchaechtePage generiert HTML code auf der Seite SchaechtePage.htm
 */

public final class SchaechtePage extends TemplatePage {
    
    public static int schachtId = -1;
    private final Table table = new Table("table");
    private final SchaechteForm formDetail  = new SchaechteForm();
    private final SchaechteSearchForm formSuchen;
    private final SchaechteNewForm formNewSchaechte;
    
    private final ActionLink viewLink = new ActionLink("view", "View");
    private final PageLink editLink = new PageLink("Edit", SchaechteEditPage.class);
    private final ActionLink deleteLink = new ActionLink("delete", "Delete");
    
    public SchaechtePage() {
        HomePage.dataBaseService.commitChange();
        formNewSchaechte= new SchaechteNewForm();
        addControl(formNewSchaechte);
        formSuchen=new SchaechteSearchForm ();
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
                schachtId = viewLink.getValueInteger();
                formDetail.copyFrom(HomePage.dataBaseService.getSchaechteForID(schachtId));
                return new ActionResult(formDetail.toString(), ActionResult.HTML);
            }
        });
        
        deleteLink.addBehavior(new DefaultAjaxBehavior() {
            @Override
            public ActionResult onAction(Control source) {
                schachtId = deleteLink.getValueInteger();
                HomePage.dataBaseService.removeSchaechte(schachtId);
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
        table.addColumn(new Column("tief"));
        table.addColumn(new Column("leiterVonSchaechte.vollname","Leiter"));
        
        Checkbox checkbox = new Checkbox();
        checkbox.setDisabled(true);
        Column column;
        column = new FieldColumn("geschlossen", checkbox);
        column.setTextAlign("center");
        column.setWidth("50px");
        table.addColumn(column);
        
        viewLink.setAttribute("name", "View");
        viewLink.setImageSrc("/images/form.png");
        viewLink.setTitle("View");        
        editLink.setImageSrc("/images/table-edit.png");
        editLink.setTitle("Edit");
        editLink.setParameter("referrer", "/page/schaechte/SchaechtePage.htm");
        deleteLink.setAttribute("name", "Delete");
        deleteLink.setImageSrc("/images/remove.png");
        deleteLink.setTitle("Delete");
        column = new Column("View/Edit");
        column.setTextAlign("center");
        AbstractLink[] links = new AbstractLink[] { viewLink, editLink,deleteLink };
        column.setDecorator(new LinkDecorator(table, links, "id"));
        column.setSortable(false);
        column.setWidth("auto");
        table.addColumn(column);
        
        table.setDataProvider(new DataProvider<Schaechte>() {
            @Override
            public List<Schaechte> getData() {
                int type = Integer.valueOf(formSuchen.typeSelect.getValue());
                Object value = formSuchen.searchField.getValue();                
                if (type == Schaechte.ID.getId() || type == Schaechte.TIEF.getId()) {
                    try{
                      Integer.parseInt(value.toString());
                    } catch (NumberFormatException e) {
                        formSuchen.searchField.setValue("");
                        type=0;
                        value = "%";
                    }
                }else{
                    value = "%" + value + "%";
                }
                return HomePage.dataBaseService.getSchaechte(type, value, formSuchen.inaktivSelect.getValue());
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
