package com.zinoviev.data.mapping;

import com.zinoviev.entity.data.runnning.quest.Player;
import com.zinoviev.entity.dto.data.running.quest.PlayerDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PlayerMapper {

    PlayerMapper INSTANCE = Mappers.getMapper(PlayerMapper.class);

    PlayerDto playerToPlayerDto(Player player);
    Player playerDtoToPlayer(PlayerDto playerDTO);
}
