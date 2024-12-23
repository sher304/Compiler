import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Scanner;

public class Controller {

    Model dataModel = (Model)Class.forName("Model").getDeclaredConstructor().newInstance();
    Method modelRunMethod = dataModel.getClass().getMethod("run");

    public Controller(String modelName) throws ClassNotFoundException, InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchMethodException {

    }

    public void readDataFrom(String fname) {
        System.out.println("START");
        Arrays.stream(dataModel.getClass().getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(Bind.class))
                .forEach(field -> {
                    System.out.println("START 2");
                    try {
                        field.setAccessible(true);
                        System.out.println("START 3");
                        if (field.getName().equals("LL")) {
                            field.set(dataModel, 5);
                            System.out.println("START LL");
                        } else {
                            System.out.println("SETTED TO THE FIELD: " + field.getName());
                            field.set(dataModel, new double[5]);
                        }
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                });

        try {
            File myObj = new File(fname);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                System.out.println(data);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public void runModel() throws InvocationTargetException, IllegalAccessException {
//        modelRunMethod.invoke(dataModel);
    }

    public void runScriptFromFile(String fname) {

    }

    public void runScript(String script) {

    }

    public String getResultsAsTsv() {
        return "";
    }
}
