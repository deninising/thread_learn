package com.dennis.conccurency.utils.semapphore;

public class Outer {
    private Inner inner = null;
    public Outer() {

    }

    public Inner getInnerInstance() {
        if(inner == null)
            inner = new Inner();
        return inner;
    }

    protected class Inner {
        public Inner() {

        }
    }
}
