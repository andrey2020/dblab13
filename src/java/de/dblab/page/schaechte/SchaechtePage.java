package de.dblab.page.schaechte;

import de.dblab.domain.Schaechte;
import de.dblab.page.HomePage;
import de.dblab.page.DataBaseService;
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


public class SchaechtePage extends TemplatePage {

    private static final long serialVersionUID = 1L;

    private final Table table = new Table("table");
    public static int schachtId = 0;
    private final ActionLink viewLink = new ActionLink("view", "View");
    PageLink editLink = new PageLink("Edit", EditSchaechte.class);
    ActionLink deleteLink = new ActionLink("delete", "Delete");
    private Column columnViewEdit;
    private Column column;
  
    private final SchaechteForm detailForm  = new SchaechteForm();
    private final DataBaseService dataBaseService = HomePage.dataBaseService;
    
    private final SearchForm formSuchen;

    public SchaechtePage() {
        
        NewForm formNewSchaechte= new NewForm();
        addControl(formNewSchaechte);
        
        formSuchen=new SearchForm ();
        addControl(formSuchen);
        initTable();
    }
    
    public void initTable(){
        addControl(table);
        addControl(viewLink);
        addControl(editLink);
       addControl(deleteLink);
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
                detailForm.copyFrom(dataBaseService.getSchaechteForID(schachtId));
                return new ActionResult(detailForm.toString(), ActionResult.HTML);
            }
        });
        deleteLink.addBehavior(new DefaultAjaxBehavior() {
            @Override
            public ActionResult onAction(Control source) {
                schachtId = deleteLink.getValueInteger();
                dataBaseService.removeSchaechte(schachtId);
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

        Checkbox checkbox = new Checkbox();
        checkbox.setDisabled(true);
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
        columnViewEdit = new Column("View/Edit");
        columnViewEdit.setTextAlign("center");
        AbstractLink[] links = new AbstractLink[] { viewLink, editLink,deleteLink };
        columnViewEdit.setDecorator(new LinkDecorator(table, links, "id"));
        columnViewEdit.setSortable(false);
        columnViewEdit.setWidth("auto");
        table.addColumn(columnViewEdit);
        

        
        table.setDataProvider(new DataProvider<Schaechte>() {
            private static final long serialVersionUID = 1L;
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
                return dataBaseService.getSchaechte(type, value, formSuchen.inaktivSelect.getValue());
            }
        });
        table.restoreState(getContext());
    }
    
    
    @Override
    public void onPost() {
      //  detailForm.copyFrom(dataBaseService.getSchaechteForID(schachtId));
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
