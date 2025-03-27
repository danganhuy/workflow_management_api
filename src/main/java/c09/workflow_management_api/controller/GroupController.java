package c09.workflow_management_api.controller;

import c09.workflow_management_api.model.Group;
import c09.workflow_management_api.model.User;
import c09.workflow_management_api.model.dto.GroupDTO;
import c09.workflow_management_api.service.group.IGroupService;
import c09.workflow_management_api.util.RequestHandler;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
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
    public ResponseEntity<?> getGroupListByUser(HttpServletRequest request) {
        User user = RequestHandler.getUser(request);
        List<Group> groupList = groupService.findAllByUser(user);
        List<GroupDTO> groupDTOList = groupList.stream().map(GroupDTO::new).toList();
        return new ResponseEntity<>(groupDTOList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getGroupById(@PathVariable Long id, HttpServletRequest request) {
        try {
            User user = RequestHandler.getUser(request);
            Group group = groupService.findByIdAndUser(id, user);
            return new ResponseEntity<>(new GroupDTO(group), HttpStatus.OK);
        } catch (ResponseStatusException e) {
            return new ResponseEntity<>(e.getMessage(), e.getStatusCode());
        }
    }

    @PostMapping()
    public ResponseEntity<?> addGroup(@RequestBody Group group, HttpServletRequest request) {
        User user = RequestHandler.getUser(request);
        group.setId(null);
        group.setCreated_by(user.getId());
        group.setCreated_at(LocalDateTime.now());
        groupService.save(group);
        return new ResponseEntity<>(new GroupDTO(group), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateGroup(@RequestBody Group group, @PathVariable Long id, HttpServletRequest request) {
        User requester = RequestHandler.getUser(request);
        Optional<Group> groupOptional = groupService.findById(id);
        if (groupOptional.isEmpty()) {
            return new ResponseEntity<>("Nhóm không tồn tại", HttpStatus.NOT_FOUND);
        }
        group.setId(id);
        group.setCreated_at(groupOptional.get().getCreated_at());
        group.setCreated_by(groupOptional.get().getCreated_by());
        groupService.save(group, requester);
        return new ResponseEntity<>(new GroupDTO(group), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteGroup(@PathVariable Long id) {
        groupService.deleteById(id);
        return new ResponseEntity<>(id, HttpStatus.OK);
    }
}
