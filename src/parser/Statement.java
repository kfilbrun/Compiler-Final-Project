
package parser;

import java.io.PrintWriter;
import lowlevel.Function;

public abstract class Statement {
    abstract void print(PrintWriter p, String tabLvl);
    
    abstract void genLLCode(Function function);
}
