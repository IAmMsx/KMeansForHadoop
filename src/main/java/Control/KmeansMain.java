package Control;

import Utils.Utils;

import java.io.IOException;
import java.net.URISyntaxException;

public class KmeansMain {
    public static void main(String[] args) throws Exception {
//        String[] Path = new String[3];
//        Path[0] = "src/main/java/input/center.txt";//centerPath
//        Path[1] = "src/main/java/input/250_each.txt";// dataPath
//        Path[2] = "src/main/java/output2";// tempCenterPath

        String[] Path = new String[3];
        Path[0] = "src/main/java/2ClassInput/center.txt";//centerPath
        Path[1] = "src/main/resources/500_each.txt";// dataPath
        Path[2] = "src/main/java/2ClassOutput";// tempCenterPath

        int count = 0;
        while (true) {
            KmeansDriver.run(Path[0], Path[1], Path[2], false);
            System.out.println("这是第" + ++count + "次循环");
            if (!Utils.compareCenterFile(Path[0], Path[1])) {// 中心点不同 继续循环
                Utils.transferCenterFile(Path[0], Path[2]);
            } else { // 最后一次循环 将点与类标写出
                Utils.deletePath(Path[2]);
                KmeansDriver.run(Path[0], Path[1], Path[2], true);
                break;
            }
        }
    }
}
