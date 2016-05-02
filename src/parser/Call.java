
package parser;

import java.util.ArrayList;
import java.io.PrintWriter;
import lowlevel.BasicBlock;
import lowlevel.Function;
import lowlevel.Operand;
import lowlevel.Operation;

public class Call extends Expression{
    //variable declaration
    public String name;
    public ArrayList<Expression> expressions;
    
    //constructor
    public Call(String n, ArrayList<Expression> e){
        name = n;
        expressions = e;
    }
    
    @Override
    public void print(PrintWriter w, String tabLvl){
        if(expressions == null){
            w.print(tabLvl + name + "(");
        }
        else {
            w.println(tabLvl + name + "\n" + tabLvl + "(");
        }
        
        if(expressions != null){
            for(Expression exp : expressions){
                if(exp != null) {
                    exp.print(w, tabLvl + "    ");
                }
            }
        }
        if(expressions == null){
                w.println(")");
        }
        else {
            w.println(tabLvl + ")");
        }
        
    }

    @Override
    void genLLCode(Function f) throws CodeGenerationException{
        BasicBlock curr = f.getCurrBlock();
        for(int i = expressions.size()-1; i >= 0; i--){
            Expression curExpr = expressions.get(i);
            curExpr.genLLCode(f);
            Operand paramOp = new Operand(Operand.OperandType.REGISTER, curExpr.getRegNum());
            Operation paramOper = new Operation(Operation.OperationType.PASS, curr);
            paramOper.setDestOperand(0, paramOp);
            curr.appendOper(paramOper);
        }
        
        Operand callOp = new Operand(Operand.OperandType.STRING, name);
        Operation callOper = new Operation(Operation.OperationType.CALL, curr);
        callOper.setDestOperand(0, callOp);
        
        curr.appendOper(callOper);
        //Move return register to regular register??
    }
}
