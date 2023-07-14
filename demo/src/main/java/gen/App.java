package gen;

import kw.gentool.GenTools;

public class App {
    public static void main(String[] args) {
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
}
