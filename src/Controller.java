import javax.script.ScriptEngine;
import javax.script.ScriptEngineFactory;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class Controller {

    String[] LLvalues;
    private int fCounter = 0;;
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
    double[] PKB; // GDP

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
                        LLvalues = new String[LL];
                        for(int i = 0; i < LL; i++) LLvalues[i] = data[i].equals("LL") ? data[1] : data[i + 1];
                        break;
                    case "twKI":
                        twKI = new double[LL];
                        if (data.length == 2) for(int i = 0; i < LL; i++) twKI[i] = Double.parseDouble(data[1]);
                        else for(int i = 0; i < LL; i++) {
                            if ((data.length - 1) <= i) twKI[i] = Double.parseDouble(data[data.length - 1]);
                            else twKI[i] = data[i].equals("twKI") ? Double.parseDouble(data[1]) : Double.parseDouble(data[i  + 1]);
                        }
                        break;
                    case "twKS":
                        twKS = new double[LL];
                        if (data.length == 2) for(int i = 0; i < LL; i++) twKS[i] = Double.parseDouble(data[1]);
                        else for(int i = 0; i < LL; i++) {
                            if ((data.length - 1) <= i) twKS[i] = Double.parseDouble(data[data.length - 1]);
                            else twKS[i] = data[i].equals("twKS") ? Double.parseDouble(data[1]) : Double.parseDouble(data[i  + 1]);
                        }
                        break;
                    case "twINW":
                        twINW = new double[LL];
                        if (data.length == 2) for(int i = 0; i < LL; i++) twINW[i] = Double.parseDouble(data[1]);
                        else for(int i = 0; i < LL; i++) {
                            if ((data.length - 1) <= i) twINW[i] = Double.parseDouble(data[data.length - 1]);
                            else twINW[i] = data[i].equals("twINW") ? Double.parseDouble(data[1]) : Double.parseDouble(data[i  + 1]);
                        }
                        break;
                    case "twEKS":
                        twEKS = new double[LL];
                        if (data.length == 2) for(int i = 0; i < LL; i++) twEKS[i] = Double.parseDouble(data[1]);
                        else for(int i = 0; i < LL; i++) {
                            if ((data.length - 1) <= i) twEKS[i] = Double.parseDouble(data[data.length - 1]);
                            else twEKS[i] = data[i].equals("twEKS") ? Double.parseDouble(data[1]) : Double.parseDouble(data[i  + 1]);
                        }
                        break;
                    case "twIMP":
                        twIMP = new double[LL];
                        if (data.length == 2) for(int i = 0; i < LL; i++) twIMP[i] = Double.parseDouble(data[1]);
                        else for(int i = 0; i < LL; i++) {
                            if ((data.length - 1) <= i) twIMP[i] = Double.parseDouble(data[data.length - 1]);
                            else twIMP[i] = data[i].equals("twIMP") ? Double.parseDouble(data[1]) : Double.parseDouble(data[i  + 1]);
                        }
                        break;
                    case "KI":
                        KI = new double[LL];
                        if (data.length == 2) for(int i = 0; i < LL; i++) KI[i] = Double.parseDouble(data[1]);
                        else for(int i = 0; i < LL; i++) {
                            if ((data.length - 1) <= i) KI[i] = Double.parseDouble(data[data.length - 1]);
                            else KI[i] = data[i].equals("KI") ? Double.parseDouble(data[1]) : Double.parseDouble(data[i  + 1]);
                        }
                        break;
                    case "KS":
                        KS = new double[LL];
                        if (data.length == 2) for(int i = 0; i < LL; i++) KS[i] = Double.parseDouble(data[1]);
                        else for(int i = 0; i < LL; i++) {
                            if ((data.length - 1) <= i) KS[i] = Double.parseDouble(data[data.length - 1]);
                            else KS[i] = data[i].equals("KS") ? Double.parseDouble(data[1]) : Double.parseDouble(data[i  + 1]);
                        }
                        break;
                    case "INW":
                        INW = new double[LL];
                        if (data.length == 2) for(int i = 0; i < LL; i++) INW[i] = Double.parseDouble(data[1]);
                        else for(int i = 0; i < LL; i++) {
                            if ((data.length - 1) <= i) INW[i] = Double.parseDouble(data[data.length - 1]);
                            else INW[i] = data[i].equals("INW") ? Double.parseDouble(data[1]) : Double.parseDouble(data[i  + 1]);
                        }
                        break;
                    case "EKS":
                        EKS = new double[LL];
                        if (data.length == 2) for(int i = 0; i < LL; i++) EKS[i] = Double.parseDouble(data[1]);
                        else for(int i = 0; i < LL; i++) {
                            if ((data.length - 1) <= i) EKS[i] = Double.parseDouble(data[data.length - 1]);
                            else EKS[i] = data[i].equals("EKS") ? Double.parseDouble(data[1]) : Double.parseDouble(data[i  + 1]);
                        }
                        break;
                    case "IMP":
                        IMP = new double[LL];
                        if (data.length == 2) for(int i = 0; i < LL; i++) IMP[i] = Double.parseDouble(data[1]);
                        else for(int i = 0; i < LL; i++) {
                            if ((data.length - 1) <= i) IMP[i] = Double.parseDouble(data[data.length - 1]);
                            else IMP[i] = data[i].equals("IMP") ? Double.parseDouble(data[1]) : Double.parseDouble(data[i  + 1]);
                        }
                        break;
                    case "PKB":
                        PKB = new double[LL];
                        if (data.length == 2) for(int i = 0; i < LL; i++) PKB[i] = Double.parseDouble(data[1]);
                        else for(int i = 0; i < LL; i++) {
                            if ((data.length - 1) <= i) PKB[i] = Double.parseDouble(data[data.length - 1]);
                            else PKB[i] = data[i].equals("PKB") ? Double.parseDouble(data[1]) : Double.parseDouble(data[i  + 1]);
                        }
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
                            case "PKB":
                                field.set(dataModel, PKB);
                                break;
                        }
                        fCounter++;
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                });
        try {
            runModel();
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public void runModel() throws InvocationTargetException, IllegalAccessException {
        modelRunMethod.invoke(dataModel);
    }

    public List<String[]> runScriptFromFile(String fname) {
        String script = "";
        try {
            File myObj = new File(fname);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String line = myReader.nextLine() + "\n";
                script += line;
            }
            myReader.close();
            return runScript(script);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private List<String> getVar(String script) {
        String variableRegex = "(?m)^\\s*(\\w+)\\s*=\\s*new\\s+\\w+";
        Pattern pattern = Pattern.compile(variableRegex);
        Matcher matcher = pattern.matcher(script);
        List<String> varNames = new ArrayList<>();
        while (matcher.find()) {
            varNames.add(matcher.group(1));
        }
        return  varNames;
    }

    public List<String[]> runScript(String script) {
        List<String> varNames = getVar(script);
        ScriptEngineManager scriptEngineManager = new ScriptEngineManager();
        ScriptEngine groovy = scriptEngineManager.getEngineByName("groovy");
        try {
            groovy.put("LL", LL);
            groovy.put("twKS", twKS);
            groovy.put("twKI", twKI);
            groovy.put("twINW", twINW);
            groovy.put("twEKS", twEKS);
            groovy.put("KI", KI);
            groovy.put("INW", INW);
            groovy.put("EKS", EKS);
            groovy.put("IMP", IMP);
            groovy.put("PKB", PKB);
            groovy.eval(script);
            List<String[]> datas = new ArrayList<>();
            for (String varName : varNames) {
                double[] resD = (double[]) groovy.get(varName);
                String[] resArr = new String[resD.length + 1];
                for (int i = 1; i < resArr.length; i++) {
                    resArr[0] = varName;
                    resArr[i] = String.valueOf(resD[i - 1]);
                }
                datas.add(resArr);
            }
            return datas;
        } catch (ScriptException e) {
            throw new RuntimeException(e);
        }
    }

    public String getResultsAsTsv() {
        return "";
    }

    public String[] getLLvalues() {
        return LLvalues;
    }

    public String[][] getBindFields() {
        String[][] bindFields = new String[fCounter][LL];
        Arrays.stream(dataModel.getClass().getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(Bind.class))
                .forEach(field -> {
                    field.setAccessible(true);
                    try {
                        switch (field.getName()) {
                            case "twKI":
                                bindFields[0] = Stream.concat(Arrays.stream(new String[]{"twKI"}), Arrays.stream(Arrays.stream((double[]) field.get(dataModel))
                                                .mapToObj(String::valueOf)
                                                .toArray(String[]::new)))
                                        .toArray(String[]::new);
                                break;
                            case "twKS":
                                bindFields[1] = Stream.concat(Arrays.stream(new String[]{"twKS"}), Arrays.stream(Arrays.stream((double[]) field.get(dataModel))
                                                .mapToObj(String::valueOf)
                                                .toArray(String[]::new)))
                                        .toArray(String[]::new);
                                break;
                            case "twINW":
                                bindFields[2] = Stream.concat(Arrays.stream(new String[]{"twINW"}), Arrays.stream(Arrays.stream((double[]) field.get(dataModel))
                                                .mapToObj(String::valueOf)
                                                .toArray(String[]::new)))
                                        .toArray(String[]::new);
                                break;
                            case "twEKS":
                                bindFields[3] = Stream.concat(Arrays.stream(new String[]{"twEKS"}), Arrays.stream(Arrays.stream((double[]) field.get(dataModel))
                                                .mapToObj(String::valueOf)
                                                .toArray(String[]::new)))
                                        .toArray(String[]::new);
                                break;
                            case "twIMP":
                                bindFields[4] = Stream.concat(Arrays.stream(new String[]{"twIMP"}), Arrays.stream(Arrays.stream((double[]) field.get(dataModel))
                                                .mapToObj(String::valueOf)
                                                .toArray(String[]::new)))
                                        .toArray(String[]::new);
                                break;
                            case "KI":
                                bindFields[5] = Stream.concat(Arrays.stream(new String[]{"KI"}), Arrays.stream(Arrays.stream((double[]) field.get(dataModel))
                                                .mapToObj(String::valueOf)
                                                .toArray(String[]::new)))
                                        .toArray(String[]::new);
                                break;
                            case "KS":
                                bindFields[6] = Stream.concat(Arrays.stream(new String[]{"KS"}), Arrays.stream(Arrays.stream((double[]) field.get(dataModel))
                                                .mapToObj(String::valueOf)
                                                .toArray(String[]::new)))
                                        .toArray(String[]::new);
                                break;
                            case "INW":
                                bindFields[7] = Stream.concat(Arrays.stream(new String[]{"INW"}), Arrays.stream(Arrays.stream((double[]) field.get(dataModel))
                                                .mapToObj(String::valueOf)
                                                .toArray(String[]::new)))
                                        .toArray(String[]::new);
                                break;
                            case "EKS":
                                bindFields[8] = Stream.concat(Arrays.stream(new String[]{"EKS"}), Arrays.stream(Arrays.stream((double[]) field.get(dataModel))
                                                .mapToObj(String::valueOf)
                                                .toArray(String[]::new)))
                                        .toArray(String[]::new);
                                break;
                            case "IMP":
                                bindFields[9] = Stream.concat(Arrays.stream(new String[]{"IMP"}), Arrays.stream(Arrays.stream((double[]) field.get(dataModel))
                                                .mapToObj(String::valueOf)
                                                .toArray(String[]::new)))
                                        .toArray(String[]::new);
                                break;
                            case "PKB":
                                bindFields[10] = Stream.concat(Arrays.stream(new String[]{"PKB"}), Arrays.stream(Arrays.stream((double[]) field.get(dataModel))
                                                .mapToObj(String::valueOf)
                                                .toArray(String[]::new)))
                                        .toArray(String[]::new);
                                break;
                        }
                    }  catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                });
        return bindFields;
    }
}
