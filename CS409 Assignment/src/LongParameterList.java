import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.*;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import java.io.FileInputStream;

public class LongParameterList {
    /** Algorithm logic:
     *  We just have a method visitor that visits the parameters, counts them, and if they are more than 5,
     *  we've detected the Long Parameter List smell.
     * **/

    public void test_run(String filename) throws Exception {
        System.out.println("\nRunning 'Long Parameter List' checks...");
        FileInputStream in = new FileInputStream(filename);

        CompilationUnit cu;
        try {
            cu = StaticJavaParser.parse(in);
        } finally {
            in.close();
        }

        new ClassDiagramVisitor().visit(cu, null);
    }

    private static class ClassDiagramVisitor extends VoidVisitorAdapter {
        public void visit(MethodDeclaration n, Object arg) {
            if(n.getParameters().size() > 5)
                System.out.println("Parameter list is too long for the following method: " + n.getName());
        }
    }
}

