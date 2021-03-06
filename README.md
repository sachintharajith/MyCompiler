MyCompiler
==========

Acknowledgement
---------------

Some of the design and implementation of this project were adopted by Dragon Book (Compilers: Principles, Techniques, and by Alfred V. Aho, Monica S. Lam, Ravi Sethi, and Jeffrey D. Ullman)

Introduction
------------

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


Features
--------

The compiler consists of,

-- A simple lexer

-- A recursive decent parser

-- SDT for postfix notation generation

-- Stack based implementation for evaluate postfix notations

-- Intermediate code generator to generate three address code

-- Simple type checker with widening operations and restricting narrowing operations

-- Machine independent code optimizer(global common-sub expression elimination)

How to Run
----------

-- Download the source code

-- Build using apache Ant or netbeans IDE. (You can open the project in NeBeans since the configuration files are          available)

-- Give source file as the input parameter

-- The three address code is displayed in console and Two files will be created with Post Fix notations and three          address code. These will be created in parent directory of the project


The first three parts are already implemented and currently working on others.

If Any clarifications needed, send me an E-mail to sachintha.rajith@gmail.com
