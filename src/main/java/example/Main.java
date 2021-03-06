package example;

import org.ratpackframework.bootstrap.RatpackServer;
import org.ratpackframework.bootstrap.RatpackServerBuilder;
import org.ratpackframework.routing.Handler;

import java.io.File;

import static org.ratpackframework.guice.Injection.handler;
import static org.ratpackframework.routing.Handlers.routes;

public class Main {

    public static void main(String[] args) {
        // Defines the Guice modules for our application
        ModuleBootstrap modulesConfigurer = new ModuleBootstrap();

        // The start of our application routing logic
        Handler appHandler = routes(new RoutingBootstrap());

        // A Handler that makes objects bound to Guice by modules available downstream
        Handler guiceHandler = handler(modulesConfigurer, appHandler);

        // The root of all file system paths in the application (will be src/ratpack)
        File baseDir = new File(System.getProperty("user.dir"));

        // The server builder allows configuring the port etc.
        RatpackServerBuilder ratpackServerBuilder = new RatpackServerBuilder(guiceHandler, baseDir);

        // Start the server and block
        RatpackServer ratpackServer = ratpackServerBuilder.build();
        ratpackServer.startAndWait();
    }
}
