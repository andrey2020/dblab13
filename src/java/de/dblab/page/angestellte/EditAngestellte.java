
package de.dblab.page.angestellte;



import de.dblab.domain.Angestellte;
import javax.annotation.Resource;

import org.apache.click.Page;
import org.apache.click.control.Checkbox;
import org.apache.click.control.FieldSet;
import org.apache.click.control.Form;
import org.apache.click.control.HiddenField;
import org.apache.click.control.Submit;
import org.apache.click.control.TextField;
import org.apache.click.extras.control.IntegerField;
import org.apache.click.util.Bindable;
import de.dblab.page.HomePage;
import de.dblab.page.TemplatePage;
import de.dblab.service.DataBaseService;
import net.sf.click.extras.control.CalendarField;
import org.apache.click.control.Button;


public class EditAngestellte extends TemplatePage {
private static final long serialVersionUID = 1L;

    private Form form = new Form("form");
    private HiddenField referrerField = new HiddenField("referrer", String.class);
    private HiddenField idField = new HiddenField("id", Integer.class);

    // Bindable variables can automatically have their value set by request parameters
    @Bindable protected Integer id;
    @Bindable protected String referrer;

  //  @Resource(name="customerService")
    private DataBaseService dataBaseService=new DataBaseService();
    Angestellte angestellte;
    // Constructor -----------------------------------------------------------

    public EditAngestellte() {
        addControl(form);
        FieldSet subFieldSet = new FieldSet("angestellte");
        form.add(referrerField);
        form.add(idField);
        
        form.setAttribute("background-color", "#f4f4f4");
        form.add(subFieldSet);
        subFieldSet.setStyle("background-color", "#f4f4f4");
        form.setButtonAlign("right");
        form.setJavaScriptValidation(true); 
        
        
        form.add(subFieldSet);
        TextField idVisibleField = new TextField("id");
        idVisibleField.setDisabled(true);
        TextField nameField = new TextField("name", true);
        nameField.setWidth("250px");
        nameField.setFocus(true);
        TextField nachnameField = new TextField("nachname", true);
        nachnameField.setWidth("250px");
        CalendarField  geburtsDatumField = new CalendarField ("geburtsdatum", true);       
        geburtsDatumField.setStyle("brown");
        TextField stelleField = new TextField("stelle");
        IntegerField gehaltField = new IntegerField("gehalt");
        gehaltField.setWidth("40px");
        Checkbox istEntlassen = new Checkbox("entlassene");
        
        subFieldSet.add(idVisibleField);
        subFieldSet.add(nameField);
        subFieldSet.add(nachnameField);
        subFieldSet.add(geburtsDatumField);
        subFieldSet.add(stelleField);
        subFieldSet.add(gehaltField);
        subFieldSet.add(istEntlassen);
        form.setColumns(1);

        form.add(new Submit("ok", "  OK  ", this, "onOkClick"));
        form.add(new Submit("cancel", this, "onCancelClick"));
        
        
    }

    // Event Handlers ---------------------------------------------------------

    /**
     * When page is first displayed on the GET request.
     *
     * @see Page#onGet()
     */
    @Override
    public void onGet() {
        if (id != null) {
            angestellte = dataBaseService.getAngestellteForID(id);

            if (angestellte != null) {
                // Copy customer data to form. The idField value will be set by
                // this call
                form.copyFrom(angestellte);
            }
        }

        if (referrer != null) {
            // Set HiddenField to bound referrer field
            referrerField.setValue(referrer);
        }
    }

    public boolean onOkClick() {
        if (form.isValid()) {
            Integer id = (Integer) idField.getValueObject();
            angestellte = dataBaseService.getAngestellteForID(id);

            if (angestellte == null) {
                angestellte = new Angestellte();
            }
            form.copyTo(angestellte);

            dataBaseService.commitChange();

            //String referrer = referrerField.getValue();
            if (referrer != null) {
                setRedirect(referrer);
            } else {
                setRedirect(HomePage.class);
            }

            return true;

        } else {
            return true;
        }
    }

    public boolean onCancelClick() {
        String referrer = referrerField.getValue();
        if (referrer != null) {
            setRedirect(referrer);
        } else {
            setRedirect(HomePage.class);
        }
        return true;
    }

}