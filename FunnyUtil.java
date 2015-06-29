/*
 * Copyright 2000-2020 YGSoft.Inc All Rights Reserved.
 */
package org.garfield.osgi.impl.ecpfunny;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

/**
 * 游戏主控工具.<br>
 * @author liupeifeng <br>
 * @version 1.0.0 2015-6-28<br>
 * @see 
 * @since JDK 1.5.0
 */
public class FunnyUtil {
	
	private FunnyUtil() {
	}
	
	/**
	 * 房间：游戏中玩家的细节信息.
	 */
	private static Map<String,Map<String, FunnyPlayer>> map = new HashMap<String, Map<String, FunnyPlayer>>();
	
	/**
	 * 缓存某个房间游戏的人数.
	 */
	private static Map<String, Integer> mapPayerCount = new HashMap<String, Integer>();
	
	/**
	 * 随机数.
	 */
	private static final Random rand = new Random();			
	/**
	 * 随机获取关键词.
	 */
	public static String getFunnyKeyword() {
		return FunnyConst.KEYWORDLIST[rand.nextInt(FunnyConst.KEYWORDLIST.length)];
	}
	
	/**获取当前游戏人数.
	 * @param homeid 房间号
	 * @return 当前游戏人数
	 */
	public static int getPayerCount(String homeid) {
		return mapPayerCount.get(homeid);
	}
	
	/**
	 * 获取一个房间号码.
	 * @return
	 */
	public static String getHomeid() {
		return String.valueOf((int)Math.ceil(Math.random()*FunnyConst.MAX_HOMEID));
	}
	
	/**
	 * 创建游戏中两类角色的人数.
	 * @param count 玩家总人数.
	 * @return 两类角色的人数信息.
	 */
	public static String createPlayType(final int count) {
		BigDecimal b = new BigDecimal(count*FunnyConst.BILI).setScale(0,BigDecimal.ROUND_HALF_UP);
		int product = b.intValue();
		int engineer = count - product;
		return "一共" + count + "名玩家；产品角色人数：" + product + "，工程师人数：" + engineer + "\n";
	}
	
	/**
	 * 获取产品个数.
	 * @param count 玩家总人数
	 * @return 产品的人数.
	 */
	public static int getProductNember(final int count) {
		int product = (int) (count*FunnyConst.BILI);
		return product;
	}
	
	/**
	 * 创建整个游戏内容的入口.
	 * @param homeid 房间号
	 * @param count 玩家人数
	 * @return 游戏的整个内容.
	 */
	public static Map<String,Map<String, FunnyPlayer>> createPlayMain(final String homeid, final int count) {
		Map<String, FunnyPlayer> playerMap = new HashMap<String, FunnyPlayer>();
		String keyword = FunnyUtil.getFunnyKeyword();
		String[] key = keyword.split(FunnyConst.SPLITSHARP);
		// 产品角色的号码
		int[] ints = FunnyUtil.randProductNumerid(FunnyUtil.getProductNember(count),count);
		for (int i = 1; i < count + 1; i++) {
			FunnyPlayer player = new FunnyPlayer();
			player.setHomeid(homeid);
			player.setNumberid(i);
			player.setNconfig(FunnyUtil.createPlayType(count));
			player.setCommonconfig(FunnyUtil.createPlayType(count) + "产品角色的关键字：" + key[0] + "；工程师的关键字：" + key[1]);
			// 如果产品角色号码匹配则赋予产品角色的关键词
			if (isExist(i, ints)) {
				player.setType(0);
				player.setKeyword(key[0]);
			} else {
				player.setType(1);
				player.setKeyword(key[1]);
			}
			playerMap.put(String.valueOf(i), player);
		}
		map.put(homeid, playerMap);
		mapPayerCount.put(homeid, count);
		return map;
	}
	
	public static String printGameStartInfo(final String homeid, final int count) {
		if (count < 4) {
			map.clear();
			return "玩家人数至少4个哦！";
		}
		StringBuffer sb = new StringBuffer();
		sb.append("房间号：").append(homeid).append("\n");
		//sb.append(FunnyKeyWordUtil.createPlayType(count));
		String playersInfo = "";
		Map<String, FunnyPlayer> playerMap = map.get(homeid);
		Map.Entry<String,FunnyPlayer> object = null;
		for (Iterator<Map.Entry<String,FunnyPlayer>> iterator = playerMap.entrySet().iterator(); iterator.hasNext();) {
			object = iterator.next();
			FunnyPlayer player = object.getValue();
			sb.append(player.getNumberid() + "号是" + player.getKeyword()).append("\n");
			playersInfo = player.getCommonconfig();
		}
		sb.append(playersInfo);
		return sb.toString();
	}
	
