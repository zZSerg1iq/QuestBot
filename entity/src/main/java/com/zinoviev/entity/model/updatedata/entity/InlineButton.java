package com.zinoviev.entity.model.updatedata.entity;

import lombok.Data;

@Data
public class InlineButton {

    private String text;
    private String callbackData;

    public InlineButton(String text, String callbackData) {
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

        public InlineButton build(){
            return new InlineButton(text, callbackData);
        }


    }


}


