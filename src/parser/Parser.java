package parser;

import scanner.Scanner;
import scanner.Token;
import scanner.TokenType;
import java.io.IOException;
import java.util.ArrayList;

public class Parser {

    //variable declarations
    public Scanner scanner;
    public Token curToken;

    //constructor
    public Parser(String fileName) throws IOException {
        scanner = new Scanner();
        scanner.ReadFile(fileName);
    }

    //exceptions
    public class ParserException extends Exception {

        public ParserException(String message) {
            super(message);
        }
    }

    //helper functions
    private void matchNextToken(TokenType type) throws ParserException {
        //advance the token
        advanceToken();
        //check for match
        if (curToken.type != type) {
            throw new ParserException("unaccepted token in matchNextToken " + curToken.type + " expected " + type);
        }
    }

    private void matchCurToken(TokenType type) throws ParserException {
        //check for match
        if (curToken.type != type) {
            throw new ParserException("unaccepted token in matchCurToken " + curToken.type + " expected " + type);
        }
        //advance the token
        advanceToken();
    }

    private void advanceToken() throws ParserException {
        try {
            curToken = scanner.GetToken();
        } catch (Scanner.ScannerException se) {
            throw new ParserException("ScannerError: " + se.getMessage());
        }

    }

    //parseX functions
    public Program parseProgram() throws ParserException {
        advanceToken();
        Program program = new Program(new ArrayList<Declaration>());

        while (curToken.type == TokenType.INT || curToken.type == TokenType.VOID) {
            program.declarations.add(parseDeclaration());
            //advanceToken();
        }
        if (curToken.type == TokenType.ENDFILE) {
            return program;
        } else {
            throw new ParserException("Unexpected token in parseProgram");
        }
    }

    private Declaration parseDeclaration() throws ParserException {
        ReturnType funDeclReturn;
        if (curToken.type == TokenType.INT) {
            funDeclReturn = ReturnType.INT;
        } else {
            funDeclReturn = ReturnType.VOID;
        }

        switch (curToken.type) {
            case INT:
                advanceToken();
                if (curToken.type != TokenType.ID) {
                    throw new ParserException("Unexpected token in parseDeclaration");
                }
                return parseDeclarationPrime(curToken.value, funDeclReturn);
            case VOID:
                advanceToken();
                if (curToken.type != TokenType.ID) {
                    throw new ParserException("Unexpected token in parseDeclaration");
                }
                advanceToken();
                return parseFunctionDeclarationPrime(curToken.value, funDeclReturn);
            default:
                throw new ParserException("Unexpected token in parseDeclaration");
        }
    }

    private Declaration parseDeclarationPrime(String idString, ReturnType funDeclReturn)
            throws ParserException {
        advanceToken();
        switch (curToken.type) {
            //Essentially an LBRACKET "OR" SEMI
            case LBRACKET:
            
                return parseVarDeclarationPrime(idString);
            case SEMI:
                advanceToken();
                return new Var_Declaration(idString);    
            case LPAREN:
                return parseFunctionDeclarationPrime(idString, funDeclReturn);
            default:
                throw new ParserException("Unexpected token in parseDeclarationPrime");
        }
    }

    private Declaration parseVarDeclarationPrime(String idString) throws ParserException {
        switch (curToken.type) {
            //Essentially an LBRACKET "OR" SEMI
            case LBRACKET:
                matchCurToken(TokenType.LBRACKET);
                int numValue = Integer.parseInt(curToken.value);
                advanceToken();
                matchCurToken(TokenType.RBRACKET);
                matchCurToken(TokenType.SEMI);
                return (Declaration) new Var_Declaration(idString, numValue);
            case SEMI:
                advanceToken();
                return (Declaration) new Var_Declaration(idString);
            default:
                throw new ParserException("Unexpected token in parseVarDeclarationPrime");
        }
    }

