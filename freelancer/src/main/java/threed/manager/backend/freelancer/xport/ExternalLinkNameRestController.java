package threed.manager.backend.freelancer.xport;

import lombok.AllArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import threed.manager.backend.freelancer.domain.models.ExternalLinkName;
import threed.manager.backend.freelancer.service.ExternalLinkNameService;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
@AllArgsConstructor
public class ExternalLinkNameRestController {
    private final ExternalLinkNameService externalLinkNameService;
    @GetMapping("/externalLinkNames")
    public List<ExternalLinkName> getAllExternalLinkNames(){
        return externalLinkNameService.getAll();
    }

    @GetMapping(value = "/file", produces = MediaType.IMAGE_PNG_VALUE)
    public @ResponseBody byte[] getFile(@RequestParam String path) throws IOException {
        File f=new File("freelancer\\src\\main\\resources\\static"+path);
        InputStream inputStream=new FileInputStream(f);
        return IOUtils.toByteArray(inputStream);
    }
}
