package de.dblab.service;


import java.util.List;
import org.apache.cayenne.ObjectContext;
import org.apache.cayenne.query.SelectQuery;
import de.dblab.domain.Angestellte;
import de.dblab.domain.Schaechte;
import de.dblab.domain.Zulassung;
import org.apache.cayenne.configuration.server.ServerRuntime;
import org.apache.cayenne.exp.Expression;
import org.apache.cayenne.exp.ExpressionFactory;
//import org.springframework.stereotype.Component;

/**
 * Provides a Angestellte Service.
 *
 * @see Angestellte
 */
//@Component
public class DataBaseService {

    //ObjectContext context = BaseContext.getThreadObjectContext();
    ObjectContext context;
    ServerRuntime runtime;
    
    public void startDataBaseService(){
        if (runtime == null){
        runtime = new ServerRuntime("cayenne-dblab.xml");
        context = runtime.newContext();
        }
     }
    
    public void stopDataBaseService(){
       runtime.shutdown();
     }
    
    public void commitChange(){
        context.commitChanges();
        //stopDataBaseService();
    }
    

    public List<Angestellte> getAngestelltes(int type, Object value, String etlassente) {
    
        startDataBaseService();
        
        SelectQuery query = new SelectQuery(Angestellte.class, Angestellte.getExpressionAngestellte(type,value));
        query.andQualifier(Angestellte.getStateAngestellte().get(etlassente));
      
        return context.performQuery(query);

    }


    public Angestellte getAngestellteForID(Integer id) {
        startDataBaseService();
        return (Angestellte) context.performQuery(new SelectQuery(Angestellte.class, Angestellte.ID.eq(id))).get(0);
    }
    public Schaechte getSchaechteForID(Integer id) {
        startDataBaseService();
        return (Schaechte) context.performQuery(new SelectQuery(Schaechte.class, Schaechte.ID.eq(id))).get(0);
    }

    public List<Schaechte> getVerboteneSchaechte(int angId) {
        startDataBaseService();
        
        Expression exp;
        exp = ExpressionFactory.notInExp(Schaechte.ID.getName(),getAngestellteForID(angId).getSchaechteZulassung());
        SelectQuery query = new SelectQuery(Schaechte.class,exp);
        return context.performQuery(query);
    }
    
     public void saveAngestellte(Angestellte angestellte) {
        startDataBaseService();
        context.registerNewObject(angestellte);
        context.commitChanges();
    }

    public void addZullassung(int angId, int schId) {
        startDataBaseService();
        Zulassung zulassung = new Zulassung();
        zulassung.setAngestellteId(angId);
        zulassung.setSchaechteId(schId);
        context.registerNewObject(zulassung);
        context.commitChanges();
    }
 }
