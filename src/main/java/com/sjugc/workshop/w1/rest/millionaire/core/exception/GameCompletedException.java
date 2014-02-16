package com.sjugc.workshop.w1.rest.millionaire.core.exception;

import com.sjugc.workshop.w1.rest.millionaire.core.model.Game;

/**
 * Exception which is thrown when end user try to access the game which is already completed.
 * 
 * @author mjovanovic
 */
public class GameCompletedException extends RuntimeException {

    private static final long serialVersionUID = -299986735754220647L;

    /**
     * Instance of {@link Game} for which exception is thrown.
     */
    private Game game;

    /**
     * Constructor
     * 
     * @param game {@link Game} instace
     */
    public GameCompletedException( Game game ) {
        this.game = game;
    }

    public Game getGame() {
        return game;
    }

    public void setGame( Game game ) {
        this.game = game;
    }

}
