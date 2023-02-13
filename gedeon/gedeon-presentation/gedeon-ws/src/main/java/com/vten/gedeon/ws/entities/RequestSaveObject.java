package com.vten.gedeon.ws.entities;

import java.util.Map;

import org.springframework.lang.NonNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RequestSaveObject {
    @NonNull
    private boolean refresh;
    private Map<String,ObjectProperty> properties;
}
