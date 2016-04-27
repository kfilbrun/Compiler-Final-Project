
package parser;
import java.io.PrintWriter;

public class Assign_Expression extends Expression {
    //variable declarations
    public Variable variable;
    public Expression expression;
    
    //constructor
    public Assign_Expression(Variable v, Expression e){
        variable = v;
        expression = e;
    }
    
    public void print(PrintWriter w, String tabLvl){
        if(variable != null) {
            variable.print(w, tabLvl);
        }
        w.println(tabLvl + "=");
        if(expression != null) {
            expression.print(w, tabLvl + "    ");
        }
    }
    
}
