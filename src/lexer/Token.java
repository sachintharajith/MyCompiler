/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package lexer;

/**
 *
 * @author vindyani
 */
public class Token {
    public final int tag;
    public Token (int t) {
        this.tag = t;
    }
    @Override
    public String toString(){
        return ""+ (char)tag;
    }
}