	/**
	 * 随机若干个产品的号码出来.
	 * @param c 多少个产品
	 * @param count 总的人数
	 * @return 产品角色的号码
	 */
	public static int[] randProductNumerid(final int c,final int count) {
		int ret[] = new int[c];
		int i = 0;
		while(true) {
			if (i == c) {
				break;
			}
			int a = (int)Math.ceil(Math.random()*count);
			if (!isExist(a, ret)) {
				ret[i++] = a; 
			}
		}
		return ret;
		
	}
	
	/**
	 * 给出的数字是否出现在数组中.
	 * @param a 数字
	 * @param ints 数组
	 * @return 是否在
	 */
	private static boolean isExist(final int a, final int[] ints) {
		boolean ret = false;
		for (int i = 0; i < ints.length; i++) {
			if (a == ints[i]) {
				ret = true;
				break;
			}
		}
		return ret;
	}
	
	
	/**
	 * 玩家加入游戏入口.
	 * @param homeid 房间号
	 * @return 游戏内容.
	 */
	public synchronized static String enjoyGame(final String homeid) {
		if (!map.containsKey(homeid)) {
			return "房间不存在，要重新创建哦！";
		}
		Map<String, FunnyPlayer> playerMap = map.get(homeid);
		if (playerMap == null) {
			return "玩家人数至少4个人哦！";
		}
		if (playerMap.isEmpty()) {
			return "人数满了！！";
		}
		StringBuffer sb = new StringBuffer();
		String playersInfo = "";
		Map.Entry<String,FunnyPlayer> object = null;
		for (Iterator<Map.Entry<String,FunnyPlayer>> iterator = playerMap.entrySet().iterator(); iterator.hasNext();) {
			object = iterator.next();
			FunnyPlayer player = object.getValue();
			sb.append("我是" + player.getNumberid() + "号").append("\n");
			sb.append("ECP关键词：").append(player.getKeyword()).append("\n");;
			playersInfo = player.getNconfig();
			// 模拟出栈的效果，拿到即删除
			iterator.remove();
			break;
		}
		map.put(homeid, playerMap);
		sb.append(playersInfo);
		
		return sb.toString();
	}
	
	/**
	 * 刚进游戏的游戏规则说明.
	 * @return 游戏规则说明
	 */
	public static String getGameInfo() {
		return "################玩转ECP关键词##############\n" +
				"由1名用户创建游戏，游戏中玩家分为两类角色：产品和工程师。\n" +
				"玩家输入房间号码获取自己的身份信息，\n" +
				"由1号玩家开始逐个描述自己拿到的ECP关键词，\n" +
				"玩家根据描述判断谁是产品角色，\n" +
				"得票多者被认为是产品，工程师将其消灭（可能误杀哦），\n" +
				"N轮过后如果产品都被消灭则工程师胜，否则产品胜。\n" +
				"请先约定好惩罚哦，输的一方要被惩罚的！\n" + 
				"特殊情况：\n" +
				"（1）自我描述中不能含有自己的关键词字眼，否则出局\n" +
				"（2）存在票数相同情况下，被指认者自我辩解后重新投票\n" +
				"##########################################\n";
	}
	
	public static void main(final String[] args) throws InterruptedException {
		
		// 开始创建游戏
		System.out.println(FunnyUtil.getGameInfo());
		int player = 7;
		// 如果是重新开始游戏，则需调用FunnyUtil.getHomeid()获取房间号
		final String homeid = FunnyUtil.getHomeid();
		// 如果已经在游戏中，则重复输入之前已经获取到的房间号无需重新生成
		FunnyUtil.createPlayMain(homeid, player);
		// 用户创建游戏后的信息反馈
		System.out.println(FunnyUtil.printGameStartInfo(homeid, player));
		
		// 游戏创建后玩家加入房间参与游戏
		System.out.println("##############################################");
		// 模拟玩家输入房间号开始游戏的场景
		for (int i = 0; i < player; i++) {
			
			new Thread(){public void run() {
				System.out.println(FunnyUtil.enjoyGame(homeid));
				System.out.println("==============================================");
			    }}.start();
			
		}
		// 模拟游戏时间
		Thread.sleep(5000);
		// 游戏结束
		
		// 相同的房间号开启游戏
		System.out.println("游戏再一次开始了！");
		// 房间号知道了，只需输入房间号
		int againPlayerCount = FunnyUtil.getPayerCount(homeid);
		FunnyUtil.createPlayMain(homeid, againPlayerCount);
		// 用户创建游戏后的信息反馈
		System.out.println(FunnyUtil.printGameStartInfo(homeid, againPlayerCount));
		
		// 游戏创建后玩家加入房间参与游戏
		System.out.println("##############################################");
		// 模拟玩家输入房间号开始游戏的场景
		for (int i = 0; i < againPlayerCount; i++) {
			
			new Thread(){public void run() {
				System.out.println(FunnyUtil.enjoyGame(homeid));
				System.out.println("==============================================");
			    }}.start();
			
		}
		
	}
}
