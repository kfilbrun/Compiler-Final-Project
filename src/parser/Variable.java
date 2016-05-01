
package parser;
import compiler.CMinusCompiler;
import java.io.PrintWriter;
import java.util.Map;
import lowlevel.Function;

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
    void genLLCode(Function f) throws CodeGenerationException {
        Map<String, Integer> localSymbolTable = f.getTable();
        if(localSymbolTable.containsKey(name)){
            this.setRegNum(localSymbolTable.get(name));
        }
        else if(CMinusCompiler.globalHash.containsKey(name)){
            this.setRegNum(f.getNewRegNum());
        }
        else{
            throw new CodeGenerationException("Variable Error: Variable not "
                    + "+ found in local or global symbol table");
        }
    }
}
