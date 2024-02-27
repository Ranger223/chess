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
            AuthData authData = userService.register(user);
            return gson.toJson(authData);
        } catch (Exception e) {
            System.out.println("Error in register handler!!");
            e.printStackTrace();
            return null;
        }

    }

    public Object clearHandler(Request req, Response res) {
        userService.clear();
        res.status(200);
        return "{}";
    }
}
