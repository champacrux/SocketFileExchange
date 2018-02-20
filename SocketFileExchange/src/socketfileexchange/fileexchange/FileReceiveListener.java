/**
 * 
 */
package socketfileexchange.fileexchange;

/**
 * Listener for File which is being received
 * @author Champ
 */
public interface FileReceiveListener {
    
    /**
     * Connection status
     * @param connected if true, otherwise false
     */
    void connectedStatus(boolean connected);
    
    /**
     * receiving has been started
     * @param fileId
     * @param file 
     */
    void receivingStarted(String fileId, String file);
    
    /**
     * receiving has been ended
     * @param fileId
     * @param file 
     */
    void receivingEnded(String fileId, String file);
    
    /**
     * progress of the file which is receiving
     * @param fileId fileId sent by client
     * @param file filename of the file
     * @param received total bytes received
     * @param total total bytes which are need to be received
     * @param progress progress from 0 to 100
     */
    void receiveProgress(String fileId, String file, long received, long total, int progress);
    
    /**
     * if some error has been occurred
     * @param fileId
     * @param file
     * @param message string from underlying exception handler
     */
    void receivingError(String fileId, String file, String message);
}
