package by.bestlunch.web.dto;

import by.bestlunch.util.HasId;

public abstract class BaseDto implements HasId {
    protected Integer id;

    BaseDto() {
    }

    BaseDto(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}