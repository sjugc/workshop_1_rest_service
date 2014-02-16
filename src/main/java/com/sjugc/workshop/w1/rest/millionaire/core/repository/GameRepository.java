package com.sjugc.workshop.w1.rest.millionaire.core.repository;

import com.sjugc.workshop.w1.rest.millionaire.core.exception.GameDoesNotExistException;
import com.sjugc.workshop.w1.rest.millionaire.core.model.Game;

/**
 * Interface for creating and reading instance of {@link Game}
 * 
 * @author mjovanovic
 */
public interface GameRepository {

    /**
     * Creates and return new instance of {@link Game}
     * 
     * @return {@link Game} instance
     */
    Game create();

    /**
     * Read {@link Game} with specific game ID.
     * If game does not exists {@link GameDoesNotExistException} is thrown,
     * 
     * @param id {@link Integer} ID of game
     * @return {@link Game} instance
     */
    Game read( Integer id );

}
