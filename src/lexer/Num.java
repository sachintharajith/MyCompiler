/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package lexer;

/**
 *
 * @author vindyani
 */
public class Num extends Token {
    public final int value;
    public Num (int v) {
        super (Tag.NUM);
        this.value = v;
    }
}
