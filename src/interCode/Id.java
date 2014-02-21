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
public class Id extends Expr {
    public int offset; //relative address
    public Id (Word id , Type p , int b) {
        super ( id , p );
        offset = b ;
    }
    @Override
    public Expr gen() {return this;}
    @Override
    public Expr reduce () {return this;}
}
