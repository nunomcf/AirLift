package common;

import entities.Entity;
import java.io.Serializable;

/**
 * Auxiliar structure used to return a State and boolean. 
 */
public class StructBool implements Serializable{
    /**
     * Entity's State
     * @serial state
     */
    private States state;

    /**
     * Expected boolean value
     * @serial arg
     */
    private boolean arg;

    /**
     * Serial version of the class
     * @serial serialVersionUID
     */
	static final long serialVersionUID = 42L;

    /**
     * StructBool instantiation
     * 
     * @param state entity's state
     * @param arg boolean value 
     */
    public StructBool(States state, boolean arg) {
        this.state = state;
        this.arg = arg;
    }

    /**
     * Returns the entity's state.
     * @return entity's current state
     */
    public States getState() {
        return state;
    }

    /**
     * Sets the entity's state.
     * @param state entity's new state
     */
    public void setState(States state) {
        this.state = state;
    }

    /**
     * Returns the entity's arg.
     * @return entity's current arg
     */
    public boolean getArg() {
        return arg;
    }

    /**
     * Set the entity's arg.
     * @param arg entity's new arg
     */
    public void setArg(boolean arg) {
        this.arg = arg;
    }

	/**
	 * Auxiliary function to unwrap the struct
	 * @return the boolean of the struct
	 */
	public boolean unwrap() {
		((Entity) Thread.currentThread()).setState(state);
		return arg;
	}

}
