/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package interCode;
import lexer.Token;
import symbols.*;
/**
 *
 * @author vindyani
 */
public class Arith extends Expr{
    public Expr exprl , expr2 ;
    public Arith (Token tok , Expr x1 , Expr x2) {
        super(tok , Type.max (x1.type , x2.type));
        exprl = x1; expr2 = x2;       
    }
    
    @Override
    public Expr gen(){        
        return new Arith (op, exprl.reduce(), expr2.reduce());
    }
    @Override
    public Expr reduce(){
        exprl = widen(exprl, exprl.type, type);
        expr2 = widen(expr2, expr2.type, type);
        Expr x = gen();
        Temp t = new Temp(type);
        emit(t.toString()+ " = " + x.toString());
        return t;
    }
    @Override
    public String toString () {
        return exprl.toString()+" "+op.toString()+" "+expr2.toString();
    }
    public Expr widen(Expr a,Type t, Type w){
         if(t.lexeme.equals(w.lexeme)) return a;
         else if(t == Type.Int && w == Type.Float){
             Temp temp = new Temp(type);
             emit(temp.toString()+ " = (float)" + a.toString());
             return temp;
         }
         else error("Type mismatch");
         return null;
     }
}