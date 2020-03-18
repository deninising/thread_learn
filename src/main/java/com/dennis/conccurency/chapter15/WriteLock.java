package com.dennis.conccurency.chapter15;

/**
 * 描述：写锁
 *
 * @author Dennis
 * @version 1.0
 * @date 2020/3/18 12:04
 */
class WriteLock implements Lock {
    // 维持一个读写资源对象
    private final ReadWriteLockImpl readWriteLock;

    WriteLock(ReadWriteLockImpl readWriteLock) {
        this.readWriteLock = readWriteLock;
    }

    @Override
    public void lock() throws InterruptedException {
        //使用Mutex作为临界资源
        synchronized (readWriteLock.getMutex()) {
            try {
                // 首先等待写入的线程加一
                readWriteLock.incrementWaitingWriters();
                while (this.readWriteLock.getWaitingWriters() > 0
                        || this.readWriteLock.getWritingWriters() > 0) {
                    this.readWriteLock.getMutex().wait();
                }
            } finally {
                // 可以写入则等待写入数量减一
                this.readWriteLock.decrementWaitingWriters();
            }
            // 正在写入，则正在写入数加一
            this.readWriteLock.incrementWritingWriters();
        }
    }

    @Override
    public void unlock() {
        //使用Mutex作为临界资源，并且进行同步，利用synchronized可重入性
        synchronized (readWriteLock.getMutex()) {
            //释放锁的过程就是使得当前writing的数量减一
            readWriteLock.decrementWritingWriters();
            //将preferWriter设置为false,可以使得reader线程获得更多的机会
            readWriteLock.changePrefer(false);
            //通知唤醒
            readWriteLock.getMutex().notifyAll();
        }
    }
}
