/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package parser;
import java.io.*;
import java.util.*;
import lexer.*;
import symbols.*;
import interCode.*;

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
    private Expr e;
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
            Expr t = e.gen();
            t.reduce();
            //System.out.println(t.type.lexeme);
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
                Expr e = expression();
               // e.
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

   private Expr expression() throws IOException{
            e =term();
            e = expressionp(e);
            //System.out.println(e.toString());
            return e;
   }

   private Expr expressionp(Expr e) throws IOException{
        while(look.tag == '+'){
            Token t = look;
            move();
            //System.out.print("+ ");    
            e = new Arith(t, e, term());
            
        }
       // System.out.println(e.toString());
        return e;
   }
   private Expr term() throws IOException{
            e = factor();
            return termp(e);
   }
   private Expr termp(Expr e) throws IOException{
        while(look.tag == '*'){
            Token t = look;
            move();       
            e = new Arith(t, e, factor());           
        }
        return e;
   }
   private Expr factor() throws IOException{
       Expr x = null;
        switch(look.tag){
            case '(':
                match('(');
                x = expression();
                match(')');
                return x;
            case Tag.NUM:                   //case for integers
                Num num = (Num)look;
               // System.out.print(num.value+" ");
                x = new Expr(look,Type.Int);
                move();
                return x;
            case Tag.ID:
                Word w = (Word)look;
                if(symbolt.get(w.lexeme) == null){
                    error (w.lexeme + " not defined") ;
                }
                System.out.print(w.lexeme+" ");
                x = new Expr(look,Type.Float);
                move();         //case for identifiers
                return x;
            case Tag.REAL:
                Real real = (Real)look;
               // System.out.print(real.value+" ");
                x = new Expr(look,Type.Float);
                move();        //case for floating point number
                return x;
            default:
              error ("syntax error");
              return x;
        }
   }

   private void writeToFile(BufferedWriter bw,String s) throws IOException{
       bw.write(s);
   }
}

