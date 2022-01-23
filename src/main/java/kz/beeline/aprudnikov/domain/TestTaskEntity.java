package kz.beeline.aprudnikov.domain;

import java.util.Objects;

public class TestTaskEntity {

    private int id;
    private String data;

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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

}
