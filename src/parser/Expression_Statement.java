
package parser;
import java.io.PrintWriter;
import lowlevel.Function;
public class Expression_Statement extends Statement{
    //variable declarations
    public Expression expression;
    
    //constructor
    public Expression_Statement(Expression e){
        expression = e;
    }
    
    @Override
    public void print(PrintWriter w, String tabLvl){
        if(expression != null){
            expression.print(w, tabLvl);
        }
    }
    
    public void genLLCode(Function function) throws CodeGenerationException{
        expression.genLLCode(function);
    }
}
