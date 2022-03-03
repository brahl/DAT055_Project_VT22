import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.swing.*;
import java.io.*;
import java.util.Scanner;

/**
 * Rename this class to search
 */

public class Scraper {
    FavEducation fe = new FavEducation();
    public Scraper(String admissionType,String query) throws IOException {
        scrape(admissionType, query);
    }

    /**
     * This would in reality have the search String as input, to be prepared for the Jsoup.connect
     * @param admissionType
     */
    public void scrape(String admissionType, String searchType) {
        Document document;
            try {
                if(searchType.equals("Chalmers")){
                    System.out.println("Sant mannen");
                    document = Jsoup.connect("https://www.studera.nu/jamfor-utbildning/?asp=Chalmers+tekniska+h%c3%b6gskola&ast=DATATEKNIK%2c+CIVILINGENJ%c3%96R&q=datateknik&f=2%5bcth&e=&ce=e.uoh.cth.tkdat.49000.20222%2ce.uoh.cth.tidal.62000.20222&cv=").get();
                } else if(searchType.equals("Handels")){
                    document = Jsoup.connect("https://www.studera.nu/jamfor-utbildning/?asp=G%c3%b6teborgs+universitet&ast=Juristprogrammet&q=juristprogrammet&e=i.uoh.gu.s2jur.1g83b.20222&ce=e.uoh.gu.s2jur.1g83b.20222%2ce.uoh.gu.s2jur.1g83b.20212&cv=").get();
                }else{
                    document = null;
                }
                Elements table = document.getElementsByTag("table");
                for (Element e : table) {
                    writeToTemp(e.getElementsByClass("sr-only").toString(), admissionType, searchType);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

    }

    public FavEducation writeToTemp(String s, String admissionType,String query) throws FileNotFoundException {
        String result;
        Scanner scanner = new Scanner(s);
        System.out.println(s);
        while (scanner.hasNextLine()) {
            result = scanner.nextLine();
            //For admissionType == "BI" or "BII"
            if(admissionType.equals("BI") || admissionType.equals("BII")) {
                if (result.contains("(" + admissionType + ")")) {
                    if(query.equals("Chalmers")){
                        fe.pName = "Datateknik";
                        fe.uni = "Chalmers";
                        fe.credits = "180hp";
                    }
                    if(query.equals("Handels")){
                        fe.pName = "Jurist";
                        fe.uni = "Handelshögskolan Göteborg";
                        fe.credits = "270hp";
                    }

                    result = scanner.nextLine();
                    fe.admissionM1 = result.substring(59, 64);
                    result = scanner.nextLine();
                    fe.admissionM2 = result.substring(59, 64);
                    result = scanner.nextLine();
                    fe.admissionM3 = result.substring(59, 64);
                    result = scanner.nextLine();
                    fe.admissionM4 = result.substring(59, 64);
                    result = scanner.nextLine();
                    fe.admissionM5 = result.substring(59, 64);
                }
            }

            if(admissionType.equals("HP")) {
                if (result.contains("(" + admissionType + ")")) {
                    fe.pName = "Datateknik";
                    fe.uni = "Chalmers";
                    fe.credits = "180hp";
                    result = scanner.nextLine();
                    if (result.contains("Lägst")) {
                        fe.admissionM1 = result.substring(59, 64);
                    } else {
                        fe.admissionM1 = "No data exists";
                    }
                    result = scanner.nextLine();
                    if (result.contains("Lägst")) {
                        fe.admissionM2 = result.substring(59, 64);
                    } else {
                        fe.admissionM1 = "No data exists";
                    }
                    result = scanner.nextLine();
                    if (result.contains("Lägst")) {
                        fe.admissionM3 = result.substring(59, 64);
                    } else {
                        fe.admissionM1 = "No data exists";
                    }
                    result = scanner.nextLine();
                    if (result.contains("Lägst")) {
                        fe.admissionM4 = result.substring(59, 64);
                    } else {
                        fe.admissionM1 = "No data exists";
                    }
                    result = scanner.nextLine();
                    if (result.contains("Lägst")) {
                        fe.admissionM5 = result.substring(59, 64);
                    } else {
                        fe.admissionM1 = "No data exists";
                    }
                }
            }
        }
        return fe;
    }
}