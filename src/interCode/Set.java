/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package interCode;
import java.io.IOException;
import symbols.*;
/**
 *
 * @author vindyani
 */
public class Set extends Stmt {
    public Id id;
    public Expr expr;
    public Type type;
    public Set(Id i, Expr ex) {
        this.id = i;
        this.expr = ex;
        if(check(i.type, ex.type) == null){
            error ("type error narrowing not allowed!");
        }
    }
    public Type check(Type t1, Type t2){
        if(t1 == Type.max(t1, t2)){
        return t1;
        }else{return null;}
        
    }
    @Override
    public void gen(){            
            Expr t = expr.gen(); //generate RHS of the assignemnt (stmt) -- evaluateinternal nodes
            Expr rt = t.reduce(); // evaluate root
            type = symbols.Type.max(id.type,expr.type); // get maxmimum type of RHS and LHS
            rt = widen(rt,rt.type,type); // do widening for evaluated EHS expression
            emit(id .toString() +  " = "+ rt.toString() );
    }

    public Expr widen(Expr a,Type t, Type w){
         if(t.lexeme.equals(w.lexeme)) return a;
         else if(t == Type.Int && w == Type.Float){
             Temp temp = new Temp(t);
             emit(temp.toString()+ " = (float)" + a.toString());
             return temp;
         }
         else error("Type mismatch");
         return null;
     }
}
