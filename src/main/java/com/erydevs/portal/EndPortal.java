package com.erydevs.portal;

import lombok.Getter;

public class EndPortal {

    @Getter
    private static boolean open = false;

    public static void setOpen(boolean value) {
        open = value;
    }

    public static boolean isOpen() {
        return open;
    }
}
