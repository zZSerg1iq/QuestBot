package com.zinoviev.data.service.impl;


import com.zinoviev.data.mapping.PlayerMapper;
import com.zinoviev.data.mapping.UserMapper;
import com.zinoviev.data.repository.ActiveQuestRepository;
import com.zinoviev.data.repository.PlayerRepository;
import com.zinoviev.data.repository.UserRepository;
import com.zinoviev.data.service.UserRepositoryService;
import com.zinoviev.entity.data.User;
import com.zinoviev.entity.data.runnning.quest.ActiveQuest;
import com.zinoviev.entity.data.runnning.quest.Player;
import com.zinoviev.entity.dto.data.UserDto;
import com.zinoviev.entity.dto.update.UpdateDto;
import com.zinoviev.entity.dto.update.include.MessageDto;
import com.zinoviev.entity.enums.SignInStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserRepositoryServiceImpl implements UserRepositoryService {

    private final UserRepository userRepository;
    private final ActiveQuestRepository activeQuestRepository;
    private final PlayerRepository playerRepository;

    @Autowired
    public UserRepositoryServiceImpl(UserRepository userRepository, ActiveQuestRepository activeQuestRepository, PlayerRepository playerRepository) {
        this.userRepository = userRepository;
        this.activeQuestRepository = activeQuestRepository;
        this.playerRepository = playerRepository;
    }

    @Override
    public UserDto getUserDataByTelegramId(UpdateDto updateDto) {
        User user = userRepository.getUserByTelegramId(updateDto.getMessageDto().getUserId());

/*        if (user == null) {
            user = saveNewUser(updateDto.getMessageDto());
        } else if (user.getPlayer() != null){
            System.out.println("PLAYER");
        }*/


        return UserMapper.INSTANCE.userToUserDto(user);
    }

    @Override
    public void saveUser(UserDto userDTO) {
        System.out.println("SAVE USER");

        User user = UserMapper.INSTANCE.userDtoToUser(userDTO);

        System.out.println(user);

/*        if (userDTO.getPlayer() != null){
            ActiveQuest activeQuest = ActiveQuestMapper.INSTANCE.activeQuestDTOToActiveQuest(userDTO.getPlayer().getActiveQuest());
            Player player = PlayerMapper.INSTANCE.playerDTOToPlayer(userDTO.getPlayer());
            player.setActiveQuest(activeQuest);
        }*/

        userRepository.save(user);
    }

    private User saveNewUser(MessageDto messageDto){
        User user = new User();
        user.setTelegramId(messageDto.getUserId());
        user.setTelegramName(messageDto.getFirstName());
        user.setSignInStatus(SignInStatus.SIGN_UP_NONE);
        return userRepository.save(user);
    }

    private Player getPlayerData(User user){
        Player player = user.getPlayer();
        ActiveQuest activeQuest = player.getActiveQuest();
        return player;
    }




}
