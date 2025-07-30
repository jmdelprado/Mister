package com.delprado.springboot.mister.springboot_mister.security.filter;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public abstract class SimpleGrantedAuthorityJsonCreator {

    @JsonCreator
    public SimpleGrantedAuthorityJsonCreator(@JsonProperty("authority") String role) {
        // Este constructor debe ser llamado por Jackson para crear la autoridad
    }
}
