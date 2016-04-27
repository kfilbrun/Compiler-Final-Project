

package parser;
import java.io.PrintWriter;

public class Selection_Statement extends Statement {
    //variable declarations
    public Expression expression;
    public Statement statement1;
    public Statement statement2;
    
    //constructor
    public Selection_Statement(Expression e, Statement s1, Statement s2){
        expression = e;
        statement1 = s1;
        statement2 = s2;
    }
    
    @Override
    public void print(PrintWriter w, String tabLvl){
        w.println(tabLvl + "IF");
        w.println(tabLvl + "(");
        if(expression != null){
            expression.print(w, tabLvl + "    ");
        }
        w.println(tabLvl + ")");
        w.println(tabLvl + "{");
        if(statement1 != null) {
            statement1.print(w, tabLvl + "    " );
        }
        w.println(tabLvl + "}");
        if(statement2 != null){
            w.println(tabLvl + "ELSE");
            w.println(tabLvl + "{");
            statement2.print(w, tabLvl + "    " );
            w.println(tabLvl + "}");
        }
    }
}
