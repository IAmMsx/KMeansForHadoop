package Module;

import org.apache.hadoop.io.Writable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Arrays;

public class Point implements Writable {
    private double[] attributes;

    public Point() {
    }

    public Point(double[] attributes) {
        this.attributes = attributes;
    }

    public Point(String line) {
        String[] split = line.split("\t");
        attributes = new double[split.length - 1];
        for (int i = 0; i < split.length - 1; i++) {
            attributes[i] = Double.parseDouble(split[i]);
        }
    }


    public double[] getAttributes() {
        return attributes;
    }

    public void setAttributes(double[] attributes) {
        this.attributes = attributes;
    }


    @Override
    public void write(DataOutput dataOutput) throws IOException {
        dataOutput.writeInt(attributes.length);
        for (double attribute : attributes) {
            dataOutput.writeDouble(attribute);
        }
    }

    @Override
    public void readFields(DataInput dataInput) throws IOException {
        int len = dataInput.readInt();
        attributes = new double[len];
        for (int i = 0; i < len; i++) {
            attributes[i] = dataInput.readDouble();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Point point = (Point) o;

        return Arrays.equals(attributes, point.attributes);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(attributes);
    }

    @Override
    public String toString() {
        StringBuilder out = new StringBuilder();
        for (double attribute : attributes) {
            out.append(attribute);
            out.append("\t");
        }
        return out.toString();
    }
}
