package server;

import handler.TestServerHandler;
import spark.*;

public class Server {

    TestServerHandler tshandler;

    public Server() {
        tshandler = new TestServerHandler();
    }
    public int run(int desiredPort) {
        Spark.port(desiredPort);

        Spark.staticFiles.location("web");

        // Register your endpoints and handle exceptions here.
        //Spark.delete("/db", (req, res) -> tshandler.clearHandler(req, res));
        Spark.delete("/db", tshandler::clearHandler);
        Spark.post("/user", tshandler::registerHandler);
        Spark.post("/session", tshandler::loginHandler);

        Spark.awaitInitialization();
        return Spark.port();
    }

    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }
}
