package biz.netcentric.processors.visitor;

import biz.netcentric.processors.ElementProcessor;
import biz.netcentric.processors.DataNodeProcessor;
import biz.netcentric.processors.TextNodeProcessor;
import org.jsoup.nodes.*;
import org.jsoup.select.NodeVisitor;

import javax.script.ScriptException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.OutputStreamWriter;

/**
 * Created by alinanicorescu on 05/04/2017.
 */
public class SlightlyNodeVisitor implements NodeVisitor {


    ProcessingState state;

    private OutputStreamWriter outputStreamWriter;

    public  SlightlyNodeVisitor(HttpServletRequest request, OutputStreamWriter outputStreamWriter) {
        this.state = new ProcessingState(request);
        this.outputStreamWriter = outputStreamWriter;
    }


    @Override
    public void head(Node node, int i) {
        /*
        try {
            outputStreamWriter.write("Entering.." + node.nodeName());
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        try {
            state.setCurrentIndex(i);

            if (!state.isShouldRender()) {
                outputStreamWriter.write("Should not render!");
                return;
            }
            if (node instanceof TextNode) {
                TextNodeProcessor.process((TextNode) node, state, outputStreamWriter);
            }
            else if (node instanceof DataNode) {
                DataNodeProcessor.process(node, state, outputStreamWriter);
            } else if (node instanceof Element) {
                ElementProcessor.process((Element) node, state, outputStreamWriter);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ScriptException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void tail(Node node, int i) {
        /*
        try {
            outputStreamWriter.write("Leaving.." + node.nodeName());
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        if (state.getEndTag() != null) {
            try {
                outputStreamWriter.write(state.getEndTag());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        state.reset();

    }
}
