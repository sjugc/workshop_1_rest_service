package com.sjugc.workshop.w1.rest.millionaire.core.exception;

import com.sjugc.workshop.w1.rest.millionaire.core.model.Game;

/**
 * Exception which is thrown when end user try to access the game which does not exists yet.
 * 
 * @author mjovanovic
 */
public class GameDoesNotExistException extends RuntimeException {

    private static final long serialVersionUID = -299986735754220647L;

    /**
     * Constructor
     * 
     * @param gameId {@link Integer} ID of requested {@link Game}
     */
    public GameDoesNotExistException( Integer gameId ) {
        super( String.format( "Game '%d' does not exist", gameId ) );
    }
}
