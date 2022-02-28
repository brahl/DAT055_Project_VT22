import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class FavEducations implements Iterable<FavEducation> {
    private List<FavEducation> FavEducations;

    public FavEducations() {
        FavEducations = new ArrayList<FavEducation>();
    }

    public void addFavEducations(FavEducation fe) {
        FavEducations.add(fe);
    }

    public void removeFavEducations(FavEducation fe) {
        FavEducations.remove(fe);
    }

    public int length() {
        return FavEducations.size();
    }

    @Override
    public Iterator<FavEducation> iterator() {
        return FavEducations.iterator();
    }
}
