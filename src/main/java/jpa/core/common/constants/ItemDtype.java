package jpa.core.common.constants;

import lombok.Getter;

@Getter
public enum ItemDtype {

    BOOK("B"),
    ALBUM("A"),
    MOVIE("M");

    private final String value;

    ItemDtype(String value) {
        this.value = value;
    }

    public static ItemDtype fromValue(String value) {
        for (ItemDtype dtype : ItemDtype.values()) {
            if (dtype.value.equals(value)) {
                return dtype;
            }
        }
        throw new IllegalArgumentException("Unknown ItemDtype: " + value);
    }
}
