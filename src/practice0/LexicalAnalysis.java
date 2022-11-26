package practice0;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Scanner;

public class LexicalAnalysis {
    public static void main(String []args){
        File file=new File("D:/lexical.c");

        LinkedList identifier=new LinkedList();
        LinkedList ConstIntNumber=new LinkedList();
        LinkedList ConstLongNumber=new LinkedList();

        try {
            Scanner scanner=new Scanner(file);
            String word_str="";
            String kind="";
            char temp_char=' ';

            while(scanner.hasNextLine())
            {
                String str=scanner.nextLine();
                //遍历处理这一行的所有字符
                for(int i=0;i<str.length();i++)
                {
                    char ch=str.charAt(i);
                    char chOperator=filter(ch);
                    if(chOperator!=' ')
                    {
                        if(temp_char==' ') {
                             if(chOperator=='"')
                             {
                                 System.out.println("(4,\""+chOperator+"\")");
                             }else
                            temp_char = chOperator;
                        }else {
                            word_str=temp_char+String.valueOf(chOperator);
                            System.out.println("(4,\""+word_str+"\")");
                            temp_char=' ';
                            word_str="";

                        }
                    }
                    else {
                        if(temp_char == '%')
                        {
                            word_str=temp_char+String.valueOf(ch);
                            System.out.println("(4,\""+word_str+"\")");
                            temp_char=' ';
                            word_str="";
                           continue;
                        }
                        else if(temp_char!=' ') {
                            System.out.println("(4,\"" + temp_char + "\")");
                            temp_char=' ';
                        }
                    }
                    ch=wordProcessChar(ch);
                    if(ch!=' ') {
                         word_str=word_str+ch;
                    }
                    else{
                        String num= constNumber(word_str);
                        if(!num.equals("")) {
                            long l = Long.parseLong(num);
                            System.out.println("(3,\""+l+"\")");
                            if (l > Integer.MAX_VALUE) {
                                ConstLongNumber.add(new ConstLongNumber(3, l));
                            } else {
                                int n = (int) l;
                                ConstIntNumber.add(new ConstIntNumber(3, n));
                            }
                        }
                        String reserve_word=ReserveWord(word_str);
                        //读取一个标识符或字符完毕,重置
                        if(reserve_word.equals("")){ //判断是否为保留字
                            String idWord=identifierWord(word_str);
                            if(!idWord.equals("")){   //判断是否是标识符
                                if(kind.equals("int")) {
                                    identifier.add(new Identifier(2, idWord, "int"));
                                }
                                else {
                                    identifier.add(new Identifier(2, idWord, "long"));
                                }
                                System.out.println("(2,\""+idWord+"\")");
                            }
                        }
                        else {
                            System.out.println("(1,\""+reserve_word+"\")");
                            kind=word_str;
                        }
                        word_str="";
                    }
                    char sep=separator(str.charAt(i));
                    if (sep!=' ')
                        System.out.println("(5,\""+sep+"\")");
                }
            }

        }catch (FileNotFoundException e)
        {
            System.out.println("找不到文件");
            e.printStackTrace();
        }
    }
    //处理保留字和标识符的字符
     public static  char wordProcessChar(char ch){
        if(ch>='a'&&ch<='z')
        {
            return ch;
        }
        if(ch>='A'&&ch<='Z')
        {
           return ch;
        }
        if(ch>='0'&&ch<='9')
        {
            return ch;
        }
        if(ch=='_')
        {
            return ch;
        }
           return ' ';
    }
     //判断是否为保留字
     public static String ReserveWord(String word) {
        String[] reserveWord = new String[]{"main", "printf", "if", "int", "for",
                "while", "do", "return", "break", "continue","long"};
        //foreach循环判断是否找到保留字
         for (String str : reserveWord) {
             if (str.equals(word)) {
                 return str;
             }
         }
         return "";
     }
     //处理标识符
     public static String identifierWord(String word){
        String identifier="";
        for(int i=0;i<word.length();i++)
        {
            if(word.charAt(0)>='0'&& word.charAt(0)<='9')
            {
                return "";
            }
            else{
                identifier=identifier+word.charAt(i);
                if(i==19)  //只识别标识符前二十个字符
                    break;
            }
        }
        return identifier;
     }
     public static String constNumber(String word) {
          String str="";
          for(int i=0;i<word.length();i++)
          {
              if(word.charAt(i)>='0'&&word.charAt(i)<='9')
              {
                  str=str+word.charAt(i);
              }
          }
          return str;
      }
      public static char filter(char ch){

          if(ch=='+')
                return ch;
          if(ch=='-')
              return ch;
          if(ch=='*')
              return ch;
          if(ch=='\\')
              return ch;
          if(ch=='=')
              return ch;
          if(ch=='<')
              return ch;
          if(ch=='>')
              return ch;
          if(ch=='!')
              return ch;
          if(ch=='%')
              return ch;
          if(ch=='"')
              return ch;
        return ' ';
      }
      public static char separator(char ch){
        if(ch==',')
            return ch;
        if(ch==';')
            return ch;
        if(ch==')')
            return ch;
        if(ch=='(')
            return ch;
        if(ch=='}')
            return ch;
        if(ch=='{')
            return ch;
        return ' ';
      }

}
