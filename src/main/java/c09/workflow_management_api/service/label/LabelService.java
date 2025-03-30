package c09.workflow_management_api.service.label;

import c09.workflow_management_api.model.Board;
import c09.workflow_management_api.model.Label;
import c09.workflow_management_api.model.composite.LabelId;
import c09.workflow_management_api.model.dto.LabelDTO;
import c09.workflow_management_api.model.type.EColor;
import c09.workflow_management_api.repository.IBoardRepository;
import c09.workflow_management_api.repository.ILabelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LabelService implements ILabelService {

    @Autowired
    private ILabelRepository labelRepository;

    @Autowired
    private IBoardRepository boardRepository;

    private static final Map<EColor, String> COLOR_MAP = Map.of(
            EColor.RED, "#FF0000",
            EColor.YELLOW, "#FFFF00",
            EColor.GREEN, "#28A745",
            EColor.BLUE, "#007BFF",
            EColor.PURPLE, "#6F42C1",
            EColor.PINK, "#E83E8C",
            EColor.ORANGE, "#FD7E14"
    );

    @Override
    public List<LabelDTO> findByBoardId(Long boardId) {
        List<Label> labels = labelRepository.findByBoardId(boardId);
        return labels.stream()
                .map(label -> new LabelDTO(
                        label.getId().getBoardId(),  // Lấy boardId
                        COLOR_MAP.get(label.getId().getColor()), // Lấy màu từ COLOR_MAP
                        label.getTitle() // Lấy tiêu đề
                ))
                .collect(Collectors.toList());
    }

    @Override
    public List<Label> findAll() {
        return labelRepository.findAll();
    }

    @Override
    public Optional<Label> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public void save(Label label) {
        labelRepository.save(label);
    }
    

    @Override
    public Label createLabel(LabelDTO labelDTO, Long boardId) {
        if (boardId == null) {
            throw new RuntimeException(" Board ID must not be null!");
        }

        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new RuntimeException(" Board not found with ID: " + boardId));

        EColor color;
        try {
            color = EColor.valueOf(labelDTO.getColor().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid color value: " + labelDTO.getColor());
        }

        // Kiểm tra xem label có trùng không
        LabelId labelId = new LabelId(boardId, color);
        if (labelRepository.existsById(labelId)) {
            throw new RuntimeException(" Label with color " + color + " already exists in board " + boardId);
        }

        Label label = new Label();
        label.setId(labelId);
        label.setBoard(board);
        label.setTitle(labelDTO.getTitle());
        return labelRepository.save(label);
    }



    @Override
    public void deleteById(Long id) {
        labelRepository.deleteById(new LabelId());
    }
}
