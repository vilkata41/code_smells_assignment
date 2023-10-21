
public class RunAllTests {
    public static void main(String[] args) throws Exception {
        try {
            /* This is common for the first four methods we have since we were provided with specific
             * test suites just for the hard methods, so we decided to use grid.java for the others.
             */
            String class_to_check = "Grid.java";

            new LongParameterList().test_run(class_to_check);
            new LargeClassStatements().test_run(class_to_check);
            new LongMethodStatements().test_run(class_to_check);
            new TemporaryField().test_run(class_to_check);
            new FeatureEnvy().test_run("Basket.java");
            new InappropriateIntimacy().test_run("HuffmanCode.java");
        }
        catch(Exception ex){
            System.out.println("Something went wrong: " + ex.getMessage());
        }

    }
}