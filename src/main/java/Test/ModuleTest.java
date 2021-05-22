package Test;

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
    public void ReadTest() throws IOException, URISyntaxException {
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
    public void mapTest() throws IOException, URISyntaxException {
        ReadTest();
        System.out.println("******************************");
        Point outV = new Point("-0.7794151074651527\t-1.504349668535982\t0.7981106876823574\t1");
        double minDistance = Double.MAX_VALUE, distance = 0.0;
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
}
