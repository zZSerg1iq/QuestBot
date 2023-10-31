package com.zinoviev.questbot.OLD.data.entity.quest;


import lombok.Data;

@Entity
@Data
@Table(name = "edited_quest")
public class EditQuest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    //владелец
    @OneToOne
    private QuestOwners questOwner;

    //редактируемый квест
    @OneToOne
    private Quest questId;

    //текущуая нода
    @OneToOne
    private QuestNode currentEditedNode;


    //текущая операция
  //  private EditOperation operation;

    

}
