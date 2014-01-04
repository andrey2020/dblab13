/** 
 * Hochschule Offenburg, Dezember 2013
 * Databanken Labor 3, Gruppe 13
 * @author Nikolaev Andrey & Ostrovskaya Anna
 */

package de.dblab.page;

import org.apache.click.Page;
import org.apache.click.util.ClickUtils;

/* 
 * Class TemplatePage generiert HTML code template.htm
 */

public class TemplatePage extends Page {

    public TemplatePage() {
        String className = getClass().getName();
        String shortName = className.substring(className.lastIndexOf('.') + 1);
        String title = ClickUtils.toLabel(shortName);
        addModel("title", title);
    }

    @Override
    public String getTemplate() {
        return "/template.htm";
    }

}
