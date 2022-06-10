package com.example.drrbnicompany.Models;

public class Job {
    private String jobId,userId,jobName,major,jobLink,jobDescription, img;

    public Job(String jobId, String userId , String jobName, String major, String jobLink, String jobDescription , String img) {
        this.jobId = jobId;
        this.userId = userId;
        this.jobName = jobName;
        this.major = major;
        this.jobLink = jobLink;
        this.jobDescription = jobDescription;
        this.img = img;
    }

    public Job() {}

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getJobLink() {
        return jobLink;
    }

    public void setJobLink(String jobLink) {
        this.jobLink = jobLink;
    }

    public String getJobDescription() {
        return jobDescription;
    }

    public void setJobDescription(String jobDescription) {
        this.jobDescription = jobDescription;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
