import javax.swing.*;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws ClassNotFoundException, InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchMethodException {
//        SwingUtilities.invokeLater(() -> menuStart());
        Controller ctl = new Controller("Model1");
        String dataDir = "/Users/esherow/Desktop/Java/Compiler/Compiler/src/";
        ctl.readDataFrom(dataDir + "data1.txt");
        ctl.runModel();
        String res = ctl.getResultsAsTsv();
        System.out.println(res);
    }

    private static void menuStart() {
        Menu menu = new Menu();
        menu.setVisible(true);
    }
}
