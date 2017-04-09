package biz.netcentric.processors;

import biz.netcentric.SlightlyProcessingContext;
import biz.netcentric.SlightlyProcessingException;
import org.jsoup.nodes.DataNode;

/**
 * Created by alinanicorescu on 05/04/2017.
 * Processor for data nodes, server side script type
 */
public class DataNodeProcessor {

    /**
     * If the node is a server side javascript snippet, the code is processed
     * @param dataNode
     * @param context
     * @throws SlightlyProcessingException
     */
    public static void process(DataNode dataNode, SlightlyProcessingContext context) throws SlightlyProcessingException {
        if (context.isScriptNode()) {
            ScriptProcessor.process(dataNode.getWholeData(), context);
        }
    }
}
