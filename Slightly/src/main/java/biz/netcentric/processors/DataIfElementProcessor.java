package biz.netcentric.processors;

import biz.netcentric.SlightlyProcessingContext;
import biz.netcentric.SlightlyProcessingException;
import org.jsoup.nodes.Element;
import java.util.Map;

/**
 * Created by alinanicorescu on 07/04/2017.
 * Processor for data-if elements
 */
public class DataIfElementProcessor {

    /**
     * Process data-if attributes of element
     * If the element contains a data-if attribute, the data-if expression is evaluated
     * If the expression is evaluated as false,
     * the element should not be displayed and the result of this method will be false.
     * Otherwise, the element will be processed and the method will return true.
     * @param element
     * @param context
     * @return <code>true</code>if the element should be displayed, <code>false</code>otherwise
     * @throws SlightlyProcessingException
     */
    public static boolean process(Element element, SlightlyProcessingContext context) throws SlightlyProcessingException {

        Map<String, String> html5DataAttr = element.dataset();
        if (html5DataAttr != null) {
            //process data- elements
            String dataIfAttr = html5DataAttr.get("if");
            if (dataIfAttr != null) {
                Object obj = ScriptExpressionProcessor.process(dataIfAttr, context);
                boolean cond = (obj != null) ? (obj instanceof Boolean ? (Boolean) obj : true) : false;
                if (cond) {
                    context.setShouldProcessNode(true);
                    element.removeAttr("data-if");
                } else {
                    context.setShouldProcessNode(false);
                }
                return cond;
            }
        }
        return true;
    }
}