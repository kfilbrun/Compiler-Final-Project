
package parser;
import java.io.PrintWriter;
import lowlevel.Function;

public class Num extends Expression{
    //variable declarations
    public int num;
    
    //constructor
    public Num(int n){
        num = n;
    }
    
    @Override
    public void print(PrintWriter w, String tabLvl){
        w.println(tabLvl + num);
    }

    @Override
    void genLLCode(Function f) {
        //get current block
        //Make assign oper
        //make two operands
        //put operands in oper
        //put opper in current block
        //annotate with new regnumber -setregnum
    }
}
