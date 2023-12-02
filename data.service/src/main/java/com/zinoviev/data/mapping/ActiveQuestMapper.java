package com.zinoviev.data.mapping;

import com.zinoviev.entity.data.runnning.quest.ActiveQuest;
import com.zinoviev.entity.dto.data.running.quest.ActiveQuestDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ActiveQuestMapper {

    ActiveQuestMapper INSTANCE = Mappers.getMapper(ActiveQuestMapper.class);

    ActiveQuestDto activeQuestToActiveQuestDto(ActiveQuest activeQuest);
    ActiveQuest activeQuestDtoToActiveQuest(ActiveQuestDto activeQuest);
}
