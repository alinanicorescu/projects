package biz.netcentric.processors;

import org.jsoup.nodes.Element;

import java.io.OutputStreamWriter;

/**
 * Created by alinanicorescu on 05.04.2017.
 */
public interface SlightlyElementProcessor {

     void process(Element element, OutputStreamWriter os) ;
}
