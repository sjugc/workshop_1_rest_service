package com.sjugc.workshop.w1.rest.millionaire.core.model;

/**
 * Wrapper class for all responses.
 * Class carry status code of business process and response object.
 * List of status codes:
 * <ul>
 * <li>0 - Process successfully completed.</li>
 * <li>1 - Process failed. Error is known. (Display error to end user)</li>
 * <li>2 - Process failed. Error is unknown. (Display generic error 'Unexpected error occured. Try
 * again later' to end user)</li>
 * </ul>
 * 
 * @author mjovanovic
 */
public class Response {

    private int stauts;

    private Object obj;

    public Response( int status, Object obj ) {
        this.stauts = status;
        this.obj = obj;
    }

    public int getStauts() {
        return stauts;
    }

    public Object getResponse() {
        return obj;
    }

}
