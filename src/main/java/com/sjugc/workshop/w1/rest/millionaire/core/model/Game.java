package com.sjugc.workshop.w1.rest.millionaire.core.model;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.sjugc.workshop.w1.rest.millionaire.core.util.GameUtil;

/**
 * Simple model of one instance of game.<br />
 * <br />
 * <b>NOTE:</b><br />
 * <ul>
 * <li>{@link JsonIgnore @JsonIgnore} annotation on filed getter indicate that that field should not
 * be parsed to output JSON.</li>
 * <li>{@link JsonInclude @JsonInclude} annotation on filed getter indicate that that field should
 * be included in parsed output JSON only if field value satisfies inclusion requirement. In out
 * case if filed value is not null field will be outputed to output JSON.</li>
 * </ul>
 * 
 * @author mjovanovic
 */
public class Game {

    /**
     * Game unique ID
     */
    private Integer id;

    /**
     * {@link List} of {@link Question}s
     */
    private List<Question> questions;

    /**
     * Game status
     */
    private GameStatus gameStatus;

    public Game() {}

    public Game( Integer id, List<Question> questions, GameStatus gameStatus ) {
        this.id = id;
        this.questions = questions;
        this.gameStatus = gameStatus;
    }

    public int getGameStatus() {
        return gameStatus.getCode();
    }

    @JsonIgnore
    public GameStatus getGameStatusEnum() {
        return gameStatus;
    }

    @JsonInclude( Include.NON_NULL )
    public Question getQuestion() {
        if ( this.gameStatus.equals( GameStatus.ENDED ) ) {
            return null;
        }
        return GameUtil.getCurrentQuestion( this );
    }

    @JsonInclude( Include.NON_NULL )
    public Integer getTreshold() {
        if ( this.gameStatus.equals( GameStatus.PLAYING ) ) {
            return null;
        }
        return GameUtil.getAchivedThreshold( this );
    }

    @JsonIgnore
    public List<Question> getQuestions() {
        return questions;
    }

    @JsonIgnore
    public Integer getId() {
        return id;
    }

    public void setGameStatus( GameStatus gameStatus ) {
        this.gameStatus = gameStatus;
    }

    public void setQuestions( List<Question> questions ) {
        this.questions = questions;
    }

    public void setId( Integer id ) {
        this.id = id;
    }
}
