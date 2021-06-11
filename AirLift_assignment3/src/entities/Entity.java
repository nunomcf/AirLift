package entities;

import common.States;

/**
 * This interface will be implemented by the entities.
 */
public interface Entity {
    /**
     * Sets the entity's state
     * @param s the state to be set
     */
	public void setState(States s);
}