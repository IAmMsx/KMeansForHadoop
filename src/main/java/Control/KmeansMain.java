package Control;

import Utils.Utils;

import java.io.IOException;
import java.net.URISyntaxException;

public class KmeansMain {
    public static void main(String[] args) throws Exception {
        String[] Path = new String[3];
        Path[0] = "src/main/java/input/center.txt";//centerPath
        Path[1] = "src/main/java/input/250_each.txt";// dataPath
        Path[2] = "src/main/java/output";// tempCenterPath

        int count = 0;
        KmeansDriver.run(Path[0], Path[1], Path[2], false);
//        while (true) {
//            Utils.transferCenterFile(Path[1], Path[3]);
//            System.out.println("这是第"+ ++count + "次循环");
//            if (Utils.compareCenterFile(Path[1], Path[2])) {// 停止循环
//                KmeansDriver.run(Path[1], Path[2], Path[3], true);
//                break;
//            }
//        }
    }
}
