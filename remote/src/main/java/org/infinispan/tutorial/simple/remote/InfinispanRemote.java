package org.infinispan.tutorial.simple.remote;

import org.infinispan.client.hotrod.RemoteCache;
import org.infinispan.client.hotrod.RemoteCacheManager;
import org.infinispan.client.hotrod.configuration.ConfigurationBuilder;
import org.infinispan.client.hotrod.impl.ConfigurationProperties;
import org.infinispan.commons.api.CacheContainerAdmin;

public class InfinispanRemote {

   public static void main(String[] args) {
      // Create a configuration for a locally-running server
      ConfigurationBuilder builder = new ConfigurationBuilder();
      builder.addServer().host("172.30.65.185").port(ConfigurationProperties.DEFAULT_HOTROD_PORT).security()
      .ssl()
      //.sniHostName("myservername")
      //.trustStoreFileName("/path/to/truststore")
      //.trustStorePassword("truststorepassword".toCharArray())
      .keyStoreFileName("/opt/infinispan/server/conf/keystore")
      .keyStorePassword("password".toCharArray());;

      RemoteCacheManager cacheManager = new RemoteCacheManager(builder.build());
      cacheManager.administration().withFlags(CacheContainerAdmin.AdminFlag.VOLATILE).getOrCreateCache("test", "org.infinispan.DIST_SYNC");
      RemoteCache<String, String> cache = cacheManager.getCache("test");
      cache.put("key", "value");
      System.out.printf("key = %s\n", cache.get("key"));
      cacheManager.stop();
   }

}
