package com.sjugc.workshop.w1.rest.millionaire.web.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.sjugc.workshop.w1.rest.millionaire.core.exception.GameCompletedException;
import com.sjugc.workshop.w1.rest.millionaire.core.model.Game;
import com.sjugc.workshop.w1.rest.millionaire.core.model.GameStatus;
import com.sjugc.workshop.w1.rest.millionaire.core.model.Question;
import com.sjugc.workshop.w1.rest.millionaire.core.model.QuestionStatus;
import com.sjugc.workshop.w1.rest.millionaire.core.model.Response;
import com.sjugc.workshop.w1.rest.millionaire.core.repository.GameRepository;
import com.sjugc.workshop.w1.rest.millionaire.core.util.GameUtil;

/**
 * Main controller of game.
 * <ul>
 * <li>{@link RestController @RestController} annotation mark that this class should be controller
 * which return value should be bound to HTTP Response body.</li>
 * <li>{@link RequestMapping @RequestMapping} annotation for mapping web request to specific handler
 * class.<br />
 * Value of field 'value' is prefix value for all {@link RequestMapping @RequestMapping} on method
 * level.<br />
 * Value of field 'produces' define type of returned data which handler class will return.</li>
 * </ul>
 * <b>NOTE:</b><br />
 * {@link RequestMapping @RequestMapping} annotation can be placed on class level and/or method
 * level.<br />
 * If is placed on class level, it indicates that values defined in annotation should be propagated
 * to annotation on method level if same values are not override on method level. Some values, like
 * 'value' value will act as prefix for method level 'value' value.
 * 
 * @author mjovanovic
 */
