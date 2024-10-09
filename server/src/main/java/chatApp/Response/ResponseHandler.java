package chatApp.Response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

public class ResponseHandler {

    /**
     * method that responsible for returning  success ResponseEntity with relevant data and status code
     *
     * @param success
     * @param status
     * @param responseObj
     * @return ResponseEntity
     */
    public static ResponseEntity<Object> generateResponse(boolean success, HttpStatus status, Object responseObj) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("success", success);
        map.put("data", responseObj);

        return new ResponseEntity<Object>(map,status);
    }

    /**
     * method that responsible for returning  error ResponseEntity with relevant errors and status code
     *
     * @param success
     * @param status
     * @param errors
     * @return ResponseEntity
     */
    public static ResponseEntity<Object> generateErrorResponse(boolean success,HttpStatus status, Object errors) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("success", success);
        map.put("errors", errors);

        return new ResponseEntity<Object>(map,status);
    }

}