package fontys.sem3.school.controller;

import fontys.sem3.school.business.*;
import fontys.sem3.school.domain.*;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/players")
@RequiredArgsConstructor
public class PlayersController {
    private final GetPlayerUseCase getPlayerUseCase;
    private final GetPlayersUseCase getPlayersUseCase;
    private final DeletePlayerUseCase deletePlayerUseCase;
    private final CreatePlayerUseCase createPlayerUseCase;
    private final UpdatePlayerUseCase updatePlayerUseCase;
    private final SearchPlayersUseCase searchPlayersUseCase;

    @GetMapping("{id}")
//    @RolesAllowed({"PLAYER", "ADMIN"})
    public ResponseEntity<Player> getPlayer(@PathVariable(value = "id") final long id) {
        final Optional<Player> playerOptional = getPlayerUseCase.getPlayer(id);
        if (playerOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(playerOptional.get());
    }

    @GetMapping
//    @RolesAllowed({"PLAYER", "ADMIN"})
    public ResponseEntity<GetAllPlayersResponse> getAllPlayers(@RequestParam(value = "country", required = false) String countryCode) {
        GetAllPlayersRequest request = new GetAllPlayersRequest();
        request.setCountryCode(countryCode);
        return ResponseEntity.ok(getPlayersUseCase.getPlayers(request));
    }

    @DeleteMapping("{playerId}")
//    @RolesAllowed({"ADMIN"})
    public ResponseEntity<Void> deletePlayer(@PathVariable int playerId) {
        deletePlayerUseCase.deletePlayer(playerId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping()
    public ResponseEntity<CreatePlayerResponse> createPlayer(@RequestBody @Valid CreatePlayerRequest request) {
        CreatePlayerResponse response = createPlayerUseCase.createPlayer(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("{id}")
    @RolesAllowed({"PLAYER", "ADMIN"})
    public ResponseEntity<Player> updatePlayer(@PathVariable("id") long id, @RequestBody @Valid UpdatePlayerRequest request) {
        request.setId(id);
        updatePlayerUseCase.updatePlayer(request);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/search")
    public ResponseEntity<List<Player>> searchPlayers(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "license", required = false) Long license) {
        List<Player> players = searchPlayersUseCase.searchPlayers(name, license);
        return ResponseEntity.ok(players);
    }
}
