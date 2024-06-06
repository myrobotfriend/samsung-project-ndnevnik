package com.doctorixx.dnevnikApp.other;

public enum FragmentsVariants {
    WEEK_FRAGMENT(0),
    FINAl_GRADES_FRAGMENT(1),
    GRADES_FRAGMENT(2),
    LAST_GRADES_FRAGMENT(3),
    MESSAGES_FRAGMENT(4),
    ANNOUNCEMENT_FRAGMENT(5),
    GRADE_CALCULATOR_FRAGMENT(6),
    GRADE_SIMULATOR_FRAGMENT(7),
    ;

    private final int id;

    FragmentsVariants(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static FragmentsVariants getFragmentById(int id) {
        for (FragmentsVariants variant : FragmentsVariants.values()) {
            if (variant.id == id) return variant;
        }
        return null;
    }

}
