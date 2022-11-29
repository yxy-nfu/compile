package practice3;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class LRAnalysis {
    public static void main(String[] args) {
        try {
            Scanner scanner=new Scanner(new File("D:/forecast.txt") );
            while (scanner.hasNextLine()){
             String expression=scanner.nextLine();
            }
        }catch (FileNotFoundException e){
            System.out.println("未找到文件");
            e.printStackTrace();
        }
    }
    public static void initForm(){
        String []grammar=new String[7];
        grammar[0]="S->E"; //扩广文法
        grammar[1]="E->E+T";
        grammar[2]="E->E-T";
        grammar[3]="T->T*F";
        grammar[4]="T->T/F";
        grammar[5]="F->(E)";
        grammar[6]="F->i";
        String terminator="+-*/()";
        String not_terminator="SETF";

        //求出所有First集存储在字符串数组中
        String[] first = new String[not_terminator.length()];
        for (int i = 0; i < not_terminator.length(); i++) {
            first[i] = First(not_terminator.charAt(i), terminator, grammar);
            System.out.println("First(" + not_terminator.charAt(i) + "):" + first[i]);
        }

    }
    public static String First(char ch, String terminator, String[] grammar) {
        //找到ch对应的文法用s表示
        StringBuilder str = new StringBuilder();
        for (String value : grammar) {
            if (value.charAt(0) == ch) {
                //找到->后面跟着的符号,判断是不是终止符再试图找到
                if (terminator.contains(String.valueOf(value.charAt(3)))) {
                    str.append(value.charAt(3));
                } else {
                    String tempString = First(value.charAt(3), terminator, grammar);
                    //检查该First集中是否含有空字符
                    int i = 3;//记录charAt的下标 相当于X->Y1Y2...YK中的下标
                    while (tempString.contains("ε")) {
                        i++;
                        tempString = tempString.replace("ε", "");
                        if (i >= value.length()) {
                            tempString = tempString + "ε";
                        } else {
                            tempString = tempString + First(value.charAt(i), terminator, grammar);
                        }
                    }
                    str.append(tempString);
                }
            }
        }
        return str.toString();
    }

}
