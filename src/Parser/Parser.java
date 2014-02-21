/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Parser;
import java.io.*;
import java.util.*;
import lexer.*;
import symbols.*;

/**
 *
 * @author vindyani
 */
public class Parser{
    private Lexer lex;    //lexer to get tokens
    private Token look;     //current lookahead token
    public static Hashtable symbolt= new Hashtable();
    private Boolean movable = true;
    private Token front = null;
    private BufferedWriter bw1;
    private BufferedWriter bw2;
    public Parser (Lexer l) throws IOException{
        lex = l;
        bw1 =new BufferedWriter(new FileWriter("PostFix.txt"));
        bw2 =new BufferedWriter(new FileWriter("ThreeAddress.txt"));
        move();
    }
    void move() throws IOException {
        if(movable){
            look = lex.scan();
        }else{
            look = front;
            movable = true;
        }
    }
    void error(String s){
        throw new Error ("near line" +Lexer.line+" : " +s);
    }
    void match(int t) throws IOException {
        if(look.tag == t){
            move();
        }
        else error ("syntax error") ;
    }

    public void program() throws IOException{
        declaration();
        list();
    }

    private void declaration() throws IOException {
        if(look.tag != Tag.BASIC){
          error ("syntax error") ;
        }
        while(look.tag == Tag.BASIC){
          Type t = type();
          id(t);
          match(';');
        }
    }
    private Type type() throws IOException{
        Type p = null;
        try {
         p = (Type)look;
        } catch (Exception e) {
        }
        match(Tag.BASIC);
        return p;

    }
    private void id(Type t) throws IOException{
        Word w = (Word)look;
        match(Tag.ID);
        symbolt.put(w.lexeme,t); //add variable name and type object to symbol table
        idp(t);
    }
    private void idp(Type t) throws IOException{
        while(look.tag == ','){
            Word w = null;
            match(',');
            try {
               w = (Word)look;
            } catch (Exception e) {
            }
            match(Tag.ID);
            symbolt.put(w.lexeme,t); //add variable name and type object to symbol table
        }
    }

    //start of expression parsing
    private void list() throws IOException{
        while(look.tag != Tag.EOF){
            stmt();
            match(';');
            System.out.print("\n");
        }
    }

   private void stmt() throws IOException{
       /*S->id=E | E if it starts with '(' or num then S->E is applied
        * if it starts with id check for the next token while storing the current token in temporary variable
        * if the next token is '=' then S->id=E is applied
        * Else S->E is aaplied with backtracking as follows
        * save current lookahead token in fron variable
        * replace look with temporar, which is the previous token
        * Call E() with "movable" false.(That is match with "look" without moving)
        * restore look with front at match function and enable "movable"
        */
       if(look.tag == '(' || look.tag == Tag.NUM || look.tag == Tag.REAL){
            expression();
       }else if( look.tag == Tag.ID){
            Token temp = look;
            match(Tag.ID);
            Word w = (Word)temp;
            System.out.print(w.lexeme+" ");
            if(look.tag == '='){
                match('=');
                expression();
                System.out.print("= ");
            }else{
                movable = false;
                front = look;
                look = temp;
                expression();
            }
       }else
           error ("syntax error") ;
   }

   private void expression() throws IOException{
            term();
            expressionp();
   }

   private void expressionp() throws IOException{
        while(look.tag == '+'){
            move();
            term();
            System.out.print("+ ");
        }
   }
   private void term() throws IOException{
            factor();
            termp();
   }
   private void termp() throws IOException{
        while(look.tag == '*'){
            move();
            factor();
            System.out.print("* ");
        }
   }
   private void factor() throws IOException{
        switch(look.tag){
            case '(':
                match('(');
                expression();
                match(')');
                break;
            case 256:                   //case for integers
                Num num = (Num)look;
                System.out.print(num.value+" ");
                match(Tag.NUM);
                break;
            case 257:
                Token temp = look;
                move();         //case for identifiers
                Word w = (Word)temp;
                if(symbolt.get(w.lexeme) == null){
                    error (w.lexeme + " not defined") ;
                }
                System.out.print(w.lexeme+" ");
                break;
            case 259:
                Real real = (Real)look;
                System.out.print(real.value+" ");
                match(Tag.REAL);        //case for floating point number 
                break;
            default:
              error ("syntax error");
        }
   }

   private void writeToFile(BufferedWriter bw,String s) throws IOException{
       bw.write(s);
   }
}

