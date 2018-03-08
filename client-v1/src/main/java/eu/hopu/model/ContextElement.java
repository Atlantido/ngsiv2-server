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

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.util.Collections;
import java.util.List;

/**
 * Context Entity
 */

public class ContextElement {

    private String id;

    @JacksonXmlProperty(isAttribute = true)
    private String type;

//    @JacksonXmlProperty(isAttribute = true)
//    private String isPattern;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty(value = "attributes")
    @JacksonXmlElementWrapper(localName = "contextAttributeList")
    @JacksonXmlProperty(localName = "contextAttribute")
    private List<ContextAttribute> contextAttributeList;

    public ContextElement() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

//    public String getPattern() {
//        return isPattern;
//    }
//
//    public void setPattern(String pattern) {
//        this.isPattern = pattern;
//    }

    public List<ContextAttribute> getContextAttributeList() {
        if (contextAttributeList == null) {
            return Collections.emptyList();
        }
        return contextAttributeList;
    }

    public void setContextAttributeList(List<ContextAttribute> contextAttributeList) {
        this.contextAttributeList = contextAttributeList;
    }

    @Override
    public String toString() {
        return "ContextElement{" +
                "id='" + id + '\'' +
                ", type='" + type + '\'' +
                ", contextAttributeList=" + contextAttributeList +
                '}';
    }
}
