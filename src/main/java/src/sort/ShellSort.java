package src.sort;


public class ShellSort {

    private static final int ARRAY_SIZE = 1024;
    private static int array[] = new int[ARRAY_SIZE];
    private static long startTime;
    private static double endTime;
    private static Thread[] pool;

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < array.length; i++) {
            array[i] = ARRAY_SIZE - i;
        }
        startTime = System.nanoTime();
        shellSort();
        endTime = (System.nanoTime() - startTime) / 1000000000.0;
        System.out.println("Work time:" + endTime);
        System.out.println("Is sorted:"+isSorted(array));
        //printArray(array);

        for (int i = 0; i < array.length; i++) {
            array[i] = ARRAY_SIZE - i;
        }
        //printArray(array);

        long startTime1 = System.nanoTime();
        parallelSort();
        endTime = (System.nanoTime() - startTime1) / 1000000000.0;
        System.out.println("Work time:" + endTime);
        //printArray(array);
        System.out.println("Is sorted:"+isSorted(array));
    }


    private static void shellSort() {
        int N = ARRAY_SIZE;
        int t;
        for (int k = N / 2; k > 0; k /= 2)
            for (int i = k; i < N; i += 1) {
                t = array[i];
                int j;
                for (j = i; j >= k; j -= k) {
                    if (t < array[j - k])
                        array[j] = array[j - k];
                    else
                        break;
                }
                array[j] = t;
            }
    }


    private static void parallelSort() throws InterruptedException {
        int k = 4;
        pool = new Thread[k];
        for (int step = array.length / 2; step >= 1; step /= 2) {
            //ExecutorService service = Executors.newFixedThreadPool(k);
            if (step > 1) {
                for (int i = 0; i < k; i++) {
                    //service.execute(new Thread(new SortSequence(step,step+(step/k)*i,step/k,array)));
                    pool[i] = new Thread(new SortSequence(step, step + (step / k) * i, step / k, array));
                    pool[i].start();
                }
            } else {
                //service.execute(new Thread(new SortSequence(step,step,step,array)));
                pool[0] = new Thread(new SortSequence(step, step, step, array));
                pool[0].start();
            }
            //service.shutdown();
            //service.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
            try {
                for (Thread thread : pool) {
                    thread.join();
                }
            } catch (InterruptedException e) {
                System.out.println("Thread interrupted");
            }
        }
    }


    private static void printArray(int[] array) {
        for (int i = 0; i < array.length; i++) {
            System.out.print("num:" + i + " " + array[i] + "  ");
        }
        System.out.println("");
    }

    public static boolean isSorted(int[] a) {
        for (int i = 0; i < a.length - 1; i++) {
            if (a[i] > a[i + 1]) {
                return false;
            }
        }

        return true;
    }
}
