package Control;

import Module.Center;
import Module.Point;
import Utils.Utils;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import java.util.ArrayList;

// 1. 更新中心点
public class KmeansReducer extends Reducer<Center, Point,Center, NullWritable> {
    @Override
    protected void reduce(Center key, Iterable<Point> values, Context context) throws IOException, InterruptedException {
        ArrayList<Point> points = new ArrayList<>();
        for (Point value : values) {
            points.add(value);
        }
        // 更新中心点
        Center outK = Utils.updataCenters(points, key);
        context.write(outK,NullWritable.get());
    }
}
