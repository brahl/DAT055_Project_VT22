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
        scrape();
        prepareTemp("tempScrape.txt");
        combineArray();
        readRes();

    }

    public void scrape(){
        print("running...");
        Document document;
        try {
            //Get Document object after parsing the html from given url.
            document = Jsoup.connect("https://www.studera.nu/jamfor-utbildning/?asp=Chalmers+tekniska+h%c3%b6gskola&ast=DATATEKNIK%2c+CIVILINGENJ%c3%96R&q=datateknik&f=2%5bcth&e=&ce=e.uoh.cth.tkdat.49000.20222%2ce.uoh.cth.tidal.62000.20222&cv=").get();

            Elements table = document.getElementsByTag("table");

            for(Element e : table){
                writeToTemp(e.getElementsByClass("sr-only").toString());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        print("Done");
    }

    public void writeToTemp(String s) throws FileNotFoundException {
        File tempScrape = new File("tempScrape.txt");
        PrintWriter writer = new PrintWriter("tempScrape.txt");
        writer.println(s);
        writer.close();
    }

    public void prepareTemp(String file) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line = " ";
            for(int i = 0; i < 2000; i++){
                line = br.readLine();
                if (line!= null){
                    if (line.contains("Termin:") && line.contains("poäng:")) {
                        writeToRes(i, line.substring(line.indexOf(":") + 2, line.indexOf(":") + 8),line.substring(line.lastIndexOf(":")+2,line.lastIndexOf(":")+7));
                    }
                    else{writeToRes(i,"empty","empty");}
                }

            }
        }
    }




    public void writeToRes(int i, String y, String p) {
        if(!y.equals("empty")){

            this.year[i]= y;
            this.point[i]= p;
        }


    }

    public void combineArray(){
        int i;

        for(i = 0; i <2000;i++){
            if(this.year[i]!=null){
                this.res[i][0]= this.year[i];
                this.res[i][1] = this.point[i];
            }

        }


    }

    public void readRes(){
        for(int i =0; i<30; i++){
            System.out.println(this.res[i][0]+" "+this.res[i][1]);
        }
    }


    public static void print(String string) {
        System.out.println(string);
    }


}
