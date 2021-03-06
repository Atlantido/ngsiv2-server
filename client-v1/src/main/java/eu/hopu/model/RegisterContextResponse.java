/*
 * Copyright (C) 2015 Orange
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package eu.hopu.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

/**
 * Created by pborscia on 10/08/2015.
 */
@JacksonXmlRootElement(localName = "registerContextResponse")
public class RegisterContextResponse {

    private String duration;

    @JsonProperty(required = true)
    private String registrationId;

    private StatusCode errorCode;

    public RegisterContextResponse() {
    }

    public RegisterContextResponse(String registrationId) {
        this.registrationId = registrationId;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getRegistrationId() {
        return registrationId;
    }

    public void setRegistrationId(String registrationId) {
        this.registrationId = registrationId;
    }

    public StatusCode getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(StatusCode errorCode) {
        this.errorCode = errorCode;
    }

    @Override
    public String toString() {
        return "RegisterContextResponse{" +
                "duration='" + duration + '\'' +
                ", registrationId='" + registrationId + '\'' +
                ", errorCode=" + errorCode +
                '}';
    }
}
