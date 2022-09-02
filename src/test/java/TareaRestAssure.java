import Entities.Post;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import static org.hamcrest.Matchers.matchesPattern;

public class TareaRestAssure {

    @Test
    public void verificarTodosLosResultados() {
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";

        Response response = RestAssured.when().get("/posts");
        response.then().log().body();

        response.then().assertThat().statusCode(200);
        response.then().assertThat().body("size()", Matchers.not(0));
    }

    @Test
    public void verificarGetDelIdUno() {
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";

        Response response = RestAssured.given().pathParam("id", "1")
                .when().get("/posts/{id}");

        response.then().log().body();
        response.then().assertThat().statusCode(200);

        response.then().assertThat().body("userId", Matchers.equalTo(1));
        response.then().assertThat().body("id", Matchers.equalTo(1));
        response.then().assertThat().body("title", Matchers.equalTo("sunt aut facere repellat provident occaecati excepturi optio reprehenderit"));
        response.then().assertThat().body("body", Matchers.equalTo("quia et suscipit\nsuscipit recusandae consequuntur expedita et cum\nreprehenderit molestiae ut ut quas totam\nnostrum rerum est autem sunt rem eveniet architecto"));
    }

    @Test
    public void postEmployeeTest() throws JsonProcessingException {
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";

        Post post = new Post();
        post.setUserId(5555);
        post.setTitle("Automatización de pruebas con REST Assured");
        post.setBody("En dicha página se encuentra detallado una serie de endpoints públicos");

        ObjectMapper mapper = new ObjectMapper();
        String payload = mapper.writeValueAsString(post);

        Response response = RestAssured.
                given().contentType(ContentType.JSON).accept(ContentType.JSON).body(payload)
                .when().post("/posts");

        response.then().log().body();

        response.then().assertThat().statusCode(201);
//        response.then().assertThat().body("id", matchesPattern("\\d+"));
    }

    @Test
    public void putEmployeeTest() throws JsonProcessingException {
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";

        Post post = new Post();
        post.setUserId(5556);
        post.setTitle("Automatización de pruebas con REST Assured");
        post.setBody("En dicha página se encuentra detallado una serie de endpoints públicos");

        ObjectMapper mapper = new ObjectMapper();
        String payload = mapper.writeValueAsString(post);

        Response response = RestAssured
                .given().contentType(ContentType.JSON).accept(ContentType.JSON).body(payload)
                .and().pathParam("id", "1")
                .when().put("/posts/{id}");

        response.then().log().body();

        response.then().assertThat().statusCode(200);
        response.then().assertThat().body("id", Matchers.equalTo(1));
    }

    @Test
    public void deleteEmployeeTest() {
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";

        Response response = RestAssured
                .given().pathParam("id", "1")
                .when().delete("/posts/{id}");

        response.then().log().body();

        response.then().assertThat().statusCode(200);
        response.then().assertThat().body(Matchers.equalTo("{}"));

    }

}
