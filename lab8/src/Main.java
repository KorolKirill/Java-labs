import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception {
        ParallelMonteCarloPi calc = new ParallelMonteCarloPi();
        long timeStart = 0L;
        System.out.println("Started: ");

        timeStart = System.currentTimeMillis();

        int thCount = 4;
        long iterations = 100_000_000l;
        double res = calc.findPI(iterations, thCount);
        long time = System.currentTimeMillis() - timeStart;
        System.out.println(
                "PI is : " + res +
                "\n THREADS  " + thCount +
                "\n ITERATIONS  " + iterations +
                        "\n TIME   " + time + " ms"
        );

    }
}

class ParallelMonteCarloPi {

    private class Point {
        double x;
        double y;

        public Point(double x, double y) {
            this.x = x;
            this.y = y;
        }

        public double calculateLength() {
            return Math.sqrt(x * x + y * y);
        }
    }

    public double findPI(long iterations, int threadsCount) throws Exception {
        if (threadsCount <= 0 || iterations <= 0) {
            throw new Exception("Wrong params.");
        }

        //множители for single
        long iterationSingle = (iterations / threadsCount);

        List<ArithmeticThread> threads = new ArrayList<ArithmeticThread>(threadsCount);

        ArithmeticThread at;
        for (int i = 0; i < threadsCount; i++) {
            at = new ArithmeticThread(iterationSingle);
            threads.add(at);
            at.start();
        }

        double result = 0;
        for (ArithmeticThread thread : threads) {
            try {
                thread.join();
                result = result == 0 ? thread.getResult() : (result + thread.getResult()) / 2 ;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // ⦁	Частка точок, які потрапили в коло дорівнює наближеному значенню π/4
        return result * 4;
    }

    private class ArithmeticThread extends Thread {
        double result;
        long iterations;

        public ArithmeticThread(long iterations) {
            this.iterations = iterations;

        }

        @Override
        public void run() {
            long counterSuccess = 0;
            for (long i = 1; i <= this.iterations; i++) {
                Point point = new Point(Math.random(), Math.random());
                if (point.calculateLength() <= 1) {
                    counterSuccess++;
                }
            }
            result = (double) counterSuccess / iterations;
        }

        public double getResult() {
            return result;
        }
    }
}