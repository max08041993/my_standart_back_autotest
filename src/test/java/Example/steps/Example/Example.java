package Example.steps.Example;

import Example.steps.BaseRestApi;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Example extends BaseRestApi {

    protected String urlSwagger= "https://pokeapi.co/api/v2";

    @When("^GET https://pokeapi.co/api/v2/pokemon/%s (.*) для примера$")
    public void getExample (String name, DataTable dt){
        String uri = "pokemon/";
        Response response = get(null, null, null, urlSwagger, uri, name);
        response.prettyPrint();
        ValidatableResponse validatableResponse = response.then();
        validatableResponse.statusCode(200);
        List<Map<String, String>> allData = dt.asMaps(String.class, String.class);
        assertResponseData(validatableResponse, allData);
    }

}
