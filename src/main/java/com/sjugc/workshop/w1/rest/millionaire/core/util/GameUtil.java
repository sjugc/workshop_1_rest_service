package com.sjugc.workshop.w1.rest.millionaire.core.util;

import com.sjugc.workshop.w1.rest.millionaire.core.exception.GameCompletedException;
import com.sjugc.workshop.w1.rest.millionaire.core.model.Game;
import com.sjugc.workshop.w1.rest.millionaire.core.model.Question;
import com.sjugc.workshop.w1.rest.millionaire.core.model.QuestionStatus;

/**
 * Utility class for iterational operations over instance of {@link Game} object.
 * 
 * @author mjovanovic
 */
public final class GameUtil {

    /**
     * Calculation of achived threshold.
     * 
     * @param game {@link Game} game instance.
     * @return number of achived threshold.
     */
    public static int getAchivedThreshold( Game game ) {
        int correctAnswers = 0;
        for ( Question q : game.getQuestions() ) {
            if ( q.getQuestionStatus().equals( QuestionStatus.ANSWERED_CORRECT ) ) {
                correctAnswers++;
            }
        }

        return correctAnswers / 5;
    }

    /**
     * Fetching current opened question or next question.
     * 
     * @param game {@link Game} game instance.
     * @return {@link Question} current question.
     * @throws GameCompletedException if all questions are marked answered.
     */
    public static Question getCurrentQuestion( Game game ) throws GameCompletedException {

        for ( Question q : game.getQuestions() ) {
            if ( q.getQuestionStatus().equals( QuestionStatus.READED ) || q.getQuestionStatus().equals( QuestionStatus.UNREADED ) ) {
                return q;
            }
        }

        throw new GameCompletedException( game );
    }

    /**
     * Calculation of remaining questions to answer in game.
     * 
     * @param game {@link Game} game instance.
     * @return number of remaining questions.
     */
    public static int getLeftQuestions( Game game ) {
        int total = game.getQuestions().size();
        for ( Question q : game.getQuestions() ) {
            if ( q.getQuestionStatus().equals( QuestionStatus.UNREADED ) ) {
                total--;
            }
        }
        return total;
    }

}
