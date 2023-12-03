public class CounterConcurrency {
    private static final Object lock = new Object();

    public static void main(String[] args) {
        Thread count1 = new Thread(() -> {
            for (int i = 1; i <= 20; i++) {
                System.out.println("Counter 1: " + i);
            }

            // Notify the waiting thread
            synchronized (lock) {
                lock.notify();
            }
        });

        Thread count2 = new Thread(() -> {
            // Wait until notified by thread 1
            synchronized (lock) {
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            for (int i = 20; i >= 0; i--) {
                System.out.println("Counter 2: " + i);
            }
        });

        count1.start();
        count2.start();
    }
}