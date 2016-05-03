
package parser;
import compiler.CMinusCompiler;
import java.io.PrintWriter;
import java.util.Map;
import lowlevel.BasicBlock;
import lowlevel.Function;
import lowlevel.Operand;
import static lowlevel.Operand.OperandType.*;
import lowlevel.Operation;
import static lowlevel.Operation.OperationType.*;

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
            int localRegNum = f.getNewRegNum();
            this.setRegNum(localRegNum);
            Operand op1 = new Operand(STRING, name);
            Operand op2 = new Operand(REGISTER, localRegNum);
            BasicBlock block = f.getCurrBlock();
            Operation newOp = new Operation(LOAD_I, block);
            newOp.setSrcOperand(0, op1);
            newOp.setDestOperand(0, op2);
            block.appendOper(newOp);
        }
        else{
            throw new CodeGenerationException("Variable Error: Variable not "
                    + " found in local or global symbol table");
        }
    }
}