    private Declaration parseFunctionDeclarationPrime(String idString, ReturnType funDeclReturn)
            throws ParserException {
        matchCurToken(TokenType.LPAREN);
        ArrayList<Parameter> params = parseParams();
        //We were advanced to sit on the ending rparen by either parseParams
        //on a void or parseParamsList on the end of the parens
        matchCurToken(TokenType.RPAREN);
        Compound_Statement compoundStatement = parseCompoundStatement();
        return (Declaration) new Fun_Declaration(funDeclReturn, idString, params, compoundStatement);
    }

    private ArrayList<Parameter> parseParams() throws ParserException {
        switch (curToken.type) {
            case VOID:
                advanceToken();
                return null;
            case INT:
                return parseParamList();
            default:
                throw new ParserException("Unexpected token in parseParams");
        }
    }

    private ArrayList<Parameter> parseParamList() throws ParserException {
        ArrayList<Parameter> params = new ArrayList<>();
        while (curToken.type == TokenType.INT) {
            params.add(parseParam());
            if (curToken.type == TokenType.RPAREN) {
                return params;
            } else if (curToken.type == TokenType.COMMA) {
                advanceToken();
            } else {
                throw new ParserException("Unexpected token in parseParamList");
            }
        }
        throw new ParserException("Unexpected token in parseParamList");
    }

    //Tested up to here in first pass
    private Parameter parseParam() throws ParserException {
        matchNextToken(TokenType.ID);
        String paramId = curToken.value;
        advanceToken();
        if (curToken.type == TokenType.LBRACE) {
            matchNextToken(TokenType.RBRACE);
            advanceToken();
            return new Parameter(paramId, true);
        } else if (curToken.type == TokenType.COMMA || curToken.type == TokenType.RPAREN) {
            return new Parameter(paramId, false);
        } else {
            throw new ParserException("Unexpected token in parseParam");
        }
    }

    private Compound_Statement parseCompoundStatement() throws ParserException {
        matchCurToken(TokenType.LBRACE);
        ArrayList<Var_Declaration> varDecls = parseLocalDeclarations();
        ArrayList<Statement> statements = parseStatementList();
        matchCurToken(TokenType.RBRACE);
        return new Compound_Statement(varDecls, statements);
    }

    private ArrayList<Var_Declaration> parseLocalDeclarations() throws ParserException {
        ArrayList<Var_Declaration> varDecls = new ArrayList<>();
        while (curToken.type == TokenType.INT) {
            advanceToken();
            String idName = curToken.value;
            matchCurToken(TokenType.ID);
            varDecls.add((Var_Declaration) parseVarDeclarationPrime(idName));
        }
        return varDecls;
    }

    private ArrayList<Statement> parseStatementList() throws ParserException {
        ArrayList<Statement> statements = new ArrayList<>();
        while (curToken.type == TokenType.NUM || curToken.type == TokenType.LPAREN
                || curToken.type == TokenType.ID || curToken.type == TokenType.SEMI
                || curToken.type == TokenType.IF || curToken.type == TokenType.WHILE
                || curToken.type == TokenType.RETURN || curToken.type == TokenType.LBRACE) {
            statements.add(parseStatement());
            //advanceToken(); //**************************************************************************************
        }
        return statements;
    }

    private Statement parseStatement() throws ParserException {
        switch (curToken.type) {
            //falls into parseExpression statement treating this as an OR statement 
            case NUM:
            case LPAREN:
            case ID:
            case SEMI:
                return (parseExpressionStatement());
            case IF:
                return (parseSelectionStatement());
            case WHILE:
                return (parseIterationStatement());
            case RETURN:
                return (parseReturnStatement());
            case LBRACE:
                return (parseCompoundStatement());
            default:
                throw new ParserException("Unexpected token in parseStatement");
        }
    }

