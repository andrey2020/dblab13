/** 
 * Hochschule Offenburg, Dezember 2013
 * Databanken Labor 3, Gruppe 13
 * @author Nikolaev Andrey & Ostrovskaya Anna
 */

package de.dblab.page;

/* 
 * Class HomePage generiert HTML code auf der Seite index.htm
 */

public class HomePage extends TemplatePage {
    
    public static final DataBaseService dataBaseService = new DataBaseService();
     
    public HomePage() {
        getModel().put("title", "Bericht Projektlabor");
    }

}
