
public class RunAllTests {
    public static void main(String[] args) throws Exception {
        try {
            /* THIS HERE is the only thing that needs to be modified, all tests implemented will
             * be run on the file specified in class_to_check.
             */
            String class_to_check = "Grid.java";

            new LongParameterList().test_run(class_to_check);
            new LargeClassStatements().test_run(class_to_check);
            new LongMethodStatements().test_run(class_to_check);
            new TemporaryField().test_run(class_to_check);
            new FeatureEnvy().test_run(class_to_check);
            new InappropriateIntimacy().test_run(class_to_check);
        }
        catch(Exception ex){
            System.out.println("Something went wrong: " + ex.getMessage());
        }

    }
}