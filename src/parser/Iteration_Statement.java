
package parser;
import java.io.PrintWriter;
import lowlevel.BasicBlock;
import lowlevel.Function;
import lowlevel.Operand;
import lowlevel.Operation;

public class Iteration_Statement extends Statement {
    //variable declarations
    public Expression expression;
    public Statement statement;
    
    //constructor
    public Iteration_Statement(Expression e, Statement s){
        expression = e;
        statement = s;
    }
    
    public void print(PrintWriter w, String tabLvl){
        w.println(tabLvl + "While");
        w.println(tabLvl + "(");
        if(expression != null) {
            expression.print(w, tabLvl + "    ");
        }
        w.println(tabLvl + ")");
        if(statement != null) {
            statement.print(w, tabLvl + "    ");
        }
    }

    @Override
    void genLLCode(Function function) throws CodeGenerationException{
        //Create basic blocks
        BasicBlock thenBlock = new BasicBlock(function);
        BasicBlock postBlock = new BasicBlock(function);
        
        //Gen code for the expression
        expression.genLLCode(function);
        //Set current block
        BasicBlock curr = function.getCurrBlock();
        
        //Generate branch to post
        genBranch(postBlock, curr);
                
        //Gen and append then block
        function.appendToCurrentBlock(thenBlock);
        function.setCurrBlock(thenBlock);
        curr = function.getCurrBlock();
        //Gen then code
        statement.genLLCode(function);
        
        //Generate looping branch to top of then
        genBranch(thenBlock, curr);

        //Append post block and set it as current to end
        function.appendToCurrentBlock(postBlock);
        function.setCurrBlock(postBlock);
    }
    
    void genBranch(BasicBlock targetBlock, BasicBlock curr){
        int branchReg = expression.getRegNum();
        Operand branchRegOp = new Operand(Operand.OperandType.REGISTER, branchReg);
        Operand compRegOp = new Operand(Operand.OperandType.REGISTER, expression.getRegNum());
        Operand tgtOp = new Operand(Operand.OperandType.BLOCK, targetBlock.getBlockNum());
        
        // add BEQ
        Operation beqOper = new Operation(Operation.OperationType.BEQ, curr);
        beqOper.setSrcOperand(0, branchRegOp);
        beqOper.setSrcOperand(1, compRegOp);
        beqOper.setDestOperand(0, tgtOp);
        curr.appendOper(beqOper);
    }
    
    
}
