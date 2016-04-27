
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
            index++;
        }
        return firstItem;
    }
    
}
