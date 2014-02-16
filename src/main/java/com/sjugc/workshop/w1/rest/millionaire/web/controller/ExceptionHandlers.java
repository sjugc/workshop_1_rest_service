package com.sjugc.workshop.w1.rest.millionaire.web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import com.sjugc.workshop.w1.rest.millionaire.core.exception.GameCompletedException;
import com.sjugc.workshop.w1.rest.millionaire.core.exception.GameDoesNotExistException;
import com.sjugc.workshop.w1.rest.millionaire.core.model.Response;

/**
 * Class for handling exception and nice propagation of exceptions to end user.
 * Every response to end user are sent in wrapper class {@link Response} for client knowledge of
 * business status of process.
 * List of status codes:
 * <ul>
 * <li>0 - Process successfully completed.</li>
 * <li>1 - Process failed. Error is known. (Display error to end user)</li>
 * <li>2 - Process failed. Error is unknown. (Display generic error 'Unexpected error occured. Try
 * again later' to end user)</li>
 * </ul>
 * <b>NOTE:</b><br />
 * <ul>
 * <li>{@link ExceptionHandler @ExceptionHandler} anntotaion define exception handler method.<br />
 * Value of field 'value' marks list of exception which handler should handle.</li>
 * </ul>
 * 
 * @author mjovanovic
 */
@ControllerAdvice
@ResponseBody
class ExceptionHandlers {

    /**
     * Handler for handling {@link GameDoesNotExistException} exception.
     * 
     * @param e {@link Exception} exception which is handled.
     * @return {@link ResponseEntity} Wrapper class for sending response object and HTTP status
     *         code.
     */
    @ExceptionHandler( value = { GameDoesNotExistException.class } )
    public ResponseEntity<Response> handleNotFounds( Exception e ) {
        return new ResponseEntity<>( new Response( 1, e.getMessage() ), HttpStatus.NOT_FOUND );
    }

    /**
     * Handler for handling {@link GameCompletedException} exception.
     * 
     * @param e {@link GameCompletedException} exception which is handled.
     * @return {@link ResponseEntity} Wrapper class for sending response object and HTTP status
     *         code.
     */
    @ExceptionHandler( value = { GameCompletedException.class } )
    public ResponseEntity<Response> handleGameCompleted( GameCompletedException e ) {
        return new ResponseEntity<>( new Response( 1, e.getGame() ), HttpStatus.BAD_REQUEST );
    }

    /**
     * Handler for handling {@link IllegalArgumentException} exception.
     * 
     * @param e {@link Exception} exception which is handled.
     * @return {@link ResponseEntity} Wrapper class for sending response object and HTTP status
     *         code.
     */
    @ExceptionHandler( value = { IllegalArgumentException.class } )
    public ResponseEntity<Response> handleIllegalArgument( Exception e ) {
        return new ResponseEntity<>( new Response( 1, e.getMessage() ), HttpStatus.BAD_REQUEST );
    }

    /**
     * Handler for handling {@link Exception} exception (all exceptions which is not expected to be
     * thrown).
     * 
     * @param e {@link Exception} exception which is handled.
     * @return {@link ResponseEntity} Wrapper class for sending response object and HTTP status
     *         code.
     */
    @ExceptionHandler( { Exception.class } )
    public ResponseEntity<Response> handleUnknownException( Exception e ) {
        return new ResponseEntity<>( new Response( 2, e.getMessage() ), HttpStatus.INTERNAL_SERVER_ERROR );
    }

}
