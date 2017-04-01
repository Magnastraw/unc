package src.sort;


public class SortSequence implements Runnable {
    private int end;
    private int step;
    private int arrayStart;
    private int[] array;

    public SortSequence(int step, int arrayStart, int end, int[] array) {
        this.step = step;
        this.arrayStart = arrayStart;
        this.end = end;
        this.array = array;
    }

    public void run() {
        for (int start = arrayStart; start < arrayStart + end; start++) {
            for (int i = start; i < this.array.length; i += step) {
                int k = i;
                int temp = this.array[i];
                while (k >= step && temp < this.array[k - step]) {
                    this.array[k] = this.array[k - step];
                    k -= step;
                }
                this.array[k] = temp;
            }
        }

    }
}
