
public class RunAllTests {
    public static void main(String[] args) {
        try {
//            new LongParameterList().test_run();
//            new LargeClassStatements().test_run();
//            new LongMethodStatements().test_run();
            new TemporaryField().test_run("Grid.java");
        }
        catch(Exception ex){
            System.out.println("Something went wrong: " + ex.getMessage());
        }

    }
}