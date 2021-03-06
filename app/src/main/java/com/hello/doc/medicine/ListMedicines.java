package com.hello.doc.medicine;

public class ListMedicines {
    private String medicineName;
    private String id;
    private String pills;
    private String reminder;
    private String thumbnail;

    public String getMedicineName() {
        return medicineName;
    }

    public String getId() {
        return id;
    }

    public String getPills() {
        return pills;
    }

    public String getReminder() {
        return reminder;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public void setMedicineName(String medicineName) {
        this.medicineName = medicineName;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setPills(String pills) {
        this.pills = pills;
    }

    public void setReminder(String reminder) {
        this.reminder = reminder;
    }


    @Override
    public String toString() {
        return "Medicines [id = " + id + ", medicineName = " + medicineName +", pills = " + pills + ", reminder = " + reminder + "]";
    }


}
