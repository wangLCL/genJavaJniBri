package gen;

import kw.gentool.GenTools;

public class App {
    public static void main(String[] args) {
        String srcPath =  "demo/src/main/java/";
        String prePath =  "demo/src/main/java/";
        GenTools genTools = new GenTools(srcPath,prePath);
////        genTools.clearTargetDir(); //clean target dir
//        genTools.genHeader(); //gen header
//        genTools.genCpp();
//        genTools.cmakeBuild();
        genTools.runBat();
//        genTools.genJar();
//        genTools.clean();

    }
}
