MyCompiler
==========

This is a simple compiler which will generate the three address code and post fix notation for a predefined context 
free grammar. 

The grammar is as follows,

  P → D L
  
  D → B N ; D | B N ;
  
  B → int | float
  
  N → N , id | id
  
  L → S ; L | S ;
  
  S → id = E | E
  
  E → E + T | T
  
  T → T × F | F
  
  F → ( E ) | num | id
  
  
The following CFG rules define the syntax of simple infix expressions that can have the basic mathematical operations 
(addition and multiplication) and can have numbers and variables.

In this language, a program consists of some declarations followed by the list of expressions. The declaration section 
will contain declarations of integer (int) or floating point number (float) variables. A valid identifier (for variables)
is a single lowercase English letter. The list of expressions contains assignment operations, additions and 
multiplications.

The compiler consists of,

-- A simple lexer

-- A recursive decent parser

-- SDT for postfix notation generation

-- Stack based implementation for evaluate postfix notations

-- Intermediate code generator to generate three address code

-- IC optimizer


The first three parts are already implemented and currently working on others.

If Any clarifications needed, send me an E-mail to sachintha.rajith@gmail.com
