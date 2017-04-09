package biz.netcentric.processors;

import biz.netcentric.SlightlyProcessingContext;
import biz.netcentric.SlightlyProcessingException;
import biz.netcentric.Utils;
import org.jsoup.nodes.Element;

import java.util.Collection;
import java.util.Map;

/**
 * Created by alinanicorescu on 05.04.2017.
 * Processor for data-for elements
 */
public class DataForElementProcessor  {

    /**
     * Process data-for attributes of element
     * If the element contains a data-for attribute, the data-for expression is evaluated
     * If the expression is evaluated as false,
     * the element should not be processed and the result of this method will be false.
     * Otherwise, the element will be processed and the method will return true.
     * @return <code>true</code>if the element should be displayed, <code>false</code>otherwise
     * @throws SlightlyProcessingException
     */
    public static boolean process(Element element, SlightlyProcessingContext context) throws SlightlyProcessingException {

        Map.Entry<String, String> forDataAttr = Utils.getForDataAttributeVarName(element);
        if (forDataAttr != null) {
            String varName = forDataAttr.getKey().substring("for-".length());
            if (context.getForNodeDataIterator() == null) {
                Object collection = ScriptExpressionProcessor.process(forDataAttr.getValue(), context);
                Collection objects = (Collection) collection;
                if (objects == null || objects.size() == 0) {
                    context.setShouldProcessNode(false);
                    return false;
                }
                context.setForNodeDataIterator(objects.iterator());
                context.setForNodeVarName(varName);
                //changed dom
                for (int i = 0; i < objects.size() - 1; i++) {
                    element.after(element.outerHtml());
                }

            }
            String var = (String)context.getForNodeDataIterator().next();
            context.getScriptEngine().put(context.getForNodeVarName(), var);
            element.removeAttr("data-" + forDataAttr.getKey());

        }
        return true;
    }


}



