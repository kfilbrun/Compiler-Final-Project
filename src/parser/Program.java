
package parser;

import java.util.ArrayList;
import java.io.PrintWriter;
import x64codegen.X64AssemblyGenerator;
import parser.*;
import lowlevel.*;
import java.util.*;
import java.io.*;
import optimizer.*;
import x86codegen.*;
import x64codegen.*;
import dataflow.*;
import java.util.logging.Level;
import java.util.logging.Logger;

//Hi Todd

public class Program {
    
    //variable declarations
    public ArrayList<Declaration> declarations = new ArrayList<Declaration>();
    
    //constructor
    public Program(ArrayList<Declaration> a){
        declarations = a;
    }
    
    public void print(PrintWriter w){
        w.println("Program");
        for(Declaration decl : declarations){
            if(decl != null){
                decl.print(w, "    ");
            }
        }
    }
    
    public CodeItem genLLCode(){
        CodeItem firstItem = null;
        CodeItem currentItem = null;
        int declListSize = declarations.size();
        int index = 0;
        while(index <declListSize){
            Declaration nextDecl = declarations.get(index);
            CodeItem newItem = nextDecl.genLLCode();
            if(currentItem == null){
                    //move currentItem to newData
                    currentItem = newItem;
                    firstItem = newItem;
            } else{
                    //link it to list of CodeItems
                    currentItem.setNextItem(newItem);
                    //move currentItem to newData
                    currentItem = newItem;
            }
    
//            if(nextDecl instanceof Var_Declaration){
//                //cast Decl into VarDecl
//                Var_Declaration myVarDecl = (Var_Declaration)nextDecl;
//                //add new Data object
//                 return new Data(1, myVarDecl.getName());

//            } else if(nextDecl instanceof Fun_Declaration){
//                
//                //cast Decl into FunDecl
//                Fun_Declaration myFunDecl = (Fun_Declaration)nextDecl;
//                //get return type and name from appropriate fields
//                ReturnType returnType = myFunDecl.getReturnType();
//                int myReturnNumber;
//                if(returnType.equals(ReturnType.INT)){
//                    myReturnNumber = 1;
//                } else{
//                    myReturnNumber = 0;
//                }
//                String name = myFunDecl.getName();
//                //create a new Function
                Function newFunc = new Function(myReturnNumber, name);
        //          walk Params, doing 2 things:
        //              1. make FuncParams
        //              2. put Param name in lccal table
//                //create new Block and make it currentBlock
//                  newFunc.createBlock0();
//                BasicBlock newBlock = new BasicBlock(newFunc);
//                newFunc.appendBlock(newBlock);
//                newFunc.setCurrBlock(newBlock);
//                //call genCode on Compound Statement
//                (myFunDecl.compoundStatement).compoundStatementGenCode();           //need return type
//            } else {
//                System.out.println("There is an error in genLLCode.");
//            }
            index++;
        }
        return firstItem;
    }
    
}
