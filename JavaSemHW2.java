// Урок 2. Почему вы не можете не использовать API
// Формат сдачи: ссылка на подписанный git-проект.

// Задание

// 1) Дана строка sql-запроса "select * from students where ". Сформируйте часть WHERE этого запроса, используя StringBuilder. 
// Данные для фильтрации приведены ниже в виде строки.
// Если значение null, то параметр не должен попадать в запрос.
// Параметры для фильтрации: {"name":"Ivanov", "country":"Russia", "city":"Moscow", "age":"null"}

// 2) Реализуйте алгоритм сортировки пузырьком числового массива, результат после каждой итерации запишите в лог-файл.

// 3) К калькулятору из предыдущего ДЗ добавить логирование.


// 1) Дана строка sql-запроса "select * from students where ". Сформируйте часть WHERE этого запроса, используя StringBuilder. 
// Данные для фильтрации приведены ниже в виде строки.
// Если значение null, то параметр не должен попадать в запрос.
// Параметры для фильтрации: {"name":"Ivanov", "country":"Russia", "city":"Moscow", "age":"null"}

public class task1 {
    public static void main(String[] args) throws Exception {
      String [] list = lib.ReadLineFromFile("dataForSelect.txt"); 
      System.out.println(list[0]); 
      StringBuilder resultSelect = LineInList(list[0]);
      System.out.println(resultSelect);
    }
    public static StringBuilder LineInList(String line) {
        String line1 = line.replace("{", "");
        String line2 = line1.replace("}", "");
        String line3 = line2.replaceAll("\"", "");
        System.out.println(line3);
        StringBuilder result = new StringBuilder("select * from students where ");

        String [] arrayData = line3.split(", ");
        for (int i =0; i < arrayData.length; i++) {
            String[] arrData = arrayData[i].split(":");
            if(arrData[1].equals("null") == false) {
                if (i != 0) {
                    result.append(", ");
                    result.append(arrData[0]);
                    result.append(" = ");
                    result.append(arrData[1]);
                } else {
                    result.append(arrData[0]);
                    result.append(" = ");
                    result.append(arrData[1]);
                }
            }
            
        }
        return result;
    }
}


// 2) Реализуйте алгоритм сортировки пузырьком числового массива, результат после каждой итерации запишите в лог-файл.

import java.io.IOException;
import java.util.Arrays;
import java.util.Random;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class task2_2 {

    public static int[] randomArr() {
        Random rand = new Random();
        int arr[] = new int[10];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = rand.nextInt(100);
            System.out.print(arr[i] + " ");
        }
        System.out.println("");
        return arr;
    }

    public static int[] babblSort(int arr[]) throws IOException {
        Logger loger = Logger.getLogger(task2_2.class.getName());
        FileHandler fHandler = new FileHandler("Task2_2.txt");
        SimpleFormatter sFormatter = new SimpleFormatter();
        fHandler.setFormatter(sFormatter);
        loger.addHandler(fHandler);

        int temp;
        for (int i = arr.length - 1; i >= 0; i--) {
            for (int j = 0; j < arr.length - 1; j++) {
                if (arr[j] < arr[j + 1]) {
                    temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }

            loger.info(Arrays.toString(arr));
        }
        return arr;

    }

    public static void print(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
    }

    public static void main(String[] args) throws IOException {

        print(babblSort(randomArr()));
    }

}


// 3) К калькулятору из предыдущего ДЗ добавить логирование.

import java.util.*;
import java.util.regex.*;
import java.util.logging.*;
import java.io.*;

public class LoggingCalc {
    public static void main(String[] args) {
        Logger logger = Logger.getLogger(LoggingCalc.class.getName());
        logger.setLevel(Level.ALL);
        logger.setUseParentHandlers(false);

        try {
            FileHandler fh = new FileHandler("calc.log");
            logger.addHandler(fh);
            SimpleFormatter sf = new SimpleFormatter();
            fh.setFormatter(sf);
        } catch (IOException ioex) {
            logger.log(Level.SEVERE, "Can't create log file handler", ioex);
        }

        Scanner scn = new Scanner(System.in);

        while (true) {
            System.out.print("Введите выражение (пустая строка для выхода): ");
            String expression = scn.nextLine();
            if (expression.equals("")) {
                break;
            }
            logger.log(Level.INFO, "Entered expression: " + expression);

            List<String> tokens = new ArrayList<>();
            Pattern pattern = Pattern.compile("([0-9.]+)|(\\-|\\+|\\*|\\/)");
            Matcher matcher = pattern.matcher(expression);
            while (matcher.find()) {
                tokens.add(matcher.group().strip());
            }

            double result = 0.0;
            double x = Double.parseDouble(tokens.get(0));
            double y = Double.parseDouble(tokens.get(2));
            switch (tokens.get(1)){
                case "+":
                    result = x + y;
                    break;
                case "-":
                    result = x - y;
                    break;
                case "*":
                    result = x * y;
                    break;
                case "/":
                    result = x / y;
                    break;
                default:
                    result = Double.NaN;
            }

            if (Double.isNaN(result)) {
                System.out.println("Вы ввели неверное выражение");
                logger.log(Level.WARNING, "Expression was wrong!");
            } else {
                System.out.println(expression + " = " + result);
                logger.log(Level.INFO, "The result is " + result);
            }
        }

        scn.close();
        try {
            throw new Exception("You're leaving claculator! Shame on you");
        } catch (Exception e) {
            logger.log(Level.WARNING, e.getMessage());
        }
    }
}