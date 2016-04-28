
package parser;
import java.io.PrintWriter;
import lowlevel.Data;
import lowlevel.FuncParam;
import lowlevel.Function;

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
    
    public FuncParam genLLCode(Function func, FuncParam prevParam){
        FuncParam p = new FuncParam(Data.TYPE_INT, name, false);
        if(prevParam == null){
            func.setFirstParam(p);
        } else {
            prevParam.setNextParam(p);
        }
        return p;
    }
}