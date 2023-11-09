package com.zinoviev.bot.controller.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.zinoviev.bot.controller.BotDataController;
import com.zinoviev.bot.message.handler.ResponseMessageBuilder;
import com.zinoviev.entity.model.UpdateData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.net.*;


@RestController
@RequestMapping("api/bot")
public class SimpleBotDataController implements BotDataController {

    private int this_port = 24001;

    //@Value("${db.service}")
    private String dbLink = "localhost";
    private String dbPort = "24002";

    private final ResponseMessageBuilder responseMessageBuilder;

    @Autowired
    public SimpleBotDataController(ResponseMessageBuilder responseMessageBuilder) {
        this.responseMessageBuilder = responseMessageBuilder;

    }

    @PostMapping("/orch/response")
    public ResponseEntity<String> orchestratorResponse(@RequestBody UpdateData updateData) {
        responseMessageBuilder.buildMessage(updateData);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    public void sendJson(UpdateData updateData) {
        /*ServerSocket serverSocket = null;

        try {
            // Создание серверного сокета и привязка к порту
            serverSocket = new ServerSocket(this_port);
        } catch (IOException e) {
            System.err.println("Could not listen on port: 8080.");
            System.exit(1);
        }

        Socket clientSocket = null;
        System.out.println("Waiting for connection...");

        try {
            // Ожидание подключения клиента
            clientSocket = serverSocket.accept();
            System.out.println("Client connected.");

            // Получение входящего потока данных от клиента
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            // Отправка ответа клиенту
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            out.println("Hello, client!");

            // Чтение данных от клиента
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                System.out.println("Received from client: " + inputLine);
                // Обработка полученных данных
            }

            // Закрытие соединения
            in.close();
            out.close();
        } catch (IOException e) {
            System.err.println("Error handling client connection.");
        } finally {
            // Закрытие сокета сервера
            if (serverSocket != null) {
                serverSocket.close();
            }
        }*/
    }




    @Override
    public void sendUpdateDataToDB(UpdateData updateData) {
        // Создаем заголовки для POST запроса
       /* HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Создаем объект запроса с указанными заголовками и телом
        HttpEntity<UpdateData> requestEntity = new HttpEntity<>(updateData, headers);

        // Отправляем POST запрос на сервер 2
        ResponseEntity<String> response = new RestTemplate()
                .exchange(dbLink, HttpMethod.POST, requestEntity, String.class);

        // Проверяем код ответа
        if(response.getStatusCode().is2xxSuccessful()) {
            System.out.println("Success");
        } else {
            System.out.println("Error");
        }*/

       /* try {
            URL url = new URL(dbLink);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json");

            con.setDoOutput(true);
            DataOutputStream out = new DataOutputStream(con.getOutputStream());

            out.writeBytes(getJsonString(updateData));
            out.flush();
            out.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }*/
    }

    private String getJsonString(UpdateData updateData) {
        return new Gson().toJson(updateData);
    }


}
