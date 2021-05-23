package Control;

import Module.Center;
import Module.Point;
import Utils.Utils;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.*;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class KmeansMapper extends Mapper<LongWritable, Text, Center, Point> {
    private ArrayList<Center> centers = new ArrayList<>();

    // 将中心点读入
    @Override
    protected void setup(Context context) throws IOException{
        URI[] cacheFiles = context.getCacheFiles();
//        File file = new File(cacheFiles[0].toURL().getPath());
        FileSystem fs = FileSystem.get(context.getConfiguration());
        FSDataInputStream fis = fs.open(new Path(cacheFiles[0]));

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fis, StandardCharsets.UTF_8));
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            centers.add(new Center(line));
        }
        IOUtils.closeStream(bufferedReader);
    }

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        //
        Point outV = new Point(value.toString());

        // 判断点与每个中心点的距离，将其分类至与其最近的中心点
        double minDistance = Double.MAX_VALUE, distance = 0.0;
        Center outK = new Center();
        for (Center center : centers) {
            try {
                distance = Utils.EuclideanDistance(center.getPoint().getAttributes(), outV.getAttributes());
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (distance < minDistance) {
                outK = center;
                minDistance = distance;
            }
        }
        context.write(outK, outV);
    }
}
