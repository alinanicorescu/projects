package biz.netcentric.processors;

import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Element;

import java.util.HashMap;

/**
 * Created by alinanicorescu on 05.04.2017.
 */
public class SlightlyElementProcessorProvider {

    private static final SlightlyElementProcessor DEFAULT = new DefaultElementProcessor();
    private static final HashMap<String, SlightlyElementProcessor> processors =
            new HashMap<>();

    static {
        processors.put("server/javascript", new JavascriptElementProcessor());
        processors.put("data-if", new DataIfElementProcessor());
        processors.put("data-for-x", new DataForElementProcessor());
    }

    public static SlightlyElementProcessor getElementProcessor(Element element) {

        Attributes attributes = element.attributes();
        String typeAttrValue = attributes.get("type");

        if ("server/javascript".equals(typeAttrValue)) {
            return processors.get("server/javascript");

        } else {
            String dataIfAttr = attributes.get("data-if");
            if (!dataIfAttr.isEmpty()) {
                return processors.get("data-if");
            } else {
                for (Attribute attribute : attributes) {
                    if (attribute.getKey().startsWith("data-for")) {
                        return processors.get("data-for-x");
                    }
                }
            }
        }
        //should iterate children here
        return DEFAULT;
    }
}
