package Control;

import Module.Center;
import Module.Point;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import java.util.ArrayList;

// 将点分类情况与类标写出
public class LastReduce extends Reducer<Center, Point, Text, IntWritable> {
    @Override
    protected void reduce(Center key, Iterable<Point> values, Context context) throws IOException, InterruptedException {
        Text outK = new Text();
        for (Point value : values) {
            outK.set(value + "\t");
            context.write(outK, new IntWritable(key.getLabel()));
        }
    }
}
