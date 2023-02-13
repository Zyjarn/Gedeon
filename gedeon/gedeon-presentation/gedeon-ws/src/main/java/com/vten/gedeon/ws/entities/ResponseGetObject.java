package com.vten.gedeon.ws.entities;

import java.util.HashMap;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResponseGetObject {
    private Status status;
    private Map<String, ObjectProperty> properties = new HashMap<>();
}
