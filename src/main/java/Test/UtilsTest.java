package Test;

import Module.Center;
import Module.Point;
import Utils.Utils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;
import org.apache.hadoop.io.IOUtils;
import org.junit.Test;

import javax.rmi.CORBA.Util;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class UtilsTest {
    private ArrayList<Point> points = new ArrayList<>();

    // 创造一个points数组用于下面的测试
    public ArrayList<Point> createPoints(){
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new FileReader("src/main/resources/center.txt"));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                points.add(new Point(line));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeStreams(bufferedReader);
        }
        return points;
    }

    @Test
    public void deletePathTest() throws Exception {
        Utils.deletePath("src/main/resources/test.txt");
    }

    @Test
    public void splitDataSetTest() throws IOException {
//        Utils.splitDataSet("src/main/resources/250_each.txt", "src/main/resources/center.txt", 4);
        Utils.splitDataSet("src/main/resources/500_each.txt","src/main/java/2ClassInput/center.txt",2);
    }

    @Test
    public void updataCentersTest() {
        ArrayList<Point> points = createPoints();
        Center center = new Center("-1.2001668081219714\t0.7302255185177612\t2.2207347379978044\t1");
        Center tempCent = Utils.updataCenters(points, center);
        System.out.println(tempCent);
    }

    @Test
    public void compareCenterFileTest() throws IOException {
        boolean b = Utils.compareCenterFile("src/main/resources/center.txt", "src/main/resources/tempCenter.txt");
        System.out.println(b);
    }

    @Test
    public void transferCenterFileTest() throws Exception {
        Utils.transferCenterFile("src/main/java/input/tempCenter.txt","src/main/java/output/output");
    }

//    @Test
//    public void test() throws IOException {
//        Path outPath = new Path("src/main/java/input/center.txt");
//        FileSystem fileSystem = outPath.getFileSystem(new Configuration());
//        FSDataOutputStream overWrite = fileSystem.create(outPath,true);
//
//
//        Path inPath = new Path("src/main/java/output/output/output3");
//        FileStatus[] listFiles = fileSystem.listStatus(inPath);
//
//        FSDataInputStream in = fileSystem.open(listFiles[0].getPath());
//        System.out.println(listFiles[0].getPath());
//        IOUtils.copyBytes(in,overWrite,4096,true);
//        overWrite.close();
//
//        IOUtils.closeStreams(overWrite,in);
//
//    }

    @Test
    public void test1() throws Exception {
        Utils.transferCenterFile("src/main/java/2ClassLastOutput/points.txt","src/main/java/2ClassOutput");
    }



}
