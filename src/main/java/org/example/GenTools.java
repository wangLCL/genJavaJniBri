package org.example;

import com.badlogic.gdx.jnigen.*;
import com.badlogic.gdx.jnigen.FileDescriptor;

import java.io.*;
import java.net.URL;

public class GenTools {

    public static void main(String[] args)  {
        clearTargetDir();
        String path = "src/main/java/org";
        File file = new File(path);
        printName(file);
        genCpp(path);
        cmakeBuild();
        runBat();  //执行老说没权限，  好吧
//        genJar();
//        genBuild();
//        antZip();
//        new SharedLibraryLoader("jni/libs/test-natives.jar").load("test");
    }

    private static void runBat() {

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

    private static void clearTargetDir() {
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
        for (File listFile : file.listFiles()) {
            if (listFile.isDirectory()){
                deleteDir(listFile);
            }else {
                listFile.delete();
            }
        }
        file.delete();
    }

    public static void cmakeBuild(){
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
                builder.append("include_directories(${PROJECT_SOURCE_DIR}/)");
                builder.append("\r\n");
                builder.append("add_library(1_demo SHARED org_example_Main.cpp)");
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
                builder.append("@echo off");
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
                writer.write(builder.toString());
                writer.flush();
                System.out.println(builder.toString());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        {
            String cmakeHeader = GenTools.class.getClassLoader().getResource("cmakeheader").getPath();
            File file = new File(cmakeHeader);
            try {
                for (File listFile : file.listFiles()) {
                    FileOutputStream output = new FileOutputStream(new File("jni/"+listFile.getName()));
                    FileInputStream inputStream = new FileInputStream(listFile);
                    write(output,inputStream);
                }
            }catch (Exception e){

            }

        }

    }

    public void cmake(){
        String command = "mkdir build \n" +
                "cd build \n" +
                "cmake .. \n" +
                "cmake --build . \n" +
                "demo \n";
        try {
            Process exec = Runtime.getRuntime().exec(command);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void printName(File file){
        File[] files = file.listFiles();
        for (File file1 : files) {
            if (file1.isDirectory()) {
                printName(file1);
            }else {
                String path = file1.getPath();
                if (path.endsWith(".java")) {
                    genHeader(path);
                }
            }
        }
    }

    private static void antZip() {
        boolean antExecutionStatus = BuildExecutor
                .executeAnt("jni/build.xml", "-v", "compile-natives");
        if (!antExecutionStatus) {
            throw new RuntimeException("Failure to execute linux/windows ant.");
        }
    }

    private static void genBuild() {
        BuildConfig buildConfig = new BuildConfig("" +
                "test", "gdx-jnigen",
                "libs", "jni");
        BuildTarget target;
        if (SharedLibraryLoader.isWindows)
            target = BuildTarget.newDefaultTarget(BuildTarget.TargetOs.Windows, SharedLibraryLoader.is64Bit);
        else if (SharedLibraryLoader.isLinux)
            target = BuildTarget.newDefaultTarget(BuildTarget.TargetOs.Linux, SharedLibraryLoader.is64Bit, SharedLibraryLoader.isARM);
        else if (SharedLibraryLoader.isMac)
            target = BuildTarget.newDefaultTarget(BuildTarget.TargetOs.MacOsX, SharedLibraryLoader.is64Bit, SharedLibraryLoader.isARM);
        else
            throw new RuntimeException("Unsupported OS to run tests.");

        new AntScriptGenerator().generate(buildConfig, target);
    }

    private static void genCpp(String path) {
        try {
            File fileDescriptor = new File(path);
            NativeCodeGenerator nativeCodeGenerator = new NativeCodeGenerator();
            nativeCodeGenerator.processDirectory(fileDescriptor);
        }catch (Exception e){

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
