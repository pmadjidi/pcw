package com.payam.test.counters;

import org.glassfish.hk2.utilities.binding.AbstractBinder;

public class MyAppBinder extends AbstractBinder {

    @Override
    protected void configure() {
        bind(new NamedCounters()).to(NamedCounters.class);
    }
}

