package org.example;

public class Main {

    // @off
    static public native int add (int a, int b); /*
		return a + b;
	*/

    static {
        System.load("D:\\github\\jjcJen\\jni\\libs\\Debug\\1_demo.dll");
    }

    public static void main(String[] args) {
        Main main = new Main();
        System.out.println(main.add(10, 39));
    }
}