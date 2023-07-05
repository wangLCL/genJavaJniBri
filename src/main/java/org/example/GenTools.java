package org.example;

import com.badlogic.gdx.jnigen.FileDescriptor;
import com.badlogic.gdx.jnigen.NativeCodeGenerator;

import java.io.*;

public class GenTools {
    public static void main(String[] args)  {
        genHeader();
        genCpp();
    }

    private static void genCpp() {
        try {
            String pa = "src/main/java/org";
            File fileDescriptor = new File(pa);
            NativeCodeGenerator nativeCodeGenerator = new NativeCodeGenerator();
            nativeCodeGenerator.processDirectory(fileDescriptor);
        }catch (Exception e){

        }
    }

    private static void genHeader() {
        String command = "javac -h jni src/main/java/org/example/Main.java";
        try {
            Process exec = Runtime.getRuntime().exec(command);
            FileInputStream errorStream = (FileInputStream) exec.getErrorStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(errorStream));
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }
            reader.close();
            String s = stringBuilder.toString();
            System.out.println(s);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
