package de.dblab.domain;

import de.dblab.domain.auto._Dblab;

public class Dblab extends _Dblab {

    private static Dblab instance;

    private Dblab() {
    
    }

    public static Dblab getInstance() {
        if(instance == null) {
            instance = new Dblab();
        }

        return instance;
    }
}
