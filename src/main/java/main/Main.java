package main;

import dao.AccountsDaoImpl;
import dao.ClientsDao;
import dao.ClientsDaoImpl;
import frontcontroller.FrontController;
import io.javalin.Javalin;
import models.Accounts;
import models.Clients;
import services.AccountsService;
import services.ClientsService;

public class Main {
    public static void main(String[] args) {

        Javalin app = Javalin.create().start(7000);
        new FrontController(app);
    }
}
