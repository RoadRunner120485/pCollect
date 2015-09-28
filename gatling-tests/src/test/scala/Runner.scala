import io.gatling.app.Gatling

/**
 * Created by sturmm on 25.09.15.
 */
object Runner extends App {

  Gatling.main(Array[String]("-rf", "build/reports", "-s", "CreateUserSimulation", "-bdf", "src/test/resources/bodies", "-bf", "build/classes/test"))

}
