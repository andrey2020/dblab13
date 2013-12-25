
package de.dblab.page;

import org.apache.click.Page;
import org.apache.click.util.ClickUtils;

public class TemplatePage extends Page {

    private static final long serialVersionUID = 1L;

    public TemplatePage() {
        String className = getClass().getName();

        String shortName = className.substring(className.lastIndexOf('.') + 1);
        String title = ClickUtils.toLabel(shortName);
        addModel("title", title);

        String srcPath = className.replace('.', '/') + ".java";
        addModel("srcPath", srcPath);
    }

    @Override
    public void onInit() {
        super.onInit();
    }

    @Override
    public void onDestroy() {
        // Remove menu for when BorderPage is serialized
     //   if (button != null) {
     //       removeControl(button);
     //   }
    }

    @Override
    public String getTemplate() {
        return "/template.htm";
    }

}
