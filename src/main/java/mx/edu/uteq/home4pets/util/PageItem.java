package mx.edu.uteq.home4pets.util;

import lombok.Getter;

@Getter
public class PageItem {

    private int number;

    private boolean actual;

    public PageItem(int number, boolean actual) {
        this.number = number;
        this.actual = actual;
    }

}
