/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package interCode;
import symbols.*;
/**
 *
 * @author vindyani
 */
public class Set extends Stmt {
    public Id id;
    public Expr expr;
    public Set(Id i, Expr ex) {
        this.id = i;
        this.expr = ex;
        if(check(i.type, ex.type) == null){
            error ("type error(narrowing not allowed!");
        }
    }
    public Type check(Type t1, Type t2){
        if(t1 == Type.max(t1, t2)){
        return t1;
        }else{return null;}
        
    }
    @Override
    public void gen() {
            Expr t = expr.gen();
            emit(id .toString() +  " = "+ t.reduce());
    }
}