    private Statement parseExpressionStatement() throws ParserException {
        switch (curToken.type) {
            case NUM:
            case LPAREN:
            case ID:
                Statement exp = new Expression_Statement(parseExpression());
                matchCurToken(TokenType.SEMI);
                return exp;
            case SEMI:
                matchCurToken(TokenType.SEMI);
                return null;
            default:
                throw new ParserException("Unexpected token in parseExpressionStatatement");
        }
    }

    private Statement parseSelectionStatement() throws ParserException {
        advanceToken();
        matchCurToken(TokenType.LPAREN);
        Expression exp = parseExpression();
        matchCurToken(TokenType.RPAREN);
        Statement if_stmt = parseStatement();
        if (curToken.type == TokenType.ELSE) {
            advanceToken();
            Statement else_stmt = parseStatement();
            return (Statement) new Selection_Statement(exp, if_stmt, else_stmt);
        } else if (curToken.type != TokenType.NUM && curToken.type != TokenType.LPAREN
                && curToken.type != TokenType.ID && curToken.type != TokenType.SEMI
                && curToken.type != TokenType.IF && curToken.type != TokenType.WHILE
                && curToken.type != TokenType.RETURN && curToken.type != TokenType.LBRACE
                && curToken.type != TokenType.RBRACE) {
            throw new ParserException("Unexpected token in parseSelectionStatement");
        } else {
            return (Statement) new Selection_Statement(exp, if_stmt, null);
        }
    }

    private Statement parseIterationStatement() throws ParserException {
        matchCurToken(TokenType.WHILE);
        matchCurToken(TokenType.LPAREN);
        Expression expression = parseExpression();
        matchCurToken(TokenType.RPAREN);
        Statement statement = parseStatement();
        return new Iteration_Statement(expression, statement);
    }

    private Statement parseReturnStatement() throws ParserException {
        matchCurToken(TokenType.RETURN);
        Expression returnExpr = null;
        if (curToken.type == TokenType.NUM || curToken.type == TokenType.LPAREN || curToken.type == TokenType.ID) {
            returnExpr = parseExpression();
            
        } 
        matchCurToken(TokenType.SEMI);
        return (Statement) new Return_Statement(returnExpr);
        
    }

//Second test Successful Run    
    private Expression parseExpression() throws ParserException {
        switch (curToken.type) {
            case NUM:
                Expression num = parseNum(curToken.value);
                advanceToken();
                return parseSimpleExpressionPrime(num);
            case LPAREN:
                advanceToken();
                Expression exp = parseExpression();
                matchCurToken(TokenType.RPAREN);
                return parseSimpleExpressionPrime(exp);
            case ID:
                String idString = curToken.value;
                advanceToken();
                return parseExpressionPrime(idString);
            default:
                throw new ParserException("Unexpected token in parseExpression");
        }
    }

    private Expression parseExpressionPrime(String idString) throws ParserException {
        if (curToken.type == TokenType.EQ) {
            advanceToken();
            Expression e = parseExpression();
            Variable v = new Variable(idString, null);
            return (Expression) new Assign_Expression(v, e);
        } else if (curToken.type == TokenType.LBRACKET) {
            advanceToken();
            Expression e1 = parseExpression();
            Variable v = new Variable(idString, e1);
            matchCurToken(TokenType.RBRACKET);
            Expression e2 = parseExpressionPrimePrime(v);
            return e2;
        } else if (curToken.type == TokenType.LPAREN) {
            advanceToken();
            ArrayList<Expression> args = parseArgs();
            Call c = new Call(idString, args);
            matchCurToken(TokenType.RPAREN);
            return (Expression) parseSimpleExpressionPrime(c);
        } else if (curToken.type == TokenType.PLUS || curToken.type == TokenType.MINUS
                || curToken.type == TokenType.TIMES || curToken.type == TokenType.OVER
                || curToken.type == TokenType.LE || curToken.type == TokenType.LT
                || curToken.type == TokenType.GE || curToken.type == TokenType.GT
                || curToken.type == TokenType.EE || curToken.type == TokenType.NE
                || curToken.type == TokenType.COMMA || curToken.type == TokenType.RBRACE
                || curToken.type == TokenType.RPAREN || curToken.type == TokenType.SEMI) {
            Variable v = new Variable(idString, null);
            Expression e = parseSimpleExpressionPrime(v);
            return e;
        } else {
            throw new ParserException("Unexpected token in parseExpressionPrime");

        }
    }

