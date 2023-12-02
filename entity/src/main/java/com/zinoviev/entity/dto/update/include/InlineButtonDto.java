package com.zinoviev.entity.dto.update.include;

import lombok.Data;

@Data
public class InlineButtonDto {

    private String text;
    private String callbackData;

    public InlineButtonDto(String text, String callbackData) {
        this.text = text;
        this.callbackData = callbackData;
    }

    public static class Builder {

        private String text;
        private String callbackData;


        public Builder() {
        }

        public Builder setText(String text){
            this.text = text;
            return this;
        }

        public Builder setCallbackData(String callbackData){
            this.callbackData = callbackData;
            return this;
        }

        public InlineButtonDto build(){
            return new InlineButtonDto(text, callbackData);
        }


    }


}


