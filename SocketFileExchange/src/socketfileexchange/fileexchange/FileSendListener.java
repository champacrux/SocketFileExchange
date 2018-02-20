/**
 * 
 */
package socketfileexchange.fileexchange;

/**
 *
 * @author Champ
 */
public interface FileSendListener {

    /**
     * connection state
     * @param connected true otherwise false 
     */
    void connectedStatus(boolean connected);
    
    /**
     * file sending has been started
     * @param fileId
     * @param file 
     */
    void sendingStarted(String fileId, String file);
    
    /**
     * file sending has been ended
     * @param fileId
     * @param file 
     */
    void sendingEnded(String fileId, String file);
    
    /**
     * if some error has been occurred
     * @param fileId
     * @param file
     * @param message string from underlying exception
     */
    void sendingError(String fileId, String file, String message);
    
    /**
     * progress of the file which is being sent
     * @param fileId any unique file id
     * @param file name of the file
     * @param sent total bytes which have been sent
     * @param total total bytes which are need to be sent
     * @param progress progress from 0 to 100
     */
    void sendProgress(String fileId, String file, long sent, long total, int progress);
}
