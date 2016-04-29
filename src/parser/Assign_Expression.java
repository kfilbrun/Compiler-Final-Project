
package parser;
import compiler.CMinusCompiler;
import java.io.PrintWriter;
import lowlevel.BasicBlock;
import lowlevel.Function;
import lowlevel.Operand;
import lowlevel.Operation;
import static lowlevel.Operand.OperandType.*;
import static lowlevel.Operation.OperationType.ASSIGN;

public class Assign_Expression extends Expression {
    //variable declarations
    public Variable variable;
    public Expression expression;
    
    //constructor
    public Assign_Expression(Variable v, Expression e){
        variable = v;
        expression = e;
    }
    
    public void print(PrintWriter w, String tabLvl){
        if(variable != null) {
            variable.print(w, tabLvl);
        }
        w.println(tabLvl + "=");
        if(expression != null) {
            expression.print(w, tabLvl + "    ");
        }
    }

    @Override
    void genLLCode(Function f) throws CodeGenerationException{
        BasicBlock b = f.getCurrBlock();
        Operation operation = null;
        
        expression.genLLCode(f);
        //variable.genLLCode(f);
        
        Operand srcOp = new Operand(REGISTER, expression.getRegNum());
        //Operand destOp = new Operand(REGISTER, variable.getRegNum());
        
        if (f.getTable().containsKey(variable.name)) {
            //assign, reg dest of variable.getRegNum
            //operation = new Operation(ASSIGN, b);
        }
        else if (CMinusCompiler.globalHash.containsKey(variable.name)) {
            //store  2 src expressReg, name
        }
        else {
            throw new CodeGenerationException("Variable not found in Assign_Expression");
        }
            
        // this stuff probably goes in if
        operation.setSrcOperand(0, srcOp);
        operation.setDestOperand(0, destOp);
        
        b.appendOper(operation);
        
        this.setRegNum(variable.getRegNum());
    }
    
}
