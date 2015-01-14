//package bookshadoop;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import org.apache.hadoop.io.Writable;

public class MyWritable implements Writable {

    public int numpair;

    public MyWritable() {
    }

    public MyWritable(int num) {
        this.numpair = num;
    }

    @Override
    public void write(DataOutput out) throws IOException {
        out.writeInt(numpair);
    }

    @Override
    public void readFields(DataInput in) throws IOException {
        numpair = in.readInt();
    }
    
    @Override
    public String toString() {
        return "" + numpair;
    }
}
