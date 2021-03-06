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
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.util.List;

/**
 * Created by pborscia on 11/08/2015.
 */
@JacksonXmlRootElement(localName = "queryContextRequest")
public class QueryContext {


    @JsonProperty(value = "entities", required = true)
  //  @JacksonXmlElementWrapper(localName = "entityIdList")
//    @JacksonXmlProperty(localName = "entityId")
    private List<EntityId> entityIdList;
    //private ContextElement entities;
    //private String type;
    //@JacksonXmlProperty(isAttribute = true)
    //private String type;


    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty(value = "attributes")
    @JacksonXmlElementWrapper(localName = "attributeList")
    @JacksonXmlProperty(localName = "attribute")
    private List<String> attributeList;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Restriction restriction;

    public QueryContext() {
    }

    public QueryContext(List<EntityId> entityIdList) {
        this.entityIdList = entityIdList;
    }

    public List<String> getAttributeList() {
        return attributeList;
    }

    public void setAttributeList(List<String> attributeList) {
        this.attributeList = attributeList;
    }

    public Restriction getRestriction() {
        return restriction;
    }


    public List<EntityId> getEntityIdList() {
        return entityIdList;
    }

    public void setEntityIdList(List<EntityId> entityIdList) {
        this.entityIdList = entityIdList;
    }
//    public String getType() {
//        return type;
//    }
//
//    public void setType(String type) {
//        this.type = type;
//    }
//
//    public String getId() {
//        return id;
//    }
//
//    public void setId(String id) {
//        this.id = id;
//    }


    public void setRestriction(Restriction restriction) {
        this.restriction = restriction;
    }

    @Override
    public String toString() {
        return "QueryContext{" +
               // "entityIdList=" + entityIdList +
                ", attributeList=" + attributeList +
                ", restriction=" + restriction +
                '}';
    }
}
