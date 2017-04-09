package biz.netcentric.processors;

import biz.netcentric.SlightlyProcessingContext;
import biz.netcentric.SlightlyProcessingException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by alinanicorescu on 05/04/2017.
 * Processor for text
 */
public class TextProcessor {

    /**
     * Process text that might contain script expressions
     * @throws SlightlyProcessingException
     */
    public static void process(String text, SlightlyProcessingContext slightlyProcessingContext) throws SlightlyProcessingException {

        Pattern p = Pattern.compile("\\$\\{[^}]*\\}");
        Matcher m = p.matcher(text);
        while (m.find()) {
            String expText = text.substring(m.start(), m.end());
            String exp = expText.substring(2, expText.length() - 1);
            Object obj = ScriptExpressionProcessor.process(exp, slightlyProcessingContext);
            text = text.replace(expText, obj.toString());
        }
        slightlyProcessingContext.write(text);

    }

}
