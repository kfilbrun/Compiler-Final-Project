
package parser;
import java.io.PrintWriter;
import lowlevel.Function;

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

    @Override
    void genLLCode(Function function) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
