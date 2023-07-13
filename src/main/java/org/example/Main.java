package org.example;

import java.io.File;

public class Main {

    // @off
    static public native int add (int a, int b); /*
		return a + b;
	*/

    static {
        File file = new File("jni\\libs\\Debug\\1_demo.dll");
        System.out.println(file.getAbsolutePath());
        System.load(file.getAbsolutePath());
//        System.setProperty("main_lib_dll","");
    }

    public static void main(String[] args) {
        Main main = new Main();
        System.out.println(main.add(10, 39));
    }
}