package com.doctorixx.easyDnevnik.stuctures;

import java.util.Objects;

public class Subject {
    private String name;

    public Subject(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object obj) {
        return (Objects.equals(this.toString(), obj.toString()));
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
