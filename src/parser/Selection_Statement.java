package parser;
import java.io.PrintWriter;
import lowlevel.BasicBlock;
import lowlevel.Function;
import lowlevel.Operand;
import lowlevel.Operation;

public class Selection_Statement extends Statement {
    //variable declarations
    public Expression expression;
    public Statement statement1;
    public Statement statement2;
    
    //constructor
    public Selection_Statement(Expression e, Statement s1, Statement s2){
        expression = e;
        statement1 = s1;
        statement2 = s2;
    }
    
    @Override
    public void print(PrintWriter w, String tabLvl){
        w.println(tabLvl + "IF");
        w.println(tabLvl + "(");
        if(expression != null){
            expression.print(w, tabLvl + "    ");
        }
        w.println(tabLvl + ")");
        w.println(tabLvl + "{");
        if(statement1 != null) {
            statement1.print(w, tabLvl + "    " );
        }
        w.println(tabLvl + "}");
        if(statement2 != null){
            w.println(tabLvl + "ELSE");
            w.println(tabLvl + "{");
            statement2.print(w, tabLvl + "    " );
            w.println(tabLvl + "}");
        }
    }

    @Override
    void genLLCode(Function function) throws CodeGenerationException{
        BasicBlock thenBlock = new BasicBlock(function);
        BasicBlock postBlock = new BasicBlock(function);
        BasicBlock elseBlock = null;
        int tgtBlock = postBlock.getBlockNum();
        
        if(statement2 != null){ //If we have an else stmt
            elseBlock = new BasicBlock(function);
            tgtBlock = elseBlock.getBlockNum();
        }
        
        expression.genLLCode(function);
        
        int branchReg = expression.getRegNum();
        Operand branchRegOp = new Operand(Operand.OperandType.REGISTER, branchReg);
        Operand compRegOp = new Operand(Operand.OperandType.REGISTER, expression.getRegNum());
        Operand tgtOp = new Operand(Operand.OperandType.BLOCK, tgtBlock);
        
        BasicBlock curr = function.getCurrBlock();
        
        // add BEQ
        Operation beqOper = new Operation(Operation.OperationType.BEQ, curr);
        beqOper.setSrcOperand(0, branchRegOp);
        beqOper.setSrcOperand(1, compRegOp);
        beqOper.setSrcOperand(2, tgtOp);
        curr.appendOper(beqOper);
                
        function.appendToCurrentBlock(thenBlock);
        function.setCurrBlock(thenBlock);
        statement1.genLLCode(function);
        
        function.appendToCurrentBlock(postBlock);
        
        if(statement2 != null){ //If we have an else stmt
            function.setCurrBlock(thenBlock);
            curr = function.getCurrBlock();
            statement1.genLLCode(function);
            
            //Somehow this needs to be handled if the the else has a return
            Operand jmpPost = new Operand(Operand.OperandType.BLOCK, tgtBlock);
            Operation jmpOper = new Operation(Operation.OperationType.JMP, curr);
            jmpOper.setSrcOperand(0, jmpPost);
            
            curr.appendOper(jmpOper);
            function.appendUnconnectedBlock(elseBlock);
        }
        
        function.setCurrBlock(postBlock); 
    }
}
