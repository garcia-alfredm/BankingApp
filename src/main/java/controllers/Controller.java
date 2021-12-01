package controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.http.Context;
import models.Accounts;
import models.Clients;
import services.AccountsService;
import services.ClientsService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Controller {
    static ClientsService clientsService = new ClientsService();
    static AccountsService accountsService = new AccountsService();

    // Client Services

    public void getAllClients(Context context) throws JsonProcessingException {
        //sending back data as json
        context.contentType("application/json");

        //get all clients from db
        List<Clients> clientsList = clientsService.getAllClients();
        //use jackson to convert list object to a string
        String jsonString = new ObjectMapper().writeValueAsString(clientsList);

        context.result(jsonString).status(200);
    }

    public void getOneClient(Context context) throws JsonProcessingException {
        //sending data back as json
        context.contentType("application/json");

        Integer clientId = Integer.parseInt(context.pathParam("id"));
        Clients client = clientsService.getOneClient(clientId);

        if(client == null){
            context.status(404);
        } else {
            context.result(new ObjectMapper().writeValueAsString(client));
        }
    }

    public void createClient(Context context){
        //use body to create class obj
        Clients client = context.bodyAsClass(Clients.class);
        //todo check client is created
        clientsService.createClient(client);
        context.result("Client is created").status(201);
    }

    public void updateClient(Context context){
        //requires query param id and body with new name bodyasclass
        Integer clientId = Integer.parseInt(context.pathParam("id"));
        Clients nameChange = context.bodyAsClass(Clients.class);
        clientsService.updateClient(clientId, nameChange.getName());
        context.result("Client updated").status(201);
    }

    public void deleteClient(Context context){
        //todo check client exists
        Integer clientId = Integer.parseInt(context.pathParam("id"));
        //todo check assoc accounts are del
        clientsService.deleteClient(clientId);
        context.result("Client deleted").status(201);
    }

    //Account Services
    public void getAllAccounts(Context context) throws JsonProcessingException {
        context.contentType("application/json");
        List<Accounts> accounts = accountsService.getAllAccounts();
        String jsonString = new ObjectMapper().writeValueAsString(accounts);

        context.result(jsonString).status(200);
    }

    public void getOneAccount(Context context) throws JsonProcessingException {
        context.contentType("application/json");

        Integer clientFkId = Integer.parseInt(context.pathParam("id"));
        Integer accountId = Integer.parseInt(context.pathParam("accid"));
        Accounts account = accountsService.getOneAccount(accountId, clientFkId);
        String jsonString = new ObjectMapper().writeValueAsString(account);

        if(account == null){
            context.status(404);
        }else {
            context.result(jsonString);
        }
    }

    public void createAccount(Context context){
        Integer clientFkId = Integer.parseInt(context.pathParam("id"));
        Accounts account = context.bodyAsClass(Accounts.class);
        account.setClientIdFk(clientFkId);

        accountsService.createAccount(account);
        context.result("Account is created").status(201);
    }

    public void updateAccount(Context context){
        //requires query param id and body with new name bodyasclass
        Integer clientFkId = Integer.parseInt(context.pathParam("id"));
        Integer accountId = Integer.parseInt(context.pathParam("accid"));
        //might need to use body
        Accounts account = context.bodyAsClass(Accounts.class);

        accountsService.updateAccount(accountId, account.getBalance(), clientFkId);

        if(account == null){
            context.status(404);
        }else {
            context.result("Account updated").status(201);
        }
    }

    public void deleteAccount(Context context){
        //requires query param id and body with new name bodyasclass
        Integer accountId = Integer.parseInt(context.pathParam("accid"));
        Integer clientFkId = Integer.parseInt(context.pathParam("id"));

        accountsService.deleteAccount(accountId, clientFkId);
        context.result("Account deleted").status(201);
    }

    public void getClientAccounts(Context context) throws JsonProcessingException {
        context.contentType("application/json");
        Integer accountId = Integer.parseInt(context.pathParam("id"));

        List<Accounts> accounts = new ArrayList<>();
        if(context.queryParam("amountLessThan") != null && context.queryParam("amountGreaterThan") != null){
            Double lessThan = Double.parseDouble(context.queryParam("amountLessThan"));
            Double greaterThan = Double.parseDouble(context.queryParam("amountGreaterThan"));
            //todo verify this works
            accounts = accountsService.getClientAccounts(accountId, lessThan, greaterThan);
        }else{
            accounts = accountsService.getClientAccounts(accountId);
        }

        String jsonString = new ObjectMapper().writeValueAsString(accounts);

        if(accounts == null){
            context.result("No such account").status(404);
        } else{
            context.result(jsonString).status(201);
        }
    }

    public void withdraw(Context context){
        Integer accountId = Integer.parseInt(context.pathParam("id"));
        Integer clientFkId = Integer.parseInt(context.pathParam("client_id_fk"));
        Double amount = context.bodyAsClass(Double.class);

        accountsService.withdraw(accountId, clientFkId, amount);
        context.result("Successful withdraw").status(201);
    }

    public void deposit(Context context){
        Integer accountId = Integer.parseInt(context.pathParam("id"));
        Integer clientFkId = Integer.parseInt(context.pathParam("client_id_fk"));
        Double amount = context.bodyAsClass(Double.class);

        accountsService.deposit(accountId, clientFkId, amount);
        context.result("Successful deposit").status(201);
    }

    public void function1(Context context){
        Integer accountId = Integer.parseInt(context.pathParam("accid"));
        Integer clientFkId = Integer.parseInt(context.pathParam("id"));
        Map<String, Double> body = context.bodyAsClass(Map.class);
        if(body.containsKey("deposit")){
            accountsService.deposit(accountId,clientFkId, body.get("deposit"));
            context.result("Successful deposit").status(201);

        }else if(body.containsKey("withdraw")){
            accountsService.withdraw(accountId, clientFkId, body.get("withdraw"));
            context.result("Successful withdraw").status(201);
        }
    }

    public void transfer(Context context){
        Integer clientId = Integer.parseInt(context.pathParam("id"));
        Integer fromAccId = Integer.parseInt(context.pathParam("accid"));
        Integer toAccId = Integer.parseInt(context.pathParam("acc2"));
        Map<String, Double> body = context.bodyAsClass(Map.class);

        accountsService.transfer(fromAccId, toAccId, clientId, body.get("amount"));
        context.result("Successful transfer").status(201);

    }
}
