package biz.netcentric;

import biz.netcentric.processors.visitor.SlightlyNodeVisitor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;
import org.jsoup.select.NodeVisitor;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.ListIterator;

/**
 * Created by alinanicorescu on 05.04.2017.
 */
public class SlightlyScriptProcessor {

    //private SlightlyProcessingContext slightlyProcessingContext;
    private OutputStreamWriter outputStreamWriter;
    private HttpServletRequest httpServletRequest;

    public SlightlyScriptProcessor(HttpServletRequest httpServletRequest, OutputStreamWriter outputStreamWriter) {
        this.httpServletRequest = httpServletRequest;
        this.outputStreamWriter = outputStreamWriter;

    }

    public void process (InputStream inputStream) {

        String baseUrl = getBaseUrl(httpServletRequest);

        Document document = null;
        try {
            document = Jsoup.parse(inputStream, null, baseUrl);
        } catch (IOException e) {
            e.printStackTrace();
        }

        SlightlyNodeVisitor nodeVisitor = new SlightlyNodeVisitor(httpServletRequest, outputStreamWriter);
        document.traverse(nodeVisitor);

    }

    private static String getBaseUrl(HttpServletRequest request) {
        String requestURL = request.getRequestURL().toString();
        int extIndex2 = requestURL.indexOf(".html");
        String requestURLNoExt = requestURL.substring(0, extIndex2);
        int slashIndex = requestURLNoExt.lastIndexOf("/");
        String baseUrl = requestURLNoExt.substring(0, slashIndex);
        return baseUrl;
    }
}
