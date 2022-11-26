package practice4;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Stack;

public class InversePolishStyle {
    public static void main(String[] args) {
        try {
            Scanner scanner = new Scanner(new File("D:/inverse.txt"));
            //预计答案存储在该文件中
            Scanner scannerProve=new Scanner(new File("D:/inverse_result.txt"));
            while (scanner.hasNextLine()) {
                String s = scanner.nextLine();
                s = priority(s);
                System.out.println("得到逆波兰式" + s);
                int answer = caculate(s);
                System.out.println("计算结果为" + answer);
                if(answer==scannerProve.nextInt()){
                    System.out.println("符合预期结果");
                }else{
                    System.out.println("错误,正确答案为"+scannerProve.nextInt());
                }
                System.out.println("/*********************************/");
            }
        } catch (FileNotFoundException e) {
            System.out.println("未找到文件");
            e.printStackTrace();
        }
    }

    public static String priority(String s) {
        String inversePolishString = "";
        Stack<Character> stack = new Stack<>();
        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            if (ch == '#') {
                while (!stack.empty()) {
                    char element = stack.pop();
                    inversePolishString = inversePolishString + element;
                }
                break;
            } else if (ch == '(') {
                stack.push(ch);
            } else if (ch == ')') {
                if (stack.empty()) {
                    System.out.println("表达式错误:')'无法找到匹配的')'");
                } else {
                    char element = stack.pop();
                    while (element != '(') {
                        inversePolishString = inversePolishString + element;
                        element = stack.pop();
                    }
                }

            } else if (ch == '+' || ch == '-') {
                if (stack.empty() || stack.peek() == '(')  //栈顶元素不是运算符,没有可比性,直接压栈
                {
                    stack.push(ch);
                } else {
                    //因为加减为运算优先级最低的符号 所以出栈直到知道栈为空或者出现左括号
                    while (!stack.empty() && stack.peek() != '(') {
                        inversePolishString = inversePolishString + stack.pop();
                    }
                    stack.push(ch);
                }
            } else if (ch == '*' || ch == '/') {
                //因为乘除为运算优先级最高的符号,所以只要考虑栈中有没有乘除号,若没有则可以直接压入
                if (stack.empty()) {
                    stack.push(ch);
                } else if (stack.peek() == '*' || stack.peek() == '/') {
                    char element = stack.pop();
                    inversePolishString = inversePolishString + element;
                    stack.push(ch);
                } else {
                    stack.push(ch);
                }
            } else if (ch >= '0' && ch <= '9') {
                //判断返回的逆波兰式是否存在两数相连的情况
                boolean sign = false;
                if (i >= 1 && !inversePolishString.equals("")) {
                    char numberSign = inversePolishString.charAt(inversePolishString.length() - 1);
                    char previousSign = s.charAt(i - 1);
                    if (previousSign < '0' || previousSign > '9') {
                        if (numberSign >= '0' && numberSign <= '9') {
                            sign = true;
                        }
                    }
                }
                if (sign) {
                    inversePolishString = inversePolishString + "&" + ch;
                } else {
                    inversePolishString = inversePolishString + ch;
                }
            } else {
                System.out.print("表达式错误,存在非法字符" + ch);
                break;
            }
        }
        return inversePolishString;
    }

    public static int caculate(String s) {
        Stack<Integer> stack = new Stack<>();
        String operator0 = ""; //用来存储第一个运算对象
        String operator1 = ""; //用来存储第二个运算对象
        boolean sign = true;//判断使用运算符1还是运算符2的标志
        //只需要考虑二目运算符的情况
        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            if (ch >= '0' && ch <= '9') {
                if (sign) {
                    operator0 = operator0 + ch;
                } else {
                    operator1 = operator1 + ch;
                }

            } else if (ch == '&') {
                sign = false;
                int number = Integer.parseInt(operator0);
                stack.push(number);
                operator0 = "";//清空该运算符
            } else {
                //将操作数2入栈
                int number = Integer.parseInt(operator1);
                operator1 = "";
                stack.push(number);
                //进行加法运算,将结果入栈
                int op1 = stack.pop();
                int op2 = stack.pop();
                switch (ch) {
                    case '+':
                        stack.push(op2 + op1);
                        break;
                    case '-':
                        stack.push(op2-op1);
                        break;
                    case'*':
                        stack.push(op2*op1);
                        break;
                    case '/':
                        stack.push(op2/op1);
                        break;
                }
            }
        }
        return stack.pop();
    }
}
