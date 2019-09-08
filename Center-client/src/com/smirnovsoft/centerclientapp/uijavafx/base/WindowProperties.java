package com.smirnovsoft.centerclientapp.uijavafx.base;

public enum  WindowProperties {

    LoginWindow("Autorization",
                300,
                300,
                "/com/smirnovsoft/centerclientapp/uijavafx/view/login.fxml"),

    MainWindow( "Child's center",
                600,
                600,
                "/com/smirnovsoft/centerclientapp/uijavafx/view/main.fxml"),

    AddEmployeeWindow ("Add Employee",
                    800,
                    600,
                    "/com/smirnovsoft/centerclientapp/uijavafx/view/addemployee.fxml"),

    AddCustomerWindow ("Add Customer",
                               800,
                               600,
                               "/com/smirnovsoft/centerclientapp/uijavafx/view/addcustomer.fxml"),

    AddGroupWindow ("Group formating",
                               1000,
                               600,
            "/com/smirnovsoft/centerclientapp/uijavafx/view/groupinitial.fxml"),

    GroupPropWindow ("Group options",
                            649,
                            365,
                            "/com/smirnovsoft/centerclientapp/uijavafx/view/groupsopt.fxml"),

    GroupsManager("Groups manager",
                                1200,
                                600,
                                "/com/smirnovsoft/centerclientapp/uijavafx/view/groupsmanager.fxml");
/*
    TimeTableWindow("Time table",
                          469,
                          400,
                          "/com/smirnovsoft/centerclientapp/uijavafx/view/timetable.fxml");*/



    private String title;
    private int width;
    private int height;
    private String fxmlLocation;

    WindowProperties(String title, int width, int height, String fxmlLocation) {
        this.title = title;
        this.width = width;
        this.height = height;
        this.fxmlLocation = fxmlLocation;
    }

    public String getTitle() {
        return title;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public String getFxmlLocation() {
        return fxmlLocation;
    }

}
