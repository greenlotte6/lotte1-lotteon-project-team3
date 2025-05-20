package kr.co.lotteOn.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryDTO implements Serializable {

    private Long categoryId;
    private String name;
    private Integer depth;
    private Integer sortOrder;
    private String useYN;
    private Long parentId;

    private List<CategoryDTO> children = new ArrayList<>();
}

