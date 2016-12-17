/**
 * Created by alinanicorescu on 17/12/2016.
 */


import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;

public class JettyServer {


        public static void main(String[] args) throws Exception {
            Server server = new Server(7878);

            WebAppContext context = new WebAppContext();
            final String webappDirLocation = "src/main/webapp/";

            context.setDescriptor(webappDirLocation + "/WEB-INF/web.xml");
            context.setResourceBase(webappDirLocation);
            context.setContextPath("/");
            context.setParentLoaderPriority(true);

            server.setHandler(context);


            server.start();
            server.join();
        }

}
