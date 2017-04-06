package biz.netcentric.processors;

import biz.netcentric.processors.visitor.ProcessingState;

import javax.script.ScriptException;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by alinanicorescu on 05/04/2017.
 */
public class TextProcessor {

    public static void process(String text, ProcessingState processingState, OutputStreamWriter out) throws IOException, ScriptException {

        Pattern p = Pattern.compile("(?!(\"))\\$\\{[^}]*\\}(?!\")");
        Matcher m = p.matcher(text);

        while ( m.find() ) {
            String expText = text.substring(m.start(), m.end());
            String exp = expText.substring(2 , expText.length() - 1 );
            ExpressionProcessor expressionProcessor = new ExpressionProcessor();
            Object obj = expressionProcessor.process(exp, processingState, out);
            text = text.replace(expText, obj.toString());
        }

        out.write(text);
    }

    /*
    public static void main(String[] args) {
        Pattern p = Pattern.compile("(?!(\"))\\$\\{[^}]*\\}(?!\")");
        Matcher m = p.matcher("${person.name}");
        System.out.println(m.matches());
    }*/
}
