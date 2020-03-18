package com.dennis.conccurency.chapter15;


/**
 * 描述： 读锁
 *
 * @author Dennis
 * @version 1.0
 * @date 2020/3/18 11:34
 */
class ReadLock implements Lock {
    // 维持一个读写资源对象
    private final ReadWriteLockImpl readWriteLock;

    ReadLock(ReadWriteLockImpl readWriteLock) {
        this.readWriteLock = readWriteLock;
    }

    @Override
    public void lock() throws InterruptedException {
        //使用Mutex作为临界资源
        synchronized (readWriteLock.getMutex()) {
            //若此时有线程在进行写操作，或者有写线程在等待并且偏向写锁的标识为true时，就会无法获取读锁，只能被挂起
            while (readWriteLock.getWritingWriters() > 0 ||
                    readWriteLock.getWaitingWriters() > 0 && readWriteLock.getPreferWriter()) {
                readWriteLock.getMutex().wait();
            }
            //成功获得读锁，并且使readingReaders的数量增加
            readWriteLock.incrementReadingReaders();
        }
    }

    @Override
    public void unlock() {
        //使用Mutex作为临界资源，并且进行同步，利用synchronized可重入性
        synchronized (readWriteLock.getMutex()) {
            //释放锁的过程就是使得当前reading的数量减一
            readWriteLock.decrementReadingReaders();
            //将preferWriter设置为true,可以使得writer线程获得更多的机会
            readWriteLock.changePrefer(true);
            //通知唤醒
            readWriteLock.getMutex().notifyAll();
        }
    }
}
