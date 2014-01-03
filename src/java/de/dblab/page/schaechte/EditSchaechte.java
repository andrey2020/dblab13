
package de.dblab.page.schaechte;

import de.dblab.domain.Schaechte;

import org.apache.click.Page;
import org.apache.click.control.Checkbox;
import org.apache.click.control.FieldSet;
import org.apache.click.control.Form;
import org.apache.click.control.HiddenField;
import org.apache.click.control.Submit;
import org.apache.click.control.TextField;
import org.apache.click.util.Bindable;
import de.dblab.page.HomePage;
import de.dblab.page.DataBaseService;
import de.dblab.page.TemplatePage;
import org.apache.click.control.Select;


public class EditSchaechte extends TemplatePage {
private static final long serialVersionUID = 1L;

    private Form form = new Form("form");
    private HiddenField referrerField = new HiddenField("referrer", String.class);
    private HiddenField idField = new HiddenField("id", Integer.class);

    // Bindable variables can automatically have their value set by request parameters
    @Bindable protected Integer id;
    @Bindable protected String referrer;

  //  @Resource(name="customerService")
    private final DataBaseService dataBaseService = HomePage.dataBaseService;
    Schaechte schaechte;
    // Constructor -----------------------------------------------------------

    public EditSchaechte() {
        addControl(form);
        FieldSet subFieldSet = new FieldSet("schaechte");
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
        
        nameField.setFocus(true);
        TextField tiefField = new TextField("tief");
        Select leiterId = new Select("leiter_id","Leiter");
        leiterId.addAll(dataBaseService.getAngestellteName());
        Checkbox istGeschlossen = new Checkbox("geschlossen");
        
        subFieldSet.add(idVisibleField);
        subFieldSet.add(nameField);
        subFieldSet.add(tiefField);
        subFieldSet.add(leiterId);
        subFieldSet.add(istGeschlossen);
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
            schaechte = dataBaseService.getSchaechteForID(id);

            if (schaechte != null) {
                // Copy customer data to form. The idField value will be set by
                // this call
                form.copyFrom(schaechte);
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
            schaechte = dataBaseService.getSchaechteForID(id);

            if (schaechte == null) {
                schaechte = new Schaechte();
            }
            form.copyTo(schaechte);

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