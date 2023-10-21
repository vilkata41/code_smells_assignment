import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.FieldAccessExpr;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.expr.Name;
import com.github.javaparser.ast.stmt.Statement;
import com.github.javaparser.ast.visitor.GenericVisitorAdapter;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import javassist.compiler.ast.MethodDecl;

import java.io.FileInputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InappropriateIntimacy {

    /**
     * Algorithm logic:
     * 1. Check all method calls (both inner and outer) and store them in a hashmap as follows:
     *    a) every class that is referred will be the key in the hashmap (including the class viewed)
     *    b) every method call will bring up the value in the (k,v) pair - BASED ON WHAT CLASS that method is in.
     * 2. Calculate the classes' percentage used.
     * 3. If any outer class has over 25% used, it will be considered Inappropriately Intimate to the given class.
     * **/
    public void test_run(String filename_to_test) throws Exception {
        System.out.println("\nRunning 'Inappropriate Intimacy' checks...");

        FileInputStream in = new FileInputStream(filename_to_test);
        CompilationUnit cu;

        try{
            cu = StaticJavaParser.parse(in);
        }
        finally{
            in.close();
        }

        new IIVisitor().visit(cu, null);
    }

    private static class IIVisitor extends VoidVisitorAdapter{

        @Override
        public void visit(ClassOrInterfaceDeclaration n, Object arg) {
            HashMap<String, Integer> methodCallExpressions = new HashMap<>();
            List<String> all_scopes = new ArrayList<>();

            n.getMethods().forEach(m -> {
                List<String> curr_scopes = new MethodVisitor().visit(m, arg);
                for (String s : curr_scopes){
                    all_scopes.add(s);
                    if(!methodCallExpressions.containsKey(s)) methodCallExpressions.put(s,1);
                    else methodCallExpressions.put(s,methodCallExpressions.get(s) + 1);
                }
            });

            for(HashMap.Entry<String,Integer> entry: methodCallExpressions.entrySet()){
                if(entry.getValue()*100/all_scopes.size() > 25 && !entry.getKey().equals("this")) System.out.println(
                        "Inappropriate Intimacy detected (in CLASS: " + n.getName().toString() + ") for the following" +
                        " class instance variable: " + entry.getKey() + ". Used percentage: "
                        + entry.getValue()*100/all_scopes.size()  + "%");
            }
            super.visit(n, arg);
        }
    }

    private static class MethodVisitor extends GenericVisitorAdapter{
        @Override
        public List<String> visit(MethodDeclaration n, Object arg) {
            List<String> scopes = new ArrayList<>();
            n.getBody().ifPresent(b -> {
                b.accept(new VoidVisitorAdapter() {
                    @Override
                    public void visit(MethodCallExpr n, Object arg) {
                        if(!n.hasScope()) scopes.add("this");
                        else scopes.add(n.getScope().get().toString());
                        super.visit(n, arg);
                    }
                }, arg);
            });
            return scopes;
        }
    }
}
