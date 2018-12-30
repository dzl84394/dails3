package org.dails.plugins.master.utils;

import javax.annotation.PostConstruct;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.leader.LeaderLatch;
import org.apache.curator.framework.recipes.leader.LeaderLatchListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MasterUtils {
	@Value("${zookeeper.url}")
	protected String url;
	
	@PostConstruct
	protected void init(String haha) throws Exception {
		CuratorFramework client = CuratorFrameworkFactory.builder().connectString( url)
				.sessionTimeoutMs(5000).retryPolicy(new ExponentialBackoffRetry(1000, 3)).build();
		client.start();
		
		
		LeaderLatch leaderLatch=new LeaderLatch(client,"");
		leaderLatch.addListener(new LeaderLatchListener() {
	        @Override
	        public void isLeader() {
	            log.info("Currently run as leader");
	        }

	        @Override
	        public void notLeader() {
	            log.info("Currently run as slave");
	        }
	    });
	    leaderLatch.start();
	
		
	}
	
	
}
