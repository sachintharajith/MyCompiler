/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package interCode;

import lexer.Token;
import symbols.Type;

/**
 *
 * @author vindyani
 */
public class Op extends Expr{
    public Op(Token tok, Type p) {
        super(tok, p);
    }
    @Override
    public Expr reduce(){
        Expr x = gen();
        Temp t = new Temp(type);
        System.out.println(t.toString()+ " = " + x.toString());
        return t;
    }
}
