package org.example;

import com.badlogic.gdx.jnigen.*;

public class App {
    public static void main(String[] args) throws Exception {
        NativeCodeGenerator jnigen = new NativeCodeGenerator();
        jnigen.generate("src", "bin", "jni", new String[] {"**/Main.java"}, null);

        BuildTarget win32 = BuildTarget.newDefaultTarget(BuildTarget.TargetOs.Windows, false);
        win32.compilerPrefix = "mingw32-";
        BuildTarget win64 = BuildTarget.newDefaultTarget(BuildTarget.TargetOs.Windows, true);
        BuildTarget linux32 = BuildTarget.newDefaultTarget(BuildTarget.TargetOs.Linux, false);
        BuildTarget linux64 = BuildTarget.newDefaultTarget(BuildTarget.TargetOs.Linux, true);
        BuildTarget mac = BuildTarget.newDefaultTarget(BuildTarget.TargetOs.MacOsX, true);

        new AntScriptGenerator().generate(new BuildConfig("my-native-lib"), win32, win64);
        BuildExecutor.executeAnt("jni/build-windows32.xml", "-v -Drelease=true clean postcompile");
        BuildExecutor.executeAnt("jni/build-windows64.xml", "-v -Drelease=true clean postcompile");
        // BuildExecutor.executeAnt("jni/build-linux32.xml", "-v", "-Drelease=true", "clean", "postcompile");
        // BuildExecutor.executeAnt("jni/build-linux64.xml", "-v", "-Drelease=true", "clean", "postcompile");
        // BuildExecutor.executeAnt("jni/build-macosx32.xml", "-v", "-Drelease=true", "clean", "postcompile");
        BuildExecutor.executeAnt("jni/build.xml", "-v pack-natives");
    }
}
