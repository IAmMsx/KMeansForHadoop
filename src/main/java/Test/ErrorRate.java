package Test;

import org.apache.hadoop.io.IOUtils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class ErrorRate {
    public static void main(String[] args) throws IOException {
        BufferedReader resultBR = new BufferedReader(new FileReader("src/main/java/2ClassLastOutput/points.txt"));
        BufferedReader trueBR = new BufferedReader(new FileReader("src/main/resources/500_each.txt"));

        HashMap<ArrayList<Double>, Integer> result = new HashMap<>();
        ArrayList<ArrayList<Double>> trueResult = new ArrayList<>();

        String line;
        int value;
        while ((line = resultBR.readLine()) != null) {
            ArrayList<Double> key = new ArrayList<>();
            String[] split = line.split("\t");
            for (int i = 0; i < split.length - 1; i++) {
                if (!split[i].equals(""))
                    key.add(Double.parseDouble(split[i]));
            }
            value = Integer.parseInt(split[split.length - 1]);
            result.put(key, value);
        }

        while ((line = trueBR.readLine()) != null) {
            ArrayList<Double> key = new ArrayList<>();
            String[] split = line.split("\t");
            for (String s : split) {
                key.add(Double.parseDouble(s));
            }
            trueResult.add(key);
        }

        double sum = 0.0;
        for (ArrayList<Double> list : trueResult) {
            ArrayList<Double> tempKey = new ArrayList<>(list.subList(0, 2));
            int trueValue = (int) Double.parseDouble(Double.toString(list.get(list.size() - 1)));
            if (result.get(tempKey) == trueValue) {
                sum++;
            }
        }

        System.out.println(sum / trueResult.size());

        IOUtils.closeStreams(resultBR, trueBR);

    }
}
