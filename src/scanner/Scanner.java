
package scanner;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author kfilbrun
 */
public class Scanner {
    
    private BufferedReader br;
    private String curLine;
    private int curPos = 0;
    private final int MAXTOKENLEN = 40;
    private StringBuffer tokenString;
    private PrintWriter writer;
    Map<String, TokenType> reservedWords = new HashMap<>();
    
    public Scanner() {
        reservedWords.put("IF", TokenType.IF);
        reservedWords.put("ELSE", TokenType.ELSE);
        reservedWords.put("INT", TokenType.INT);
        reservedWords.put("RETURN", TokenType.RETURN);
        reservedWords.put("VOID", TokenType.VOID);
        reservedWords.put("WHILE", TokenType.WHILE);
        tokenString = new StringBuffer();
    }
    
    private enum StateType {
        START, INASSIGN, INCOMMENT, INNUM, INID, DONE
    }
    
    public void ReadFile(String fileName) throws IOException {
        br = new BufferedReader(new FileReader(fileName));
    }
    
    private void GetNextChar(){
        curPos++;
    }
    
    private char PeekChar() {
        try {
            //On first line or end of line
            if(curLine == null || curPos == curLine.length()){
                curLine = br.readLine();
                while(curLine != null && curLine.isEmpty()){
                    curLine = br.readLine();
                }
                curPos = 0;
            }
            //If no next line available, EOF
            if(curLine == null) {
                return '\0';
            }
            return curLine.charAt(curPos);
        }
        catch (Exception ex) {
            //This would only happen if we tried to peek at next line.
            //This should not happen.
            return '\0'; //Should never happen
        }   
    }
    
    public Token GetToken() throws ScannerException {
        TokenType currentTokenType = null;
        tokenString.setLength(0);
        StateType state = StateType.START;
        Boolean save = false;
        
        while(state != StateType.DONE) {
            char c = PeekChar();
            save = false;
            switch(state) {
                case START:
                    if(Character.isDigit(c)){
                        state = StateType.INNUM;
                    }
                    else if(Character.isAlphabetic(c)){
                        state = StateType.INID;
                    }
                    else if(c == ':'){
                        state = StateType.INASSIGN;
                    }
                    else if((c == ' ') || (c == '\t') || (c == '\n')){
                        GetNextChar();
                    }
                    else {
                        state = StateType.DONE;
                        switch(c){
                            case '\0':
                                currentTokenType = TokenType.ENDFILE;
                                break;
                            case '!':
                                GetNextChar();
                                if(PeekChar() == '=') {
                                    currentTokenType = TokenType.NE;
                                }
                                break;
                            case ',':
                                currentTokenType = TokenType.COMMA;
                                GetNextChar();
                                break;
                            case '[':
                                currentTokenType = TokenType.LBRACKET;
                                GetNextChar();
                                break;
                            case ']':
                                currentTokenType = TokenType.RBRACKET;
                                GetNextChar();
                                break;
                            case '{':
                                currentTokenType = TokenType.LBRACE;
                                GetNextChar();
                                break;
                            case '}':
                                currentTokenType = TokenType.RBRACE;
                                GetNextChar();
                                break;
                            case '=':
                                currentTokenType = TokenType.EQ;
                                GetNextChar();
                                if(PeekChar() == '=') {
                                    currentTokenType = TokenType.EE;
                                    GetNextChar();
                                }
                                break;
                            case '<':
                                currentTokenType = TokenType.LT;
                                GetNextChar();
                                if(PeekChar() == '=') {
                                    currentTokenType = TokenType.LE;
                                    GetNextChar();
                                }
                                break;
                            case '>':
                                currentTokenType = TokenType.GT;
                                GetNextChar();
                                if(PeekChar() == '=') {
                                    currentTokenType = TokenType.GE;
                                    GetNextChar();
                                }
                                break;
                            case '+':
                                currentTokenType = TokenType.PLUS;
                                GetNextChar();
                                break;
                            case '-':
                                currentTokenType = TokenType.MINUS;
                                GetNextChar();
                                break;
                            case '*':
                                currentTokenType = TokenType.TIMES;
                                GetNextChar();
                                break;
                            case '/':
                                currentTokenType = TokenType.OVER;
                                GetNextChar();
                                if(PeekChar() == '*') {
                                    state = StateType.INCOMMENT;
                                    GetNextChar();
                                }
                                break;
                            case '(':
                                currentTokenType = TokenType.LPAREN;
                                GetNextChar();
                                break;
                            case ')':
                                currentTokenType = TokenType.RPAREN;
                                GetNextChar();
                                break;
                            case ';':
                                currentTokenType = TokenType.SEMI;
                                GetNextChar();
                                break;
                            default:
                                currentTokenType = TokenType.ERROR;
                                GetNextChar();
                                break;
                        }
                    }
                    break;
                    
                case INCOMMENT:
                    if(c == '*'){
                        GetNextChar();
                        if(PeekChar() == '/') {
                            state = StateType.START;
                            GetNextChar();
                        }
                    }
                    else {
                        GetNextChar();
                    }
                    break;
                    
                case INASSIGN:
                    state = StateType.DONE;
                    if(c == '='){
                        currentTokenType = TokenType.ASSIGN;
                        GetNextChar();
                    }
                    else{
                        currentTokenType = TokenType.ERROR;
                    }
                    break;
                    
                case INNUM:
                    if(!Character.isDigit(c)){
                        if(Character.isAlphabetic(c)){
                            throw new ScannerException("Malformed ID");
                        }
                        state = StateType.DONE;
                        currentTokenType = TokenType.NUM;
                    }
                    else {
                        save = true;
                        GetNextChar();
                    }
                    break;
                case INID:
                    if (!Character.isAlphabetic(c) && !Character.isDigit(c)) {
                       /* backup in the input */
                       state = StateType.DONE;
                       currentTokenType = TokenType.ID;
                    }
                    else {
                        save = true;
                        GetNextChar();
                    }
                    break;
                default: /* should never happen */
                    state = StateType.DONE;
                    currentTokenType = TokenType.ERROR;
                    throw new ScannerException("Token error in scanner.");
                }
            if ((save) && (tokenString.length() <= MAXTOKENLEN)){
                tokenString.append(c);
            }
            if (state == StateType.DONE && currentTokenType == TokenType.ID){
                currentTokenType = ReservedLookup(tokenString.toString());
            }
        }
        String outStr;
        Token currentToken;
        outStr = currentTokenType.name();
        if(currentTokenType == TokenType.ID || currentTokenType == TokenType.NUM) {
            outStr = outStr + ' ' + tokenString.toString();
            currentToken = new Token(currentTokenType, tokenString.toString());
        }
        else
        {
            currentToken = new Token(currentTokenType, null);
        }
        PrintToken(outStr);
        if(currentTokenType == TokenType.ENDFILE) {
            writer.close();
        }
        return currentToken;
    }
    
    private TokenType ReservedLookup(String str) {
        TokenType token = reservedWords.get(str.toUpperCase());
        return (token == null) ? TokenType.ID : token;
    }
    
    public class ScannerException extends Exception {
        public ScannerException(String message) {
            super(message);
        }
    }
    
    private void PrintToken(String s)
    {
        if(writer == null) {
            try {
                writer = new PrintWriter("tokens.txt", "UTF-8");
            }
            catch(Exception ex) {
                //Whoops
            }
        }
        writer.println(s);
    }
}