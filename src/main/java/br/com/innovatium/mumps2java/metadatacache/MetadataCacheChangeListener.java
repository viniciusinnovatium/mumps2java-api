package br.com.innovatium.mumps2java.metadatacache;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

/*
@MessageDriven(name = "MetadataCacheChangeListener", activationConfig = {
		@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
		@ActivationConfigProperty(propertyName = "destination", propertyValue = "java:/queue/atualizacaocache"),
		@ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge") })
		*/
public class MetadataCacheChangeListener implements MessageListener {
	
	private final MetadataCache metadataCache = MetadataCache.getCache();

	public void onMessage(Message rcvMessage) {
		ObjectMessage msg = (ObjectMessage) rcvMessage;
		try {

			Object[] keyValue = (Object[]) msg.getObject();
			metadataCache.set((Object[]) keyValue[0], keyValue[1]);

			System.out.println("Atualizando a chave: " + keyValue[0]
					+ " com o valor: " + keyValue[1]);
		} catch (JMSException e) {
			throw new RuntimeException(e);
		}
	}
}