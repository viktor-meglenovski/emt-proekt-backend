package threed.manager.backend.client.service;

import threed.manager.backend.client.domain.models.Client;

import java.util.List;

public interface ClientService {

    List<Client> findAll();
    Client findByEmail(String email);
    Client rateByEmail(String email, int grade);
    Client addNewClient(String email, String name, String surname);
    Client editClient(String email, String name, String surname);
    Client editClientCompanyName(String email, String company);
}
