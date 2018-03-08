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

package eu.hopu;

import eu.hopu.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

/**
 * Controller for the NGSI 9/10 convenient REST requests
 * Deviation from standard:
 *  - no support for attributeDomains requests
 *  - only NGSI 10 REST requests are supported
 */
public class NgsiRestBaseController {

    private static Logger logger = LoggerFactory.getLogger(NgsiRestBaseController.class);

    private NgsiValidation ngsiValidation;

    public NgsiRestBaseController() {
        ngsiValidation = new NgsiValidation();
    }

    @POST
    @Produces({"application/json", "plain/text"})
    @Path("/contextEntities/{entityID}")
    public Response appendContextElement(@PathParam("entityID") String entityID,
                                         AppendContextElement appendContextElement,
                                         @Context HttpServletRequest httpServletRequest) throws Exception {
        ngsiValidation.checkAppendContextElement(appendContextElement);
        return Response.ok().entity(appendContextElement(entityID, appendContextElement)).build();
    }

    @POST
    @Produces({"application/json", "plain/text"})
    @Path("/contextEntities/{entityID}/attributes")
    public Response appendContextElementAttributes(@PathParam("entityID") String entityID,
                                                   AppendContextElement appendContextElement,
                                                   @Context HttpServletRequest httpServletRequest) throws Exception {
        ngsiValidation.checkAppendContextElement(appendContextElement);
        return Response.ok().entity(appendContextElement(entityID, appendContextElement)).build();
    }

    @PUT
    @Produces({"application/json", "plain/text"})
    @Path("/contextEntities/{entityID}")
    public Response updateContextElement(@PathParam("entityID") String entityID,
                                         UpdateContextElement updateContextElement,
                                         @Context HttpServletRequest httpServletRequest) throws Exception {
        ngsiValidation.checkUpdateContextElement(updateContextElement);
        return Response.ok().entity(updateContextElement(entityID, updateContextElement)).build();
    }

