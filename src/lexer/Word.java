/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package lexer;

/**
 *
 * @author vindyani
 */
public class Word extends Token {
    public final String lexeme;
    public Word(int t, String lexeme) {
        super(t);
        this.lexeme = new String(lexeme);
    }
}
