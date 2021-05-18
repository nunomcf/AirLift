package proxies;

import common.Message;

/**
 * Interface for a shared region proxy, which
 * processes and replies to messages.
 */
public interface SharedRegionProxy {
    /**
     * Process message and decide which shared region
     * function to call. Replies to the message accordingly.
     * @param msg received messages
     * @return reply
     */
    public Message processAndReply(Message msg);

    /**
     * Gets simulation status.
     * @return if the simulation has ended
     */
    public boolean getSimStatus();
}
