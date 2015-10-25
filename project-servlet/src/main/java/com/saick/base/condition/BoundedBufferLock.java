package com.saick.base.condition;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 实现一个可阻塞式队列，先进先出的原则，参见java的api文档
 * 
 * 可以使用3个Condition实现3个线程循环调用执行
 * 
 * ArrayBlockingQueue
 * 
 * @author Liubao
 * @2014年12月16日
 * 
 */
public class BoundedBufferLock {
    //普通锁
    final static Lock lock = new ReentrantLock();
    final Condition notFull = lock.newCondition();
    final Condition notEmpty = lock.newCondition();
    //读写锁
    static Condition condition1 = lock.newCondition();
    static Condition condition2 = lock.newCondition();
    static Condition condition3 = lock.newCondition();
    static ReadWriteLock lockReadWrite1 =new ReentrantReadWriteLock();
    static ReadWriteLock lockReadWrite2 =new ReentrantReadWriteLock();
    static ReadWriteLock lockReadWrite3 =new ReentrantReadWriteLock();
    static int notifyFlag=1;

    final Object[] items = new Object[100];
    int putptr, takeptr, count;
    
    //main测试方法
    public static void main(String[] args) {
        final BoundedBufferLock bufferLock=new BoundedBufferLock();
        ExecutorService executorService = Executors.newFixedThreadPool(1);
        
        // 放入的线程1，放入5个数据
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    for (int i = 0; i < 5; i++) {
                        if (notifyFlag != 1) {
                            condition1.wait();
                        }
                        Thread.sleep(1000);
                        bufferLock.put(i);
                        System.out.println(Thread.currentThread().getName()
                                + "当前队列1中的个数为：" + bufferLock.count);
                        notifyFlag = 2;
                        condition2.notify();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                }
            }
        });
        // 放入的线程2，放入5个数据
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    for (int i = 0; i < 5; i++) {
                        if (notifyFlag != 2) {
                            condition2.wait();
                        }
                        bufferLock.put(i);
                        System.out.println(Thread.currentThread().getName()
                                + "当前队列2中的个数为：" + bufferLock.count);
                        notifyFlag = 3;
                        condition3.notify();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                }
            }
        });
        //取数据的线程,每隔1秒钟，取一个数据
        executorService.execute(new Runnable(){
            @Override
            public void run() {
                while(bufferLock.count>0) {
                    try {
                        if (notifyFlag != 3) {
                            condition3.wait();
                        }
                        Thread.sleep(1000);
                        bufferLock.take();
                        System.out.println(Thread.currentThread().getName()+"取数据后，当前队列中的个数为："+bufferLock.count);
                        notifyFlag = 1;
                        condition1.notify();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }finally{
                    }
                }
            }
        });
        
        executorService.shutdown();
        System.out.println(Thread.currentThread().getName()+"程序执行完毕了。。。");
        
    }

    /**
     * 放数据方法
     */
    public void put(Object x) throws InterruptedException {
        lock.lock();
        try {
            while (count == items.length){
                notFull.await();
            }
            items[putptr] = x;
            if (++putptr == items.length){
                putptr = 0;
            }
            ++count;
            notEmpty.signal();
        } finally {
            lock.unlock();
        }
    }

    /**
     * 取数据方法
     */
    public Object take() throws InterruptedException {
        lock.lock();
        try {
            while (count == 0){
                notEmpty.await();
            }
            Object x = items[takeptr];
            if (++takeptr == items.length){
                takeptr = 0;
            }
            --count;
            notFull.signal();
            return x;
        } finally {
            lock.unlock();
        }
    }
}
