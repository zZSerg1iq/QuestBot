package com.zinoviev.questbot.OLD.quest_file_validator.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.zinoviev.sandbox.bot.entity.models.quest.BotQuest;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Properties;
import java.util.stream.Collectors;

public class QuestFilesManager {

    public BotQuest readQuestFile(String filename) throws Exception{
        BotQuest quest;

        try (Reader reader = new InputStreamReader(new FileInputStream(filename), StandardCharsets.UTF_8)) {
            Gson gson = new GsonBuilder().create();
            quest = gson.fromJson(reader, BotQuest.class);
        }
        return quest;
    }




    public void buildQuestFile(BotQuest quest, String filename){
        try (Writer writer = new OutputStreamWriter(
                new FileOutputStream(filename), StandardCharsets.UTF_8)) {
            Gson gson = new GsonBuilder().create();
            gson.toJson(quest, writer);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }







    public HashSet<Long> getIdSet(String file, String idDescription){

        Properties props = new Properties();
        String answer;
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8));
            props.load(reader);
            answer = props.getProperty(idDescription, null);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        HashSet<Long> tempId = new HashSet<>();
        if (answer != null && answer.length()>1){
           tempId = Arrays.stream(answer.split(",")).mapToLong(Long::parseLong).boxed().collect(Collectors.toCollection(HashSet::new));
        }

        return tempId;
    }



    public String getProperty(String file, String requestText){
        System.out.println("-------------------------------------------");
        System.out.println(file);
        System.out.println("-------------------------------------------");

        Properties props = new Properties();
        String answer = null;
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8));
            props.load(reader);
            answer = props.getProperty(requestText.toLowerCase(), "");
        } catch (FileNotFoundException ignored) {
            System.out.println("no property file");
        } catch (IOException ignored) {
        }

        return answer;
    }

}
