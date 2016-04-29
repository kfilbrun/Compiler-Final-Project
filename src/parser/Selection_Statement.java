package parser;
import java.io.PrintWriter;
import lowlevel.BasicBlock;
import lowlevel.Function;

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
//        BasicBlock ifBlock = new BasicBlock(function);
//        function.appendToCurrentBlock(ifBlock);
//        function.setCurrBlock(ifBlock);
        
        BasicBlock thenBlock = new BasicBlock(function);
        BasicBlock postBlock = new BasicBlock(function);
        BasicBlock elseBlock = null;
        int tgtBlock = postBlock.getBlockNum();
        
        //if elseStat elseBlock = new BaicBlock and tgtBlock = elseBlock.getBlock
        if(statement2 != null){ //If we have an else stmt
            elseBlock = new BasicBlock(function);
            tgtBlock = elseBlock.getBlockNum();
        }
        
        expression.genLLCode(function);
        int branchReg = expression.getRegNum();
        
        BasicBlock curr = function.getCurrBlock();
        // add BEQ
        
        function.appendToCurrentBlock(thenBlock);
        function.setCurrBlock(thenBlock);
        statement1.genLLCode(function);
        
        
        function.appendToCurrentBlock(postBlock);
        
        if(statement2 != null){ //If we have an else stmt
            elseBlock = new BasicBlock(function);
            function.setCurrBlock(thenBlock);
            statement1.genLLCode(function);
            elseBlock.setNextBlock(postBlock); //jump post
            function.appendUnconnectedBlock(elseBlock);
        }
        
        function.setCurrBlock(postBlock); 
    }
}
