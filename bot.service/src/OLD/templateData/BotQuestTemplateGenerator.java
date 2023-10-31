package com.zinoviev.questbot.OLD.templateData;

import com.zinoviev.sandbox.bot.entity.SharedQuest;
import com.zinoviev.sandbox.bot.entity.models.quest.BotGeoPoint;
import com.zinoviev.sandbox.bot.entity.models.quest.BotQuest;
import com.zinoviev.sandbox.bot.entity.models.quest.BotQuestNode;

import java.util.*;


public class BotQuestTemplateGenerator {

    private static final Random RANDOM = new Random();

    public static List<BotQuest> getQuestList(long userId, int questCount){
        BotQuestTemplateGenerator generator = new BotQuestTemplateGenerator();

        List<BotQuest> quests = new ArrayList<>();

        for (int i = 0; i < questCount; i++) {
            quests.add(generator.getNewQuest(userId));
        }

        return quests;
    }

    private BotQuest getNewQuest(long userId){
        BotQuest quest = new BotQuest();
        quest.setAuthor(userId);
        quest.setName(getRandomWords(RANDOM.nextInt(8)+1));
        quest.setShared(SharedQuest.PUBLIC);
        quest.setCost(0);


        int x = RANDOM.nextInt(100)+10;
        for (int i = 0; i < x; i++) {
            BotQuestNode node = new BotQuestNode();

            /////////////
            setNodeMessages(node);
            /////////////
            node.setStageName(getRandomWords(RANDOM.nextInt(8)+1));
            /////////////
            setAnswers(node);
            /////////////
            setReactOnIncorrectAnswer(node);
            /////////////
            setReactOnCorrectAnswer(node);
            /////////////
            setGeoPoint(node);
            /////////////
          quest.addQuestPart(node);
        }
       return quest;
    }

    private void setNodeMessages(BotQuestNode node){
        var tempMessages = new LinkedHashMap<String, Integer>();
        int messCount = RANDOM.nextInt(5)+1;
        for (int j = 0; j < messCount; j++) {
            tempMessages.put(getRandomWords(RANDOM.nextInt(25)+1), RANDOM.nextInt(5000));
        }
        node.setNodeMessages(tempMessages);
        System.out.println("Messages------------------------------------------------------------");
        tempMessages.forEach((s, integer) -> System.out.println(s));
    }

    private void setAnswers(BotQuestNode node){
        int yesNo = RANDOM.nextInt(10);
        if (yesNo <= 5) {
            HashSet<String> answers = new HashSet<>();
            int xx = RANDOM.nextInt(20)+3;
            for (int j = 0; j < xx; j++) {
                answers.add(getRandomWords(RANDOM.nextInt(3)+1));
            }
            node.setExpectedUserAnswers(answers);
            node.setRequiredNumberOfAnswers(RANDOM.nextInt(3)+1);
        } else {
            node.setExpectedUserAnswers(null);
            node.setRequiredNumberOfAnswers(0);
        }
        System.out.println("answers------------------------------------------------------------");
        System.out.println(node.getRequiredNumberOfAnswers());
        if (node.getRequiredNumberOfAnswers() > 0){
            node.getExpectedUserAnswers().forEach(System.out::println);
        }
    }
    private void setReactOnIncorrectAnswer(BotQuestNode node){
        if (node.getRequiredNumberOfAnswers() == 0){
            return;
        }
        int yesNo = RANDOM.nextInt(10);
        if (yesNo <=5) {
            List<String> react = new ArrayList<>();
            int xx = RANDOM.nextInt(15) + 1;
            for (int j = 0; j < xx; j++) {
                react.add(getRandomWords(RANDOM.nextInt(3) + 1));
            }
            node.setReactOnIncorrectAnswerMessages(true);
            node.setIncorrectAnswerReactMessages(react);
        }else {
            node.setReactOnIncorrectAnswerMessages(false);
        }
        System.out.println("incorrect answers react------------------------------------------------------------");
        System.out.println(node.isReactOnIncorrectAnswerMessages());
        if (node.isReactOnIncorrectAnswerMessages()) {
            node.getIncorrectAnswerReactMessages().forEach(System.out::println);
        }
    }

    private void setReactOnCorrectAnswer(BotQuestNode node){
        if (node.getRequiredNumberOfAnswers() == 0){
            return;
        }

        int yesNo = RANDOM.nextInt(10);
        if (yesNo <=5) {
            List<String> react = new ArrayList<>();
            int xx = RANDOM.nextInt(15) + 1;
            for (int j = 0; j < xx; j++) {
                react.add(getRandomWords(RANDOM.nextInt(3) + 1));
            }

            node.setReactOnCorrectAnswerMessages(true);
            node.setCorrectAnswerReactMessages(react);
        } else {
            node.setReactOnCorrectAnswerMessages(false);
        }
        System.out.println("Correct answers react------------------------------------------------------------");
        System.out.println(node.isReactOnCorrectAnswerMessages());
        if (node.isReactOnCorrectAnswerMessages()) {
            node.getCorrectAnswerReactMessages().forEach(System.out::println);
        }
    }

