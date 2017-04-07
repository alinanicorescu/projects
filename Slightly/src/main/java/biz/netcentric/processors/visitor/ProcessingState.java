package biz.netcentric.processors.visitor;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
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
    private int siblingStateIndex;

    private int siblingIndex;
    private Map<Integer, Map> siblingState = new HashMap<>();

    private HttpServletRequest httpServletRequest;
    ScriptEngineManager scriptEngineManager;

    private String[] endTags;

    ScriptEngine engine;

    

    public void initSiblingState() {
        this.siblingState = new HashMap<>();
        this.siblingStateIndex = currentIndex;
    }

    public void addToSiblingState(Integer siblingIndex, Map state) {
        this.siblingState.put(siblingIndex, state);
    }

    public Map getFromSiblingState(Integer sibling) {
        return this.siblingState.get(sibling);

    }

    public int getSiblingIndex() {
        return siblingIndex;
    }

    public void setSiblingIndex(int siblingIndex) {
        this.siblingIndex = siblingIndex;
    }

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
    }

    public void setCurrentIndex(int currentIndex) {
        if (currentIndex >this.siblingStateIndex) {
            initSiblingState();
        }
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
