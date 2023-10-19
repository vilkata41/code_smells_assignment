import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.*;
import com.github.javaparser.ast.expr.*;
import com.github.javaparser.ast.stmt.ExpressionStmt;
import com.github.javaparser.ast.visitor.GenericVisitorAdapter;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import org.checkerframework.checker.units.qual.A;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TemporaryField {

    /**
     * My logic with this:
     *
     * 1. Visit all methods and see what methods access what fields.
     * 2. See what fields have not been accessed by more than 1 method.
     * 3. Smell detected.
     * **/

    public void test_run(String input_file) throws Exception {
        System.out.println("Running 'Temporary Field' smell detection...");

        FileInputStream in = new FileInputStream(input_file);
        CompilationUnit cu;

        try{
            cu = StaticJavaParser.parse(in);
        }
        finally{
            in.close();
        }

        new TempFieldVisitor().visit(cu, null);

    }

    private static class TempFieldVisitor extends VoidVisitorAdapter{

        @Override
        public void visit(ClassOrInterfaceDeclaration n, Object arg) {
            System.out.println("Checking CLASS: " + n.getName());

            //tells how many methods have used a certain variable, starting from 0 when declared
            HashMap<String, Integer> fieldVariablesVisitedTimes = new HashMap<>();
            //populating the hashmap...
            n.getFields().forEach(field -> {
                field.getVariables().forEach(fieldVariable -> {
                    if(!fieldVariablesVisitedTimes.containsKey(fieldVariable)){
                        fieldVariablesVisitedTimes.put(fieldVariable.getNameAsString(), 0); // initially 0 times visited
                    }
                });
            });

            n.getMethods().forEach(m -> {
                List<String> fieldsUsed = new MethodVisitor().visit(m, arg);
                List<String> alreadyMarkedAsVisited = new ArrayList<>();
                for(String f: fieldsUsed){
                    if(!alreadyMarkedAsVisited.contains(f)){
                        // if the current variable accessed is a field, we increment the methods that use it
                        if(fieldVariablesVisitedTimes.containsKey(f)){
                            alreadyMarkedAsVisited.add(f);
                            fieldVariablesVisitedTimes.put(f, fieldVariablesVisitedTimes.get(f) + 1);
                        }
                    }
                }
            });

            fieldVariablesVisitedTimes.forEach((k,v) -> {
                if(v == 1){
                    System.out.println("Temporary field detected: " + k);
                }
            });

            super.visit(n, arg);
        }
    }

    private static class MethodVisitor extends GenericVisitorAdapter{
        @Override
        public List visit(MethodDeclaration n, Object arg) {
            List<String> result = new ArrayList<>();
            n.getBody().ifPresent(b -> {
                b.accept(new VoidVisitorAdapter() {
                    @Override
                    public void visit(NameExpr n, Object arg) {
                        result.add(n.getNameAsString());
                        super.visit(n, arg);
                    }

                    @Override
                    public void visit(FieldAccessExpr n, Object arg) {
                        result.add(n.getNameAsString());
                        super.visit(n, arg);
                    }
                }, arg);
            });

            super.visit(n, arg);
            return result;
        }
    }
}
