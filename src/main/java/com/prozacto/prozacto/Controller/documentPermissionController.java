package com.prozacto.prozacto.Controller;

import com.prozacto.prozacto.model.DocumentPermissionDto;
import com.prozacto.prozacto.service.DocumentPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/permission")
public class documentPermissionController {

    @Autowired
    DocumentPermissionService documentPermissionService;

    @PostMapping()
    public DocumentPermissionDto grantAccess(@RequestBody DocumentPermissionDto documentPermissionDto){
        return documentPermissionService.grantAccess(documentPermissionDto);
    }

    @DeleteMapping()
    public String deleteAccess(@RequestBody DocumentPermissionDto documentPermissionDto){
        documentPermissionService.deleteAccess(documentPermissionDto);
        return "Permission Revoked Successfully!";
    }
}
