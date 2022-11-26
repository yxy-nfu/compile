package practice2;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Forecast {
    public static void main(String[] args) {
        try {
            Scanner scanner = new Scanner(new File("D:/forecast.txt"));
            String[][] form = initForecastForm();
            while (scanner.hasNextLine()) {
                String expression = scanner.nextLine();


            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    public static String[][] initForecastForm() {
        //初始化一个预测分析表,xy表示行和列
        String x = "+-*/()i#";
        String y = "ETGFSE";
        String[][] forecast = new String[6][9];
        for (int i = 0; i < forecast.length; i++)
            for (int j = 0; j < forecast[0].length; j++) {
                forecast[i][j] = "";
            }
        for (int i = 1; i < forecast.length; i++) {
            forecast[i][0] = String.valueOf(x.charAt(i - 1));
        }
        for (int j = 1; j < forecast[0].length; j++) {
            forecast[0][j] = String.valueOf(y.charAt(j - 1));
        }
        /*
        传入的文法已将同样非终止符开始的文法合并在一起
        方便后面First集的运算
         */
        String[] grammar = new String[5];
        grammar[0] = "E->TG";
        grammar[1] = "G->+TG|-TG|ε";
        grammar[2] = "T->FS";
        grammar[3] = "S->*FS|/FS|ε";
        grammar[4] = "F->(E)|i";

        String terminator = "+-*/()iε";
        String not_terminator = "EGTSF";

        show(forecast);

        return forecast;
    }

    public static void show(String[][] forecast) {
        System.out.print("     ");
        for (int i = 1; i < forecast[0].length; i++) {
            System.out.print(forecast[0][i] + "         ");
        }
        System.out.println();
        for (int i = 1; i < forecast.length; i++) {
            System.out.print(forecast[i][0] + "  ");
            for (int j = 1; j < forecast[0].length; j++) {
                if (!forecast[i][j].equals("")) {
                    System.out.print(forecast[i][j] + "    ");
                } else {
                    System.out.print("          ");
                }
            }
            System.out.println();
        }
    }

    //求First集
    public static String First(char ch, String terminator, String not_terminator, String[] grammar) {
        //找到ch对应的文法用s表示
        String s = "";
        for (int i = 0; i < grammar.length; i++) {
            if (grammar[i].charAt(1) == ch) {
                s = grammar[i];
            }
        }
        String str = "";
        //找到->后面跟着的符号,判断是不是终止符
        if (terminator.contains(String.valueOf(s.charAt(3)))) {
            str = str + s.charAt(3);
        } else {
            String tempString = First(s.charAt(3), terminator, not_terminator, grammar);
            //检查该First集中是否含有空字符
            int i=3;//记录charAt的下标 相当于X->Y1Y2...YK中的下标
            while (tempString.contains("ε")) {
                i++;
                tempString = tempString.replace("ε", "");
                if(s.charAt(i)=='|'||s.charAt(i)=='\"'){
                    tempString=tempString+"ε";
                }
            }
            str = str + tempString;
        }
        return str;
    }


}
