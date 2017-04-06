package biz.netcentric.processors;

import biz.netcentric.processors.visitor.ProcessingState;
import org.jsoup.nodes.TextNode;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.IOException;
import java.io.OutputStreamWriter;

/**
 * Created by alinanicorescu on 05/04/2017.
 */
public class ScriptProcessor {

    public static void process(String script, ProcessingState state, OutputStreamWriter os) throws IOException, ScriptException {
        ScriptEngine scriptEngine = state.getEngine();
        scriptEngine.eval(script);

    }
}
