/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package symbols;
import lexer.*;
/**
 *
 * @author vindyani
 */
public class Type extends Word {
public int width = 0;
public Type(int tag , String lexeme , int w){
    super (tag,lexeme);
    width = w;
}
public static final Type Int = new Type(Tag.BASIC,"int",4), Float = new Type(Tag.BASIC,"float",8);
}
