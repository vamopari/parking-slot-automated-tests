import com.google.gson.Gson
import io.restassured.RestAssured
import io.restassured.http.ContentType
import io.restassured.response.Response

class TestSlots extends TestDemo{
    def setup(){
        def  reqBody = new HashMap();
        reqBody.put("username", "vishal")
        reqBody.put("password", "password@123")
        Response response = RestAssured.given().request()
                .contentType(ContentType.JSON)
                .body(reqBody)
                .post(basepath+"/login/");
        println(response.asString());

        Gson gson = new Gson();
        Map<String,String> attributes = gson.fromJson(response.asString(), Map.class);
        access_token = "Bearer "+attributes.access_token
    }

    def "list all slots"(){
        given: "list all slots"
        def limit = 10;
        def offset = 0
        def path = basepath+"/slot/?limit="+limit+"&offset="+offset
        println(path)

        when: "slots listing api"
        Response response = RestAssured.given().request()
                .accept(ContentType.JSON)
                .header("Authorization", access_token)
                .get(path)
        println(response.asString())
        Gson gson = new Gson();
        Map<String,Object> attributes = gson.fromJson(response.asString(),Map.class);
        then:"check response for list api"
        assert response.getStatusCode() == 200
        assert attributes.results instanceof ArrayList
        assert attributes.count == 1381
        assert attributes.next instanceof String
    }

    def "book this slot"(){
        given:"book a slot"
        def path = basepath+"/reservation/"
        def  reqBody = new HashMap();
        reqBody.put("price", "10")
        reqBody.put("payment_status", true)
        reqBody.put("payment_type", "Credit Card")
        reqBody.put("paid_amount", "10")
        reqBody.put("user", 1)
        reqBody.put("slot", 1)

        when:"book a parking slot"
        Response response = RestAssured.given().request()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .header("Authorization", access_token)
                .body(reqBody)
                .post(path)

        println(response.asString())
        Gson gson = new Gson();
        Map<String,String> attributes = gson.fromJson(response.asString(), Map.class);

        then:"check response for post api"
        assert response.getStatusCode() == 201
    }
}

