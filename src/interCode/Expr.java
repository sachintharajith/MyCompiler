/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package interCode;
import lexer.*;
import symbols.*;
/**
 *
 * @author vindyani
 */
public class Expr extends Node {
    public Token op;
    public Type type;

    public Expr(Token op, Type type) {
        this.op = op;
        this.type = type;
    }
    public Expr gen() {
        return this;
    }
    public Expr reduce(){
        return this;
    }
}