package biz.netcentric.processors;

import org.jsoup.nodes.TextNode;

import java.io.IOException;
import java.io.OutputStreamWriter;

/**
 * Created by alinanicorescu on 05/04/2017.
 */
public class ScriptProcessor {

    public static void process(String script, OutputStreamWriter os) throws IOException {
        os.write("Script processor");

    }
}
