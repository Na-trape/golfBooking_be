package fontys.sem3.school.business.impl;

import fontys.sem3.school.business.DeletePlayerUseCase;
import fontys.sem3.school.repository.PlayerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class DeletePlayerUseCaseImpl implements DeletePlayerUseCase {
    private final PlayerRepository playerRepository;

    @Override
    @Transactional
    public void deletePlayer(long playerId) {
        this.playerRepository.deleteById(playerId);
    }
}
