import javax.swing.*;
import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws ClassNotFoundException, InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchMethodException {
        Controller ctl = new Controller("Model1");
//        String dataDir = "/Users/esherow/Desktop/Java/Compiler/Compiler/src/";
//        ctl.readDataFrom(dataDir + "data2.txt");
//        ctl.getResultsAsTsv();
        SwingUtilities.invokeLater(() -> menuStart(ctl));
    }

    public static List<String> listFilesUsingJavaIO(String dir) {
        return Stream.of(new File(dir).listFiles())
                .filter(file -> !file.isDirectory() && file.toString().endsWith(".txt"))
                .map(File::getName)
                .collect(Collectors.toList());
    }

    private static void menuStart(Controller controller) {
        Menu menu = new Menu(controller);
        menu.setVisible(true);
        List<String> dataLists = listFilesUsingJavaIO("/Users/esherow/Desktop/Java/Compiler/Compiler/src");
        menu.setDataModel(dataLists);
    }
}
