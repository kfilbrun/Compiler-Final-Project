
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
        if(expression != null){
            expression.genLLCode(function);
            Operation movOper = new Operation(Operation.OperationType.ASSIGN, function.getCurrBlock());
            Operand srcOper = new Operand(Operand.OperandType.REGISTER, expression.getRegNum());
            Operand dest = new Operand(Operand.OperandType.MACRO,"RetReg");
            movOper.setSrcOperand(0, srcOper);
            movOper.setDestOperand(0, dest);
            function.getCurrBlock().appendOper(movOper);
        }
                
        Operation retJumpOper = new Operation(Operation.OperationType.JMP, function.getCurrBlock());
        Operand jmpLocation = new Operand(Operand.OperandType.BLOCK, function.getReturnBlock().getBlockNum());
        retJumpOper.setSrcOperand(0, jmpLocation);
        function.getCurrBlock().appendOper(retJumpOper);
    }
}