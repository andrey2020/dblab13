/** 
 * Hochschule Offenburg, Dezember 2013
 * Databanken Labor 3, Gruppe 13
 * @author Nikolaev Andrey & Ostrovskaya Anna
 */

package de.dblab.page.angestellte;
import de.dblab.domain.Angestellte;
import de.dblab.page.HomePage;
import de.dblab.page.TemplatePage;
import org.apache.click.control.Checkbox;
import org.apache.click.control.FieldSet;
import org.apache.click.control.Form;
import org.apache.click.control.HiddenField;
import org.apache.click.control.Submit;
import org.apache.click.control.TextField;
import org.apache.click.extras.control.IntegerField;
import org.apache.click.util.Bindable;
import net.sf.click.extras.control.CalendarField;

/* 
 * Class AngestellteEditPage generiert HTML code auf der Seite edit-angestellte.htm
 */

public class AngestellteEditPage extends TemplatePage {

    private Form form = new Form("form");
    private HiddenField referrerField = new HiddenField("referrer", String.class);
    private HiddenField idField = new HiddenField("id", Integer.class);

    // Bindable variables can automatically have their value set by request parameters
    @Bindable protected Integer id;
    @Bindable protected String referrer;

    Angestellte angestellte;

    public AngestellteEditPage() {
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
        TextField nameField = new TextField("name", true);
        TextField nachnameField = new TextField("nachname", true);
        CalendarField  geburtsDatumField = new CalendarField ("geburtsdatum", true);       
        TextField stelleField = new TextField("stelle");
        IntegerField gehaltField = new IntegerField("gehalt");
        
        
        idVisibleField.setDisabled(true);
        nameField.setMaxLength(45);
        nameField.setFocus(true);
        nachnameField.setMaxLength(45);
        geburtsDatumField.setStyle("brown");
        stelleField.setMaxLength(45);
        gehaltField.setMaxLength(10);
        
        subFieldSet.add(idVisibleField);
        subFieldSet.add(nameField);
        subFieldSet.add(nachnameField);
        subFieldSet.add(geburtsDatumField);
        subFieldSet.add(stelleField);
        subFieldSet.add(gehaltField);
        subFieldSet.add(new Checkbox("entlassene"));
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
            angestellte = HomePage.dataBaseService.getAngestellteForID(id);
            if (angestellte != null) {
                // Copy customer data to form. The idField value will be set by this call
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
            angestellte = HomePage.dataBaseService.getAngestellteForID(id);
            if (angestellte == null) {
                angestellte = new Angestellte();
            }
            form.copyTo(angestellte);
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