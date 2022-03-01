import javax.swing.*;
import java.io.IOException;

public class SearchAntagning {

    private FavEducation favEducb1;
    private FavEducation favEducb2;
    private FavEducations result;
    private String query;
    Scraper b1 = new Scraper("BI");
    Scraper b2 = new Scraper("BII");

    public SearchAntagning(String query) throws IOException {
        this.query = query;
        this.result = search();

    }

    private FavEducations search() {
        FavEducations searchResultList = new FavEducations();

        if (query.equals("Datateknik, Chalmers")){
           favEducb1 = b1.fe;
           favEducb2 = b2.fe;
           searchResultList.addFavEducations(favEducb1);
           searchResultList.addFavEducations(favEducb2);
           return searchResultList;
        }
        if(query.equals("Handelshögskolan")){
            System.out.println("Din konformistiska horunge - Norrlänningen");
            return null;
        }
        else {
            System.out.println("You can have what ever education you like, as long as its Datateknik - Ford");
            return null;
        }
    }











}
