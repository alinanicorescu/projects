import biz.netcentric.Person;
import biz.netcentric.SlightlyScriptProcessor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.*;

/**
 * Created by alinanicorescu on 09/04/2017.
 */

@RunWith(Parameterized.class)
public class SlightlyProcessingTest {

    private static ScriptEngine SCRIPT_ENGINE;

    private String inputFile;
    private String outputFile;
    private String id;

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {"index.html", "result.html", null}, {"index_1.html", "result_1.html", "1"},
                {"index_2.html", "result_2.html", "2"}, {"index_3.html", "result_3.html", "3"}
        });
    }

    public SlightlyProcessingTest(String inputFile, String outputFile, String id) {
        this.inputFile = inputFile;
        this.outputFile = outputFile;
        this.id = id;
    }


    @BeforeClass
    public static void init() {
        ScriptEngineManager scriptEngineManager = new ScriptEngineManager();
        SCRIPT_ENGINE = scriptEngineManager.getEngineByName("rhino");
    }


    @Test
    public void testProcessing() throws URISyntaxException, IOException {

        File inputHtml = getFile(inputFile);
        FileInputStream is = new FileInputStream(inputHtml);

        File outputHtml = getFile(outputFile);
        FileOutputStream output = new FileOutputStream(outputHtml);
        OutputStreamWriter os  = new OutputStreamWriter(output);

        SlightlyScriptProcessor.process(is, "", os, SCRIPT_ENGINE);
        os.close();

        String text = getStringContent(outputHtml);
        runAsserts(id, text);

    }

    public static void runAsserts(String id, String text) {
        Person person = Person.lookup(id);
        List<String> children = person.getChildren();
        String name = person.getName();
        Document document = Jsoup.parse(text);
        Elements dataElements = document.getElementsByAttributeStarting("data-");
        Assert.assertTrue(dataElements.size() == 0 );
        Assert.assertTrue(name.equals(document.title()));
        Elements elements = document.body().getElementsByTag("h1");
        Assert.assertTrue(elements.size() == 1);
        Element h1 = elements.first();
        Assert.assertTrue(h1.attr("title").equals(name));
        Assert.assertTrue(h1.text().equals(name));

        Elements h2elements = document.body().getElementsByTag("h2");
        if (person.isMarried()) {
            Assert.assertTrue(h2elements.size() > 0);
            Element h2= h2elements.first();
            String spouse = person.getSpouse();
            Assert.assertTrue(h2.attr("title").equals(spouse));
            Assert.assertTrue(h2.text().contains(spouse));
        } else {
            Assert.assertTrue(h2elements.size() == 0);
        }

        Elements divelements = document.body().getElementsByTag("div");
        Assert.assertTrue(divelements.size() ==  person.getChildren().size());

        if (divelements.size() > 0) {
            ListIterator<Element> divIterator = divelements.listIterator();
            int i = 0;
            while (divIterator.hasNext()) {
                Assert.assertTrue(divIterator.next().text().contains(children.get(i++)));
            }
        }

    }

    public static File getFile(String filename) throws URISyntaxException {
        URL resourceUrl = SlightlyProcessingTest.class.getResource(filename);
        File file = new File(resourceUrl.toURI());
        return file;
    }

    public static String getStringContent(File fileName) throws FileNotFoundException {
        Scanner scanner = new Scanner(fileName);
        String text = scanner.useDelimiter("\\A").next();
        scanner.close();
        return text;
    }
}
