package com.vten.gedeon.ws.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Status for response call to webservices
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Status {
    private String code;
    private String message;
}
