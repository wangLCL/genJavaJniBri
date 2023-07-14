package kw.gentool;

import com.badlogic.gdx.jnigen.NativeCodeGenerator;

import java.io.*;
import java.util.jar.Attributes;
import java.util.jar.JarEntry;
import java.util.jar.JarOutputStream;
import java.util.jar.Manifest;

public class GenTools {
    private boolean isWindows = System.getProperty("os.name").contains("Windows");
    private boolean isLinux = System.getProperty("os.name").contains("Linux");
    private boolean isMac = System.getProperty("os.name").contains("Mac");
    private String srcPath =  "demo/src/main/java/";
    private String prePath =  "demo/src/main/java/";

    public GenTools(String srcPath,String prePath){
        this.srcPath = srcPath;
        this.prePath = prePath;
    }

    public static void main(String[] args)  {
        String srcPath =  "demo/src/main/java/";
        String prePath =  "demo/src/main/java/";
        GenTools genTools = new GenTools(srcPath,prePath);
        genTools.clearTargetDir();
        genTools.genHeader();
        genTools.genCpp();
        genTools.cmakeBuild();
        genTools.runBat();
        genTools.genJar();
    }

    public void genJar(){
        try {
            buildJarWithDll();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static final String DLL_PATH = "jni/libs/Debug/1_demo.dll";  // 替换为你的 DLL 文件路径
    private static final String JAR_PATH = "libs/test.jar";  // 替换为你要生成的 JAR 文件路径

    private static void buildJarWithDll() throws IOException {
        File dllFile = new File(DLL_PATH);
        File jarFile = new File(JAR_PATH);

        // 创建 JAR 文件
        Manifest manifest = new Manifest();
        manifest.getMainAttributes().put(Attributes.Name.MANIFEST_VERSION, "1.0");
        manifest.getMainAttributes().put(Attributes.Name.MAIN_CLASS, GenTools.class.getName());

        try (JarOutputStream jarOutputStream = new JarOutputStream(new FileOutputStream(jarFile), manifest)) {
            // 将 DLL 文件添加到 JAR 文件中
            addToJar(jarOutputStream, dllFile, dllFile.getName());
        }

        System.out.println("JAR 文件已生成：" + jarFile.getAbsolutePath());
    }

    private static void addToJar(JarOutputStream jarOutputStream, File file, String entryName) throws IOException {
        try (InputStream inputStream = new FileInputStream(file)) {
            JarEntry jarEntry = new JarEntry(entryName);
            jarOutputStream.putNextEntry(jarEntry);

            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                jarOutputStream.write(buffer, 0, bytesRead);
            }

            jarOutputStream.closeEntry();
        }
    }

    public void runBat() {
        File file = new File("jni/run.bat");
        System.out.println();
        try {
            String s = "cmd /c start " + file.getAbsolutePath();
            Process exec = Runtime.getRuntime().exec(s);
            exec.waitFor();
            FileInputStream errorStream = (FileInputStream) exec.getErrorStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(errorStream));
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void clearTargetDir() {
        File file = new File("jni");
        deleteDir(file);
    }

    public static void write (FileOutputStream output,InputStream input) {
        try {
            byte[] buffer = new byte[4096];
            while (true) {
                int length = input.read(buffer);
                if (length == -1) break;
                output.write(buffer, 0, length);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (input != null) input.close();
            } catch (Exception ignored) {
            }
            try {
                if (output != null) output.close();
            } catch (Exception ignored) {
            }
        }
    }


    public static void deleteDir(File file){
        if (file.listFiles()==null)return;
        for (File listFile : file.listFiles()) {
            if (listFile.isDirectory()){
                deleteDir(listFile);
            }else {
                listFile.delete();
            }
        }
        file.delete();
    }

    public void cmakeBuild(){
        {
            File file = new File("jni/CMakeLists.txt");
            try {
                FileWriter writer = new FileWriter(file);
                StringBuilder builder = new StringBuilder();
                builder.append("cmake_minimum_required(VERSION 3.10)");
                builder.append("\r\n");
                builder.append("project(\"1_demo\")");
                builder.append("\r\n");
                builder.append("set(LIBRARY_OUTPUT_PATH ../libs)");
                builder.append("\r\n");
                builder.append("file(GLOB cppfile ${PROJECT_SOURCE_DIR}/*cpp)");
                builder.append("\r\n");
                builder.append("include_directories(${PROJECT_SOURCE_DIR}/)");
                builder.append("\r\n");
                builder.append("add_library(1_demo SHARED ${cppfile})");
                writer.write(builder.toString());
                writer.flush();
                System.out.println(builder.toString());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        {
            /**
             * create file: type nul > txt.txt
             * delete file: del txt.txt
             */
            File file = new File("jni/run.bat");
            try {
                FileWriter writer = new FileWriter(file);
                StringBuilder builder = new StringBuilder();
                if (isWindows) {
                    builder.append("@echo off");
                }else if (isLinux){
                    builder.append("!/bin/bash");
                }else {
                    throw new RuntimeException("no support !");
                }
                builder.append("\r\n");
                builder.append("cd jni");
                builder.append("\r\n");
                builder.append("mkdir build");
                builder.append("\r\n");
                builder.append("cd build");
                builder.append("\r\n");
                builder.append("cmake ..");
                builder.append("\r\n");
                builder.append("cmake --build .");
                builder.append("\r\n");

                writer.write(builder.toString());
                writer.flush();
                System.out.println(builder.toString());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        {
            GenTools.class.getResource(".");
            String cmakeHeader = GenTools.class.getClassLoader().getResource("cmakeheader").getPath();
            File file = new File(cmakeHeader);
            try {
                for (File listFile : file.listFiles()) {
                    FileOutputStream output = new FileOutputStream(new File("jni/"+listFile.getName()));
                    FileInputStream inputStream = new FileInputStream(listFile);
                    write(output,inputStream);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public void genHeader(){
        File file = new File(srcPath);
        bianFile(file);
    }

    public void bianFile(File file){
        File[] files = file.listFiles();
        for (File file1 : files) {
            if (file1.isDirectory()) {
                bianFile(file1);
            }else {
                String path = file1.getPath();
                if (path.endsWith(".java")) {
                    genHeader(path);
                }
            }
        }
    }


    public void genCpp() {
        try {
            File fileDescriptor = new File(srcPath);
            NativeCodeGenerator nativeCodeGenerator = new NativeCodeGenerator(prePath);
            nativeCodeGenerator.processDirectory(fileDescriptor);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private static void genHeader(String path) {
        String command = "javac -h jni " + path;
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
