package de.dblab.page.angestellte;


import java.util.HashMap;
import java.util.List;

import org.apache.click.ActionListener;
import org.apache.click.ActionResult;
import org.apache.click.Control;
import org.apache.click.ajax.DefaultAjaxBehavior;
import org.apache.click.control.AbstractLink;


import org.apache.click.control.*;
import org.apache.click.control.Form;
import org.apache.click.control.PageLink;
import org.apache.click.control.Select;
import org.apache.click.control.Table;
import org.apache.click.control.TextField;
import org.apache.click.dataprovider.DataProvider;
import org.apache.click.element.Element;
import org.apache.click.element.JsImport;
import org.apache.click.element.JsScript;
import org.apache.click.extras.control.FieldColumn;
import org.apache.click.extras.control.LinkDecorator;
import de.dblab.domain.Angestellte;
import de.dblab.domain.Schaechte;
import de.dblab.page.TemplatePage;
import de.dblab.service.DataBaseService;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
//import org.springframework.stereotype.Component;

//@Component
public final class AngestelltePage extends TemplatePage {

    private static final long serialVersionUID = 1L;

    private final Table table = new Table("table");
    public static int angId = 0;
    private final PageLink editLink = new PageLink("Edit", EditAngestellte.class);
    private ActionLink viewLink = new ActionLink("view", "View");
    private Column columnViewEdit;
    private Column column;
  
    private final AngestelterForm detailForm;
    public static final DataBaseService dataBaseService = new DataBaseService();
    
    private final SearchForm formSuchen;

    // Constructor ------------------------------------------------------------

    public AngestelltePage() {
        this.detailForm = new AngestelterForm();
        
        NewForm formNewAngestellter= new NewForm();
        addControl(formNewAngestellter);
        
        formSuchen=new SearchForm ();
        addControl(formSuchen);
        initTable();
    }
    
    public void initTable(){
        addControl(table);
        addControl(viewLink);
       
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
        
        viewLink.setAttribute("name", "View");
        viewLink.setImageSrc("/images/form.png");
        viewLink.setTitle("View");
        //viewLink.setParameter("referrer", "/page/angestellte/AngestelltePage.htm");
        
            
        editLink.setImageSrc("/images/table-edit.png");
        editLink.setTitle("Edit Angestellte");
        editLink.setParameter("referrer", "/AngestelltePage.htm");
        columnViewEdit = new Column("View/Edit");
        columnViewEdit.setTextAlign("center");
          // sizeSelect.setSize(8);



        AbstractLink[] links = new AbstractLink[] { viewLink,editLink };
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
                        
                     value = calendar.get(Calendar.YEAR) + "-" + 
                             (calendar.get(Calendar.MONTH)+1) + "-" + 
                             calendar.get(Calendar.DATE);
                     
                    } catch (ParseException ex) {
                        formSuchen.searchField.setValue("");
                        type=1;
                        value = "%";
                        // not an integer!
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
        detailForm.copyFrom(dataBaseService.getAngestellteForID(angId));
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
