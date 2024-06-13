package fontys.sem3.school.business.impl;

import fontys.sem3.school.domain.Player;
import fontys.sem3.school.repository.entity.PlayerEntity;

public class PlayerConverter {
    private PlayerConverter() {
    }

    public static Player convert(PlayerEntity player) {
        return Player.builder()
                .id(player.getId())
                .license(player.getLicense())
                .name(player.getName())
                .country(CountryConverter.convert(player.getCountry()))
                .build();
    }
}
