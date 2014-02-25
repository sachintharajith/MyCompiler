/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package symbols;
import lexer.*;
import interCode.*;
import com.sun.org.apache.xalan.internal.xsltc.runtime.Hashtable;

/**
 *
 * @author vindyani
 * This class maintains a sumbol tables for identifiers
 * It has methods to put an identifier to symbol table and get the Id corresponding to a given token
 */
public class SymbolT {
    public static Hashtable table = new Hashtable();

    public static void add(Token t, Id i){
        table.put(t, i);
    }
    public static Id get(Token t){
        Id id = (Id)table.get(t);
        if(id!=null) return id;
        else return null;
    }
}
