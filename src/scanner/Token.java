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
public class Token {
    
    public Token(TokenType t, String s){
        type = t;
        value = s;
    }
    
    public TokenType type;
    public String value; 
}
