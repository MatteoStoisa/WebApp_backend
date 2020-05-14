package it.polito.ai.laboratorio2.dtos;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class TeamProposal {
    @NotBlank
    @NotNull
    @NotEmpty
    String teamName;
    @NotNull
    List<String> memberIds;
}
