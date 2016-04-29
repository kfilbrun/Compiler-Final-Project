
package parser;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import lowlevel.BasicBlock;
import lowlevel.Function;
import lowlevel.Operand;
import static lowlevel.Operand.OperandType.REGISTER;
import lowlevel.Operation;
import static lowlevel.Operation.OperationType;

public class Binary_Expression extends Expression{
    //variable declarations
    public Expression expression1;
    public OpType opType;
    public Expression expression2;
    Map<OpType, OperationType> opTypeToOperationType = new HashMap<>();
    //constructor
    public Binary_Expression(Expression e1, OpType o, Expression e2){
        expression1 = e1;
        opType = o;
        expression2 = e2;
        
        opTypeToOperationType.put(OpType.PLUS, OperationType.ADD_I);
        opTypeToOperationType.put(OpType.MINUS, OperationType.SUB_I);
        opTypeToOperationType.put(OpType.TIMES, OperationType.MUL_I);
        opTypeToOperationType.put(OpType.OVER, OperationType.DIV_I);
        opTypeToOperationType.put(OpType.LE, OperationType.LTE);
        opTypeToOperationType.put(OpType.LT, OperationType.LT);
        opTypeToOperationType.put(OpType.GE, OperationType.GTE);
        opTypeToOperationType.put(OpType.GT, OperationType.GT);
        opTypeToOperationType.put(OpType.EE, OperationType.EQUAL);
        opTypeToOperationType.put(OpType.NE, OperationType.NOT_EQUAL);
    }
    
    public void print(PrintWriter w, String tabLvl){
        w.println(tabLvl + opType.name());
        if(expression1 != null) {
            expression1.print(w, tabLvl + "    ");
        }
        if(expression2 != null) {
            expression2.print(w, tabLvl + "    ");
        }
    }

    @Override
    void genLLCode(Function f) throws CodeGenerationException{
        BasicBlock b = f.getCurrBlock();
        //call gencode on left and right children
        expression1.genLLCode(f);
        expression2.genLLCode(f);
        //Store results of children gencode
        int expr1Location = expression1.getRegNum();
        int expr2Location = expression2.getRegNum();      
        
        //This will be the final operation
        OperationType opT = opTypeToOperationType.get(opType);
        Operation binExpOper = new Operation(opT, b);
        //Get Location for your result
        int newRegNum = f.getNewRegNum();
        Operand dest = new Operand(REGISTER, newRegNum);
        this.setRegNum(newRegNum);
        //Add operation to do your function
        Operand expr1Op = new Operand(REGISTER, expr1Location);
        Operand expr2Op = new Operand(REGISTER, expr2Location);
        
        binExpOper.setDestOperand(0, dest);
        binExpOper.setSrcOperand(0, expr1Op);
        binExpOper.setSrcOperand(1, expr2Op);
        
        b.appendOper(binExpOper);
    }
}
