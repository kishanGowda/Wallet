package com.example.wallet.Api;

import java.util.ArrayList;
import java.util.Date;

// import com.fasterxml.jackson.databind.ObjectMapper; // version 2.11.1
// import com.fasterxml.jackson.annotation.JsonProperty; // version 2.11.1
/* ObjectMapper om = new ObjectMapper();
Root root = om.readValue(myJsonString, Root.class); */
public class Standard {
    public int id;
    public String std;
    public String section;
    public String stream;
    public Date createdAt;
    public Date updatedAt;
    public ArrayList<String> students;
    public String status;
    public Object courseId;
    public String courseName;
}
