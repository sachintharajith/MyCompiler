/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package compilerproject;
import lexer.*;
import Parser.*;
/**
 *
 * @author vindyani
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        Lexer l = new Lexer("input.txt");
        Parser p = new Parser(l);
        p.program();
    }

}
