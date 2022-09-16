package threed.manager.backend.freelancer.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import threed.manager.backend.freelancer.domain.models.ExternalLinkName;
import threed.manager.backend.freelancer.domain.repository.ExternalLinkNameRepository;
import threed.manager.backend.freelancer.service.ExternalLinkNameService;

import java.util.List;

@Service
@AllArgsConstructor
public class ExternalLinkNameServiceImpl implements ExternalLinkNameService {
    private final ExternalLinkNameRepository externalLinkNameRepository;
    @Override
    public List<ExternalLinkName> getAll() {
        return externalLinkNameRepository.findAll();
    }
}
