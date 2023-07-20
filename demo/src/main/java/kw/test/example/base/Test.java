package kw.test.example.base;

import kw.test.example.ArrayDemo;
import kw.test.example.ClassDemo;
import kw.test.example.MathUtils;
import kw.test.example.SizeofTest;

import java.io.File;

public class Test {
    static {
        File file = new File("jni\\libs\\Debug\\1_demo.dll");
        System.out.println(file.getAbsolutePath());
        System.load(file.getAbsolutePath());
    }

    public static void main(String[] args) {
//        Test test = new Test();
//        test.classDemo();
        SizeofTest test = new SizeofTest();
        System.out.println(test.catSizeof("xx"));
    }

    public void classDemo(){
        ClassDemo demo = new ClassDemo();
        long people = demo.getPeople();
        System.out.println(people);
    }

    public void mathDemo(){
        MathUtils utils = new MathUtils();
        System.out.println(utils.add(100,10));
        System.out.println(utils.minus(100, 2));
        System.out.println(utils.multiply(3, 4));
    }

    public void arrayDemo(){
        int a[] = new int[100];
        for (int i = 0; i < 100; i++) {
            a[i] = i;
        }
        ArrayDemo demo = new ArrayDemo();
        int[] ints = demo.readArr(a);
        for (int anInt : ints) {
            System.out.println(anInt);
        }
    }
}
