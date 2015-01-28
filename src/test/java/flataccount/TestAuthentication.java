package flataccount;



import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.suv.flat.model.User;
import com.suv.flat.service.CoreService;

public class TestAuthentication {
	User user;
	User wrongUser;
	
	@Before
	public void initialize(){
		user=new User();
		user.setUser_login("test");
		user.setPassword("test");
		user.setOrg_id(1);
		wrongUser=new User();
		wrongUser.setUser_login("test123");
		wrongUser.setPassword("test");
	}
	
	@Test
	public void correctAuthenticate(){
		 assertEquals(CoreService.authenticateUser(user),true);
	}
	
	@Test
	public void apiAuthenticate() throws IOException{
		ObjectMapper mapper = new ObjectMapper();
		URL url = new URL("http://localhost:8080/cyberaccount/api/login");
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setDoOutput(true);
		conn.setRequestMethod("POST");
		conn.setRequestProperty("Content-Type", "application/json");
		String input=mapper.writeValueAsString(user);
		OutputStream os = conn.getOutputStream();
		os.write(input.getBytes());
		os.flush();
		BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
		String output;
		System.out.println("Output from Server .... \n");
		while ((output = br.readLine()) != null) {
			System.out.println(">>" + output);
		}
	}
}
