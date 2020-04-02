package com.dennis.conccurency.atomic.chapter01;

/**
 * 描述:获取锁异常
 *
 * @author Dennis
 * @version 1.0
 * @date 2020/3/31 16:47
 */
public class GetLockException extends Exception {
    public GetLockException() {
        super();
    }

    public GetLockException(String message) {
        super(message);
    }

    public GetLockException(String message, Throwable cause) {
        super(message, cause);
    }

    public GetLockException(Throwable cause) {
        super(cause);
    }
}
