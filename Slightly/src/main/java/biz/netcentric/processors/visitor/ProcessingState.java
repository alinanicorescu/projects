package biz.netcentric.processors.visitor;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by alinanicorescu on 05/04/2017.
 */
public class ProcessingState {

    private boolean isScript = false;
    private boolean shouldRender = true;
    private int renderSettingIndex;
    private int scriptSettingIndex;
    private int currentIndex;
    private HttpServletRequest httpServletRequest;
    ScriptEngineManager scriptEngineManager;

    private int endIndex;
    private String endTag;

    ScriptEngine engine;

    public ProcessingState(HttpServletRequest httpServletRequest) {
        this.httpServletRequest = httpServletRequest;
        this.scriptEngineManager = new ScriptEngineManager();
        this.engine = scriptEngineManager.getEngineByName("rhino");
        engine.put("request", httpServletRequest);

    }

    public ScriptEngine getEngine() {
        return engine;
    }

    public void setEngine(ScriptEngine engine) {
        this.engine = engine;
    }

    public void setEndTag(String endTag) {
        this.endIndex = currentIndex;
        this.endTag = endTag;
    }

    public String getEndTag() {
        if (currentIndex == endIndex) {
            return endTag;
        }
        return null;
    }

    public boolean isShouldRender() {
        if (renderSettingIndex <= currentIndex) {
            return shouldRender;
        } else {
            return true;
        }    }

    public void setShouldRender(boolean shouldRender) {
        this.shouldRender = shouldRender;
        this.renderSettingIndex = currentIndex;
    }


    public boolean isScript() {
        if (scriptSettingIndex <= currentIndex) {
            return isScript;
        }
        return false;
    }

    public void setIsScript(boolean isScript) {
        this.isScript = isScript;
        this.scriptSettingIndex = currentIndex;
    }


    public void reset() {
        if (currentIndex == scriptSettingIndex) {
            isScript = false;
        }
        if (currentIndex == renderSettingIndex) {
            shouldRender = true;
        }
        this.endTag = null;
    }

    public void setCurrentIndex(int currentIndex) {
        this.currentIndex = currentIndex;
    }

    public HttpServletRequest getHttpServletRequest() {
        return httpServletRequest;
    }

    public void setHttpServletRequest(HttpServletRequest httpServletRequest) {
        this.httpServletRequest = httpServletRequest;
    }
}
