package org.garfield.osgi.impl.ecpfunny;

/**
 * 游戏参与者可收到的信息.<br>
 * @author liupeifeng <br>
 * @version 1.0.0 2015-6-28<br>
 * @see 
 * @since JDK 1.5.0
 */
public class FunnyPlayer extends FunnyBaseInfo{
	
	public int getNumberid() {
		return numberid;
	}

	public void setNumberid(final int newNumberid) {
		numberid = newNumberid;
	}

	/**
	 * 号码.
	 */
	private int numberid;
	
	/**
	 * 角色类型.
	 */
	private int type;

	public int getType() {
		return type;
	}

	public void setType(final int newType) {
		type = newType;
	}


	
	

}
