package de.dblab.service.cayenne;


import org.apache.cayenne.exp.Expression;
import org.apache.cayenne.exp.ExpressionFactory;


/**
 * @param <E>
 *            The type this property returns.
 * @since 3.2
 */
public class Property<E> extends org.apache.cayenne.exp.Property<E>{

    /**
     * Name of the property in the object
     * showName of the property in the object
     * id of the property in the object
     */
    private String titleName = "";
    private int id = -1;
    
    
    public Property(String name, String titleName, int id) {
        super(name);
        this.titleName = titleName;
        this.id = id;
    }
    
    public Property(String name) {
        super(name);
    }

    
   
    public String getTitleName() {
        return titleName;
    }
    
    public int getId() {
        return id;
    }
     
    public Expression likeIgnoreCaseExp(E value) {
        Expression exp;
        E nValue = (E) value;
        if (nValue.getClass() == Integer.class){
            exp = ExpressionFactory.likeIgnoreCaseExp(getName(), (E) value);
        }else{
            exp = ExpressionFactory.likeIgnoreCaseExp(getName(), value);
        }
        return exp;
    }

}
