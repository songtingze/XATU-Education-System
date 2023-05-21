package com.xatu.common.constant;

public class Regex {
    private static final String classNumberRegex = "[本,硕]\\d{4}";

    public static boolean judgeClassNumber(String classNumber){
        return classNumber.matches(classNumberRegex);
    }


//    public static void main(String[] args){
//        System.out.println(Regex.judgeClassNumber("硕2203"));
//    }

}
