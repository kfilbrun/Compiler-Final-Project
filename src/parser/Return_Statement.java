
package parser;
import java.io.PrintWriter;

public class Return_Statement extends Statement{
    //variable declarations
    public Expression expression;
    
    //constructor
    public Return_Statement(Expression e){
        expression = e;
    }
    
    @Override
    public void print(PrintWriter w, String tabLvl){
        w.println(tabLvl + "Return");
        if(expression != null) {
            expression.print(w, tabLvl + "    ");
        }
    }
}
