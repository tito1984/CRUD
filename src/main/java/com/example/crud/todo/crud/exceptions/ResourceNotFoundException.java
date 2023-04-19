package com.example.crud.todo.crud.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private String nameOfTheRecurse;
    private String nameOfField;
    private long valueOfField;

    public ResourceNotFoundException(String nameOfTheRecurse, String nameOfField, long valueOfField) {
        super(String.format("%s not founded with : %s : '%s'", nameOfTheRecurse, nameOfField, valueOfField));
        this.nameOfTheRecurse = nameOfTheRecurse;
        this.nameOfField = nameOfField;
        this.valueOfField = valueOfField;
    }

    public String getNameOfTheRecurse() {
        return nameOfTheRecurse;
    }

    public void setNameOfTheRecurse(String nameOfTheRecurse) {
        this.nameOfTheRecurse = nameOfTheRecurse;
    }

    public String getNameOfField() {
        return nameOfField;
    }

    public void setNameOfField(String nameOfField) {
        this.nameOfField = nameOfField;
    }

    public long getValueOfField() {
        return valueOfField;
    }

    public void setValueOfField(long valueOfField) {
        this.valueOfField = valueOfField;
    }

}
