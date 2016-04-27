
package parser;
import java.io.PrintWriter;
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
    void genLLCode(Function function) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
