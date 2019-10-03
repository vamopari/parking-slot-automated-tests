import groovy.json.JsonBuilder
import io.restassured.RestAssured
import io.restassured.http.ContentType
import io.restassured.response.Response

class TestUserSpec extends TestDemo {

	def setupSpec() {
		println("setup spec changed");
		
	}
	
	def setup() {
		println("set up");\
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
				.headers
				.body(reqBody)
				.post(basepath+"/login/");
		println(response.asString());

		then: "Status code is 200 and access token is given"
		assert response.getStatusCode() == 200
	}

}
