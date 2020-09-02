package be.intecbrussel.schoolsout.view;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CmdMenuItem {
    private Integer id;
    private String header;
    private String content;
    private String description;
    private CmdQuery query;
}
