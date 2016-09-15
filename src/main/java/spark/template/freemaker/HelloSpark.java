package spark.template.freemaker;

import spark.Session;
import spark.Spark;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import static spark.Spark.*;

/**
 * @author Semih Okan Pehlivan
 */
public class HelloSpark {

    private static Logger logger = java.util.logging.Logger.getLogger(HelloSpark.class.getName());

    public static void main(String[] args) {


        get("/hi", (request, response) -> "Hello Spark Framework");

        //response.body returns the string but it is sending the 404 not found
        get("/response", (request, response) -> "Hi " + request.body());

        get("/private", (request, response) -> {
            response.status(401);
            return "Go away";
        });

        get("/get/:name", (request, response) -> "Hi " + request.params("name"));

        get("/users/:section", (request, response) -> {
            response.type("text/xml");
            return "<?xml version=\"1.0\" encoding=\"UTF-8\"?><news>" + request.params("section") + "</news>";
        });

        get("/protected", (request, response) -> {
            halt(403, "i don't think so");
            logger.info("/protected method");
            return null;
        });

        get("/redirect", (request, response) -> {
            response.redirect("/hi");
            return null;
        });

        get("/", (request, response) -> "root");

        before("/book", (request, response) -> response.header("name", "Java for beginners before filter"));

        get("/book", (request, response) -> "Java for beginner");

        after("/book", (request, response) -> response.header("year", "2020 after filter"));

        get("/foo", (request, response) -> {
            request.attribute("foo", "bar");
            return null;
        });

        after("/foo", (request, response) -> {
            for (String attr : request.attributes()) {
                logger.info("attr:" + attr);
            }
        });

        after("/foo", (request, response) -> {
            Object foo = request.attribute("foo");
            response.type("text/xml");
            response.body(asXml("foo", foo));
        });

        get("/json", "application/json",(request, response) -> {
            //response.type("application/json");
            return "{\"message\": \"Hello Spark Json\"}";
        });

        // try with the setting resource files
        Spark.get("/template", (request, response) -> {
            Map<String, Object> attribute = new HashMap<>();
            attribute.put("message", "hello free maker template engine");

            return modelAndView(attribute, "hello.ftl");
        }, new FreeMakerTemplateEngine());

        get("/set-cookie", (request, response) -> {
            String name = "William";
            response.cookie("name", name);
            return "Set the cookie";
        });

        get("/get-cookie", (request, response) -> {
            String cookie = request.cookie("name");
            if (cookie != null) {
                return "Cookie: " + cookie;
            }else{
                return "No cookie now !";
            }
        });

        get("/remove-cookie", (request, response) -> {
            String cookie = request.cookie("name");
            if (cookie != null) {
                response.removeCookie("name");
                logger.info("removed the cookie " + cookie);
                return "Removed the cookie";
            } else {
                return "No cookie";
            }
        });

        //Session
        get("/set-session", (request, response) -> {
            Session session = request.session(true);
            //session.maxInactiveInterval(20);
            return "created the session";
        });

        get("/session-info", (request, response) -> {
            Session session = request.session();
            String sessionID = session.id();
            Long time = session.creationTime();
            Date sessionTime = new Date(time);
            Integer inactivate = session.maxInactiveInterval();

            return String.format("Session id : %s\n Session date: %s\n Inactive: %d\n",
                    sessionID, sessionTime.toString(), inactivate);
        });

        get("/invalidate-session", (request, response) -> {
            request.session().invalidate();
            return "Invalidated the session";
        });

        redirect.get("/from", "/to");

        get("/to", (request, response) -> "to");

        get("/exception", (request, response) -> {
            throw new CustomException("Exception message");
        });

        exception(CustomException.class, (exception, request, response) -> {
            logger.info("Exception is handled");
            response.redirect("/hi");
        });
    }

    private static String asXml(String name, Object value) {
        return "<?xml version=\"1.0\" encoding=\"UTF-8\"?><" + name +">" + value + "</"+ name + ">";
    }
}
