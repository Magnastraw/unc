package src.bank;

import java.util.Random;


public class Bank {
    private static double accountMoney = 1000;
    private Thread workerPool[];
    private Worker workers[];

    public Bank(int workersAmount) {
        workerPool = new Thread[workersAmount];
        workers = new Worker[workersAmount];
        for (int i = 0; i < workersAmount; i++) {
            workers[i] = new Worker(i);
            workerPool[i] = new Thread(workers[i]);
            workerPool[i].start();
        }
    }

    public static double getBankMoney() {
        return accountMoney;
    }

    public synchronized static void addMoney(double money) {
        accountMoney += money;
    }

    public synchronized static  double takeMoney(double money) {
        if ((accountMoney - money) < 0) {
            return 0;
        } else {
            accountMoney -= money;
            return money;
        }
    }

    public Worker getFreeWorker() {

        Worker minQueueWorker = workers[0];
        for (int i = 1; i < workers.length; i++) {
            if (minQueueWorker.getQueue().size() > workers[i].getQueue().size()) {
                minQueueWorker = workers[i];
            }
        }
        return minQueueWorker;
    }

}
