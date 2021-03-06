package com.game.common.server.session;

import java.util.concurrent.atomic.AtomicInteger;

import com.game.common.pb.object.GameObject;
import com.game.common.server.action.IAction;
import com.game.common.server.config.Config;
import com.game.common.server.queue.MessageQueue;
import com.game.common.server.queue.MessageQueueFactory;
import com.game.pb.server.message.MessageObj;

import io.netty.channel.Channel;

/**
 * @author tangjp
 *
 */
public class GameSession {
	public static final AtomicInteger atomicId=new AtomicInteger();
	private int id;
	private long createTime;
	private long readTime;
	private Channel channel;
	private MessageQueue messageQueue;
	
	public GameSession(Channel channel) {
		// TODO Auto-generated constructor stub
		id=atomicId.incrementAndGet();
		createTime=System.currentTimeMillis();
		readTime=createTime;
		this.channel=channel;
		int poolId=id%Config.MESSAGE_POOL_NUM;
		messageQueue=MessageQueueFactory.getInstance()
				.getMessageQueue(MessageQueueFactory.MessageQueueType.multiType,poolId);
	}
	
	public void addMessage(IAction<MessageObj.NetMessage> msg){
		messageQueue.addQueue(msg);
	}

	public int getId() {
		return id;
	}

	public long getCreateTime() {
		return createTime;
	}

	public long getReadTime() {
		return readTime;
	}

	public void updateReadTime() {
		this.readTime = System.currentTimeMillis();
	}

	public Channel getChannel() {
		return channel;
	}

	public void setChannel(Channel channel) {
		this.channel = channel;
	}
	
	
}
