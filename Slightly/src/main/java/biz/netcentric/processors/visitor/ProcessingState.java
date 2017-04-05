package biz.netcentric.processors.visitor;

/**
 * Created by alinanicorescu on 05/04/2017.
 */
public class ProcessingState {

    private boolean isScript = false;
    private boolean shouldRender = true;
    private int renderSettingIndex;
    private int scriptSettingIndex;
    private int currentIndex;

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
        this.currentIndex = currentIndex;
    }
}
