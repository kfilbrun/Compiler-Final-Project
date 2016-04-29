
package parser;
import java.io.PrintWriter;
import lowlevel.Function;

public abstract class Expression {
    abstract void print(PrintWriter p, String tabLvl);

    abstract void genLLCode(Function func) throws CodeGenerationException;
    
    private int regNum;
    
    public int getRegNum(){
        return regNum;
    }
    
    public void setRegNum(int newRegNum){
        regNum = newRegNum;
    }
}