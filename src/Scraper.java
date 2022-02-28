import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;

public class Scraper {
    String[] year = new String[2000];
    String[] point = new String[2000];
    private String[][] res = new String[2000][2];

    public Scraper() throws IOException {
        File tempFile = new File("tempFileS");
        scrape(tempFile);


    }

    public void scrape(File tempFile) {
        Document document;
        try {
            //Get Document object after parsing the html from given url.
            document = Jsoup.connect("https://www.studera.nu/jamfor-utbildning/?asp=Chalmers+tekniska+h%c3%b6gskola&ast=DATATEKNIK%2c+CIVILINGENJ%c3%96R&q=datateknik&f=2%5bcth&e=&ce=e.uoh.cth.tkdat.49000.20222%2ce.uoh.cth.tidal.62000.20222&cv=").get();

            Elements table = document.getElementsByTag("table");

            for (Element e : table) {
                writeToTemp(e.getElementsByClass("sr-only").toString(), tempFile);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void writeToTemp(String s, File tempfile) throws FileNotFoundException {
        PrintWriter writer = new PrintWriter(tempfile);
        writer.println(s);
        writer.close();
    }

}
