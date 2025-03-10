package c09.workflow_management_api.controller;

import c09.workflow_management_api.model.Group;
import c09.workflow_management_api.service.group.IGroupService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/group")
@CrossOrigin("*")
public class GroupController {
    private final IGroupService groupService;

    public GroupController(IGroupService groupService) {
        this.groupService = groupService;
    }

    @GetMapping()
    public ResponseEntity<?> getGroupList() {
        return new ResponseEntity<>(groupService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getGroupById(@PathVariable Long id) {
        Optional<Group> group = groupService.findById(id);
        if (group.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(group.get(), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<?> addGroup(@RequestBody Group group) {
        group.setId(null);
        groupService.save(group);
        return new ResponseEntity<>(group, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateGroup(@RequestBody Group group, @PathVariable Long id) {
        group.setId(id);
        System.out.println(group);
        System.out.println(id);
        if (groupService.findById(id).isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        groupService.save(group);
        return new ResponseEntity<>(group, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteGroup(@PathVariable Long id) {
        if (groupService.findById(id).isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        groupService.deleteById(id);
        return new ResponseEntity<>(id, HttpStatus.OK);
    }
}
