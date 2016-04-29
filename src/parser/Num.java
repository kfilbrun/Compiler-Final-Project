
package parser;
import java.io.PrintWriter;
import lowlevel.BasicBlock;
import lowlevel.Function;
import lowlevel.Operand;
import static lowlevel.Operand.OperandType.*;
import lowlevel.Operation;
import static lowlevel.Operation.OperationType.ASSIGN;

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

    @Override
    void genLLCode(Function f) {
        //get current block
        BasicBlock b = f.getCurrBlock();
        //Make assign oper
        Operation operation = new Operation(ASSIGN, b);
        //make two operands
        int register = f.getNewRegNum();
        Operand regOp = new Operand(REGISTER, register);
        Operand intOp = new Operand(INTEGER, num);
        //put operands in oper
        operation.setSrcOperand(0, intOp);
        operation.setDestOperand(0, regOp);
        //put oper in current block
        b.appendOper(operation);
        //annotate with new regnumber -setregnum
        this.setRegNum(register);
    }
}
