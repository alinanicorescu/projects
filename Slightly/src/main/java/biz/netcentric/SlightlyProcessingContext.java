package biz.netcentric;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by alinanicorescu on 05.04.2017.
 */
public class SlightlyProcessingContext {

    private HttpServletRequest httpServletRequest;

    public SlightlyProcessingContext(HttpServletRequest httpServletRequest) {
        this.httpServletRequest = httpServletRequest;
    }

    public HttpServletRequest getHttpServletRequest() {
        return httpServletRequest;
    }

    public void setHttpServletRequest(HttpServletRequest httpServletRequest) {
        this.httpServletRequest = httpServletRequest;
    }
}
