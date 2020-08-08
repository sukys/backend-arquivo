package br.com.southsytem.fileprocessor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import br.com.southsytem.fileprocessor.entity.Client;
import br.com.southsytem.fileprocessor.service.ClientService;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class ClientTest extends TestCase {


	List<String> correctData = new ArrayList<>(
			Arrays.asList("002ç2345675434544345çJose da SilvaçRural", 
					"002ç3456754345443467çBruno CostaçConstrucao", 
					"002ç4567543344434567çEduardo PereiraçRural",
					"002ç6754345443467890çMateus EduardoçInformatica", 
					"002ç4334443456712345çNicolas TeslaçEnergia" ));
	
	ClientService service = new ClientService();
	
	/**
	 * Create the test case
	 *
	 * @param testName name of the test case
	 */
	public ClientTest(String testName) {
		super(testName);
	}

	/**
	 * @return the suite of tests being tested
	 */
	public static Test suite() {
		return new TestSuite(ClientTest.class);
	}

	
	public void testConversion() {
		service.resetDatabase();
		service.convertAndSave(correctData.get(0).split("ç"));
		Client client = service.findAll().get(0);
		assertEquals("Jose da Silva", client.getName());
	}
	
	
	public void testAllConversions() {
		service.resetDatabase();
		for (String data : correctData) {
			service.convertAndSave(data.split("ç"));
		}
		int total = service.count();
		assertEquals(5, total);
	}
	
}
