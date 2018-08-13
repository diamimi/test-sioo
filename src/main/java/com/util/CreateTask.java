package com.util;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author: HeQi
 * @Date:Create in 15:57 2018/8/9
 */
public class CreateTask extends RecursiveAction {
    // 每个"小任务"最多只打印20个数
    private static final int MAX = 100;

    private int start;
    private int end;
    //private AtomicInteger num = new AtomicInteger(0);
    private AtomicInteger n = new AtomicInteger(0);
    public static int num = 0;

    CreateTask(int start, int end) {
        this.start = start;
        this.end = end;
    }

    public synchronized void sss() {
        int l = RangeRandom.getInstance().getRangeRandom(start, end);
        System.out.println(Thread.currentThread().getName() + "," + l);
    }

    @Override
    protected void compute() {
        // 当end-start的值小于MAX时候，开始打印
        if ((end - start) < MAX) {
               /* List<Integer> mobiles = new ArrayList<>(100);
                int l = RangeRandom.getInstance().getRangeRandom(start, end);
                if (mobiles.contains(l)) {
                    continue;
                }
                mobiles.add(l);
                System.out.println(l);*/

            //  while (num.get()<10000){
            synchronized (n){
                while (num < 10000) {
                    sss();
                    num++;
                }
            }



            //   num.incrementAndGet();
            //  }
            //  System.out.println(num.get());
        } else {
            // 将大任务分解成两个小任务
            int middle = (start + end) / 2;
            CreateTask left = new CreateTask(start, middle);
            CreateTask right = new CreateTask(middle, end);
            // 并行执行两个小任务
            left.fork();
            right.fork();
        }

    }
}

class ForkJoinPoolTest {
    /**
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        // 创建包含Runtime.getRuntime().availableProcessors()返回值作为个数的并行线程的ForkJoinPool
        ForkJoinPool forkJoinPool = new ForkJoinPool(4);
        // 提交可分解的PrintTask任务
        forkJoinPool.submit(new CreateTask(0, 1000));
        forkJoinPool.awaitTermination(2, TimeUnit.SECONDS);//阻塞当前线程直到 ForkJoinPool 中所有的任务都执行结束
        // 关闭线程池
        forkJoinPool.shutdown();
        //System.out.println(CreateTask.num);
    }
}
