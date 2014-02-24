/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package parser;
import java.util.*;
import java.io.*;
import lexer.*;
import symbols.*;
import interCode.*;
import java.util.Stack;

/**
 *
 * @author vindyani
 */
public class Parser{
    private Lexer lex;    //lexer to get tokens
    private Token look;     //current lookahead token
    private Boolean movable = true;
    private Token front = null;
    private BufferedWriter bw1;
    public static BufferedWriter bw2;
    private Expr e;
    private int offset = 0; //relative offset for each variable
    private Stack <Double> machine = new Stack<Double>();
    private Hashtable<Token,Double> var= new Hashtable<Token,Double>();
    private Token Rhs;
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
        Token tok = look;
        match(Tag.BASIC);
        Type p = (Type)tok;
        return p;

    }
    private void id(Type t) throws IOException{
        Token tok = look;
        match(Tag.ID);
        Id id = new Id((Word)tok,t,offset);
        SymbolT.add(tok,id); //add variable to symbol table
        offset += t.width;      
        idp(t);
    }
    private void idp(Type t) throws IOException{
        while(look.tag == ','){
            move();
            Token tok = look;
            match(Tag.ID);
            Id id = new Id((Word)tok,t,offset);
            SymbolT.add(tok,id); //add variable to symbol table
        }
    }

    //start of expression parsing
    private void list() throws IOException{
        while(look.tag != Tag.EOF){
            stmt();
            match(';');
            emit(bw1, "; ");
            Number num = machine.pop();
            if(SymbolT.get(Rhs).type == Type.Int){
                emit(bw1,Integer.toString(num.intValue()));
            }else if(SymbolT.get(Rhs).type == Type.Float){
                emit(bw1,num.toString());
            }
            
            bw1.newLine();
        }
        bw1.close();
        bw2.close();
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
            Expr t = e.gen();
            t.reduce();
       }else if( look.tag == Tag.ID){
            Token temp = look;
            match(Tag.ID);
            Word w = (Word)temp;
            emit(bw1,w.lexeme+" ");
            if(look.tag == '='){
                Rhs = temp;
                move();
                Id id = SymbolT.get(temp);
                if(id == null){
                    error (w.lexeme + " not defined") ;
                }
                Stmt s =new interCode.Set(id,expression());
                emit(bw1,"= ");
                var.put(w,machine.peek());
                s.gen();
            }else{
                movable = false;
                front = look;
                look = temp;
                expression();
                Expr t = e.gen();
                t.reduce();
            }
       }else
           error ("syntax error") ;
   }

   private Expr expression() throws IOException{
            e =term();
            e = expressionp(e);
            return e;
   }

   private Expr expressionp(Expr e) throws IOException{
        while(look.tag == '+'){
            Token t = look;
            move();
            e = new Arith(t, e, term());
            emit(bw1,"+ ");
            machinePop(t);
        }
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
            emit(bw1,"* ");
            machinePop(t);
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
                emit(bw1,num.value+" ");
                x = new Expr(look,Type.Int);
                machine.push((double)num.value);
                move();
                return x;
            case Tag.ID:
                x = SymbolT.get(look);
                Word w = (Word)look;
                if(x == null){
                    error (w.lexeme + " not defined") ;
                }
                emit(bw1,w.lexeme+" ");
                double val = 0;
                if(var.get(w) != null){
                    val=var.get(w);
                }
                machine.push(val);
                move();         //case for identifiers
                return x;
            case Tag.REAL:
                Real real = (Real)look;
                emit(bw1,real.value+" ");
                x = new Expr(look,Type.Float);
                machine.push((double)real.value);
                move();        //case for floating point number
                return x;
            default:
              error ("syntax error");
              return x;
        }
   }

   private void emit(BufferedWriter bw,String s) throws IOException{
       bw.write(s);
   }

   private void machinePop(Token operator){
        double num1 =  machine.pop(), num2 = machine.pop(), result = 0;
        switch(operator.tag){
            case '+':
                result = num1+num2;
                break;
            case '*':
                result = num1*num2;
                break;
            default:
                error("something went wrong!");

        }
        machine.push(result);
   }
}

