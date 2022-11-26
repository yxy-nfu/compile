package practice2;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
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
        String y = "EGTSF";
        String[][] forecast = new String[6][9];
        for (int i = 0; i < forecast.length; i++)
            for (int j = 0; j < forecast[0].length; j++) {
                forecast[i][j] = "";
            }
        for (int i = 1; i < forecast.length; i++) {
            forecast[i][0] = String.valueOf(y.charAt(i - 1));
        }
        for (int j = 1; j < forecast[0].length; j++) {
            forecast[0][j] = String.valueOf(x.charAt(j - 1));
        }
        /*
        传入的文法已将同样非终止符开始的文法合并在一起
        方便后面First集的运算
         */
        String[] grammar = new String[10];
        grammar[0] = "E->TG";
        grammar[1] = "G->+TG";
        grammar[2] = "G->-TG";
        grammar[3] = "G->ε";
        grammar[4] = "T->FS";
        grammar[5] = "S->*FS";
        grammar[6] = "S->/FS";
        grammar[7] = "S->ε";
        grammar[8] = "F->(E)";
        grammar[9] = "F->i";

        String terminator = "+-*/()iε";
        String not_terminator = "EGTSF";
        //求出所有First集存储在字符串数组中
        String[] first = new String[not_terminator.length()];
        for (int i = 0; i < not_terminator.length(); i++) {
            first[i] = First(not_terminator.charAt(i), terminator, grammar);
            System.out.println("First(" + not_terminator.charAt(i) + "):" + first[i]);
        }
        //求出所有Follow集存储在字符串数组中
        String[] follow = new String[not_terminator.length()];
        for (int i = 0; i < not_terminator.length(); i++) {
            follow[i] = Follow(not_terminator.charAt(i), first, terminator, not_terminator, grammar);
            System.out.println("Follow(" + not_terminator.charAt(i) + "):" + follow[i]);
        }
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
    public static String First(char ch, String terminator, String[] grammar) {
        //找到ch对应的文法用s表示
        String s = "";
        StringBuilder str = new StringBuilder();
        for (String value : grammar) {
            if (value.charAt(0) == ch) {
                s = value;
                //找到->后面跟着的符号,判断是不是终止符再试图找到
                if (terminator.contains(String.valueOf(s.charAt(3)))) {
                    str.append(s.charAt(3));
                } else {
                    String tempString = First(s.charAt(3), terminator, grammar);
                    //检查该First集中是否含有空字符
                    int i = 3;//记录charAt的下标 相当于X->Y1Y2...YK中的下标
                    while (tempString.contains("ε")) {
                        i++;
                        tempString = tempString.replace("ε", "");
                        if (i >= s.length()) {
                            tempString = tempString + "ε";
                        } else {
                            tempString = tempString + First(s.charAt(i), terminator, grammar);
                        }
                    }
                    str.append(tempString);
                }
            }
        }
        return str.toString();
    }

    //求Follow集
    public static String Follow(char ch, String[] first, String terminator, String not_terminator, String[] grammar) {
        StringBuilder str = new StringBuilder();
        if (ch == 'E') {
            str.append("#");
        } //置"#"于开始符号的Follow集中
        for (String value : grammar) {
            int index = value.indexOf(ch);
            if (index > 3) {
                index++;
                //该符号为文法结尾
                if (index >= value.length()) {
                    str.append(Follow(value.charAt(0), first, terminator, not_terminator, grammar));
                } else {
                    //该符号后面还有符号
                    int index1 = terminator.indexOf(value.charAt(index));
                    if (index1 >= 0) {
                        str.append(value.charAt(index1));
                    }
                    int index0 = not_terminator.indexOf(value.charAt(index));
                    if (index0 >= 0 && first[index0].contains("ε")) {
                        str.append(first[index0].replace("ε", ""));
                        str.append(Follow(value.charAt(0), first, terminator, not_terminator, grammar));
                    } else if (index0 >= 0) {
                        str.append(first[index0]);
                    }
                }
            }
        }
        //使用HashSet过滤重复值
        HashSet<Character> hashSet=new HashSet<>();
        for(int i=0;i<str.length();i++){
            hashSet.add( str.toString().charAt(i));
        }
        str.delete(0,str.length());
        for(char c:hashSet)
        {
            str.append(c);
        }
        return str.toString();
    }


}
