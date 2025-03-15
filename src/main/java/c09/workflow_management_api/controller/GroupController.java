package c09.workflow_management_api.controller;

import c09.workflow_management_api.model.Group;
import c09.workflow_management_api.model.dto.GroupDTO;
import c09.workflow_management_api.service.group.IGroupService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<?> getGroupList() {
        List<Group> groupList = groupService.findAll();
        List<GroupDTO> groupDTOList = groupList.stream().map(GroupDTO::new).toList();
        return new ResponseEntity<>(groupDTOList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getGroupById(@PathVariable Long id) {
        Optional<Group> group = groupService.findById(id);
        if (group.isEmpty()) {
            return new ResponseEntity<>("Nhóm không tồn tại", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new GroupDTO(group.get()), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<?> addGroup(@RequestBody Group group) {
        group.setCreated_at(LocalDateTime.now());
        groupService.save(group);
        return new ResponseEntity<>(new GroupDTO(group), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateGroup(@RequestBody Group group, @PathVariable Long id) {
        Optional<Group> groupOptional = groupService.findById(id);
        if (groupOptional.isEmpty()) {
            return new ResponseEntity<>("Nhóm không tồn tại", HttpStatus.NOT_FOUND);
        }
        group.setId(id);
        group.setCreated_at(groupOptional.get().getCreated_at());
        group.setCreated_by(groupOptional.get().getCreated_by());
        groupService.save(group);
        return new ResponseEntity<>(new GroupDTO(group), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteGroup(@PathVariable Long id) {
        if (groupService.findById(id).isEmpty()) {
            return new ResponseEntity<>("Nhóm không tồn tại", HttpStatus.NOT_FOUND);
        }
        groupService.deleteById(id);
        return new ResponseEntity<>(id, HttpStatus.OK);
    }
}
