package biz.netcentric;

import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.StringJoiner;

/**
 * Created by alinanicorescu on 09/04/2017.
 */
public class Utils {

    public static String getRequestedHtmlFileName(String servletPath) {
        int extIndex = servletPath.indexOf(".html");
        String pathWithNoExt = servletPath.substring(0, extIndex);
        String htmlFileName = pathWithNoExt.substring(pathWithNoExt.lastIndexOf("/"));
        return htmlFileName + ".html";
    }

    public static String getBaseUrl(HttpServletRequest request) {
        String requestURL = request.getRequestURL().toString();
        int extIndex2 = requestURL.indexOf(".html");
        String requestURLNoExt = requestURL.substring(0, extIndex2);
        int slashIndex = requestURLNoExt.lastIndexOf("/");
        String baseUrl = requestURLNoExt.substring(0, slashIndex);
        return baseUrl;
    }

    public static boolean isServerSideScriptTag(Element element) {
        return "script".equals(element.tagName()) && "server/javascript".equals(element.attr("type"));

    }

    public static String getStartTagText(Element element) {
        StringJoiner startTagHtml = new StringJoiner("");
        startTagHtml.add("<").add(element.tagName());
        if (element.attributes().size() > 0) {
            startTagHtml.add(element.attributes().html());
        }
        startTagHtml.add(">");
        return startTagHtml.toString();
    }

    public static String getEndTagText(Element element) {
        return new StringJoiner("").add("</").add(element.tagName()).add(">").toString();
    }

    public static Map.Entry<String, String> getForDataAttributeVarName(Element element) {
        Map<String, String> html5DataAttr = element.dataset();
        if (html5DataAttr != null) {
            for (Map.Entry<String, String> attrEntry : html5DataAttr.entrySet()) {
                if (attrEntry.getKey().startsWith("for")) {
                    return attrEntry;
                }
            }
        }
        return null;
    }

    public static String getExceptionMessage(Node node) {
        return "Exception while processing script, document node: " + node.baseUri();
    }
}
