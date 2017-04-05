package biz.netcentric;

import biz.netcentric.processors.SlightlyElementProcessor;
import biz.netcentric.processors.SlightlyElementProcessorProvider;
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

    private SlightlyProcessingContext slightlyProcessingContext;
    private OutputStreamWriter outputStreamWriter;

    public SlightlyScriptProcessor(SlightlyProcessingContext slightlyProcessingContext, OutputStreamWriter outputStreamWriter) {
        this.slightlyProcessingContext = slightlyProcessingContext;
        this.outputStreamWriter = outputStreamWriter;

    }

    public void process (InputStream inputStream) {

        HttpServletRequest request = slightlyProcessingContext.getHttpServletRequest();
        String baseUrl = getBaseUrl(request);

        Document document = null;
        try {
            document = Jsoup.parse(inputStream, null, baseUrl);
            //use node traversor
        } catch (IOException e) {
            e.printStackTrace();
        }

        Elements elements =  document.getElementsByTag("html");
        if (elements.isEmpty()) {
            //exception
        } else {
            Element htmlElement = elements.first();
            Elements htmlElements = htmlElement.children();
            ListIterator<Element> htmlElementsIterator = htmlElements.listIterator();
            while (htmlElementsIterator.hasNext()) {
                Element childElement = htmlElementsIterator.next();

                SlightlyElementProcessor elementProcessor =
                        SlightlyElementProcessorProvider.getElementProcessor(childElement);
                elementProcessor.process(childElement, outputStreamWriter);
            }
        }

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
