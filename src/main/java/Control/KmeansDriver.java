package Control;

import Module.Center;
import Module.Point;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class KmeansDriver {
    public static void run(String centerPath, String dataPath, String tempCenterPath, boolean isLast) throws IOException, URISyntaxException, InterruptedException, ClassNotFoundException {
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf);
        job.addCacheFile(new URI(centerPath));// 中心点的路径

        job.setJarByClass(KmeansDriver.class);

        job.setMapperClass(KmeansMapper.class);
        job.setMapOutputKeyClass(Center.class);
        job.setMapOutputValueClass(Point.class);

        if (!isLast) {
            job.setReducerClass(KmeansReducer.class);
            job.setOutputKeyClass(Center.class);
            job.setOutputValueClass(NullWritable.class);
        }

        FileInputFormat.setInputPaths(job, new Path(dataPath));
        FileOutputFormat.setOutputPath(job, new Path(tempCenterPath));

        System.out.println(job.waitForCompletion(true));
    }
}
