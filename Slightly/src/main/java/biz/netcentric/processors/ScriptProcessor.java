package biz.netcentric.processors;

import biz.netcentric.SlightlyProcessingContext;
import biz.netcentric.SlightlyProcessingException;

import javax.script.ScriptEngine;
import javax.script.ScriptException;

/**
 * Created by alinanicorescu on 05/04/2017.
 * Processor for server side script code
 */
public class ScriptProcessor {

    /**
     * Evaluate the given javascript snippet
     * @param script
     * @param context
     * @throws SlightlyProcessingException
     */
    public static void process(String script, SlightlyProcessingContext context) throws SlightlyProcessingException {
        ScriptEngine scriptEngine = context.getScriptEngine();
        try {
            scriptEngine.eval(script);
        } catch (ScriptException e) {
            throw new SlightlyProcessingException("An exception occurred while processing script: ", e);
        }

    }
}
