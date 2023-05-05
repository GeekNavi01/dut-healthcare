package com.example.duth;

public class AppointmentHelperClass {
    String service, visit_reason, symptoms_experienced, blood_type, appDate, appTime;

    public AppointmentHelperClass() {
    }

    public AppointmentHelperClass(String service, String blood_type, String visit_reason, String symptoms_experienced, String appDate, String appTime) {
        this.service = service;
        this.blood_type = blood_type;
        this.visit_reason = visit_reason;
        this.symptoms_experienced = symptoms_experienced;
        this.appDate = appDate;
        this.appTime = appTime;
    }

    public String getAppDate() {
        return appDate;
    }

    public void setAppDate(String appDate) {
        this.appDate = appDate;
    }

    public String getAppTime() {
        return appTime;
    }

    public void setAppTime(String appTime) {
        this.appTime = appTime;
    }

    public String getBlood_type() {
        return blood_type;
    }

    public void setBlood_type(String blood_type) {
        this.blood_type = blood_type;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getVisit_reason() {
        return visit_reason;
    }

    public void setVisit_reason(String visit_reason) {
        this.visit_reason = visit_reason;
    }

    public String getSymptoms_experienced() {
        return symptoms_experienced;
    }

    public void setSymptoms_experienced(String symptoms_experienced) {
        this.symptoms_experienced = symptoms_experienced;
    }
}
