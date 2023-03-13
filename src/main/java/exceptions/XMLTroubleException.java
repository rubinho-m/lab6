/**
 * XMLTroubleException is an Exception class that is thrown when parser can't parse data from xml because it's incorrect.
 */

package exceptions;

public class XMLTroubleException extends RuntimeException {
    public XMLTroubleException(String message) {
        System.out.println(message);
    }
}
