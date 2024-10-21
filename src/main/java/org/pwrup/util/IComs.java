package org.pwrup.util;

/**
 * @note essentially an interface to replace the Shuffleboard and allow for a
 *       more robust implementation
 */
public interface IComs {
    /**
     * @param topic the topic that a given data is being published to
     * @param data  the data to be published as Object type
     * @param type  the type of the Object. this can be used with libraries to
     *              convert the Object to a Json.
     */
    void onData(String topic, Object data, Class<?> type);
}
