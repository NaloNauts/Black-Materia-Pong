package com.example.java_pong;

import com.example.java_pong.model.PlayerModel;

public class PlayerModelBuilder {
    private String name;
    private int score;

    public PlayerModelBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public PlayerModelBuilder setScore(int score) {
        this.score = score;
        return this;
    }

    public PlayerModel build() {
        PlayerModel playerModel = new PlayerModel(name);
        playerModel.setScore(score);
        return playerModel;
    }
}
