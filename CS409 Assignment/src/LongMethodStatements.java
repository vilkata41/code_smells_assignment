import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.stmt.Statement;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.github.javaparser.resolution.TypeSolver;
import com.github.javaparser.symbolsolver.JavaSymbolSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.ReflectionTypeSolver;

import java.io.FileInputStream;
import java.util.concurrent.atomic.AtomicInteger;

public class LongMethodStatements {
    /** Algorithm logic:
     *  We find all Statements in a method by the .findAll() method. Then, we count them using an atomic integer.
     *  If there are more than 20 statements in a given method, it is considered to be with too many statements.
     *  Meaning, we've discovered the Long Method smell.
     * **/

    public void test_run(String filename) throws Exception {
        System.out.println("\nRunning 'Long Method Statements' checks...");

        FileInputStream in = new FileInputStream(filename);
        CompilationUnit cu;
        try {
            cu = StaticJavaParser.parse(in);
        } finally {
            in.close();
        }

        new ClassDiagramVisitor().visit(cu, null);

        TypeSolver typeSolver = new ReflectionTypeSolver();
        JavaSymbolSolver symbolSolver = new JavaSymbolSolver(typeSolver);
        StaticJavaParser.getParserConfiguration().setSymbolResolver(symbolSolver);
    }

    private static class ClassDiagramVisitor extends VoidVisitorAdapter {

        public void visit(MethodDeclaration n, Object arg) {
            AtomicInteger i = new AtomicInteger();
            n.findAll(Statement.class).forEach(s ->
                    i.getAndIncrement());
            if (i.intValue() > 20) {
                System.out.println(n.getName() + " is a long method (" + i + " statements)");
            }
        }
    }
}
