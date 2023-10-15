package com.example.demo4;

import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.HashMap;
import java.util.Map;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("Введите выражение (или 'exit' для выхода): ");
            String input = scanner.nextLine();

            if (input.equalsIgnoreCase("exit")) {
                System.out.println("Выход из программы.");
                break;
            }

            try {
                String result = calc(input);
                System.out.println("Результат: " + result);
            } catch (Exception e) {
                System.out.println("Ошибка: " + e.getMessage());
            }
        }
        scanner.close();
    }

    public static String calc(String input) throws Exception {
        String[] tokens = input.split(" ");
        if (tokens.length != 3) {
            throw new Exception("Неверный формат ввода. Используйте два числа и одну арифметическую операцию.");
        }

        String num1Str = tokens[0];
        String operator = tokens[1];
        String num2Str = tokens[2];

        boolean isRoman = isRomanNumber(num1Str) && isRomanNumber(num2Str);

        int num1 = isRoman ? romanToArabic(num1Str) : Integer.parseInt(num1Str);
        int num2 = isRoman ? romanToArabic(num2Str) : Integer.parseInt(num2Str);

        if (num1 < 1 || num1 > 10 || num2 < 1 || num2 > 10) {
            throw new Exception("Числа должны быть от 1 до 10 включительно.");
        }

        int result;
        switch (operator) {
            case "+":
                result = num1 + num2;
                break;
            case "-":
                result = num1 - num2;
                break;
            case "*":
                result = num1 * num2;
                break;
            case "/":
                if (num2 == 0) {
                    throw new Exception("Ошибка: Деление на ноль!");
                }
                result = num1 / num2;
                break;
            default:
                throw new Exception("Ошибка: Неизвестная арифметическая операция.");
        }

        return isRoman ? arabicToRoman(result) : Integer.toString(result);
    }

    private static boolean isRomanNumber(String input) {
        return Pattern.matches("^[IVXLCDMivxlcdm]+$", input);
    }

    private static int romanToArabic(String input) {
        Map<Character, Integer> romanToArabicMap = new HashMap<>();
        romanToArabicMap.put('I', 1);
        romanToArabicMap.put('V', 5);
        romanToArabicMap.put('X', 10);
        romanToArabicMap.put('L', 50);
        romanToArabicMap.put('C', 100);
        romanToArabicMap.put('D', 500);
        romanToArabicMap.put('M', 1000);

        int result = 0;
        int prevValue = 0;

        for (int i = input.length() - 1; i >= 0; i--) {
            int value = romanToArabicMap.get(input.charAt(i));
            if (value < prevValue) {
                result -= value;
            } else {
                result += value;
            }
            prevValue = value;
        }

        return result;
    }

    private static String arabicToRoman(int number) {
        if (number < 1 || number > 3999) {
            throw new IllegalArgumentException("В Римской системе счисления нет отрицательных чисел!");
        }

        String[] romanSymbols = {"I", "IV", "V", "IX", "X", "XL", "L", "XC", "C", "CD", "D", "CM", "M"};
        int[] arabicValues = {1, 4, 5, 9, 10, 40, 50, 90, 100, 400, 500, 900, 1000};

        StringBuilder result = new StringBuilder();

        int i = romanSymbols.length - 1;

        while (number > 0) {
            if (number >= arabicValues[i]) {
                result.append(romanSymbols[i]);
                number -= arabicValues[i];
            } else {
                i--;
            }
        }

        return result.toString();
    }
}