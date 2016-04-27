package parser;
import java.io.PrintWriter;
import lowlevel.CodeItem;

public abstract class Declaration {
    abstract void print(PrintWriter p, String tabLvl);

    abstract CodeItem genLLCode();
}
