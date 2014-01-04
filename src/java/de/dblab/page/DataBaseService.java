/** 
 * Hochschule Offenburg, Dezember 2013
 * Databanken Labor 3, Gruppe 13
 * @author Nikolaev Andrey & Ostrovskaya Anna
 */

package de.dblab.page;

import de.dblab.domain.Angestellte;
import de.dblab.domain.Schaechte;
import de.dblab.domain.Zeit;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import org.apache.cayenne.BaseContext;
import org.apache.cayenne.CayenneDataObject;
import org.apache.cayenne.ObjectContext;
import org.apache.cayenne.exp.Expression;
import org.apache.cayenne.exp.ExpressionFactory;
import org.apache.cayenne.query.SelectQuery;

/* 
 * Class DataBaseService beschreibt Zusammenwirken zwischen Databankenobjekten und Javaobjekten
 */

public class DataBaseService {
    ObjectContext context;
    //ServerRuntime runtime;
    
    public void startDataBaseService(){
        if (context == null){
            //runtime = new ServerRuntime("cayenne-dblab.xml");
            //contex = runtime.newContext();//BaseContext.getThreadObjectContext();
            context = BaseContext.getThreadObjectContext();
        }
     }
    
    public void commitChange(){
        startDataBaseService();
        context.commitChanges();
    }
    
    public List<Angestellte> getAngestelltes(int type, Object value, String etlassente) {
        startDataBaseService();
        SelectQuery query = new SelectQuery(Angestellte.class, Angestellte.getExpressionAngestellte(type,value));
        query.andQualifier(Angestellte.getStateAngestellte().get(etlassente));
        return context.performQuery(query);
    }

    public List<Schaechte> getSchaechte(int type, Object value, String aktiv) {
        startDataBaseService();
        SelectQuery query = new SelectQuery(Schaechte.class, Schaechte.getExpressionSchaechte(type,value));
        query.andQualifier(Schaechte.getStateSchaechte().get(aktiv));
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
        Expression exp = ExpressionFactory.notInExp(Schaechte.ID.getName(),getAngestellteForID(angId).getSchaechteZulassung());
        SelectQuery query = new SelectQuery(Schaechte.class,exp);
        return context.performQuery(query);
    }

     public void saveObject(CayenneDataObject Obj){
        startDataBaseService();
        context.registerNewObject(Obj);
        context.commitChanges();
     }

    public List<Zeit> getZeitFromAngestellte(int angId) {
        startDataBaseService();
        Expression exp = ExpressionFactory.likeExp(Zeit.TO_ANGESTELLTE.getName(),angId);
        SelectQuery query = new SelectQuery(Zeit.class, exp);
        return context.performQuery(query);
        
    }

    public void beginArbeit(Integer angId, int schachtId) {
        startDataBaseService();
        Zeit z = new Zeit();
        z.setZeitEingang(new Date(System.currentTimeMillis()));
        z.setToAngestellte(this.getAngestellteForID(angId));
        z.setToSchaechte(this.getSchaechteForID(schachtId));
        context.registerNewObject(z);
        context.commitChanges();
    }

    public List<Angestellte> getAngestellteMoeglich(int schachtId) {
        Expression ex = ExpressionFactory.notInExp(Angestellte.ID.getName(), 
                (List<Angestellte>) context.performQuery(
                new SelectQuery(Angestellte.class, 
                ExpressionFactory.matchExp("zeitArray.zeitAusgang", null)))).andExp(
                ExpressionFactory.likeExp("schaechteZulassung.id",schachtId));
        SelectQuery query = new SelectQuery(Angestellte.class, ex);
        return (List<Angestellte>) context.performQuery(query);
    }

    public void endeArbeit(Integer angId) {
        startDataBaseService();
        Expression ex = ExpressionFactory.likeExp(Zeit.TO_ANGESTELLTE.getName(), angId);
        Expression ex2=ExpressionFactory.matchExp(Zeit.ZEIT_AUSGANG.getName(), null).andExp(ex);
        SelectQuery query = new SelectQuery(Zeit.class, ex2);
        Zeit z=(Zeit) context.performQuery(query).get(0);
        z.setZeitAusgang(new Date(System.currentTimeMillis()));
        context.commitChanges();
    }

    public List<Zeit> getAngestellteImSchacht(int schachtId) {
        Expression ex2 = Zeit.TO_SCHAECHTE.eq(getSchaechteForID(schachtId));
        Expression ex = ExpressionFactory.matchExp(Zeit.ZEIT_AUSGANG.getName(), null).andExp(ex2);
        SelectQuery query = new SelectQuery(Zeit.class, ex);
        return (List<Zeit>) context.performQuery(query);
    }

    public void removeAngestellte(int angId) {
        context.deleteObjects(getAngestellteForID(angId));
        commitChange();
    }

    public void removeSchaechte(int schachtId) {
        context.deleteObjects(getSchaechteForID(schachtId));
        context.commitChanges();
    }

    public HashMap<Integer,String> getAngestellteName() {
        List<Angestellte> ang = this.getAngestelltes(1, "%", "im Betrieb");
        HashMap<Integer,String> s= new HashMap<Integer,String>();
        for(int i=0;i<ang.size();i++){
           s.put(ang.get(i).getId(),ang.get(i).getVollname());
        }
        return s;
    }
 }
