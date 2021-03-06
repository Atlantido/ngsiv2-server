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

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

/**
 * UnsubcribeContextResponse
 */
@JacksonXmlRootElement(localName = "unsubscribeContextResponse")
public class UnsubscribeContextResponse {

    private StatusCode statusCode;

    private String subscriptionId;

    public UnsubscribeContextResponse() {
    }

    public UnsubscribeContextResponse(StatusCode statusCode, String subscriptionId) {
        this.statusCode = statusCode;
        this.subscriptionId = subscriptionId;
    }

    public String getSubscriptionId() {
        return subscriptionId;
    }

    public void setSubscriptionId(String subscriptionId) {
        this.subscriptionId = subscriptionId;
    }

    public StatusCode getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(StatusCode statusCode) {
        this.statusCode = statusCode;
    }

    @Override
    public String toString() {
        return "UnsubscribeContextResponse{" +
                "statusCode=" + statusCode +
                ", subscriptionId='" + subscriptionId + '\'' +
                '}';
    }
}
