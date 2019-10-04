import com.google.gson.Gson
import io.restassured.RestAssured
import io.restassured.http.ContentType
import io.restassured.response.Response
import java.lang.reflect.Type;
import com.google.gson.reflect.TypeToken;

class TestUserSpec extends TestDemo {

	def setupSpec() {
		println("setup spec changed");
	}
	
	def setup() {
		println("set up");
	}
	
	def "test_user_login_with_correct_creds"(){
		
		// can pass different user name and password combinations and use @unroll???
		
		given:"Correct username and Password"
		def  reqBody = new HashMap();
		reqBody.put("username", "vishal")
		reqBody.put("password", "password@123")

		when: "calling login api with correct credentials"
		Response response = RestAssured.given().request()
				.contentType(ContentType.JSON)
				.body(reqBody)
				.post(basepath+"/login/");
		println(response.asString());

		Gson gson = new Gson();
		Map<String,String> attributes = gson.fromJson(response.asString(),Map.class);
		access_token = attributes.access_token
		println(access_token)
		then: "Status code is 200 and access token is given"
		assert response.getStatusCode() == 200

	}

	def "test_user_login_with_wrong_creds"(){

		// can pass different user name and password combinations and use @unroll???

		given:"Correct username and Password"
		def  reqBody = new HashMap();
		reqBody.put("username", "vishal")
		reqBody.put("password", "password@1234")

		when: "calling login api with correct credentials"
		Response response = RestAssured.given().request()
				.contentType(ContentType.JSON)
				.body(reqBody)
				.post(basepath+"/login/");
		println(response.asString());


		then: "Status code is 400 and access token is given"
		assert response.getStatusCode() == 400
	}
}