    @PUT
    @Produces({"application/json", "plain/text"})
    @Path("/contextEntities/{entityID}/attributes")
    public Response updateContextElementAttributes(@PathParam("entityID") String entityID,
                                                   UpdateContextElement updateContextElement,
                                                   @Context HttpServletRequest httpServletRequest) throws Exception {
        ngsiValidation.checkUpdateContextElement(updateContextElement);
        return Response.ok().entity(updateContextElement(entityID, updateContextElement)).build();
    }


//    @RequestMapping(method = RequestMethod.GET,
//            value = {"/contextEntities/{entityID}", "/contextEntities/{entityID}/attributes"})
//    final public ResponseEntity<ContextElementResponse> getContextEntity(@PathVariable String entityID,
//            HttpServletRequest httpServletRequest) throws Exception {
//        registerIntoDispatcher(httpServletRequest);
//        return new ResponseEntity<>(getContextElement(entityID), HttpStatus.OK);
//    }
//
//    @RequestMapping(method = RequestMethod.DELETE,
//            value = {"/contextEntities/{entityID}", "/contextEntities/{entityID}/attributes"})
//    final public ResponseEntity<StatusCode> deleteContextEntity(@PathVariable String entityID,
//            HttpServletRequest httpServletRequest) throws Exception {
//        registerIntoDispatcher(httpServletRequest);
//        return new ResponseEntity<>(deleteContextElement(entityID), HttpStatus.OK);
//    }
//
//    /* Context Attributes */
//
//    @RequestMapping(method = RequestMethod.POST,
//            value = "/contextEntities/{entityID}/attributes/{attributeName}",
//            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
//    final public ResponseEntity<StatusCode> appendContextAttributeValue(@PathVariable String entityID,
//            @PathVariable String attributeName,
//            @RequestBody UpdateContextAttribute updateContextAttribute,
//            HttpServletRequest httpServletRequest) throws Exception {
//        registerIntoDispatcher(httpServletRequest);
//        ngsiValidation.checkUpdateContextAttribute(entityID, attributeName, null, updateContextAttribute);
//        return new ResponseEntity<>(appendContextAttribute(entityID, attributeName, updateContextAttribute), HttpStatus.OK);
//    }
//
//    @RequestMapping(method = RequestMethod.PUT,
//            value = "/contextEntities/{entityID}/attributes/{attributeName}",
//            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
//    final public ResponseEntity<StatusCode> updateContextAttribute(@PathVariable String entityID,
//            @PathVariable String attributeName,
//            @RequestBody UpdateContextAttribute updateContextAttribute,
//            HttpServletRequest httpServletRequest) throws Exception {
//        registerIntoDispatcher(httpServletRequest);
//        ngsiValidation.checkUpdateContextAttribute(entityID, attributeName, null, updateContextAttribute);
//        return new ResponseEntity<>(updateContextAttribute(entityID, attributeName, updateContextAttribute), HttpStatus.OK);
//    }
//
//    @RequestMapping(method = RequestMethod.GET,
//            value = "/contextEntities/{entityID}/attributes/{attributeName}")
//    final public ResponseEntity<ContextAttributeResponse> getContextAttribute(@PathVariable String entityID,
//            @PathVariable String attributeName,
//            HttpServletRequest httpServletRequest) throws Exception {
//        registerIntoDispatcher(httpServletRequest);
//        return new ResponseEntity<>(getContextAttribute(entityID, attributeName), HttpStatus.OK);
//    }
//
//    @RequestMapping(method = RequestMethod.DELETE,
//            value = "/contextEntities/{entityID}/attributes/{attributeName}")
//    final public ResponseEntity<StatusCode> deleteContextAttribute(@PathVariable String entityID,
//            @PathVariable String attributeName,
//            HttpServletRequest httpServletRequest) throws Exception {
//        registerIntoDispatcher(httpServletRequest);
//        return new ResponseEntity<>(deleteContextAttribute(entityID, attributeName), HttpStatus.OK);
//    }
//
//    /* Context Attributes Value instances */
//
//    @RequestMapping(method = RequestMethod.PUT,
//            value = "/contextEntities/{entityID}/attributes/{attributeName}/{valueID}",
//            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
//    final public ResponseEntity<StatusCode> updateContextAttributeValue(@PathVariable String entityID,
//            @PathVariable String attributeName,
//            @PathVariable String valueID,
//            @RequestBody UpdateContextAttribute updateContextAttribute,
//            HttpServletRequest httpServletRequest) throws Exception {
//        registerIntoDispatcher(httpServletRequest);
//        ngsiValidation.checkUpdateContextAttribute(entityID, attributeName, valueID, updateContextAttribute);
//        return new ResponseEntity<>(updateContextAttributeValue(entityID, attributeName, valueID, updateContextAttribute), HttpStatus.OK);
//    }
//
//    @RequestMapping( method = RequestMethod.GET,
//            value = "/contextEntities/{entityID}/attributes/{attributeName}/{valueID}")
//    final public ResponseEntity<ContextAttributeResponse> getContextAttributeValue(@PathVariable String entityID,
//            @PathVariable String attributeName,
//            @PathVariable String valueID,
//            HttpServletRequest httpServletRequest) throws Exception {
//        registerIntoDispatcher(httpServletRequest);
//        return new ResponseEntity<>(getContextAttributeValue(entityID, attributeName, valueID), HttpStatus.OK);
//    }
//
//    @RequestMapping(method = RequestMethod.DELETE,
//            value = "/contextEntities/{entityID}/attributes/{attributeName}/{valueID}")
//    final public ResponseEntity<StatusCode> deleteContextAttributeValue(@PathVariable String entityID,
//            @PathVariable String attributeName,
//            @PathVariable String valueID,
//            HttpServletRequest httpServletRequest) throws Exception {
//        registerIntoDispatcher(httpServletRequest);
//        return new ResponseEntity<>(deleteContextAttributeValue(entityID, attributeName, valueID), HttpStatus.OK);
//    }
//
//    /* Entity types */
//
//    @RequestMapping(method = RequestMethod.GET,
//            value = {"/contextEntityTypes/{typeName}",
//                    "/contextEntityTypes/{typeName}/attributes"})
//    final public ResponseEntity<QueryContextResponse> getContextEntityTypes(
//            @PathVariable String typeName,
//            HttpServletRequest httpServletRequest) throws Exception {
//        registerIntoDispatcher(httpServletRequest);
//        return new ResponseEntity<>(getContextEntitiesType(typeName), HttpStatus.OK);
//    }
//
//    @RequestMapping(method = RequestMethod.GET,
//            value = "/contextEntityTypes/{typeName}/attributes/{attributeName}")
//    final public ResponseEntity<QueryContextResponse> getContextEntityTypes(
//            @PathVariable String typeName,
//            @PathVariable String attributeName,
//            HttpServletRequest httpServletRequest) throws Exception {
//        registerIntoDispatcher(httpServletRequest);
//        return new ResponseEntity<>(getContextEntitiesType(typeName, attributeName), HttpStatus.OK);
//    }
//
//    /* Subscriptions */
//
//    @RequestMapping(method = RequestMethod.POST,
//            value = "/contextSubscriptions",
//            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
//    final public ResponseEntity<SubscribeContextResponse> createSubscription(
//            @RequestBody SubscribeContext subscribeContext,
//            HttpServletRequest httpServletRequest) throws Exception {
//        registerIntoDispatcher(httpServletRequest);
//        ngsiValidation.checkSubscribeContext(subscribeContext);
//        return new ResponseEntity<>(createSubscription(subscribeContext), HttpStatus.OK);
//    }
//
//    @RequestMapping(method = RequestMethod.PUT,
//            value = "/contextSubscriptions/{subscriptionID}",
//            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
//    final public ResponseEntity<UpdateContextSubscriptionResponse> updateSubscription(
//            @PathVariable String subscriptionID,
//            @RequestBody UpdateContextSubscription updateContextSubscription,
//            HttpServletRequest httpServletRequest) throws Exception {
//        registerIntoDispatcher(httpServletRequest);
//        ngsiValidation.checkUpdateSubscription(subscriptionID, updateContextSubscription);
//        return new ResponseEntity<>(updateSubscription(updateContextSubscription), HttpStatus.OK);
//    }
//
//    @RequestMapping(method = RequestMethod.DELETE,
//            value = "/contextSubscriptions/{subscriptionID}")
//    final public ResponseEntity<UnsubscribeContextResponse> deleteSubscription(
//            @PathVariable String subscriptionID,
//            HttpServletRequest httpServletRequest) throws Exception {
//        registerIntoDispatcher(httpServletRequest);
//        return new ResponseEntity<>(deleteSubscription(subscriptionID), HttpStatus.OK);
//    }

