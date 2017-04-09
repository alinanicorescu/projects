package biz.netcentric.processors;

import biz.netcentric.SlightlyProcessingContext;
import biz.netcentric.SlightlyProcessingException;
import biz.netcentric.Utils;
import org.jsoup.nodes.*;

/**
 * Created by alinanicorescu on 05.04.2017.
 */
public class SimpleElementProcessor {

    public static void process(Element element, SlightlyProcessingContext context) throws SlightlyProcessingException {
        String startTagHtml = Utils.getStartTagText(element);
       if (element.childNodes().size() > 0) {
           //in html5, only non void elements must be closed
          context.setEndTag(Utils.getEndTagText(element));
        }
        TextProcessor.process(startTagHtml.toString(), context);
    }
}
