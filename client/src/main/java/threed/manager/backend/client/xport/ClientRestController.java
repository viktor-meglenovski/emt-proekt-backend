package threed.manager.backend.client.xport;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

@RestController("/api/client")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class ClientRestController {
}

