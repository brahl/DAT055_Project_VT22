/**
 * @author Viktor Rafstedt
 * @version 1
 *
 */

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class AdmissionResults implements Iterable<AdmissionResult> {
    private List<AdmissionResult> FavEducations;

    public AdmissionResults() {
        FavEducations = new ArrayList<AdmissionResult>();
    }

    public void addFavEducations(AdmissionResult fe) {
        FavEducations.add(fe);
    }

    public void removeFavEducations(AdmissionResult fe) {
        FavEducations.remove(fe);
    }

    public int length() {
        return FavEducations.size();
    }

    @Override
    public Iterator<AdmissionResult> iterator() {
        return FavEducations.iterator();
    }
}
