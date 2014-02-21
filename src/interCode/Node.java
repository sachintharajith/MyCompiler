/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package interCode;

/**
 *
 * @author vindyani
 */

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
        System.out.println (" \t " + s );
    }
}