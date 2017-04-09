package biz.netcentric.processors;

import biz.netcentric.SlightlyProcessingContext;
import biz.netcentric.SlightlyProcessingException;

import javax.script.ScriptEngine;
import javax.script.ScriptException;

/**
 * Created by alinanicorescu on 09/04/2017.
 * Processor for server side javascript expressions
 */
public class ScriptExpressionProcessor {

    /**
     * Evaluate the given javascript expression and return the result
     * @param script
     * @param context
     * @throws SlightlyProcessingException
     */

    public static Object process(String script, SlightlyProcessingContext context) throws SlightlyProcessingException {
        ScriptEngine scriptEngine = context.getScriptEngine();
        try {
           return scriptEngine.eval(script);
        } catch (ScriptException e) {
            throw new SlightlyProcessingException("An exception occurred while processing script: ", e);
        }
    }
}
