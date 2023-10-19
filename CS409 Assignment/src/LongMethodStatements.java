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

    public void test_run() throws Exception {

        FileInputStream in = new FileInputStream("Grid.java");
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
