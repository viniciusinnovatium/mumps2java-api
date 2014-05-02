package br.com.innovatium.mumps2java.cachemanager;

import java.io.IOException;
import java.util.List;

import org.infinispan.client.hotrod.RemoteCache;
import org.infinispan.client.hotrod.RemoteCacheManager;
import org.infinispan.client.hotrod.configuration.Configuration;
import org.infinispan.client.hotrod.configuration.ConfigurationBuilder;
import org.infinispan.protostream.MessageMarshaller;

import br.com.innovatium.mumps2java.datastructure.Node;

public final class CacheManagerHotRod extends CacheManager {
	final static String SERVER_HOST = "localhost";
	final static int SERVER_PORT = 11222;

	final static Configuration config;
	final static RemoteCache<Object, Object> cache;
	static {
		config = new ConfigurationBuilder().addServer()
				.host(SERVER_HOST).port(SERVER_PORT).build();
		cache = new RemoteCacheManager(config, true).getCache("PEDIDO");
	}

	public CacheManagerHotRod() {
		super(cache);
		/*
		SerializationContext srcCtx = ProtoStreamMarshaller.getSerializationContext(rcm);

		try {
			srcCtx.registerProtofile("/lib.protobin");
			srcCtx.registerMarshaller(Node.class, new NodeMarshaller());
		} catch (Exception e) {
			throw new IllegalStateException("Fail on registry node marshaller. Impossible create cache manager.", e);
		}
		*/
	}
	public List<Object[]> like(String path) {
		/*
		final Query query = Search.getQueryFactory(cache).from(Node.class)
				.having("path").like("%" + path + "%").toBuilder().build();
				
				
				 
		SearchManager searchManager = org.infinispan.query.Search.getSearchManager(cache.);

		// create any standard Lucene query, via Lucene's QueryParser or any other means:
		org.apache.lucene.search.Query fullTextQuery = //any Apache Lucene Query

		// convert the Lucene query to a CacheQuery:
		CacheQuery cacheQuery = searchManager.getQuery( fullTextQuery );

		// get the results:
		List<Object> found = cacheQuery.list();
				 

		return query.list();
		 */
		throw new UnsupportedOperationException();
	}

	class NodeMarshaller implements MessageMarshaller<Node> {

		public void writeTo(ProtoStreamWriter writer, Node node) throws IOException {
			/*
			writer.writeObject("subscript", node.getSubscript());
			writer.writeString("path", node.getPath());
		    writer.writeString("value", node.getValue().toString());
		    */
		}

		public Class<? extends Node> getJavaClass() {
			return Node.class;
		}

		public String getTypeName() {
			return "br.com.disclic.cachemanager.SuperNode";
		}

		public Node readFrom(ProtoStreamReader reader)
				throws IOException {
			throw new UnsupportedOperationException();
			//return new Node(reader.readString("subscript"), reader.readString("path"), reader.readString("value"));
		}
	}

	public List<Node> like(String... subs) {
		// TODO Auto-generated method stub
		return null;
	}
}
