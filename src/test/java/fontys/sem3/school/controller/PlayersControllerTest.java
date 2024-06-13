package fontys.sem3.school.controller;

import fontys.sem3.school.business.*;
import fontys.sem3.school.domain.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class PlayersControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GetPlayerUseCase getPlayerUseCase;
    @MockBean
    private GetPlayersUseCase getPlayersUseCase;
    @MockBean
    private DeletePlayerUseCase deletePlayerUseCase;
    @MockBean
    private CreatePlayerUseCase createPlayerUseCase;
    @MockBean
    private UpdatePlayerUseCase updatePlayerUseCase;

    @Test
    @WithMockUser(username = "10@fontys.nl", roles = {"PLAYER"})
    void getPlayer_shouldReturn200WithPlayer_whenPlayerFound() throws Exception {
        Player player = Player.builder()
                .country(getBrazilDTO())
                .name("Rivaldo Vítor Borba Ferreira")
                .license(222L)
                .id(10L)
                .build();
        when(getPlayerUseCase.getPlayer(10L)).thenReturn(Optional.of(player));

        mockMvc.perform(get("/players/10"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Type", APPLICATION_JSON_VALUE))
                .andExpect(content().json("""
                            {"id":10,"license":222,"name":"Rivaldo Vítor Borba Ferreira","country":{"id":1,"code":"BR","name":"Brazil"}}
                        """));

        verify(getPlayerUseCase).getPlayer(10L);
    }

    @Test
    @WithMockUser(username = "10@fontys.nl", roles = {"PLAYER"})
    void getPlayer_shouldReturn404Error_whenPlayerNotFound() throws Exception {
        when(getPlayerUseCase.getPlayer(10L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/players/10"))
                .andDo(print())
                .andExpect(status().isNotFound());

        verify(getPlayerUseCase).getPlayer(10L);
    }

    @Test
    @WithMockUser(username = "admin@fontys.nl", roles = {"ADMIN"})
    void getAllPlayers_shouldReturn200WithPlayersList_WhenNoFilterProvided() throws Exception {
        GetAllPlayersResponse responseDTO = GetAllPlayersResponse.builder()
                .players(List.of(
                        Player.builder().id(1L).name("Romario").license(111L).country(getBrazilDTO()).build(),
                        Player.builder().id(1L).name("Ronaldo").license(222L).country(getBrazilDTO()).build()
                ))
                .build();
        GetAllPlayersRequest request = GetAllPlayersRequest.builder().build();
        when(getPlayersUseCase.getPlayers(request)).thenReturn(responseDTO);

        mockMvc.perform(get("/players"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Type", APPLICATION_JSON_VALUE))
                .andExpect(content().json("""
                            {
                                "players":[
                                    {"id":1,"license":111,"name":"Romario","country":{"id":1,"code":"BR","name":"Brazil"}},
                                    {"id":1,"license":222,"name":"Ronaldo","country":{"id":1,"code":"BR","name":"Brazil"}}
                                ]
                            }
                        """));

        verify(getPlayersUseCase).getPlayers(request);
    }

    @Test
    @WithMockUser(username = "admin@fontys.nl", roles = {"ADMIN"})
    void getAllPlayers_shouldReturn200WithPlayersList_WhenCountryFilterProvided() throws Exception {
        Country country = Country.builder()
                .code("NL")
                .name("Netherlands")
                .id(2L)
                .build();
        GetAllPlayersResponse responseDTO = GetAllPlayersResponse.builder()
                .players(List.of(
                        Player.builder().id(1L).name("Dennis Bergkamp").license(111L).country(country).build(),
                        Player.builder().id(1L).name("Johan Cruyff").license(222L).country(country).build()
                ))
                .build();
        GetAllPlayersRequest request = GetAllPlayersRequest.builder()
                .countryCode("NL")
                .build();
        when(getPlayersUseCase.getPlayers(request)).thenReturn(responseDTO);

        mockMvc.perform(get("/players").param("country", "NL"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Type", APPLICATION_JSON_VALUE))
                .andExpect(content().json("""
                            {
                                "players":[
                                    {"id":1,"license":111,"name":"Dennis Bergkamp","country":{"id":2,"code":"NL","name":"Netherlands"}},
                                    {"id":1,"license":222,"name":"Johan Cruyff","country":{"id":2,"code":"NL","name":"Netherlands"}}
                                ]
                            }
                        """));

        verify(getPlayersUseCase).getPlayers(request);
    }

    @Test
    @WithMockUser(username = "admin@fontys.nl", roles = {"ADMIN"})
    void deletePlayer_shouldReturn204() throws Exception {
        mockMvc.perform(delete("/players/100"))
                .andDo(print())
                .andExpect(status().isNoContent());

        verify(deletePlayerUseCase).deletePlayer(100L);
    }

    @Test
    void createPlayer_shouldReturn201_whenRequestIsValid() throws Exception {
        CreatePlayerRequest expectedRequest = CreatePlayerRequest.builder()
                .license(222L)
                .password("test123")
                .name("James")
                .countryId(1L)
                .build();
        when(createPlayerUseCase.createPlayer(expectedRequest))
                .thenReturn(CreatePlayerResponse.builder()
                        .playerId(200L)
                        .build());

        mockMvc.perform(post("/players")
                        .contentType(APPLICATION_JSON_VALUE)
                        .content("""
                                {
                                    "license": "222",
                                    "name": "James",
                                    "password": "test123",
                                    "countryId": "1"
                                }
                                """))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().json("""
                            { "playerId":  200 }
                        """));

        verify(createPlayerUseCase).createPlayer(expectedRequest);
    }

    @Test
    @WithMockUser(username = "10@fontys.nl", roles = {"PLAYER"})
    void updatePlayer_shouldReturn204() throws Exception {
        mockMvc.perform(put("/players/100")
                        .contentType(APPLICATION_JSON_VALUE)
                        .content("""
                                {
                                    "name": "James",
                                    "countryId": "1"
                                }
                                """))
                .andDo(print())
                .andExpect(status().isNoContent());

        UpdatePlayerRequest expectedRequest = UpdatePlayerRequest.builder()
                .id(100L)
                .name("James")
                .countryId(1L)
                .build();
        verify(updatePlayerUseCase).updatePlayer(expectedRequest);
    }

    private Country getBrazilDTO() {
        return Country.builder()
                .id(1L)
                .code("BR")
                .name("Brazil")
                .build();
    }
}