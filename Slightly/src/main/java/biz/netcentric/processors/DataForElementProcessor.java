package biz.netcentric.processors;

import org.jsoup.nodes.Element;

import java.io.IOException;
import java.io.OutputStreamWriter;

/**
 * Created by alinanicorescu on 05.04.2017.
 */
public class DataForElementProcessor implements SlightlyElementProcessor {

    @Override
    public void process(Element element, OutputStreamWriter os) {

        try {
            os.write("Evaluating: " + DataForElementProcessor.class + " element: " + element.tagName());
            os.write("</br>");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
