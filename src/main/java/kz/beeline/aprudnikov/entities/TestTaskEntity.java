package kz.beeline.aprudnikov.entities;

import java.util.Objects;

public class TestTaskEntity {

    private final int id;
    private final String data;

    public TestTaskEntity(int id, String data) {
        this.id = id;
        this.data = data;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof TestTaskEntity other))
            return false;
        boolean currencyCodeEquals = (this.data == null && other.data == null)
                || (this.data != null && this.data.equals(other.data));
        return Objects.equals(this.id, other.id) && currencyCodeEquals;
    }

}
