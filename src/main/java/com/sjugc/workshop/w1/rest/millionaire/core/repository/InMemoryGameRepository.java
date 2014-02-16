package com.sjugc.workshop.w1.rest.millionaire.core.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.sjugc.workshop.w1.rest.millionaire.core.exception.GameDoesNotExistException;
import com.sjugc.workshop.w1.rest.millionaire.core.model.Game;
import com.sjugc.workshop.w1.rest.millionaire.core.model.GameStatus;
import com.sjugc.workshop.w1.rest.millionaire.core.model.Question;
import com.sjugc.workshop.w1.rest.millionaire.core.util.QuestionReader;

/**
 * In-Memory Repository.
 * 
 * @author mjovanovic
 */
@Component
public class InMemoryGameRepository implements GameRepository {

    /**
     * {@link QuestionReader} object.
     * Injected with {@link Autowired @Autowired} annotation.
     */
    @Autowired
    private QuestionReader questionReader;

    /**
     * {@link HashMap} holder of all {@link Game} instances.
     */
    private final Map<Integer, Game> games = new HashMap<>();

    /**
     * ID generator
     */
    private final AtomicInteger idGenerator = new AtomicInteger();

    /*
     * (non-Javadoc)
     * @see com.sjugc.workshop.w1.rest.millionaire.core.repository.GameRepository#create()
     */
    @Override
    public Game create() {
        // Generate new ID for game
        Integer id = this.idGenerator.getAndIncrement();

        // Create new Game
        Game game = new Game( id, generateQuestions(), GameStatus.PLAYING );

        // Save new Game in Game holder map.
        this.games.put( id, game );

        // Return newly created game.
        return game;
    }

    /*
     * (non-Javadoc)
     * @see
     * com.sjugc.workshop.w1.rest.millionaire.core.repository.GameRepository#read(java.lang.Integer)
     */
    @Override
    public Game read( Integer id ) {
        // Check if game exists
        if ( this.games.containsKey( id ) ) {
            // If exists return Game
            return this.games.get( id );
        }
        // If not exists throw an GameDoesNotExistException exception
        throw new GameDoesNotExistException( id );
    }

    /**
     * Fetch random questions for game.
     * 
     * @return {@link List} of 15 {@link Question}s
     */
    private List<Question> generateQuestions() {
        return questionReader.readQuestions();
    }

}
