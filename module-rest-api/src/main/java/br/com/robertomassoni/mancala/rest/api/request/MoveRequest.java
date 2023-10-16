package br.com.robertomassoni.mancala.rest.api.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MoveRequest {
    @NotNull
    private String name;
    @NotEmpty
    private String lastName;
}
