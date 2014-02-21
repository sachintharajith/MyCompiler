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
public class Temp extends Expr {
    static int count = 0;
    int number = 0;
    public Temp(Type p) {
        super(new Word(Tag.TEMP, "t"),p);
        number = count++;
    }
    @Override
    public String toString(){
        return "t" + number;
    }
}
