/** 
 * Hochschule Offenburg, Dezember 2013
 * Databanken Labor 3, Gruppe 13
 * @author Nikolaev Andrey & Ostrovskaya Anna
 */

package de.dblab.page.schaechte;

import de.dblab.domain.Schaechte;
import de.dblab.page.HomePage;
import de.dblab.page.TemplatePage;
import org.apache.click.control.Checkbox;
import org.apache.click.control.FieldSet;
import org.apache.click.control.Form;
import org.apache.click.control.HiddenField;
import org.apache.click.control.Submit;
import org.apache.click.control.TextField;
import org.apache.click.util.Bindable;
import org.apache.click.control.Select;
import org.apache.click.extras.control.IntegerField;

/* 
 * Class SchaechteEditPage generiert HTML code auf der Seite edit-schaechte.htm
 */

public class SchaechteEditPage extends TemplatePage {

    private final Form form = new Form("form");
    private final HiddenField referrerField = new HiddenField("referrer", String.class);
    private final HiddenField idField = new HiddenField("id", Integer.class);

    // Bindable variables can automatically have their value set by request parameters
    @Bindable protected Integer id;
    @Bindable protected String referrer;

    Schaechte schaechte;

    public SchaechteEditPage() {
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
        TextField nameField = new TextField("name", true);
        IntegerField tiefField = new IntegerField("tief");
        Select leiterId = new Select("leiter_id","Leiter");
        
        idVisibleField.setDisabled(true);
        nameField.setMaxLength(45);
        nameField.setFocus(true);
        leiterId.addAll(HomePage.dataBaseService.getAngestellteName());
        tiefField.setMaxLength(10);
        
        subFieldSet.add(idVisibleField);
        subFieldSet.add(nameField);
        subFieldSet.add(tiefField);
        subFieldSet.add(leiterId);
        subFieldSet.add(new Checkbox("geschlossen"));
        form.setColumns(1);

        form.add(new Submit("ok", "  OK  ", this, "onOkClick"));
        form.add(new Submit("cancel", this, "onCancelClick"));
        
        
    }

    // Event Handlers ---------------------------------------------------------

    /*
     * When page is first displayed on the GET request.
     */
    @Override
    public void onGet() {
        if (id != null) {
            schaechte = HomePage.dataBaseService.getSchaechteForID(id);
            if (schaechte != null) {
                // Copy customer data to form. The idField value will be set by this call
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
            schaechte = HomePage.dataBaseService.getSchaechteForID(id);
            if (schaechte == null) {
                schaechte = new Schaechte();
            }
            form.copyTo(schaechte);
            HomePage.dataBaseService.commitChange();
            if (referrer != null) {
                setRedirect(referrer);
            } else {
                setRedirect(HomePage.class);
            }
        }
        return true;
    }

    public boolean onCancelClick() {
        if (referrer != null) {
            setRedirect(referrer);
        } else {
            setRedirect(HomePage.class);
        }
        return true;
    }

}