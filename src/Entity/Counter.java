package Entity;

/**
 * Created by 47 on 2017/4/4.
 */
public class Counter {
    private int hits = 1;

    // Increment and return the number of hits
    public int getHits() {
        return hits++;
    }
}
