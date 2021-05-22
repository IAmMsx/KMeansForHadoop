package Utils;

import Module.Center;
import Module.Point;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;
import org.apache.hadoop.io.IOUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Utils {

    // 删除tempcenter文件 注意：输出为文件夹
    public static void deletePath(String tempCenter) throws Exception {
        Configuration conf = new Configuration();
        Path path = new Path(tempCenter);
        FileSystem fileSystem = path.getFileSystem(conf);
        fileSystem.delete(path, true);
    }

    // 计算欧氏距离
    public static double EuclideanDistance(double[] a, double[] b) throws Exception {
        if (a.length != b.length) {
            throw new Exception("两样例维数不同");
        }
        //
        int distance = 0;
        for (int i = 0; i < a.length; i++) {
            distance += Math.sqrt(Math.pow(a[i] - b[i], 2));
        }

        return distance;
    }

    // 划分中心点
    public static void splitDataSet(String dataPath, String centerPath, int k) throws IOException {
        ArrayList<Center> centers = new ArrayList<>();
        BufferedReader bufferedReader = new BufferedReader(new FileReader(dataPath));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(centerPath));
        String line;
        int count = 1;
        while ((line = bufferedReader.readLine()) != null) {
            centers.add(new Center(line));
        }
        // 打乱数据集
        Collections.shuffle(centers);
        // 选前k个并使其标签分别为为1到k
        for (int i = 0; i < k; i++) {
            centers.get(i).setLabel(i + 1);
            bufferedWriter.write(centers.get(i).toString() + "\n");
        }
        IOUtils.closeStreams(bufferedReader, bufferedWriter);
    }

    // 更新中心点
    public static Center updataCenters(ArrayList<Point> points, Center key) {
        // 得到属性的维数
        int size = points.get(0).getAttributes().length;
        double[] centerAttr = new double[size];
        Center tempCent = new Center();
        // 计算每一列的平均值
        for (int i = 0; i < size; i++) {
            double sum = 0.0; // 第i列的总和
//            double[] attributes = points.get(i).getAttributes();
            for (Point point : points) {
                double tempAttr = point.getAttributes()[i];
                sum += tempAttr;
            }
            centerAttr[i] = sum / points.size();
        }
//        centers.add(new Center(new Point(centerAttr), key.getLabel()));
        tempCent = new Center(new Point(centerAttr), key.getLabel());
        return tempCent;
    }

    // 判断新旧两次运行生成的中心点是否相同
    public static boolean compareCenterFile(String centerPath, String tempCenterPath) throws IOException {

        BufferedReader centerReader = new BufferedReader(new FileReader(centerPath));
        BufferedReader tempCenterReader = new BufferedReader(new FileReader(tempCenterPath));

        ArrayList<Center> centers = new ArrayList<>();
        ArrayList<Center> tempCenter = new ArrayList<>();

        String line;
        while ((line = centerReader.readLine()) != null) {
            centers.add(new Center(line));
        }
        while ((line = tempCenterReader.readLine()) != null) {
            tempCenter.add(new Center(line));
        }
//            Arrays.equals(tempCenter, centers);
        IOUtils.closeStreams(centerReader, tempCenterReader);

        return centers.containsAll(tempCenter) && tempCenter.containsAll(centers);
    }

    /*
    将tempCenter中的内容转移到center中并删除tempCenter
     */
//    public static void transferCenterFile(String centerPath, String tempCenterPath) throws Exception {
//        // 清空centerFile
//        Path outPath = new Path(centerPath);
//        FileSystem fileSystem = outPath.getFileSystem(new Configuration());
//        FSDataOutputStream overWrite = fileSystem.create(outPath,true);
//        overWrite.writeChars("");
//
//        Path inPath = new Path(tempCenterPath);
//        FileStatus[] listFiles = fileSystem.listStatus(inPath);
////        for (int i = 0; i < listFiles.length; i++) {
////            FSDataOutputStream out = fileSystem.create(outPath);
////            FSDataInputStream in = fileSystem.open(listFiles[i].getPath());
////            IOUtils.copyBytes(in, out, 4096, true);
////        }
//        FSDataInputStream in = fileSystem.open(listFiles[0].getPath());
//        IOUtils.copyBytes(in,overWrite,4096,true);
//
//        overWrite.close();
//        in.close();
//
//        deletePath(tempCenterPath);
//    }
}