    private void setGeoPoint(BotQuestNode node){
        int yesNo = RANDOM.nextInt(10);

        if (node.getRequiredNumberOfAnswers() == 0 || yesNo <= 3) {
            BotGeoPoint botGeoPoint = new BotGeoPoint();
            botGeoPoint.setLatitude(56.856230945260826);
            botGeoPoint.setLongitude(60.6403056750622);

            botGeoPoint.setGeoPointRadius(RANDOM.nextInt(25)+10);
            LinkedHashMap<String, Integer> mess = new LinkedHashMap<>();
            int xx = RANDOM.nextInt(5) + 1;
            for (int j = 0; j < xx; j++) {
                //mess.put(getRandomWords(RANDOM.nextInt(15) + 1), 0);
                mess.put("точка достигнута ", 0);
            }
            botGeoPoint.setOnPointMessages(mess);


            botGeoPoint.setGeoPointMeddleRadius(RANDOM.nextInt(botGeoPoint.getGeoPointRadius() + 300)+ botGeoPoint.getGeoPointRadius()+50);
            ///////////////////////////////////////////
            mess = new LinkedHashMap<>();
            xx = RANDOM.nextInt(5) + 1;
            for (int j = 0; j < xx; j++) {
                //mess.put(getRandomWords(RANDOM.nextInt(15) + 1), 0);
                mess.put("внутренний радиус достигнут ", 0);
            }
            botGeoPoint.setMiddleRadiusReachedMessages(mess);


            botGeoPoint.setGeoPointOuterRadius(RANDOM.nextInt(botGeoPoint.getGeoPointMeddleRadius() + 300) + botGeoPoint.getGeoPointMeddleRadius()+50);
            ///////////////////////////////////////////
            mess = new LinkedHashMap<>();
            xx = RANDOM.nextInt(5) + 1;
            for (int j = 0; j < xx; j++) {
                //mess.put(getRandomWords(RANDOM.nextInt(15) + 1), 0);
                mess.put("внешний радиус достигнут ", 0);
            }
            botGeoPoint.setOuterRadiusReachedMessages(mess);


            ///////////////////////////////////////////
            node.setGeolocationPoint(botGeoPoint);

            node.setOnReachedMainPointSwitchToNextNode(node.getExpectedUserAnswers() == null);
            node.setPauseInSecBeforeSwitchingToTheNextNode(5);
        } else {
            node.setGeolocationPoint(null);
            node.setOnReachedMainPointSwitchToNextNode(false);
            node.setPauseInSecBeforeSwitchingToTheNextNode(0);
        }
        System.out.println(node.getGeolocationPoint());
    }

