package com.dails.master.utils;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.leader.LeaderLatch;
import org.apache.curator.framework.recipes.leader.LeaderLatchListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


@Component
public class MasterUtils {
	@Value("${zookeeper.url}")
	protected String url;
	
	protected String leaderPath="/role/_default";
	
	Map<String, Boolean> masterMap = new HashMap<String, Boolean>();
	private static final Logger logger = LogManager.getLogger(MasterUtils.class);
	
	
	@PostConstruct
	protected void init(String haha) throws Exception {
		CuratorFramework client = CuratorFrameworkFactory
				.builder()
				.connectString( url)
				.sessionTimeoutMs(5000)
				.retryPolicy(new ExponentialBackoffRetry(1000, 3))
				.build();
		client.start();
		
		
		// 启动里面有
		//配置文件里面有指定
		//如果都没有，才走zk
		
		LeaderLatch leaderLatch=new LeaderLatch(client,leaderPath);
		leaderLatch.addListener(new LeaderLatchListener() {
	        @Override
	        public void isLeader() {
	        	logger.info("Currently run as leader");
	            masterMap.put(leaderPath, true);
	        }

	        @Override
	        public void notLeader() {
	        	logger.info("Currently run as slave");
	        }
	    });
	    leaderLatch.start();
	
		
	}
	
	public boolean isMaster () {
		Boolean b = masterMap.get(leaderPath);
		return b == null ? false : b;
	}

	
	
}
