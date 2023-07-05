package org.example;

import com.badlogic.gdx.jnigen.FileDescriptor;
import com.badlogic.gdx.jnigen.NativeCodeGenerator;

import java.io.File;

public class GenTools {
    public static void main(String[] args) throws Exception {
        String pa = "src/main/java/org/example/";
        File fileDescriptor = new File(pa);
        NativeCodeGenerator nativeCodeGenerator = new NativeCodeGenerator();
        nativeCodeGenerator.processDirectory(fileDescriptor);
    }
}
