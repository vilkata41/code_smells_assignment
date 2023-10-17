import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.*;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import java.io.FileInputStream;

public class LongParameterList {

    public static void main(String[] args) throws Exception {
        FileInputStream in = new FileInputStream("Grid.java");

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
                System.out.println(n.getName() + " Parameter list is too long");
        }
    }
}

