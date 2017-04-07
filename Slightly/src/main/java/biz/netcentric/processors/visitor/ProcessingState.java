package biz.netcentric.processors.visitor;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by alinanicorescu on 05/04/2017.
 */
public class ProcessingState {

    private boolean isScript = false;
    private boolean shouldRender = true;
    private int renderSettingIndex;
    private int scriptSettingIndex;
    private int currentIndex;

    public String getForVarName() {
        return forVarName;
    }

    public void setForVarName(String forVarName) {
        this.forVarName = forVarName;
    }

    private String forVarName;
    public Iterator getForDataIterator() {
        return forDataIterator;
    }

    public void setForDataIterator(Iterator forDataIterator) {
        this.forDataIterator = forDataIterator;
    }

    private Iterator forDataIterator;


    private HttpServletRequest httpServletRequest;
    ScriptEngineManager scriptEngineManager;

    private String[] endTags;

    ScriptEngine engine;

    



    public ProcessingState(HttpServletRequest httpServletRequest, int elSize) {
        this.httpServletRequest = httpServletRequest;
        this.scriptEngineManager = new ScriptEngineManager();
        this.engine = scriptEngineManager.getEngineByName("rhino");
        engine.put("request", httpServletRequest);
        this.endTags = new String[elSize];
    }

    public ScriptEngine getEngine() {
        return engine;
    }

    public void setEngine(ScriptEngine engine) {
        this.engine = engine;
    }

    public void setEndTag(String endTag) {
        System.out.println("Writing the end tag for: " + endTag + " i " +currentIndex);

        this.endTags[currentIndex] = endTag;
    }

    public String getEndTag(int forNodeIndex) {
       return this.endTags[forNodeIndex];
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
        if (forDataIterator!= null && !forDataIterator.hasNext()) {
            forDataIterator = null;
            forVarName = null;
        }
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

    public int getCurrentIndex() {
        return currentIndex;
    }
}
