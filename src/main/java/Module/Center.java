package Module;

import org.apache.hadoop.io.WritableComparable;
import org.checkerframework.checker.units.qual.Length;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Objects;
import java.util.logging.Level;

public class Center implements WritableComparable<Center> {
    private Point point;
    private Integer label;

    public Center() {
        point = new Point(); // 不写会throw point空指针
    }

    public Center(Point point, int label) {
        this();
        this.point = point;
        this.label = label;
    }

    public Center(String line) {
        this();
        point = new Point(line);
        String[] split = line.split("\t");
        label = Integer.parseInt(split[split.length - 1]);
    }

    public Point getPoint() {
        return point;
    }

    public void setPoint(Point point) {
        this.point = point;
    }

    public Integer getLabel() {
        return label;
    }

    public void setLabel(Integer label) {
        this.label = label;
    }

    @Override
    public int compareTo(Center o) {
        return this.label.compareTo(o.label);
    }

    @Override
    public void write(DataOutput dataOutput) throws IOException {
        dataOutput.writeInt(label);
        point.write(dataOutput);
//        dataOutput.writeInt(point.getAttributes().length);
//        for (double attribute : point.getAttributes()) {
//            dataOutput.writeDouble(attribute);
//        }
    }

    @Override
    public void readFields(DataInput dataInput) throws IOException {
        label = dataInput.readInt();
//        int len = dataInput.readInt();
//        double[] attrs = new double[len];
//        for (int i = 0; i < attrs.length; i++) {
//            attrs[i] = dataInput.readDouble();
//        }
//        point.setAttributes(attrs);
        point.readFields(dataInput);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Center center = (Center) o;

        if (!Objects.equals(point, center.point)) return false;
        return Objects.equals(label, center.label);
    }

    @Override
    public int hashCode() {
        int result = point != null ? point.hashCode() : 0;
        result = 31 * result + (label != null ? label.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return point.toString() + label;
    }
}
