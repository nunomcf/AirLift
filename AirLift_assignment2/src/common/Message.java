package common;

import java.io.Serializable;

/**
 * This data type defines the structure of the messages to be
 * exchanged by the clients and the servers.
 */
public class Message implements Serializable{

	private static final long serialVersionUID = 2019L;

    /**
     * Entity's Id
     * @serial id
     */
	private int id;
	
	/**
     * Entity's ID value is valid
     * @serial bid
     */
    private boolean bid;

    /**
     * Entity's Id
     * @serial state
     */
	private States state;
	
	/**
     * Entity's state value is valid
     * @serial bstate
     */
    private boolean bstate;

	/**
     * Type of this message
     * @serial mt
     */
	private MessageType mt;

	/**
     * Message type is valid
     * @serial bmt
     */
    private boolean bmt;


	/**
     * First int value to be transported
     * @serial intVal1
     */
	private int intVal1;

	/**
     * First int value is valid
     * @serial bintVal1
     */
	private boolean bintVal1;

	/**
     * Second int value to be transported
     * @serial intVal2
     */
	private int intVal2;

	/**
     * Second int value is valid
     * @serial bintVal2
     */
	private boolean bintVal2;

	/**
     * Third int value to be transported
     * @serial intVal3
     */
	private int intVal3;

	/**
     * Third int value is valid
     * @serial bintVal3
     */
	private boolean bintVal3;

	/**
     * First boolean value to be transported
     * @serial boolVal1
     */
	private boolean boolVal1;

	/**
     * First boolean value is valid
     * @serial bboolVal1
     */
	private boolean bboolVal1;

	/**
     * Second boolean value to be transported
     * @serial boolVal2
     */
	private boolean boolVal2;

	/**
     * Second boolean value is valid
     * @serial bboolVal2
     */
	private boolean bboolVal2;

	 /**
     * Message instantiation
     */
	public Message() {
		bid=false;
		bstate=false;
		bmt=false;
		bintVal1=false;
		bintVal2=false;
		bintVal3=false;
		bboolVal1=false;
		bboolVal2=false;
	}

    /**
     * Returns the entity's id.
     * @return entity's id
     */
    public int getEntityId() { 
		if(!bid) assert(false);
        return id;
	}
	
    /**
     * Sets the entity's id.
     * @param id entity's id
     */
	public void setEntityId(int id){
		bid=true;
		this.id=id;
	}

    /**
     * Returns the entity's state.
     * @return customer's state
     */
	public States getEntityState(){
		if(!bstate) assert(false);
		return state;
	}

    /**
     * Sets the entitiy's state.
     * @param s desired state
     */
	public void setEntityState(States s){
		bstate=true;
		state=s;
	}

	/**
     * Returns the message type.
     * @return message type
     */
	public MessageType getMessageType(){
		if(!bmt) assert(false);
		return mt;
	}

    /**
     * Sets the message type.
     * @param mt message type
     */
	public void setMessageType(MessageType mt){
		bmt=true;
		this.mt=mt;
	}

    /**
     * Sets the first int value to be transported.
     * @param v value
     */
	public void setIntVal1(int v){
		bintVal1=true;
		intVal1=v;
	}

	/**
     * Returns the first int value.
     * @return value
     */
	public int getIntVal1(){
		if(!bintVal1) assert(false);
		return intVal1;
	}

	/**
     * Sets the second int value to be transported.
     * @param v value
     */
	public void setIntVal2(int v){
		bintVal2=true;
		intVal2=v;
	}

	/**
     * Returns the second int value.
     * @return value
     */
	public int getIntVal2(){
		if(!bintVal2) assert(false);
		return intVal2;
	}

	/**
     * Sets the third int value to be transported.
     * @param v value
     */
	public void setIntVal3(int v){
		bintVal3=true;
		intVal3=v;
	}

	/**
     * Returns the third int value.
     * @return value
     */
	public int getIntVal3(){
		if(!bintVal3) assert(false);
		return intVal3;
	}

	/**
     * Sets the first boolean value to be transported.
     * @param v value
     */
	public void setBoolVal1(boolean v){
		bboolVal1=true;
		boolVal1=v;
	}

	/**
     * Returns the first boolean value.
     * @return value
     */
	public boolean getBoolVal1(){
		if(!bboolVal1) assert(false);
		return boolVal1;
	}

	/**
     * Sets the second boolean value to be transported.
     * @param v value
     */
	public void setBoolVal2(boolean v){
		bboolVal2=true;
		boolVal2=v;
	}

	/**
     * Returns the second boolean value.
     * @return value
     */
	public boolean getBoolVal2(){
		if(!bboolVal2) assert(false);
		return boolVal2;
	}
}
