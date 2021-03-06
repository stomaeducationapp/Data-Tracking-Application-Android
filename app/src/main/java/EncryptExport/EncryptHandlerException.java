package EncryptExport;

/* AUTHOR INFORMATION
 * CREATOR - Jeremy Dunnet 09/10/2018
 * LAST MODIFIED BY - Jeremy Dunnet 27/11/2018
 */

/* CLASS/FILE DESCRIPTION
 * This exception is a personalized exception inheritance so all errors from this package are grouped under the same heading - to increase tracking ability to what caused which error
 */

/* VERSION HISTORY
 * 08/10/2018 - Created class
 * 27/11/2018 - Updated to a public class so observers can use
 */

/* REFERENCES
 * Based on similar custom exception used by team member Patrick Crockford (in XML_Reader and related classes)
 */

public class EncryptHandlerException extends Exception {
    public EncryptHandlerException()
    {
    }

    EncryptHandlerException(String message)
    {
        super(message);
    }
}