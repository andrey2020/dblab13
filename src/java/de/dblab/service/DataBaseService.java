package de.dblab.service;


import java.util.List;
import org.apache.cayenne.ObjectContext;
import org.apache.cayenne.configuration.server.ServerRuntime;
import org.apache.cayenne.query.SelectQuery;
import de.dblab.domain.Angestellte;
import org.apache.cayenne.BaseContext;
//import org.springframework.stereotype.Component;

/**
 * Provides a Angestellte Service.
 *
 * @see Angestellte
 */
//@Component
public class DataBaseService {
   // ObjectContext context;
    //ServerRuntime runtime;
    ObjectContext context = BaseContext.getThreadObjectContext();
    public void startDataBaseService(){
     //   runtime = new ServerRuntime("cayenne-dblab.xml");
     //   context = runtime.newContext();
     }
    
    public void stopDataBaseService(){
      //  runtime.shutdown();
     }
    

    public List<Angestellte> getAngestelltes(int type, Object value, String etlassente) {
        startDataBaseService();

        SelectQuery query = new SelectQuery(Angestellte.class, Angestellte.getExpressionAngestellte(type,value));
        query.andQualifier(Angestellte.getStateAngestellte().get(etlassente));
      
        List<Angestellte>  temp = (List<Angestellte>) context.performQuery(query);
        
        stopDataBaseService();
        return temp;
    }

    

    
    public void saveAngestellte(Angestellte angestellte) {
           }

    public Angestellte getAngestellteForID(Integer id) {
        startDataBaseService();
        
        List<Angestellte>  temp = 
                (List<Angestellte>) context.performQuery(
                        new SelectQuery(Angestellte.class, Angestellte.ID.eq(id)));
        stopDataBaseService();
        
        return (Angestellte) temp.get(0);
    }
    
 }
