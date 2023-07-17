package kw.test.example;

import java.io.File;

/**
 * math
 */
public class MathUtils {

    // @off
    public native int add (int a, int b); /*
		return a + b;
	*/

    public native int minus (int a, int b); /*
		return a - b;
	*/

    public native int multiply (int a, int b); /*
		return a * b;
	*/

    static {
        File file = new File("jni\\libs\\Debug\\1_demo.dll");
        System.out.println(file.getAbsolutePath());
        System.load(file.getAbsolutePath());
//        System.setProperty("main_lib_dll","");
    }

    public static void main(String[] args) {
        MathUtils main = new MathUtils();
        System.out.println(main.add(10, 10));
        System.out.println(main.minus(30, 10));
        System.out.println(main.multiply(1, 4));
    }
}