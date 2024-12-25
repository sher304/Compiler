import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Scanner;

public class Controller {

    int LL = 0;
    double[] twKI; // the growth rate of private consumption
    double[] twKS; // the growth rate of public consumption
    double[] twINW; // investment growth
    double[] twEKS; // export growth
    double[] twIMP; // import growth

    double[] KI; // private consumption
    double[] KS; // public consumption
    double[] INW; // investments
    double[] IMP; // import
    double[] EKS; // export

    Model dataModel = (Model)Class.forName("Model").getDeclaredConstructor().newInstance();
    Method modelRunMethod = dataModel.getClass().getMethod("run");

    public Controller(String modelName) throws ClassNotFoundException, InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchMethodException {

    }

    public void readDataFrom(String fname) {

        try {
            File myObj = new File(fname);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String[] data = myReader.nextLine().split(" ");
                switch (data[0]) {
                    case "LATA":
                        LL = data.length - 1;
                        break;
                    case "twKI":
                        twKI = new double[LL];
                        for(int i = 0; i < LL; i++) twKI[i] = Double.parseDouble(data[1]);
                        break;
                    case "twKS":
                        twKS = new double[LL];
                        for(int i = 0; i < LL; i++) twKS[i] = Double.parseDouble(data[1]);
                        break;
                    case "twINW":
                        twINW = new double[LL];
                        for(int i = 0; i < LL; i++) twINW[i] = Double.parseDouble(data[1]);
                        break;
                    case "twEKS":
                        twEKS = new double[LL];
                        for(int i = 0; i < LL; i++) twEKS[i] = Double.parseDouble(data[1]);
                        break;
                    case "twIMP":
                        twIMP = new double[LL];
                        for(int i = 0; i < LL; i++) twIMP[i] = Double.parseDouble(data[1]);
                        break;
                    case "KI":
                        KI = new double[LL];
                        for(int i = 0; i < LL; i++) KI[i] = Double.parseDouble(data[1]);
                        break;
                    case "KS":
                        KS = new double[LL];
                        for(int i = 0; i < LL; i++) KS[i] = Double.parseDouble(data[1]);
                        break;
                    case "INW":
                        INW = new double[LL];
                        for(int i = 0; i < LL; i++) INW[i] = Double.parseDouble(data[1]);
                        break;
                    case "EKS":
                        EKS = new double[LL];
                        for(int i = 0; i < LL; i++) EKS[i] = Double.parseDouble(data[1]);
                        break;
                    case "IMP":
                        IMP = new double[LL];
                        for(int i = 0; i < LL; i++) IMP[i] = Double.parseDouble(data[1]);
                        break;
                }
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        Arrays.stream(dataModel.getClass().getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(Bind.class))
                .forEach(field -> {
                    field.setAccessible(true);
                    try {
                        switch (field.getName()) {
                            case "LL":
                                field.set(dataModel, LL);
                                break;
                            case "twKI":
                                field.set(dataModel, twKI);
                                break;
                            case "twKS":
                                field.set(dataModel, twKS);
                                break;
                            case "twINW":
                                field.set(dataModel, twINW);
                                break;
                            case "twEKS":
                                field.set(dataModel, twEKS);
                                break;
                            case "twIMP":
                                field.set(dataModel, twIMP);
                                break;
                            case "KI":
                                field.set(dataModel, KI);
                                break;
                            case "KS":
                                field.set(dataModel, KS);
                                break;
                            case "INW":
                                field.set(dataModel, INW);
                                break;
                            case "EKS":
                                field.set(dataModel, EKS);
                                break;
                            case "IMP":
                                field.set(dataModel, IMP);
                                break;
                        }
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                });
    }

    public void runModel() throws InvocationTargetException, IllegalAccessException {
        modelRunMethod.invoke(dataModel);
    }

    public void runScriptFromFile(String fname) {

    }

    public void runScript(String script) {

    }

    public String getResultsAsTsv() {
        for(int i = 0; i < LL; i++) {
            System.out.println("twKS " + twKS[i] + " ");
            System.out.println("KI " + KI[i] + " ");
        }
        return "";
    }
}