    private Expression parseExpressionPrimePrime(Variable v) throws ParserException {
        if (curToken.type == TokenType.EQ) {
            advanceToken();
            return parseExpression();
        } else if (curToken.type == TokenType.PLUS || curToken.type == TokenType.MINUS
                || curToken.type == TokenType.TIMES || curToken.type == TokenType.OVER
                || curToken.type == TokenType.LE || curToken.type == TokenType.LT
                || curToken.type == TokenType.GE || curToken.type == TokenType.GT
                || curToken.type == TokenType.EE || curToken.type == TokenType.NE
                || curToken.type == TokenType.COMMA || curToken.type == TokenType.RBRACE
                || curToken.type == TokenType.RPAREN || curToken.type == TokenType.SEMI) {
            return parseSimpleExpressionPrime(v);
        } else {
            throw new ParserException("Unexpected token in parseExpressionPrimePrime");
        }
    }

    private Expression parseAdditiveExpression() throws ParserException {
        Expression lhs = parseTerm();
        while (curToken.type == TokenType.PLUS || curToken.type == TokenType.MINUS) {
            OpType myOperation;
            if (curToken.type == TokenType.PLUS) {
                myOperation = OpType.PLUS;
            } else {
                myOperation = OpType.MINUS;
            }
            advanceToken();
            Expression rhs = parseTerm();
            lhs = (Expression) new Binary_Expression(lhs, myOperation, rhs);
        }
        return lhs;
    }

    private Expression parseAdditiveExpressionPrime(Expression lhs) throws ParserException {
        Expression mylhs = parseTermPrime(lhs);
        while (curToken.type == TokenType.PLUS || curToken.type == TokenType.MINUS) {
            OpType myOperation;
            if (curToken.type == TokenType.PLUS) {
                myOperation = OpType.PLUS;
            } else {
                myOperation = OpType.MINUS;
            }
            advanceToken();
            Expression rhs = parseTerm();
            mylhs = (Expression) new Binary_Expression(mylhs, myOperation, rhs);
        }
        return mylhs;
    }

    private Expression parseTerm() throws ParserException {
        Expression lhs = parseFactor();
        while (curToken.type == TokenType.TIMES || curToken.type == TokenType.OVER) {
            OpType myOperation;
            if (curToken.type == TokenType.TIMES) {
                myOperation = OpType.TIMES;
            } else {
                myOperation = OpType.OVER;
            }
            advanceToken();
            Expression rhs = parseFactor();
            lhs = (Expression) new Binary_Expression(lhs, myOperation, rhs);
        }
        return lhs;
    }

    private Expression parseTermPrime(Expression lhs) throws ParserException {
        while (curToken.type == TokenType.TIMES || curToken.type == TokenType.OVER) {
            OpType myOperation;
            if (curToken.type == TokenType.TIMES) {
                myOperation = OpType.TIMES;
            } else {
                myOperation = OpType.OVER;
            }
            advanceToken();
            Expression rhs = parseFactor();
            lhs = new Binary_Expression(lhs, myOperation, rhs);
        }
        return (Expression) lhs;
    }