    /*
     * Methods overridden by child classes to handle the NGSI v1 convenient REST requests
     */

    protected AppendContextElementResponse appendContextElement(String entityID,
                                                                AppendContextElement appendContextElement) throws Exception {
        throw new UnsupportedOperationException("appendContextElement");
    }

    protected UpdateContextElementResponse updateContextElement(String entityID,
                                                                UpdateContextElement updateContextElement) throws Exception {
        throw new UnsupportedOperationException("updateContextElement");
    }

    protected ContextElementResponse getContextElement(String entityID) throws Exception {
        throw new UnsupportedOperationException("getContextElement");
    }

    protected StatusCode deleteContextElement(String entityID) throws Exception {
        throw new UnsupportedOperationException("deleteContextElement");
    }

    protected StatusCode appendContextAttribute(String entityID, String attributeName,
            UpdateContextAttribute updateContextAttribute) throws Exception {
        throw new UnsupportedOperationException("appendContextAttribute");
    }

    protected StatusCode updateContextAttribute(final String entityID, String attributeName,
            UpdateContextAttribute updateContextElementRequest) throws Exception {
        throw new UnsupportedOperationException("updateContextAttribute");
    }

    protected ContextAttributeResponse getContextAttribute( String entityID, String attributeName) throws Exception {
        throw new UnsupportedOperationException("getContextAttribute");
    }

    protected StatusCode deleteContextAttribute(String entityID, String attributeName) throws Exception {
        throw new UnsupportedOperationException("deleteContextAttribute");
    }

    protected StatusCode updateContextAttributeValue(final String entityID, String attributeName, String valueID,
            UpdateContextAttribute updateContextElementRequest) throws Exception {
        throw new UnsupportedOperationException("updateContextAttributeValue");
    }

    protected ContextAttributeResponse getContextAttributeValue( String entityID, String attributeName, String valueID) throws Exception {
        throw new UnsupportedOperationException("getContextAttributeValue");
    }

    protected StatusCode deleteContextAttributeValue(String entityID, String attributeName, String valueID) throws Exception {
        throw new UnsupportedOperationException("deleteContextAttributeValue");
    }

    protected QueryContextResponse getContextEntitiesType(String typeName) throws Exception {
        throw new UnsupportedOperationException("getContextEntitiesType");
    }

    protected QueryContextResponse getContextEntitiesType(String typeName, String attributeName) throws Exception {
        throw new UnsupportedOperationException("getContextEntitiesType");
    }

    protected SubscribeContextResponse createSubscription(final SubscribeContext subscribeContext) throws Exception {
        throw new UnsupportedOperationException("createSubscription");
    }

    protected UpdateContextSubscriptionResponse updateSubscription(
            UpdateContextSubscription updateContextSubscription) throws Exception {
        throw new UnsupportedOperationException("updateSubscription");
    }

    protected UnsubscribeContextResponse deleteSubscription(String subscriptionID) throws Exception {
        throw new UnsupportedOperationException("deleteSubscription");
    }

}
