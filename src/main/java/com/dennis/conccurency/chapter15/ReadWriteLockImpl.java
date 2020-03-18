package com.dennis.conccurency.chapter15;

class ReadWriteLockImpl implements ReadWriteLock {
    // 内部互斥资源
    private final Object MUTEX = new Object();
    // 当前有多少个线程正在写入
    private int writingWriters = 0;
    // 当前有多少个正在等在的写入线程
    private int waitingWriters = 0;
    // 当前有多少个线程正在读取
    private int readingWriters = 0;
    // 写偏好设置标记
    private boolean preferWriter;

    // 默认情况下preferWriter为true,即：写线程更容易拿到锁资源
    public ReadWriteLockImpl() {
        this(true);
    }

    public ReadWriteLockImpl(boolean preferWriter) {
        this.preferWriter = preferWriter;
    }

    @Override
    public ReadLock readLock() {
        return new ReadLock(this);
    }

    @Override
    public WriteLock writeLock() {
        return new WriteLock(this);
    }

    @Override
    public int getWritingWriters() {
        return 0;
    }

    @Override
    public int getWaitingWriters() {
        return 0;
    }

    @Override
    public int getReadingReaders() {
        return 0;
    }

    Object getMutex() {
        return this.MUTEX;
    }

    void incrementWritingWriters() {
        this.writingWriters++;
    }

    void incrementWaitingWriters() {
        this.waitingWriters++;
    }

    void incrementReadingReaders() {
        this.readingWriters++;
    }

    void decrementWritingWriters() {
        this.writingWriters--;
    }

    void decrementWaitingWriters() {
        this.waitingWriters--;
    }

    void decrementReadingReaders() {
        this.readingWriters--;
    }

    boolean getPreferWriter() {
        return this.preferWriter;
    }

    void changePrefer(boolean b) {
        this.preferWriter = b;
    }
}
