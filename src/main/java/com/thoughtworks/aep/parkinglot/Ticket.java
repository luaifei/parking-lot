package com.thoughtworks.aep.parkinglot;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

@Getter
@AllArgsConstructor
public class Ticket {
    private String parkingLotName;
    private String identityNo;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ticket ticket = (Ticket) o;
        return Objects.equals(identityNo, ticket.identityNo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(identityNo);
    }
}
