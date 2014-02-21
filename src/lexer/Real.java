/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package lexer;

/**
 *
 * @author vindyani
 */
public class Real extends Token {
    public final float value ;
    public Real (float v) {
        super (Tag.REAL);
        value = v;
    }
    @Override
    public String toString() { return "" + value ; }
}
