/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package lexer;
import symbols.*;
import java.io.*;
import java.util.*;
/**
 *
 * @author vindyani
 */
public class Lexer {
    public static int line = 1;
    char peek = ' ';
    Hashtable words = new Hashtable ( ) ;
    InputStream in;
    void reserve(Word w){
        words.put(w.lexeme, w);
    }

    public Lexer(File sourceFile) throws IOException{
        in = new FileInputStream(sourceFile);
        reserve(Type.Int);
        reserve(Type.Float);
    }

    void readch() throws IOException{
        peek = (char)in.read();
    }

    public Token scan() throws IOException{
        for(; ;readch()){
            if(peek == ' ' || peek == '\t' || peek == '\r'){
                continue;
            }else if(peek == '\n'){
                line++;
                continue;
            }else
                break;
        }
        if(Character.isDigit(peek)){
            int v = 0;
            do{
                v = v*10+ Character.digit(peek, 10);
                readch();
            }while(Character.isDigit(peek));
            if(peek != '.'){
                //System.out.println(v);
                return new Num(v);
            }
            int d = 10;
            float real = v;
            for(;;){
                readch();
                if(!Character.isDigit(peek)){
                    break;
                }
                real = real + (float)Character.digit(peek, 10)/d;
                d = d*10;
            }
            //System.out.println(real);
            return new Real(real);
        }if(Character.isLetter(peek)){
            if(Character.isUpperCase(peek)){
             throw new Error("Upper case letters are not allowed!");
            }
            StringBuffer b = new StringBuffer() ;
            do {
                b.append (peek);
                readch();
            } while(Character.isLetterOrDigit(peek));
            String s = b.toString() ;
            Word w = (Word)words.get(s);
            if (w!=null) {
                //System.out.println(w.lexeme);
                return w;
            }
            if(s.length()>1){
             throw new Error("identifier with more than one character is not allowed");
            }
            w = new Word(Tag.ID,s);
            words.put(s, w);
            //System.out.println(w.lexeme);
            return w;
        }
        Token tok = new Token(peek);
        peek = ' ';
        //System.out.println(tok.toString());
        return tok ;
    }
}
