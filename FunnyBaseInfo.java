package org.garfield.osgi.impl.ecpfunny;

/**
 * 参与者收到的信息基类.<br>
 * @author liupeifeng <br>
 * @version 1.0.0 2015-6-28<br>
 * @see 
 * @since JDK 1.5.0
 */
public class FunnyBaseInfo {
	
	/**
	 * 玩家能看到的信息.
	 */
	protected String nconfig;
	
	/**
	 * 房间号.
	 */
	protected String homeid;
	
	/**
	 * ECP关键词.
	 */
	protected String keyword;
	
	/**
	 * 创建游戏者，即游戏中用户所能看到的信息.
	 */
	protected String commonconfig;
	
	public String getHomeid() {
		return homeid;
	}

	public void setHomeid(final String newHomeid) {
		homeid = newHomeid;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(final String newKeyword) {
		keyword = newKeyword;
	}

	public String getCommonconfig() {
		return commonconfig;
	}

	public void setCommonconfig(final String newCommonconfig) {
		commonconfig = newCommonconfig;
	}
	
	

	public String getNconfig() {
		return nconfig;
	}

	public void setNconfig(final String newNconfig) {
		nconfig = newNconfig;
	}


}
