package common;

import entities.Entity;
import java.io.Serializable;

/**
 * Auxiliar structure used to return a State and int array. 
 */
public class StructIntArr implements Serializable{

	/**
     * Entity's State
     * @serial state
     */
    private States state;

	/**
     * Expected int array
     * @serial arg
     */
    private int[] arg;

	/**
     * Serial version of the class
     * @serial serialVersionUID
     */
	static final long serialVersionUID = 42L;

	/**
     * StructIntArr instantiation
     * 
     * @param state entity's state
     * @param arg boolean value 
     */
    public StructIntArr(States state, int[] arg) {
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
    public int[] getArg() {
        return arg;
    }

	/**
     * Set the entity's arg.
     * @param arg entity's new arg
     */
    public void setArg(int[] arg) {
        this.arg = arg;
    }

    /**
	 * Auxiliary function to unwrap the struct
	 * @return the int[] of the struct
	 */
	public int[] unwrap() {
		((Entity) Thread.currentThread()).setState(state);
		return arg;
	}
}
