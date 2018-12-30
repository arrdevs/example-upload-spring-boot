package server.demo.pdxsis.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import server.demo.pdxsis.entity.ObjectMapDescription;
import server.demo.pdxsis.entity.UploadFileResponse;
import server.demo.pdxsis.service.FileStorageService;
import server.demo.pdxsis.service.ObjectLocationService;

@Controller
@RequestMapping("/photo")
public class MyControllerApi {

	@Autowired
	FileStorageService fileStorageService;
	
	@Autowired
	ObjectLocationService objectLocationService;
	
	@RequestMapping
	public String index(Model model, @PageableDefault(size = 10) Pageable pageable) {
		Page<ObjectMapDescription> page = objectLocationService.findAll(pageable);
		model.addAttribute("page", page);
		model.addAttribute("currentPage",page);
		return "view-photo";
	}
		
	@PostMapping("/uploadFile")
	@ResponseBody
    public UploadFileResponse uploadFile(@RequestParam("file") MultipartFile file, 
    				@RequestParam("country") String country,
    				@RequestParam("state") String state,
    				@RequestParam("city") String city,
    				@RequestParam("postalCode") String postalCode
    		) {
        String fileName = fileStorageService.storeFile(file);
        String pathImageFile = "/photo/downloadFile/";
        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(pathImageFile)
                .path(fileName)
                .toUriString();

        ObjectMapDescription omd = new ObjectMapDescription();
        omd.setCity(city.replaceAll("^\"|\"$", ""));
        omd.setCountry(country.replaceAll("^\"|\"$", ""));
        omd.setPostalCode(postalCode.replaceAll("^\"|\"$", ""));
        omd.setState(state.replaceAll("^\"|\"$", ""));
        omd.setImageUrl(pathImageFile+fileName);
        
        objectLocationService.save(omd);
        return new UploadFileResponse(fileName, fileDownloadUri,
                file.getContentType(), file.getSize());
    }

	@GetMapping("/downloadFile/{fileName:.+}")
	@ResponseBody
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) {
        // Load file as Resource
		System.out.println("working koq");
        Resource resource = fileStorageService.loadFileAsResource(fileName);

        // Try to determine file's content type
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            System.out.println("not valid file");
        }

        // Fallback to the default content type if type could not be determined
        if(contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
}
