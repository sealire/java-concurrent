package org.leesia.concurrent;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public class JavaTest {

    public static void printValur(String str){
        System.out.println("print value : "+str);
    }

    public static void main(String[] args) {
        List<String> al = Arrays.asList("a", "b", "c", "d");
        al.forEach(JavaTest::printValur);

        Consumer<String> methodParam = JavaTest::printValur;
        al.forEach(x -> methodParam.accept(x));
    }
}
