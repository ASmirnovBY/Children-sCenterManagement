package com.smirnovsoft.centerclientapp.data;

public enum Position {
    ArtTeacher("Art teacher"),
    MusicTeacher("Music teacher"),
    DanceTeacher("Dance teacher"),
    EnglishTeacher("English teacher"),
    SubstituteTeacher("Substitute teacher");

   private String strPosition;


   private Position(String strPosition) {
        this.strPosition = strPosition;
    }

    public String getStrPosition() {
        return strPosition;
    }
}