@RestController
@RequestMapping( value = "Millionaire/games", produces = { MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8" } )
public class GameController {

    /**
     * Game repository.
     * Injected with {@link Autowired @Autowired} annotation.
     */
    @Autowired
    private GameRepository gameRepository;

    /**
     * Handler method for creating new instance of {@link Game}
     * <ul>
     * <li>{@link RequestMapping @RequestMapping} annotation for mapping web request to specific
     * method of handler class.<br />
     * Value of field 'method' indicate type of HTTP method which we will access to handler method.</li>
     * </ul>
     * 
     * @return {@link ResponseEntity} Klasa omotac kojom uz objekt odgovora mozemo poslati i
     *         HTTP status kod odgovora.
     * @return {@link ResponseEntity} Wrapper class which along with response object allow us to
     *         send HTTP status code and HTTP Response Headers.
     */
    @RequestMapping( method = RequestMethod.GET )
    public ResponseEntity<Void> createGame() {
        // Creating new instance of Game in our Game repository
        Game game = this.gameRepository.create();

        // Creating instance of object HttpHeaders through we will send URI to newly created Game.
        HttpHeaders headers = new HttpHeaders();

        // To HTTP Response Headers we set location to new game
        headers.setLocation( linkTo( GameController.class ).slash( game.getId() ).toUri() );

        // Return HTTP Response Headers with HTTP status code 201 - Created
        return new ResponseEntity<>( headers, HttpStatus.CREATED );
    }

    /**
     * Handler method for fetching specific instance of {@link Game}.
     * <ul>
     * <li>{@link RequestMapping @RequestMapping} annotation for mapping web request to specific
     * method of handler class.<br />
     * Value of field 'value' is path on which this handler method should be mapped.<br />
     * Value of field 'method' indicate type of HTTP method which we will access to handler method.</li>
     * </ul>
     * <b>NOTE:</b><br />
     * If value of field 'value' in annotation {@link RequestMapping @RequestMapping} has value with
     * curly brackets then that value should be mapped into path variable.
     * 
     * @param gameId {@link Integer} Annotated with {@link PathVariable @PathVariable} annotation
     *            with which we define that method parameter should be extracted from path URL. If
     *            method parameter and value inside of curly brackets have same name, value will be
     *            mapped by default. If for some reason value inside curly brackets and method
     *            parameter cannot be named equally, inside of {@link PathVariable @PathVariable} is
     *            needed to write value of path variable which we want to map as method parameter,
     *            for example: method( {@link PathVariable @PathVariable}("gameId") {@link Integer}
     *            game ).
     * @return {@link ResponseEntity} Wrapper class which along with response object allow us to
     *         send HTTP status code and HTTP Response Headers.
     */
    @RequestMapping( value = "/{gameId}", method = RequestMethod.GET )
    public ResponseEntity<Response> getQuestion( @PathVariable Integer gameId ) {

        // Fetch instance of Game with specific Game ID
        Game game = this.gameRepository.read( gameId );

        // Check if Game is in status for playing
        if ( !game.getGameStatusEnum().equals( GameStatus.PLAYING ) ) {
            // If game is not in status of playing we throw GameCompletedException exception
            throw new GameCompletedException( game );
        }

        // Fetching current questions.
        Question currentQuestion = GameUtil.getCurrentQuestion( game );

        // Set question status to Readed
        currentQuestion.setQuestionStatus( QuestionStatus.READED );

        // Return Game to with current question to end user with HTTP status code 200 - OK
        return new ResponseEntity<>( new Response( 0, game ), HttpStatus.OK );
    }

    /**
     * Handler method for submitting answer.
     * <ul>
     * <li>{@link RequestMapping @RequestMapping} Anotacija za mapiranje zahtjeva na specificnu
     * handler metodu klase.<br />
     * Vrijednost polja 'value' oznacava mapiranje metode handler klase.<br />
     * Vrijednost polja 'method' oznacava tip HTTP metode kojom cemo pristupati handler metodi.</li>
     * </ul>
     * 
     * @param gameId {@link Integer} Annotated with {@link PathVariable @PathVariable} annotation
     *            with which we define that method parameter should be extracted from path URL.
     * @param anwserIndex {@link Integer} Annotated with {@link PathVariable @PathVariable}
     *            annotation with which we define that method parameter should be extracted from
     *            path URL.
     * @return {@link ResponseEntity} Wrapper class which along with response object allow us to
     *         send HTTP status code and HTTP Response Headers.
     * @throws IllegalArgumentException if user submit not possible answer.
     */
    @RequestMapping( value = "/{gameId}/{anwserIndex}", method = RequestMethod.POST )
    public ResponseEntity<Response> submitAnswer( @PathVariable Integer gameId, @PathVariable Integer anwserIndex ) throws IllegalArgumentException {

        // Check if answer is possible
        if ( anwserIndex < 0 || anwserIndex > 3 ) {
            // If index of answer is not good, notify user about that
            throw new IllegalArgumentException( "Nepostojeci odgovor." );
        }

        // Fetch instance of Game with specific Game ID
        Game game = this.gameRepository.read( gameId );

        // Check if Game is in status for playing
        if ( !game.getGameStatusEnum().equals( GameStatus.PLAYING ) ) {
            // If game is not in status of playing we throw GameCompletedException exception
            throw new GameCompletedException( game );
        }

        // Fetching current questions.
        Question question = GameUtil.getCurrentQuestion( game );

        // Creating instance of object HttpHeaders through we will send URI to next action location.
        HttpHeaders headers = new HttpHeaders();

        // Check if submitted answer is correct
        if ( question.getCorrect() == anwserIndex ) {
            // If user submit correct answer, we mark question as answered correct
            question.setQuestionStatus( QuestionStatus.ANSWERED_CORRECT );

            // Generate URI to next posible action
            headers.setLocation( linkTo( GameController.class ).slash( game.getId() ).toUri() );

            // Check if user just answer last question in game
            if ( GameUtil.getLeftQuestions( game ) == 0 ) {
                // If user just answer to last question in game, we mark game as ended.
                game.setGameStatus( GameStatus.ENDED );
            }

        } else {
            // If user submit incorrect answer, we mark question as answered wrong
            question.setQuestionStatus( QuestionStatus.ANSWERED_WRONG );

            // Generate URI to next possible action
            headers.setLocation( linkTo( GameController.class ).toUri() );

            // We mark game as ended
            game.setGameStatus( GameStatus.ENDED );

        }

        // Return informations about game with HTTP Response headers and HTTP status code 200 - OK
        return new ResponseEntity<>( new Response( 0, game ), headers, HttpStatus.OK );

    }

}
