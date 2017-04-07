package biz.netcentric;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;

/**
 * Created by alinanicorescu on 05.04.2017.
 */
public class SlightlyServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String servletPath = req.getServletPath();
        int extIndex = servletPath.indexOf(".html");
        String pathWithNoExt = servletPath.substring(0, extIndex);
        String htmlFileName = pathWithNoExt.substring(pathWithNoExt.lastIndexOf("/"));

        //resp.getOutputStream().print("Slightly servlet : req html file is! :" + htmlFileName);
        InputStream is = getServletContext().getResourceAsStream(htmlFileName + ".html");

        /*
        String requestURL = req.getRequestURL().toString();
        int extIndex2 = requestURL.indexOf(".html");
        String requestURLNoExt = requestURL.substring(0, extIndex2);
        int slashIndex = requestURLNoExt.lastIndexOf("/");
        String baseUrl = requestURLNoExt.substring(0, slashIndex);
        resp.getOutputStream().print("\nbase url " + baseUrl);
        */

        OutputStreamWriter os = new OutputStreamWriter(resp.getOutputStream());
        SlightlyProcessingContext processingContext = new SlightlyProcessingContext(req);
        SlightlyScriptProcessor slightlyScriptProcessor = new SlightlyScriptProcessor(req, os);
        slightlyScriptProcessor.process(is);
        os.close();


/*      String reqURI = req.getServletPath();
        resp.getOutputStream().print("\nreq.getServletPath() " + req.getServletPath());
        resp.getOutputStream().print("\nreq.getContextPath() " + req.getContextPath());
        resp.getOutputStream().print("\nreq.getRequestURI() " + req.getRequestURI());
        resp.getOutputStream().print("\nreq.getPathInfo() " + req.getPathInfo());
        resp.getOutputStream().print("\nreq.getRequestURI() " + req.getRequestURI());
        resp.getOutputStream().print("\nreq.getRequestURL() " + req.getRequestURL());
*/
    }
}
