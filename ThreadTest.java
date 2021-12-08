import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ThreadTest {
    private static ReentrantLock lock = new ReentrantLock();
    private static int count = 0;
    private static Condition condition = lock.newCondition();

    public static void main(String[] args) {
        Thread A = new Thread(() -> {
            lock.lock();
            try {
                while(true) {
                    if (count == 9) {
                        break;
                    }
                    if(count %3 == 0) {
                        count++;
                        System.out.println("A");
                        condition.signalAll();
                    } else {
                        try {
                            //等待
                            condition.await();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            } finally {
                lock.unlock();
            }
        });

        Thread B = new Thread(() -> {
            lock.lock();
            try {
                while(true) {
                    if (count == 9){
                        break;
                    }
                    if (count % 3 ==1) {
                        count++;
                        System.out.println("B");
                        condition.signalAll();
                    } else {
                        try {
                            condition.await();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            } finally {
                lock.unlock();
            }
        });

        Thread C = new Thread(() -> {
            lock.lock();
            try {
                while(true) {
                    if(count % 3 == 2) {
                        count++;
                        System.out.println("C");
                        condition.signalAll();
                    } else {
                        try {
                            condition.await();
                        } catch (InterruptedException e) {
                        e.printStackTrace();
                        }
                    }
                }
            } finally {
                lock.unlock();
            }
        });

        A.start();
        B.start();
        C.start();
    } 
}