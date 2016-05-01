
package parser;
import java.io.PrintWriter;
import lowlevel.Function;
import lowlevel.Operand;
import lowlevel.Operation;

public class Return_Statement extends Statement{
    //variable declarations
    public Expression expression;
    
    //constructor
    public Return_Statement(Expression e){
        expression = e;
    }
    
    @Override
    public void print(PrintWriter w, String tabLvl){
        w.println(tabLvl + "Return");
        if(expression != null) {
            expression.print(w, tabLvl + "    ");
        }
    }

    @Override
    void genLLCode(Function function) throws CodeGenerationException{
        Operation oper = new Operation(Operation.OperationType.RETURN, function.getCurrBlock());
        if(expression != null){
            expression.genLLCode(function);
            Operand returnOper = new Operand(Operand.OperandType.REGISTER, expression.getRegNum());
            oper.setSrcOperand(0, returnOper);
        }
        function.getCurrBlock().appendOper(oper);
        
        Operation retJumpOper = new Operation(Operation.OperationType.JMP, function.getCurrBlock());
        Operand jmpLocation = new Operand(Operand.OperandType.BLOCK, function.genReturnBlock().getBlockNum());
        retJumpOper.setDestOperand(0, jmpLocation);
    }
}
