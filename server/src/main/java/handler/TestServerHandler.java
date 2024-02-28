package handler;

import model.AuthData;
import model.UserData;
import service.UserService;
import spark.Request;
import spark.Response;
import com.google.gson.Gson;

public class TestServerHandler {
    public UserService userService = new UserService();
    public Gson gson = new Gson();

    public Object registerHandler(Request req, Response res) {
        UserData user;
        user = gson.fromJson(req.body(), UserData.class);

        try {
            if(userService.userExists(user)) {
                res.status(403);
                return "{ \"message\": \"Error: already taken\" }";
            }
            AuthData authData = userService.register(user);
            res.status(200);
            return gson.toJson(authData);
        } catch (Exception e) {
            System.out.println("Error in register handler!!");
            e.printStackTrace();
            return null;
        }

    }

    public Object loginHandler(Request req, Response res) {
        UserData user;
        user = gson.fromJson(req.body(), UserData.class);

        try {
            if(!userService.userExists(user)) {
                res.status(401);
                return "{ \"message\": \"Error: unauthorized\" }";
            }
            AuthData authData = userService.login(user);
            if(authData == null) {
                res.status(401);
                return "{ \"message\": \"Error: unauthorized\" }";
            }
            res.status(200);
            return gson.toJson(authData);
        } catch (Exception e) {
            System.out.println("Error in login handler!!");
            e.printStackTrace();
            return null;
        }
    }

    public Object logoutHandler(Request req, Response res) {
        return null;
    }

    public Object clearHandler(Request req, Response res) {
        userService.clear();
        res.status(200);
        return "{}";
    }
}
