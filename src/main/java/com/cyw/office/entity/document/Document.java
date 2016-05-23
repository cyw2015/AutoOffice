package com.cyw.office.entity.document;

import java.util.Date;

import com.cyw.office.util.BaseEntity;

public class Document extends BaseEntity{
    private String docCode;

    private Integer id;

    private Object docTitle;

    private Object docContent;

    private Date edittime;

    private Date pubtime;

    private Object recipients;

    private Object attachment;

    private String state;

    private String creater;

    private Object attachmentPath;
    private Object recipientsCode;
    public Object getRecipientsCode() {
		return recipientsCode;
	}

	public void setRecipientsCode(Object recipientsCode) {
		this.recipientsCode = recipientsCode;
	}

	public Object getAttachmentPath() {
		return attachmentPath;
	}

	public void setAttachmentPath(Object attachmentPath) {
		this.attachmentPath = attachmentPath;
	}

	public String getDocCode() {
        return docCode;
    }

    public void setDocCode(String docCode) {
        this.docCode = docCode == null ? null : docCode.trim();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Object getDocTitle() {
        return docTitle;
    }

    public void setDocTitle(Object docTitle) {
        this.docTitle = docTitle;
    }

    public Object getDocContent() {
        return docContent;
    }

    public void setDocContent(Object docContent) {
        this.docContent = docContent;
    }

    public Date getEdittime() {
        return edittime;
    }

    public void setEdittime(Date edittime) {
        this.edittime = edittime;
    }

    public Date getPubtime() {
        return pubtime;
    }

    public void setPubtime(Date pubtime) {
        this.pubtime = pubtime;
    }

    public Object getRecipients() {
        return recipients;
    }

    public void setRecipients(Object recipients) {
        this.recipients = recipients;
    }

    public Object getAttachment() {
        return attachment;
    }

    public void setAttachment(Object attachment) {
        this.attachment = attachment;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state == null ? null : state.trim();
    }

    public String getCreater() {
        return creater;
    }

    public void setCreater(String creater) {
        this.creater = creater == null ? null : creater.trim();
    }
}