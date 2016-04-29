
package parser;
import java.io.PrintWriter;
import lowlevel.BasicBlock;
import lowlevel.Function;

public class Iteration_Statement extends Statement {
    //variable declarations
    public Expression expression;
    public Statement statement;
    
    //constructor
    public Iteration_Statement(Expression e, Statement s){
        expression = e;
        statement = s;
    }
    
    public void print(PrintWriter w, String tabLvl){
        w.println(tabLvl + "While");
        w.println(tabLvl + "(");
        if(expression != null) {
            expression.print(w, tabLvl + "    ");
        }
        w.println(tabLvl + ")");
        if(statement != null) {
            statement.print(w, tabLvl + "    ");
        }
    }

    @Override
    void genLLCode(Function function) throws CodeGenerationException{
        BasicBlock initialExprBlock = new BasicBlock(function);
        function.appendToCurrentBlock(initialExprBlock);
        function.setCurrBlock(initialExprBlock);
        expression.genLLCode(function);
        
        BasicBlock thenBlock = new BasicBlock(function);
        function.appendToCurrentBlock(thenBlock);
        function.setCurrBlock(thenBlock);
        statement.genLLCode(function);
        
        BasicBlock finalExprBlock = new BasicBlock(function);
        function.appendToCurrentBlock(finalExprBlock);
        function.setCurrBlock(finalExprBlock);
        expression.genLLCode(function);
        finalExprBlock.setNextBlock(thenBlock);
        
        BasicBlock postBlock = new BasicBlock(function);
        function.appendToCurrentBlock(postBlock);
        function.setCurrBlock(postBlock);
    }
}
