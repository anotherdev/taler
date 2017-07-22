package com.anotherdev.taler.api.bitcoinaverage.model;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableList;

public class Fiats {

    private static final Joiner COMMA_JOINER = Joiner.on(",");

    private ImmutableList<String> fiats;


    private Fiats(String... fiats) {
        this.fiats = ImmutableList.copyOf(fiats);
    }

    @Override
    public String toString() {
        return COMMA_JOINER.join(fiats);
    }


    public static Fiats of(String... fiats) {
        return new Fiats(fiats);
    }
}
