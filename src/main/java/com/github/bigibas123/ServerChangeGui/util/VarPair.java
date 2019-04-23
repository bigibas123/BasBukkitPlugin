package com.github.bigibas123.ServerChangeGui.util;

import lombok.Getter;

@Getter
public class VarPair<T1, T2> {

    private final T1 a;
    private final T2 b;

    public VarPair(T1 a, T2 b) {
        this.a = a;
        this.b = b;
    }

    public T1 a() {
        return this.a;
    }

    public T2 b() {
        return this.b;
    }
}
