package biz.netcentric;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import javax.script.ScriptEngine;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;

/**
 * Created by alinanicorescu on 05.04.2017.
 * Process a Slightly html5 document
 */
public class SlightlyScriptProcessor {

    public static void process(InputStream in, String baseUrl, OutputStreamWriter os, ScriptEngine engine) throws IOException {
        Document document = Jsoup.parse(in, null, baseUrl);
        SlightlyProcessingContext context = new SlightlyProcessingContext(os, engine);
        context.initEndTags(document.getAllElements().size());
        SlightlyNodeVisitor nodeVisitor = new SlightlyNodeVisitor(context);
        document.traverse(nodeVisitor);
    }

}
