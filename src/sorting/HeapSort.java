package sorting;

/**
 * @author Omer Ufuk Efendioglu
 */
public class HeapSort {

    private int heapSize;

    public void heapSort(int[] A) {
        heapSize = A.length;
        BuildMaxHeap(A);

        for (int i = A.length - 1; i > 0; i--) {
            int temp = A[0];
            A[0] = A[i];
            A[i] = temp;
            heapSize--;
            MaxHeapify(A, 0);
        }
    }

    public void BuildMaxHeap(int[] A) {
        for (int i = A.length / 2 - 1; i >= 0; i--) {
            MaxHeapify(A, i);
        }
    }

    public void MaxHeapify(int[] A, int i) {
        int l = Left(i);
        int r = Right(i);
        int max;
        if (l < heapSize) {
            if (A[l] > A[i]) {
                max = l;
            } else {
                max = i;
            }
        } else {
            max = i;
        }

        if (r < heapSize) {
            if (A[r] > A[max]) {
                max = r;
            }
        }

        if (max != i) {
            int temp = A[i];
            A[i] = A[max];
            A[max] = temp;
            MaxHeapify(A, max);
        }
    }

    public int Left(int i) {
        return 2 * i;
    }

    private int Right(int i) {
        return (2 * i) + 1;
    }
}
