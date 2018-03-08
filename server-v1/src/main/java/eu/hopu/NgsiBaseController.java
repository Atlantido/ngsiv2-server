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
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

/**
 * Controller for the NGSI 9/10 requests
 */
@Path("")
public class NgsiBaseController {

    private static Logger logger = LoggerFactory.getLogger(NgsiBaseController.class);

    public NgsiBaseController() {
        System.out.println("me inicializo");
    }

    @POST
    @Produces({"application/json", "plain/text"})
    @Path("/notifyContext")
    public Response notifyContextRequest(
            NotifyContext notify, @Context HttpServletRequest httpServletRequest) throws Exception {
        System.out.println("al menos llego");
        return Response.ok().entity(notifyContext(notify)).build();
    }

    @POST
    @Produces({"application/json", "plain/text"})
    @Path("/updateContext")
    public Response updateContextRequest(
            UpdateContext updateContext, @Context HttpServletRequest httpServletRequest) throws Exception {
        return Response.ok().entity(updateContext(updateContext)).build();
    }

    @POST
    @Produces({"application/json", "plain/text"})
    @Path("/registerContext")
    public Response registerContextRequest(
            RegisterContext registerContext, @Context HttpServletRequest httpServletRequest) throws Exception {
        return Response.ok().entity(registerContext(registerContext)).build();
    }


    @POST
    @Produces({"application/json", "plain/text"})
    @Path("/subscribeContext")
    public Response subscribeContextRequest(SubscribeContext subscribeContext, @Context HttpServletRequest httpServletRequest) throws Exception {
        return Response.ok().entity(subscribeContext(subscribeContext)).build();
    }

    @POST
    @Produces({"application/json", "plain/text"})
    @Path("/updateContextSubscription")
    public Response updateContextSubscription(UpdateContextSubscription updateContextSubscription,
                                              @Context HttpServletRequest httpServletRequest) throws Exception {
        return Response.ok().entity(updateContextSubscription(updateContextSubscription)).build();
    }

    @POST
    @Produces({"application/json", "plain/text"})
    @Path("/unsubscribeContext")
    public Response unsubscribeContextRequest(UnsubscribeContext unsubscribeContext,
                                              @Context HttpServletRequest httpServletRequest) throws Exception {
        return Response.ok().entity(unsubscribeContext(unsubscribeContext)).build();
    }

    @POST
    @Produces({"application/json", "plain/text"})
    @Path("/queryContext")
    public Response queryContextRequest(QueryContext queryContext, @Context HttpServletRequest httpServletRequest) throws Exception {
        return Response.ok().entity(queryContext(queryContext)).build();
    }

    protected NotifyContextResponse notifyContext(final NotifyContext notify) throws Exception {
        throw new UnsupportedOperationException("notifyContext");
    }

    protected UpdateContextResponse updateContext(final UpdateContext update) throws Exception {
        throw new UnsupportedOperationException("updateContext");
    }

    protected RegisterContextResponse registerContext(final RegisterContext register) throws Exception {
        throw new UnsupportedOperationException("registerContext");
    }

    protected SubscribeContextResponse subscribeContext(final SubscribeContext subscribe) throws Exception {
        throw new UnsupportedOperationException("subscribeContext");
    }

    protected UpdateContextSubscriptionResponse updateContextSubscription(final UpdateContextSubscription updateContextSubscription) throws Exception {
        throw new UnsupportedOperationException("updateContextSubscription");
    }

    protected UnsubscribeContextResponse unsubscribeContext(final UnsubscribeContext unsubscribe) throws Exception {
        throw new UnsupportedOperationException("unsubscribeContext");
    }

    protected QueryContextResponse queryContext(final QueryContext query) throws Exception {
        throw new UnsupportedOperationException("queryContext");
    }
}
