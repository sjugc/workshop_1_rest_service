package com.sjugc.workshop.w1.rest.millionaire.core.model;

/**
 * Game status enumeration with status codes.
 * 
 * @author mjovanovic
 */
public enum GameStatus {

    PLAYING( 1 ), ENDED( 0 );

    private int code;

    GameStatus( int code ) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
