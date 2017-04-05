package biz.netcentric.processors;

import biz.netcentric.processors.visitor.ProcessingState;

import java.io.IOException;
import java.io.OutputStreamWriter;

/**
 * Created by alinanicorescu on 05/04/2017.
 */
public class ExpressionProcessor {

    public Object process (String expression, ProcessingState state, OutputStreamWriter os) throws IOException {
        os.write("Expression processor :" + expression + "\n");
        return null;
    }
}
