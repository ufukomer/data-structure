package sorting;

/**
 * @author Omer Ufuk Efendioglu
 */
public final class BitonicSort {
    public final static boolean ASCENDING = true, DESCENDING = false;
    public int[] a;

    /**
     * When called with parameters lo = 0, cnt = a.length() and dir = ASCENDING,
     * procedure bitonicSort sorts the whole array a.
     *
     * @param b
     */
    public BitonicSort(int b[]) {
        this.a = b;
        bitonicSort(0, a.length, ASCENDING);
    }

    /**
     * Procedure bitonicSort first produces a bitonic sequence by recursively
     * sorting its two halves in opposite directions, and then calls
     * bitonicMerge.
     *
     * @param lo
     * @param cnt
     * @param dir
     */
    public void bitonicSort(int lo, int cnt, boolean dir) {
        if (cnt > 1) {
            int k = cnt / 2;
            bitonicSort(lo, k, ASCENDING);
            bitonicSort(lo + k, k, DESCENDING);
            bitonicMerge(lo, cnt, dir);
        }
    }

    /**
     * The procedure bitonicMerge recursively sorts a bitonic sequence in
     * ascending order, if dir = ASCENDING, and in descending order otherwise.
     * The sequence to be sorted starts at index position lo, the number of
     * elements is cnt.
     *
     * @param lo
     * @param cnt
     * @param dir
     */
    public void bitonicMerge(int lo, int cnt, boolean dir) {
        if (cnt > 1) {
            int k = cnt / 2;
            int i;
            for (i = lo; i < lo + k; i++) {
                compare(i, i + k, dir);
            }
            bitonicMerge(lo, k, dir);
            bitonicMerge(lo + k, k, dir);
        }
    }

    /**
     * A comparator is modelled by the procedure compare, where the parameter
     * dir indicates the sorting direction. If dir is ASCENDING and a[i] > a[j]
     * is true or dir is DESCENDING and a[i] > a[j] is false then a[i] and a[j]
     * are interchanged.
     *
     * @param i
     * @param j
     * @param dir
     */
    public void compare(int i, int j, boolean dir) {
        if (dir == (a[i] > a[j])) {
            int h = a[i];
            a[i] = a[j];
            a[j] = h;
        }
    }
}
