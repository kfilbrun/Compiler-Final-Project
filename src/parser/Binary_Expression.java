
package parser;
import java.io.PrintWriter;

public class Binary_Expression extends Expression{
    //variable declarations
    public Expression expression1;
    public OpType opType;
    public Expression expression2;
    
    //constructor
    public Binary_Expression(Expression e1, OpType o, Expression e2){
        expression1 = e1;
        opType = o;
        expression2 = e2;
    }
    
    public void print(PrintWriter w, String tabLvl){
        w.println(tabLvl + opType.name());
        if(expression1 != null) {
            expression1.print(w, tabLvl + "    ");
        }
        if(expression2 != null) {
            expression2.print(w, tabLvl + "    ");
        }
    }

    @Override
    void genLLCode() {
        //call gencode on left and right children
        expression1.genLLCode();
        expression2.genLLCode();
        //Store results of children gencode
        //Get Location for your result
        //Add operation to do your function
    }
}
