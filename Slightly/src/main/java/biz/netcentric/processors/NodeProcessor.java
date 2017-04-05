package biz.netcentric.processors;

import biz.netcentric.processors.visitor.ProcessingState;
import org.jsoup.nodes.Node;

import java.io.IOException;
import java.io.OutputStreamWriter;

/**
 * Created by alinanicorescu on 05/04/2017.
 */
public class NodeProcessor {

    public static void process(Node node, ProcessingState state, OutputStreamWriter os) throws IOException {
        if (state.isScript()) {
            //ScriptProcessor.process(node);
            os.write("Node processor: is script tag");
            return;
        }
    }

}
