package lineTenCustomersApi;

import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

public class LineTenCustomers extends Simulation {

  private HttpProtocolBuilder httpProtocol = http
    .baseUrl("https://linetencustomerapi.azurewebsites.net")
    .acceptHeader("application/json")
    .userAgentHeader("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/116.0.0.0 Safari/537.36 Edg/116.0.1938.76");

  private ScenarioBuilder scn = scenario("LineTenCustomersAPI Add and Get Customer details")
    .exec(
      http("Add Customer Info")
        .post("/Api/add")
        .body(ElFileBody("bodies/addCustomer.json")).asJson()
    )
    .pause(3)
    .exec(
      http("Get Customer Info")
        .get("/Api/get")
    )
    .pause(3)
    .exec(
      http("Bad request with incorrect ID")
        .get("/Api/get/d92819f8-8830-4450-adba-eec5fec804b5")
        .check(status().is(404))
    );

  {
	  setUp(scn.injectOpen(rampUsers(10).during(20))).protocols(httpProtocol);
  }
}
