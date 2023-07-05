package org.example;

public class Main {

    // @off

    static public native int add (int a, int b); /*
		return a + b;
	*/

    static {
        System.load("C:\\Users\\Administrator\\source\\repos\\Project6\\x64\\Release\\Project6.dll");
    }

    public static void main(String[] args) {
        Main main = new Main();
        System.out.println(main.add(10, 39));
    }
}