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
public class Arith extends Op{
    public Expr exprl , expr2 ;
    public Arith (Token tok , Expr xl , Expr x2) {
        super(tok , null); exprl = xl; expr2 = x2;
        type = Type.max (exprl.type , expr2.type);
        exprl = widen(exprl, exprl.type, type);
        expr2 = widen(expr2, expr2.type, type);
    }
    
    @Override
    public Expr gen(){
        return new Arith (op, exprl.reduce(), expr2.reduce());
    }
    @Override
    public String toString () {
        return exprl.toString()+" "+op.toString()+" "+expr2.toString();
    }
     public Expr widen(Expr a,Type t, Type w){
         if(t.lexeme.equals(w.lexeme)) return a;
         else if(t == Type.Int && w == Type.Float){
             Temp temp = new Temp(type);
             System.out.println(temp.toString()+ " = (float)" + a.toString());
             return temp;
         }
         else error("Type mismatch");
         return null;
     }
}