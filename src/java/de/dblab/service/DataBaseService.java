package de.dblab.service;


import java.util.List;
import org.apache.cayenne.ObjectContext;
import org.apache.cayenne.query.SelectQuery;
import de.dblab.domain.Angestellte;
import de.dblab.domain.Schaechte;
import de.dblab.domain.Zeit;
import org.apache.cayenne.BaseContext;
//import org.springframework.stereotype.Component;

/**
 * Provides a Angestellte Service.
 *
 * @see Angestellte
 */
//@Component
public class DataBaseService {

    ObjectContext context = BaseContext.getThreadObjectContext();

    

    public List<Angestellte> getAngestelltes(int type, Object value, String etlassente) {


        SelectQuery query = new SelectQuery(Angestellte.class, Angestellte.getExpressionAngestellte(type,value));
        query.andQualifier(Angestellte.getStateAngestellte().get(etlassente));
      
        return context.performQuery(query);

    }
    

    
    public void saveAngestellte(Angestellte angestellte) {
           }


    public Angestellte getAngestellteForID(Integer id) {
        return (Angestellte) context.performQuery(new SelectQuery(Angestellte.class, Angestellte.ID.eq(id))).get(0);
    }
    public Schaechte getSchaechteForID(Integer id) {
        return (Schaechte) context.performQuery(new SelectQuery(Schaechte.class, Schaechte.ID.eq(id))).get(0);
    }
 }
