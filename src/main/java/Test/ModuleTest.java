package Test;

import Control.KmeansDriver;
import Module.Center;
import Module.Point;
import Utils.Utils;
import jdk.internal.org.objectweb.asm.tree.analysis.Value;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.mapreduce.Job;
import org.junit.Test;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

import static Utils.Utils.EuclideanDistance;

public class ModuleTest {
    private ArrayList<Center> centers = new ArrayList<>();

    @Test
    public void ReadTest22() throws IOException, URISyntaxException {
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf);
        job.addCacheFile(new URI("src/main/resources/center.txt"));
    }

    @Test
    public void ReadTest() throws IOException {
        File file = new File("src/main/resources/center.txt");
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            centers.add(new Center(line));
        }
        IOUtils.closeStream(bufferedReader);
        for (Center center : centers) {
            System.out.println(center);
        }
    }

    @Test
    public void mapTest() throws IOException {
        ReadTest();
        System.out.println("******************************");
        Point outV = new Point("-0.7794151074651527\t-1.504349668535982\t0.7981106876823574\t1");
        double minDistance = Double.MAX_VALUE, distance;
        Center outK = new Center();
        for (Center center : centers) {
            try {
                distance = EuclideanDistance(center.getPoint().getAttributes(), outV.getAttributes());
                if (distance < minDistance) {
                    outK = center;
                    minDistance = distance;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.out.println("outk:" + outK);
        System.out.println("outV:" + outV);
    }

    @Test
    public void test() throws Exception {
//        String[] Path = new String[3];
//        Path[0] = "src/main/java/input/center.txt";//centerPath
//        Path[1] = "src/main/java/input/250_each.txt";// dataPath
//        Path[2] = "src/main/java/output2";// tempCenterPath

        String[] Path = new String[3];
        Path[0] = "src/main/java/2ClassInput/center.txt";//centerPath
        Path[1] = "src/main/resources/500_each.txt";// dataPath
        Path[2] = "src/main/java/2ClassOutput";// tempCenterPath

        Utils.deletePath(Path[2]);
        KmeansDriver.run(Path[0], Path[1], Path[2], true);
    }
}
