package com.anotherdev.taler.api.bitcoinaverage.model;

import java.util.Collections;
import java.util.List;

public class Symbols extends Response {

    List<String> symbols = Collections.emptyList();


    public List<String> getSymbols() {
        return symbols;
    }
}
