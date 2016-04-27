
package parser;
import java.io.PrintWriter;

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
}
