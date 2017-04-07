package biz.netcentric.processors;

import biz.netcentric.processors.visitor.ProcessingState;
import org.jsoup.nodes.DataNode;
import org.jsoup.nodes.Node;

import javax.script.ScriptException;
import java.io.IOException;
import java.io.OutputStreamWriter;

/**
 * Created by alinanicorescu on 05/04/2017.
 */
public class DataNodeProcessor {

    public static void process(Node node, ProcessingState state, OutputStreamWriter os) throws IOException, ScriptException {
        if (state.isScript()) {
            ScriptProcessor scriptProcessor = new ScriptProcessor();
            DataNode dataNode = (DataNode)node;
            scriptProcessor.process(dataNode.getWholeData(), state, os);
            //os.write("Node processor: is script tag");
            return;
        }
    }
}
