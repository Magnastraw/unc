package src.matrix;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Locale;

public class Matrix {
    private static int MATRIX_ROW;
    private static int thread_amount;
    private static String input;
    private static long st;
    private static long en;
    private static double matrix1[][];
    private static double matrix2[][];
    private static double resultMatrix[][];

    private static double[][] getDefaultMatrix(double[][] matrix) {
        for (int i = 0; i < MATRIX_ROW; i++) {
            for (int j = 0; j < MATRIX_ROW; j++) {
                matrix[i][j] = Math.random() * 10 - 5;
            }
        }
        return matrix;
    }

    private static double[][] oneThreadMultiply(double[][] matrix1, double[][] matrix2) {
        for (int row = 0; row < MATRIX_ROW; row++) {
            for (int col = 0; col < MATRIX_ROW; col++) {
                for (int i = 0; i < MATRIX_ROW; i++) {
                    resultMatrix[row][col] += matrix1[i][col] * matrix2[row][i];
                }
            }
        }
        return resultMatrix;
    }

    private static double[][] clearMatrix(double[][] matrix) {
        for (int i = 0; i < MATRIX_ROW; i++) {
            for (int j = 0; j < MATRIX_ROW; j++) {
                matrix[i][j] = 0;
            }
        }
        return matrix;
    }

    private static void printOneRow(double[][] matrix) {
        System.out.println("Time in seconds:" + (en - st) / (1000000000.0));
        System.out.print("First row:");
        for (int j = 0; j < ((matrix.length > 50) ? 50 : matrix.length); j++) {
            System.out.format(Locale.ROOT, "%.2f ", matrix[0][j]);
        }
        System.out.println("\n\n");
    }

    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        do {
            System.out.println("Input row number:");
            input = bufferedReader.readLine();
            try {
                MATRIX_ROW = Integer.valueOf(input);
            } catch (NumberFormatException e) {
                throw new NumberFormatException("Error: wrong type, required numbers");
            }

            matrix1 = new double[MATRIX_ROW][MATRIX_ROW];
            matrix2 = new double[MATRIX_ROW][MATRIX_ROW];
            resultMatrix = new double[MATRIX_ROW][MATRIX_ROW];

            matrix1 = getDefaultMatrix(matrix1);
            matrix2 = getDefaultMatrix(matrix2);

            if (MATRIX_ROW < 1000) {
                st = System.nanoTime();
                resultMatrix = oneThreadMultiply(matrix1, matrix2);
                en = System.nanoTime();
                printOneRow(resultMatrix);
                resultMatrix = clearMatrix(resultMatrix);
            } else {
                System.out.println("Too much rows for one thread");
            }
            System.out.println("Input thread amount:");
            input = bufferedReader.readLine();
            try {
                thread_amount = Integer.valueOf(input);
            } catch (NumberFormatException e) {
                throw new NumberFormatException("Error: wrong type, required numbers");
            }
            st = System.nanoTime();
            multiThreadMultiply(thread_amount);
            en = System.nanoTime();
            printOneRow(resultMatrix);
            System.out.println("Exit? (y/n):");
            input = bufferedReader.readLine();
        } while (!input.equals("y"));
    }

    private static void multiThreadMultiply(int thread_amount) {
        int row_amount;
        int additional;
        thread_amount = (thread_amount > MATRIX_ROW) ? MATRIX_ROW : thread_amount;
        try {
            row_amount = MATRIX_ROW / thread_amount;
        } catch (ArithmeticException e) {
            throw new ArithmeticException("Thread amount is 0");
        }
        System.out.println("Thread amount:" + thread_amount);
        additional = MATRIX_ROW % thread_amount;

        Thread[] pool = new Thread[thread_amount];
        int start = 0;
        for (int i = 0; i < thread_amount; i++) {
            int row_per_thread = (i == 0) ? row_amount + additional : row_amount;
            pool[i] = new Thread(new MatrixThread(start, row_per_thread - 1));
            start += row_per_thread;
            pool[i].start();
        }

        try {
            for (Thread thread : pool) {
                thread.join();
            }
        } catch (InterruptedException e) {
            System.out.println("Thread interrupted");
        }
    }


    private static class MatrixThread implements Runnable {
        private int start;
        private int row_amount;

        MatrixThread(int start, int row_amount) {
            this.start = start;
            this.row_amount = row_amount;
        }

        @Override
        public void run() {
            for (int row = start; row < start + row_amount; row++) {
                for (int col = 0; col < MATRIX_ROW; col++) {
                    for (int i = 0; i < MATRIX_ROW; i++) {
                        resultMatrix[row][col] += matrix1[i][col] * matrix2[row][i];
                    }
                }
            }
        }
    }
}


