package biz.netcentric;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;

/**
 * Created by alinanicorescu on 05.04.2017.
 * Servlet that handles Slightly html scripts
 */
public class SlightlyServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String servletPath = req.getServletPath();
        InputStream is = getServletContext().getResourceAsStream(
                Utils.getRequestedHtmlFileName(servletPath));
        String baseUrl = Utils.getBaseUrl(req);

        OutputStreamWriter os = new OutputStreamWriter(resp.getOutputStream());

        ScriptEngineManager scriptEngineManager = new ScriptEngineManager();
        ScriptEngine scriptEngine = scriptEngineManager.getEngineByName("rhino");
        scriptEngine.put("request", req);

        SlightlyScriptProcessor.process(is, baseUrl, os, scriptEngine);
        os.close();
    }

}
