
package parser;
import java.io.PrintWriter;

public class Parameter {
    //variable declarations
    String name;
    Boolean isArray;
    
    //constructor
    public Parameter(String n, Boolean a){
        name = n;
        isArray = a;
    }
    
    public void print(PrintWriter w){
        w.print(" " + name + (isArray ? "[]" : "") + " ");
    }
    
    public void genLLCode(){
        
    }
}
