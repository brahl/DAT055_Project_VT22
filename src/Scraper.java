import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.util.Scanner;

public class Scraper {
    FavEducation fe = new FavEducation();
    public Scraper() throws IOException {
        scrape();
    }

    public void scrape() {
        Document document;
        String s;
        int i = 0;
        try {
            document = Jsoup.connect("https://www.studera.nu/jamfor-utbildning/?asp=Chalmers+tekniska+h%c3%b6gskola&ast=DATATEKNIK%2c+CIVILINGENJ%c3%96R&q=datateknik&f=2%5bcth&e=&ce=e.uoh.cth.tkdat.49000.20222%2ce.uoh.cth.tidal.62000.20222&cv=").get();
            Elements table = document.getElementsByTag("table");
            for (Element e : table) {
                writeToTemp(e.getElementsByClass("sr-only").toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public FavEducation writeToTemp(String s) throws FileNotFoundException {
        String result;
        Scanner scanner = new Scanner(s);
        while (scanner.hasNextLine()) {
            result = scanner.nextLine();
            if(result.contains("BI") && !result.contains("BII")) {
                fe.pName = "Datateknik";
                fe.uni = "Chalmers";
                fe.credits = "180hp";
                result = scanner.nextLine();
                fe.admissionM1 = result.substring(59,64);
                result = scanner.nextLine();
                fe.admissionM2 = result.substring(59,64);
                result = scanner.nextLine();
                fe.admissionM3 = result.substring(59,64);
                result = scanner.nextLine();
                fe.admissionM4 = result.substring(59,64);
                result = scanner.nextLine();
                fe.admissionM5 = result.substring(59,64);
            }
        }
        return fe;
    }
}