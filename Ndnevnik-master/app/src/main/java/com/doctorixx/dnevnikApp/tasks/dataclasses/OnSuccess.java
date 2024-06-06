package com.doctorixx.dnevnikApp.tasks.dataclasses;

public interface OnSuccess<T> {
    void run(T t);
}
