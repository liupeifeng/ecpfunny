/**
 * 
 */
package org.garfield.osgi.impl.ecpfunny;

/**
 * 常量类.<br>
 * @author liupeifeng <br>
 * @version 1.0.0 2015-6-28<br>
 * @see 
 * @since JDK 1.5.0
 */
public final class FunnyConst {
	
	/**
	 * 关键词.
	 * 应该迁移到文本或者数据库，并加以丰富.
	 */
	public static final String[] KEYWORDLIST = {"行为动作#行为事件", "表达式#运算符", "并行分支#条件分支", "拉生成#推生成", "实体转换#协同处理"};

	/**
	 * 产品角色与工程师的比例.
	 */
	public static final float BILI = 0.3f;
	
	/**
	 * 最大房间号码.
	 */
	public static final int MAX_HOMEID = 9999;
	
	/**
	 * 关键词中产品和工程师的分隔符.
	 */
	public static final String SPLITSHARP = "#";
}
