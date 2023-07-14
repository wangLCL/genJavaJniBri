package kw.test.example;

import java.io.File;

public class Main {

    // @off
    static public native int add (int a, int b); /*
		return a + b;
	*/

    static public native int min (int a, int b); /*
		return a - b;
	*/

    static public native int max (int a, int b); /*
	    return a > b ? a : b;
//		return a + b;
	*/

    static public native int[] readArr(int []a);/*
 jboolean isCopy;
    jint *jint1 = env->GetIntArrayElements(obj_a,&isCopy);
    jintArray out = env->NewIntArray(10);
    env->SetIntArrayRegion(out, 0, 10, jint1);
    return out;
    */


    static {
        File file = new File("jni\\libs\\Debug\\1_demo.dll");
        System.out.println(file.getAbsolutePath());
        System.load(file.getAbsolutePath());
//        System.setProperty("main_lib_dll","");
    }

    public static void main(String[] args) {
        Main main = new Main();
//        System.out.println(main.add(10, 39));
//        System.out.println(max(10,23));
        int a[] = new int[100];
        for (int i = 0; i < 100; i++) {
            a[i] = i;
        }
        int[] ints = main.readArr(a);
        for (int anInt : ints) {
            System.out.println(anInt);
        }
    }
}