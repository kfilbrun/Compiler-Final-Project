
package parser;
import java.io.PrintWriter;

public class Variable extends Expression{
    //variable declarations
    public String name;
    public Expression expression;
    
    //constructor
    public Variable(String n, Expression e){
        name = n;
        expression = e;
    }
    
    @Override
    public void print(PrintWriter w, String tabLvl){
        w.println(tabLvl + name);
        if(expression != null){
            w.println(tabLvl + "[");
            expression.print(w, tabLvl + "   ");
            w.println(tabLvl + "]");
        }
    }

    @Override
    void genLLCode() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
