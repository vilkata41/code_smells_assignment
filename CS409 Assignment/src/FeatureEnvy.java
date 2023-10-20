import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.visitor.GenericVisitorAdapter;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FeatureEnvy {

    public void test_run(String input_file) throws Exception {
        FileInputStream in = new FileInputStream(input_file);
        CompilationUnit cu;

        try{
            cu = StaticJavaParser.parse(in);
        }
        finally{
            in.close();
        }

        new FeatureEnvyVisitor().visit(cu, null);
    }

    private static class FeatureEnvyVisitor extends VoidVisitorAdapter {

        @Override
        public void visit(ClassOrInterfaceDeclaration n, Object arg) {

            n.getMethods().forEach(m -> {
                List<String> scopes = new MethodVisitor().visit(m, arg);
                HashMap<String, Integer> methodCallExpressions = new HashMap<>();
                for (String s : scopes){
                    if(!methodCallExpressions.containsKey(s)) {
                        methodCallExpressions.put(s,1);
                    }
                    else{
                        methodCallExpressions.put(s,methodCallExpressions.get(s) + 1);
                    }
                }
                methodCallExpressions.forEach((k,v) ->{
                    if(100*v/scopes.size() > 35 && !k.equals("this")){
                        System.out.println("Feature Envy Detected: " + m.getNameAsString());
                    }
                });
            });

            super.visit(n, arg);
        }
    }

    private static class MethodVisitor extends GenericVisitorAdapter {

        @Override
        public List visit(MethodDeclaration n, Object arg) {
            List<String> scopes = new ArrayList<>();
            n.getBody().ifPresent(b -> {
                b.accept(new VoidVisitorAdapter() {
                    @Override
                    public void visit(MethodCallExpr a, Object arg) {
                        if(!a.hasScope()) scopes.add("this");
                        else scopes.add(a.getScope().get().toString());
                        super.visit(a, arg);
                    }
                }, arg);
            });

            super.visit(n,arg);
            return scopes;
        }
    }
}
