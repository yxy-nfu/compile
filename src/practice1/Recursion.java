package practice1;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Recursion {
    public static void main(String []args){
        File file=new File("D:/recursion.txt");
        try{
            Scanner scanner=new Scanner(file);
            String expression="";
            while(scanner.hasNext())
            {
                expression = scanner.next() + expression;
            }
           if(processString(expression)){
               System.out.println(expression+"是合法符号串");
           }
           else{
               System.out.println(expression+"是非法字符串");
           }
        }
        catch (FileNotFoundException e){
         System.out.println("文件打开失败");
         e.printStackTrace();
        }
    }
    public static boolean processString(String expression){
           String exp="";
           for(int i=0;i<expression.length();i++){
               if(expression.charAt(i)=='#') {
                   break;
               }
               else{
                   exp=exp+expression.charAt(i);
               }
           }
        return E("TG", exp).equals(exp);
    }
    public static String E (String str,String expression) {
        if(!str.equals(""))
            System.out.println("E->"+str);
        str=str.replaceFirst("E","TG");
        if(!str.equals(""))
            System.out.println("E->"+str);
        str=T(str,expression);
        str=G(str,expression);
        return str;
    }
    public static String T(String str,String expression){
        str=str.replaceFirst("T","FS");
        if(!str.equals(""))
            System.out.println("E->"+str);
        str=F(str,expression);
        if(!str.equals("")) {
            str = S(str, expression);
        }
        return str;
    }
    public static String F(String str,String expression){
        if(test(str,expression,'i'))
        {
           str=str.replaceFirst("F","i");
           if(!str.equals(""))
               System.out.println("E->"+str);
           return str;
        }
        else if(test(str,expression,'('))
            {
                str=str.replaceFirst("F","(E)");
                str=E(str,expression);
                if(!str.equals(""))
                    System.out.println("E->"+str);
                return str;
            }
        else {
            System.out.println("Error:E->"+str);
        }
        return "";
    }
    public static String G(String str,String expression){
        if(test(str,expression,'+')){
            str=str.replaceFirst("G","+TG");
            if(!str.equals(""))
                System.out.println("E->"+str);
            str=T(str,expression);
            str=G(str,expression);
            return str;
     }
        else if(test(str,expression,'-')) {
            str=str.replaceFirst("G","-TG");
            if(!str.equals(""))
                System.out.println("E->"+str);
            str=T(str,expression);
            str=G(str,expression);
            return str;
        }
        str=str.replaceFirst("G","");
        System.out.println("E->"+str);
        return str;
    }
    public static String S(String str,String expression){
        if(test(str,expression,'*'))
        {
            str=str.replaceFirst("S","*FS");
            if(!str.equals(""))
                System.out.println("E->"+str);
            str=F(str,expression);
            if(!str.equals("")) {
                str = S(str, expression);
                return str;
            }
        } else if (test(str,expression,'/')) {
            str=str.replaceFirst("S","/FS");
            if(!str.equals(""))
                System.out.println("E->"+str);
            str=F(str,expression);
            if(!str.equals("")) {
                str = S(str, expression);
                return str;
            }
        }
        str=str.replaceFirst("S","");
        if(!str.equals(""))
            System.out.println("E->"+str);
        return str;
    }
    public static boolean test(String str1,String expression,char ch){
        for(int i=0;i<str1.length()&&i<expression.length();i++)
        {
            if(str1.charAt(i)!=expression.charAt(i)) {
                if(ch==expression.charAt(i))
                {
                    str1=str1.replaceFirst(String.valueOf(str1.charAt(i)),String.valueOf(ch));
                    str1=str1.substring(0,i+1);
                    expression=expression.substring(0,i+1);
                    return str1.equals(expression);
                }
                return false;
            }
        }
        return false;
    }
}
