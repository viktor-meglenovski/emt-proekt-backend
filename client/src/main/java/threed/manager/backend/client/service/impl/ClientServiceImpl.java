package threed.manager.backend.client.service.impl;

import org.springframework.stereotype.Service;
import threed.manager.backend.client.domain.models.Client;
import threed.manager.backend.client.domain.repository.ClientRepository;
import threed.manager.backend.client.service.ClientService;

import java.util.List;

@Service
public class ClientServiceImpl implements ClientService {
    private final ClientRepository clientRepository;

    public ClientServiceImpl(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public List<Client> findAll() {
        return clientRepository.findAll();
    }

    @Override
    public Client findByEmail(String email) {
        return clientRepository.findByEmail(email);
    }

    @Override
    public Client rateByEmail(String email, int grade) {
        Client client=findByEmail(email);
        client.rateClient(grade);
        clientRepository.save(client);
        return client;
    }

    @Override
    public Client addNewClient(String email, String name, String surname) {
        Client newClient=new Client(email,name,surname);
        clientRepository.save(newClient);
        return newClient;
    }

    @Override
    public Client editClient(String email, String name, String surname) {
        Client client=findByEmail(email);
        client.editNameAndSurname(name,surname);
        clientRepository.save(client);
        return client;
    }

    @Override
    public Client editClientCompanyName(String email, String company) {
        Client client=findByEmail(email);
        client.updateCompanyName(company);
        clientRepository.save(client);
        return client;
    }
}
