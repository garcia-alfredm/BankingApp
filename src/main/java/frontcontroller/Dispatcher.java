package frontcontroller;

import controllers.Controller;
import io.javalin.Javalin;

import static io.javalin.apibuilder.ApiBuilder.*;

public class Dispatcher {
    Controller controller = new Controller();

    public Dispatcher(Javalin app){
        //clients
        /*app.get("/clients", controller::getAllClients);
        app.post("/clients", controller::createClient);
        app.get("/clients/{id}", controller::getOneClient);
        app.put("/clients/{id}", controller::updateClient);
        //todo finish
        app.delete("/clients/{id}", controller::deleteClient);
        //would never use getAllAccounts
        app.get("/clients/{id}/accounts", controller::getClientAccounts);
        //body requires fk and balance
        app.post("/clients/{id}/accounts", controller::createAccount);
        app.get("/clients/{id}/accounts/{accid}", controller::getOneAccount);
        app.put("/clients/{id}/accounts/{accid}", controller::updateAccount);
        app.delete("/clients/{id}/accounts/{accid}", controller::deleteAccount);
        app.patch("/clients/{id}/accounts/{accid}", controller::function1);
        app.patch("/clients/{id}/accounts/{accid}/transfer/{acc2}", controller::transfer);*/


        app.routes(() -> {
            path("clients", () -> {
                get(controller::getAllClients); //get all clients
                post(controller::createClient); //create client
                path("{id}", () -> {
                    get(controller::getOneClient); //get one client
                    put(controller::updateClient); //update client
                    delete(controller::deleteClient); //delete client
                    path("accounts", ()-> {
                        get(controller::getClientAccounts); //get all client accounts
                        post(controller::createAccount); //create new account
                        path("{accid}", () -> {
                            get(controller::getOneAccount); //get one account
                            put(controller::updateAccount); //update one accout
                            delete(controller::deleteAccount); //delete one account
                            patch(controller::function1);  //perform withdraw/deposit
                            path("transfer", () ->{
                                path("{acc2}", () -> {
                                    patch(controller::transfer); //transfer from one account to another
                                });
                            });
                        });
                    });
                });
            });
        });
    }
}
