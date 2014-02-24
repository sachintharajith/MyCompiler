/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package interCode;

/**
 *
 * @author vindyani
 */

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import lexer.*;
public class Node{
    int lexline = 0 ;
    Node () {
        lexline = Lexer.line ;
    }
    void error (String s) {
        throw new Error("near line "+lexline+ ": "+s);
    }
    public void emit (String s ){
        System.out.println (s);
        try {
            parser.Parser.bw2.write(s);
            parser.Parser.bw2.newLine();
        } catch (IOException ex) {
            Logger.getLogger(Node.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}