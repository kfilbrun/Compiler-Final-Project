/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scanner;

/**
 *
 * @author kfilbrun
 */
public enum TokenType {
    //book-keeping tokens
    ENDFILE, ERROR,
    //reserved words
    IF, ELSE, INT, RETURN, VOID, WHILE,
    //multichar tokens
    ID, NUM,
    //special symbols
    ASSIGN, EQ, EE, LT, LE, GT, GE, PLUS, MINUS, TIMES, OVER, LPAREN,
    NE, COMMA, RPAREN, SEMI, LBRACE, RBRACE, LBRACKET, RBRACKET;
}