    private Expression parseSimpleExpressionPrime(Expression lhs) throws ParserException {
        Expression e1 = parseAdditiveExpressionPrime(lhs);
        if ( curToken.type == TokenType.LE || curToken.type == TokenType.LT
                || curToken.type == TokenType.GE || curToken.type == TokenType.GT
                || curToken.type == TokenType.EE || curToken.type == TokenType.NE) {
            OpType myOperation = OpType.PLUS;       //needed to initialize to something just in case
            switch (curToken.type) {
                case LE:
                    myOperation = OpType.LE;
                    break;
                case LT:
                    myOperation = OpType.LT;
                    break;
                case GE:
                    myOperation = OpType.GE;
                    break;
                case GT:
                    myOperation = OpType.GT;
                    break;
                case EE:
                    myOperation = OpType.EE;
                    break;
                case NE:
                    myOperation = OpType.NE;
                    break;
            }
            advanceToken();
            Expression e2 = parseAdditiveExpression();
            return (Expression) new Binary_Expression(e1, myOperation, e2);
        } else if (curToken.type == TokenType.COMMA || curToken.type == TokenType.RBRACKET
                || curToken.type == TokenType.RPAREN || curToken.type == TokenType.SEMI) {
            return e1;
        }
        else {
            throw new ParserException("Unexpected token in parseSimpleExpressionPrime");
        }
    }

    private Expression parseFactor() throws ParserException {
        String idString = curToken.value;
        switch (curToken.type) {
            case LPAREN:
                advanceToken();
                Expression e = parseExpression();
                matchCurToken(TokenType.RPAREN);
                return e;
            case ID:
                advanceToken();
                if (curToken.type == TokenType.LPAREN) {
                    advanceToken();
                    return parseCall(idString);
                } else {
                    return parseIDExpression(idString);
                }
            case NUM:
                advanceToken();
                return parseNum(idString);
            default:
                throw new ParserException("Unexpected token in parseFactor");
        }
    }

    private Expression parseNum(String numValue) {
        return (Expression) new Num(Integer.parseInt(numValue));
    }

    private Expression parseCall(String idString) throws ParserException {
        ArrayList<Expression> e = parseArgs();
        matchCurToken(TokenType.RPAREN);
        return (Expression) new Call(idString, e);
    }

    private Expression parseIDExpression(String idString) throws ParserException {
        //advanceToken();
        if (curToken.type == TokenType.LBRACKET) {
            Expression expression = parseExpression();
            matchCurToken(TokenType.RBRACKET);
            return (Expression) new Variable(idString, expression);
        } else if (curToken.type == TokenType.PLUS || curToken.type == TokenType.MINUS
                || curToken.type == TokenType.TIMES || curToken.type == TokenType.OVER
                || curToken.type == TokenType.LE || curToken.type == TokenType.LT
                || curToken.type == TokenType.RBRACKET 
                || curToken.type == TokenType.GE || curToken.type == TokenType.GT
                || curToken.type == TokenType.EE || curToken.type == TokenType.NE
                || curToken.type == TokenType.RPAREN || curToken.type == TokenType.SEMI) {
            return (Expression) new Variable(idString, null);
        } else {
            throw new ParserException("Unexpected token in parseIDExpression");
        }
    }

    private ArrayList<Expression> parseArgs() throws ParserException {
        if (curToken.type == TokenType.NUM || curToken.type == TokenType.LPAREN
                || curToken.type == TokenType.ID) {
            ArrayList<Expression> expressions = parseArgsList();
            return expressions;
        } else if (curToken.type == TokenType.RPAREN) {
            return null;
        } else {
            throw new ParserException("Unexpected token in parseArgs");
        }
    }

    private ArrayList<Expression> parseArgsList() throws ParserException {
        ArrayList<Expression> args = new ArrayList<>();
        while (curToken.type == TokenType.NUM || curToken.type == TokenType.LPAREN
                || curToken.type == TokenType.ID) {
            Expression e = parseExpression();
            args.add(e);

            //May need to advance here **************************************************************
            if (curToken.type == TokenType.RPAREN) {
                return args;
            } else if (curToken.type == TokenType.COMMA) {
                advanceToken();
            } else {
                throw new ParserException("Unexpected token in parseArgsList");
            }
        }
        throw new ParserException("Unexpected token in parseArgsList");
    }
}
