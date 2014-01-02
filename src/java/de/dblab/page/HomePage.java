
package de.dblab.page;

import de.dblab.domain.Angestellte;
import de.dblab.domain.Schaechte;
import de.dblab.domain.Zeit;
import java.util.Date;

import java.util.List;
import org.apache.cayenne.BaseContext;
import org.apache.cayenne.CayenneDataObject;
import org.apache.cayenne.ObjectContext;
import org.apache.cayenne.configuration.server.ServerRuntime;
import org.apache.cayenne.exp.Expression;
import org.apache.cayenne.exp.ExpressionFactory;
import org.apache.cayenne.query.SelectQuery;


public class HomePage extends TemplatePage {
    
    

    
    
    private static final long serialVersionUID = 1L;
    public static final DataBaseService dataBaseService = new DataBaseService();
    public HomePage() {
        getModel().put("title", "Bericht Projektlabor");
    }

}
