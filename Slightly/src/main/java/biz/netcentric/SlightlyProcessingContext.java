package biz.netcentric;

import javax.script.ScriptEngine;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Iterator;

/**
 * Created by alinanicorescu on 05/04/2017.
 * Contains all the context data needed for node processing while traversing the document
 */
public class SlightlyProcessingContext {

    /**
     * variables set while traversing a serverside java script node
     * this will be useful when traversing child nodes
     */
    private boolean scriptNode = false;
    private int scriptNodeLevel;

    /**
     * variables set while traversing a node that should not be processed
     * this will be useful when traversing child nodes
     */
    private boolean shouldProcessNode = true;
    private int processNodeLevel;

    /**
     * holds the current node level
     */
    private int currentNodeLevel;

    /**
     * variables used to hold information related to a data-for node,
     * if present
     */
    private String forNodeVarName;
    private Iterator forNodeDataIterator;

    /**
     * array holding the end tags for elements, at index = node level
     */
    private String[] endTags;


    /**
     * variables related to processing
     */
    private ScriptEngine scriptEngine;
    private OutputStreamWriter os;



    public SlightlyProcessingContext( OutputStreamWriter os, ScriptEngine scriptEngine) {
        this.scriptEngine = scriptEngine;
        this.os = os;
    }

    public void initEndTags(int elementsCount) {
        this.endTags = new String[elementsCount];
    }

    public ScriptEngine getScriptEngine() {
        return scriptEngine;
    }

    public void write(String text) throws SlightlyProcessingException {
        try {
            this.os.write(text);
        } catch (IOException e) {
            throw SlightlyProcessingException.createWriteException(e);
        }
    }

    public void writeEndTag() throws SlightlyProcessingException {
        String tag = getEndTag(currentNodeLevel);
        if (tag != null) {
            try {
                this.os.write(tag);
            } catch (IOException e) {
                throw SlightlyProcessingException.createWriteException(e);
            } finally {
                endTags[currentNodeLevel] = null;
            }
        }
    }

    public void setCurrentNodeLevel(int currentNodeLevel) {
        this.currentNodeLevel = currentNodeLevel;
    }

    public String getForNodeVarName() {
        return forNodeVarName;
    }

    public void setForNodeVarName(String forNodeVarName) {
        this.forNodeVarName = forNodeVarName;
    }

    public Iterator getForNodeDataIterator() {
        return forNodeDataIterator;
    }

    public void setForNodeDataIterator(Iterator forNodeDataIterator) {
        this.forNodeDataIterator = forNodeDataIterator;
    }


    public void setEndTag(String endTag) {
        this.endTags[currentNodeLevel] = endTag;
    }

    public String getEndTag(int nodeIndex) {
       return this.endTags[nodeIndex];
    }

    public void setShouldProcessNode(boolean shouldProcessNode) {
        this.shouldProcessNode = shouldProcessNode;
        this.processNodeLevel = currentNodeLevel;
    }

    /**
     *
     * @return <code>true</code>if the node at the current level should be processed
     */
    public boolean shouldProcessNode() {
        if (this.currentNodeLevel >= this.processNodeLevel) {
            //(A node should be processed only if its parent should be processed)
            return this.shouldProcessNode;
        }
        return true;
    }

    /**
     *
     * @return <code>true</code> if the node at the current level is server side script
     */
    public boolean isScriptNode() {
        if (this.currentNodeLevel >= this.scriptNodeLevel) {
            return this.scriptNode;
        }
        return true;
    }

    public void setScriptNode(boolean scriptNode) {
        this.scriptNode = scriptNode;
        this.scriptNodeLevel = this.currentNodeLevel;
    }

    /**
     * Reset the context variables after processing a node
     */
    public void reset() {

        if (this.currentNodeLevel == this.scriptNodeLevel) {
            //(should only reset the script flag if leaving the same node as when was set)
            this.scriptNode = false;
        }
        if (this.currentNodeLevel == this.processNodeLevel) {
            //(should only reset the render flag if leaving the same node as when was set)
            this.shouldProcessNode = true;
        }

        //reset the for node data if previously existed and the iteration was over
        if (this.forNodeDataIterator != null && !this.forNodeDataIterator.hasNext()) {
            this.forNodeDataIterator = null;
            this.forNodeVarName = null;
        }
    }

}