    private String[] WORDS = {"Сидя", "на", "склоне", "зеленого", "поросшего", "травой", "и", "редким", "кустарником", "холма", "Нари", "наблюдал", "за", "облаками", "медленно", "надвигающимися", "на", "поселок", "со", "стороны", "гор", "далеко", "на", "западе", "Сегодня", "был", "один", "из", "тех", "редких", "дней", "когда", "не", "надо", "было", "пасти", "скот", "хлопотать", "по", "хозяйству", "или", "учиться", "у", "отца", "кузнечному", "делу", "Сегодня", "он", "мог", "просто", "насладиться", "жизнью", "так", "-", "как", "это", "делали", "все", "остальные", "дети", "в", "поселке", "Те", "-", "кто", "мог", "в", "полной", "мере", "быть", "ребенком", "а", "не", "довольствоваться", "жалкими", "днями", "вырванными", "из", "серой", "рутины", "обязанностей", "и", "дел", "навалившихся", "с", "раннего", "детства", "Он", "вновь", "попытался", "вспомнить", "лицо", "матери", "Она", "умерла", "всего", "несколько", "лет", "назад", "и", "он", "ненавидел", "себя", "за", "то", "что", "черты", "ее", "лица", "ускользали", "из", "памяти", "все", "больше", "и", "больше", "Помотав", "головой", "и", "отогнав", "наползающие", "грустные", "мысли", "он", "заставил", "себя", "подняться", "и", "отряхнувшись", "неторопливо", "стал", "спускаться", "к", "подножию", "холма", "где", "находился", "его", "родной", "поселок", "Голод", "все", "сильнее", "начинал", "жечь", "желудок", "принося", "ощущение", "легкой", "тошноты", "и", "беспокойства", "Ему", "хотелось", "по", "скорее", "отдать", "весь", "улов", "отцу", "что", "бы", "тот", "сварил", "свою", "знаменитую", "уху", "повторить", "которую", "Нари", "ни", "как", "не", "удавалось", "хоть", "он", "и", "старался", "делать", "все", "в", "точности", "как", "он", "Нари", "был", "единственным", "ребенком", "в", "семье", "живя", "с", "приемными", "родителями", "о", "чем", "сам", "даже", "не", "догадывался", "Он", "остался", "один", "в", "возрасте", "пяти", "месяцев", "Его", "настоящие", "родители", "пропали", "во", "время", "поездки", "в", "городок", "находящийся", "всего", "в", "десяти", "километрах", "от", "поселка", "В", "тот", "роковой", "день", "ничего", "с", "виду", "не", "предвещавшие", "легкие", "облака", "на", "горизонте", "стали", "быстро", "заволакивать", "все", "небо", "превратившись", "в", "мрачные", "серые", "тучи", "а", "вскоре", "началась", "такая", "чудовищная", "гроза", "какой", "невидовали", "в", "этих", "краях", "уже", "очень", "много", "лет", "Молнии", "сверкали", "одна", "за", "одной", "а", "ветер", "достигал", "такой", "силы", "что", "срывал", "крыши", "с", "домов", "словно", "то", "были", "аккуратно", "уложенные", "бумажные", "листы", "Люди", "попрятались", "по", "своим", "домам", "и", "подвалам", "усердно", "молясь", "о", "помощи", "свыше", "но", "боги", "в", "тот", "день", "услышали", "далеко", "не", "всех", "После", "того", "как", "все", "стихло", "глазам", "людей", "предстала", "ужасная", "картина:", "многие", "деревья", "были", "поломаны", "вырваны", "с", "корнем", "больше", "половины", "домов", "лишилась", "крыш", "а", "некоторые", "из", "них", "и", "вовсе", "были", "полностью", "разрушены", "Арис", "-", "родной", "брат", "отца", "мальчика", "и", "его", "жена", "Кари", "присматривающие", "за", "малышом", "в", "тот", "роковой", "день", "сами", "лишились", "большой", "части", "крыши", "дома", "и", "части", "сарая", "со", "скотом", "хотя", "сами", "животные", "не", "смотря", "на", "это", "чудом", "остались", "целы", "Когда", "настоящие", "родители", "Нари", "не", "вернулись", "ни", "в", "этот", "ни", "на", "следующий", "ни", "на", "утро", "третьего", "дня", "Арис", "и", "Кари", "забили", "тревогу", "В", "надежде", "найти", "своего", "брата", "и", "его", "жену", "Арис", "отправился", "по", "единственной", "дороге", "ведущей", "к", "городу", "по", "направлению", "к", "которому", "те", "уехали", "тремя", "днями", "ранее", "Дорога", "была", "в", "ужасном", "состоянии", "На", "ней", "то", "тут", "то", "там", "лежали", "перегораживая", "проезд", "огромные", "ветви", "деревьев", "а", "сама", "она", "превратилась", "в", "труднопроходимую", "грязь", "скользкую", "достигавшую", "в", "некоторых", "местах", "глубины", "в", "десяток", "с", "лишним", "сантиметров", "Страхи", "Ариса", "многократно", "усилились", "когда", "у", "обочины", "под", "поломанным", "обгоревшим", "деревом", "он", "нашел", "вещи", "принадлежавшие", "жене", "брата", "а", "спустя", "всего", "пару", "минут", "в", "том", "же", "месте", "обнаружил", "амулет", "который", "тот", "всегда", "носил", "при", "себе", "Это", "был", "небольшой", "кусок", "янтаря", "на", "черной", "прочной", "веревочке", "Еще", "в", "детстве", "они", "с", "братом", "выбрали", "два", "одинаковых", "камня", "и", "носили", "на", "шее", "в", "знак", "родства", "братства", "и", "нерушимой", "дружбы", "Теперь", "камень", "висел", "на", "обломанной", "ветви", "в", "полуметре", "от", "земли", "вместе", "с", "большим", "лоскутом", "ткани", "оторванной", "без", "сомнения", "от", "рубахи", "его", "брата", "Все", "знали", "что", "в", "этих", "местах", "водились", "волки", "медведи", "и", "другие", "крупные", "хищники", "но", "если", "бы", "это", "было", "нападение", "животных", "или", "людей", "то", "должны", "были", "бы", "остаться", "хоть", "какие-то", "следы", "но", "кроме", "вещей", "амулета", "и", "лоскута", "ткани", "на", "дереве", "-", "ничего", "найдено", "не", "было", "Родители", "Нари", "словно", "испарились", "Именно", "тогда", "Арис", "понял", "что", "его", "брат", "уже", "вряд", "ли", "вернется", "домой", "Вместе", "с", "Кари", "они", "взяли", "малыша", "себе", "начав", "заботится", "о", "нем", "так", "-", "как", "заботились", "бы", "о", "своем", "собственном", "И", "дали", "друг-другу", "слово:", "никогда", "не", "говорить", "мальчику", "о", "том", "что", "случилось", "с", "его", "настоящими", "родителями"};
    private String getRandomWord() {
        return WORDS[RANDOM.nextInt(WORDS.length)];
    }
    private String getRandomWords(int wordCount){
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < wordCount; i++) {
            sb.append(getRandomWord()).append(" ");
        }
            return sb.toString();
        }
}
