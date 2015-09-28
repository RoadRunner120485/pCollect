import io.gatling.core.Predef._
import io.gatling.http.Predef._

import scala.concurrent.duration._

/**
 * Created by sturmm on 23.09.15.
 */
class CreateUserSimulation extends Simulation {


  val httpConf = http // 4
    .baseURL("http://localhost:8081") // 5
    .contentTypeHeader("application/json")
    .acceptHeader("application/json") // 6

  val scn = scenario("BasicSimulation") // 7
    .exec(http("request_1")  // 8
    .put("/customer").body(StringBody("""{
                                         |  "name": {
                                         |      "salutation": "Mr.",
                                         |      "fname": "Hans",
                                         |      "lname": "Sarpei"
                                         |  },
                                         |  "address": {
                                         |      "street": "Mustrstr.",
                                         |      "houseNo": "12",
                                         |      "zip": "12345",
                                         |      "city": "Musterhausen",
                                         |      "country": "germany"
                                         |  }
                                         |}""".stripMargin('|')))) // 9
    .pause(2) // 10

  setUp( // 11
//    scn.inject(rampUsers(100) over (10 seconds)) // 12
    scn.inject(
//      nothingFor(4 seconds), // 1
//      atOnceUsers(10), // 2
//      rampUsers(10) over(5 seconds), // 3
//      rampUsers(800) over(15 seconds), // 4
      constantUsersPerSec(800) during(10 seconds) // 4
//      constantUsersPerSec(20) during(15 seconds) randomized, // 5
//      rampUsersPerSec(10) to(20) during(10 minutes), // 6
//      rampUsersPerSec(10) to(20) during(10 minutes) randomized, // 7
//      splitUsers(1000) into(rampUsers(10) over(10 seconds)) separatedBy(10 seconds), // 8
//      splitUsers(1000) into(rampUsers(10) over(10 seconds)) separatedBy(atOnceUsers(30)), // 9
//      heavisideUsers(1000) over(20 seconds) // 10
    )
  ).protocols(httpConf) // 13

}
