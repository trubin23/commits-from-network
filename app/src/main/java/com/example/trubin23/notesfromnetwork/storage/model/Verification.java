package com.example.trubin23.notesfromnetwork.storage.model;

import com.squareup.moshi.Json;

/**
 * Created by Andrey on 01.01.2018.
 */

public class Verification {

    @Json(name = "verified")
    private Boolean verified;
    @Json(name = "reason")
    private String reason;
    @Json(name = "signature")
    private Object signature;
    @Json(name = "payload")
    private Object payload;

    public Boolean getVerified() {
        return verified;
    }

    public void setVerified(Boolean verified) {
        this.verified = verified;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Object getSignature() {
        return signature;
    }

    public void setSignature(Object signature) {
        this.signature = signature;
    }

    public Object getPayload() {
        return payload;
    }

    public void setPayload(Object payload) {
        this.payload = payload;
    }

}
