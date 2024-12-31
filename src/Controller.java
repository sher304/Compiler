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

    private double[] parseData(String[] data, int LL, String varName) {
        double[] values = new double[LL];
        if (data.length == 2) {
            Arrays.fill(values, Double.parseDouble(data[1]));
        } else {
            for (int i = 0; i < LL; i++) {
                values[i] = (i + 1 < data.length) ? Double.parseDouble(data[i + 1]) : Double.parseDouble(data[data.length - 1]);
            }
        }
        return values;
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
                        twKI = parseData(data, LL, "twKI");
                        break;
                    case "twKS":
                        twKS = parseData(data, LL, "twKS");
                        break;
                    case "twINW":
                        twINW = parseData(data, LL, "twINW");
                        break;
                    case "twEKS":
                        twEKS = parseData(data, LL, "twEKS");
                        break;
                    case "twIMP":
                        twIMP = parseData(data, LL, "twIMP");
                        break;
                    case "KI":
                        KI = parseData(data, LL, "KI");
                        break;
                    case "KS":
                        KS = parseData(data, LL, "KS");
                        break;
                    case "INW":
                        INW = parseData(data, LL, "INW");
                        break;
                    case "EKS":
                        EKS = parseData(data, LL, "EKS");
                        break;
                    case "IMP":
                        IMP = parseData(data, LL, "IMP");
                        break;
                    case "PKB":
                        PKB = parseData(data, LL, "PKB");
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
        StringBuilder tsvBuilder = new StringBuilder(new String());
        String[][] bindFields = getBindFields();
        for (String[] row : bindFields) {
            String r = String.join("\t", row) + "\n";
            tsvBuilder.append(r);
        }
        return tsvBuilder.toString();
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
