package com.vten.gedeon.ws.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Response entity for call to delete service
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResponseDelete {
    private Status status;
}
