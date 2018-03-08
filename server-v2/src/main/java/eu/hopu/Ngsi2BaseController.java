/*
 * Copyright (C) 2016 Orange
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package eu.hopu;

import ch.qos.logback.core.status.Status;
import com.fasterxml.jackson.databind.ObjectMapper;
import eu.hopu.exception.ConflictingEntitiesException;
import eu.hopu.model.*;
import httpMethods.PATCH;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.*;
import java.util.regex.Pattern;

/**
 * Controller for the NGSI v2 requests
 */

@Path("")
public class Ngsi2BaseController {

    private Map<String,String> resources;

    private  Map<String,Entity> entities;

    private static Logger logger = LoggerFactory.getLogger(Ngsi2BaseController.class);

    private static Pattern fieldPattern = Pattern.compile("[\\x21\\x22\\x24\\x25\\x27-\\x2E\\x30-\\x3E\\x40-\\x7E]*");

    private ObjectMapper objectMapper;


    public Ngsi2BaseController() {
        entities = new HashMap<>();
        resources = new HashMap<>();
        resources.put("retrieve","get entities");
    }

    @PostConstruct
    private void init() {
        Entity entityTest = new Entity("Prueba1","SmartSpot");
        Attribute attribute = new Attribute(10);
        attribute.setType(Optional.of("Number"));
        entityTest.setAttributes("temperature",attribute);
        attribute = new Attribute(124);
        attribute.setType(Optional.of("Number"));
        entityTest.setAttributes("humidity",attribute);
        entities.put("Prueba",entityTest);

    }
/*
    @GET
    @Path("/create")
    public Response createPrueba() {
        AsyncRestTemplate template = new AsyncRestTemplate();
        Ngsi2Client orion = new Ngsi2Client(template,"http://192.168.1.250:1026");
        orion.addHttpHeader("fiware-service","SmartSpot");
        orion.addHttpHeader("fiware-servicepath","/smartspot");
        orion.setHttpHeaderJSON();
        Entity entity = entities.get("Prueba");
        orion.addEntity(entity);

        return Response.ok().build();
    }
*/
    /**
     * Endpoint get /v2
     * @return the list of supported operations under /v2 and http status 200 (ok)
     * @throws Exception
     */
    @GET
    @Path("/")
    public Response listResourcesEndpoint() throws Exception {
         return Response.ok().entity(listResources()).build();
    }

    @POST
    @Path("/entities")
    @Consumes(MediaType.APPLICATION_JSON)
    final public Response createEntityEndpoint(String body) {
        //TODO: to support keyValues as options
        Entity entity = createEntity(body);
        if(!entities.containsKey(entity.getId())) {
            entities.put("lgag",entity);
            return Response.status(Response.Status.CREATED).build();
        }
        return Response.status(Status.ERROR).entity("Already exists an entity with these ID").build();
   }

    @GET
    @Path("/entities")
    @Produces(MediaType.APPLICATION_JSON)
    final public Response listEntitiesEndpoint(@DefaultValue(" ") @QueryParam("options") String keyValues) throws Exception {
        boolean isKeyValues = false;
        if(keyValues.equals("keyValues"))
            isKeyValues = true;

        return Response.ok().entity("retrieve entitie Method").build();
    }

    @GET
    @Path("/entities/{id}")
    final public Response retrieveEntityEndpoint(@PathParam("id") String id, @DefaultValue(" ") @QueryParam("options") String keyValues) throws Exception {
        boolean isKeyValues = false;
        if(keyValues.equals("keyValues"))
            isKeyValues = true;
        return Response.ok().entity("retrieve entity Method").build();
    }

    /**
     *  Create a new attribute or attributes
     */
    @POST
    @Path("/entities/{id}/attrs")
    @Consumes(MediaType.APPLICATION_JSON)
    final public Response appendEntityEndpoint(@PathParam("id") String id,String body, @DefaultValue(" ") @QueryParam("options") String keyValues) {
        boolean isKeyValues = false;
        if(keyValues.equals("keyVaules"))
            isKeyValues = true;

        return Response.ok().build();
    }

    /**
     *
     * Update an attributte or more of the entity
     * This method can send a Http status 200 (ok) or 409 (conflict)
     */
    @PATCH
    @Path("/entities/{id}/attrs")
    @Consumes(MediaType.APPLICATION_JSON)
    final public Response updateAttributeByEntityIdEndpoint(@PathParam("id") String id, String body) {
        return Response.ok().build();
    }

    @PUT
    @Path("/entities/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    final public Response replaceAllEntityAttributesEndpoint(@PathParam("id") String id,String body,@DefaultValue(" ") @QueryParam("options") String keyValues) {
        boolean isKeyValues = false;
        if(keyValues.equals("keyVaules"))
            isKeyValues = true;

        return Response.noContent().build();
    }

    @DELETE
    @Path("/entities/{id}")
    final public Response removeEntityEndpoint(@PathParam("id") String id) {
        return Response.noContent().build();

    }

    /**
     * This method can send a Http status 200 (ok) or 409 (conflict)
     */
    @GET
    @Path("/entities/{entityId}/attrs/{attrName}")
    @Produces(MediaType.APPLICATION_JSON)
    final public Response retrieveAttributeByEntityIdEndpoint(@PathParam("entityId") String entityId, @PathParam("attrName") String attrName) {

        //Response.status(Response.Status.CONFLICT).build(); <-- 409 Conflict
        return Response.ok().entity("attribute").build();
    }

    /**
     * This method can send a Http status 200 (ok) or 409 (conflict)
     */
    @DELETE
    @Path("/entities/{entityId}/attrs/{attrName}")
    @Produces(MediaType.APPLICATION_JSON)
    final public Response removeAttributeByEntityIdEndpoint(@PathParam("entityId") String entityId, @PathParam("attrName") String attrName) {
        //Response.status(Response.Status.CONFLICT).build(); <-- 409 Conflict

        return Response.status(Response.Status.NO_CONTENT).build();
    }
    /**
     * This method can send a Http status 200 (ok) or 409 (conflict)
     */
    @GET
    @Path("/entities/{entityId}/attrs/{attrName}/value")
    final public Response retrieveAttributeValueEndpoint(@PathParam("entityId") String entityId, @PathParam("attrName") String attrName) {
        //Response.status(Response.Status.CONFLICT).build(); <-- 409 Conflict
        return Response.ok().entity(4).build();
    }

    @GET
    @Path("/types")
    @Produces(MediaType.APPLICATION_JSON)
    final public Response retrieveEntityTypesEndpoint(@DefaultValue(" ") @QueryParam("options") String keyValues) {
        boolean isKeyValues = false;
        if(keyValues.equals("keyValues"))
            isKeyValues = true;
        return Response.ok().entity("retrieve types").build();
    }

    @GET
    @Path("/types/{typeId}")
    @Produces(MediaType.APPLICATION_JSON)
    final public Response retrieveEntityTypeEndpoint(@PathParam("typeId") String typeId) {
        return Response.ok().entity("retrieve rype").build();
    }

    @GET
    @Path("/subscriptions")
    final public Response listSubscriptionsEndpoint(){
            return Response.ok().entity("retrieve subscriptions").build();
    }

    @GET
    @Path("/subscriptions/{subscriptionID}")
    @Produces(MediaType.APPLICATION_JSON)
    final public Response retrieveSubscriptionEndpoint(@PathParam("subscriptionID") String subscriptionID){
        return Response.ok().entity("retrieve subscription").build();
    }

    @DELETE
    @Path("/subscriptions/{subscriptionID}")
    final public Response removeSubscriptionEndpoint(@PathParam("subscriptionID") String subscriptionID) {
        return Response.noContent().build();
    }

/*
   @PUT
    @Path("/entities/{entityId}/attrs/{attrName}/value")
    @Consumes(MediaType.APPLICATION_JSON)
   final public Response updateAttributeValueEndpoint() {
       //Response.status(Response.Status.CONFLICT).build();
        return Response.noContent().build();
    }

    @GET
    @Path("/registrations")
    final public Response listRegistrationsEndpoint() {

        return Response.ok().entity("retrieve registration").build();
    }

    @POST
    @Path("/registrations")
    @Consumes(MediaType.APPLICATION_JSON)
    final public Response createRegistrationEndpoint(String body) {
        return Response.status(Response.Status.CREATED).build();
    }

    @GET
    @Path("/registrations/{registrationId}")
    final public Response retrieveRegistrationEndpoint(@PathParam("registrationId") String registrationId) {
        return Response.ok().entity("retrieve registration").build();
    }

    @PATCH
    @Path("/registrations/{registrationId}")
    final public Response updateRegistrationEndpoint(@PathParam("registrationId") String registrationId) {
        return Response.noContent().build();
    }

    @DELETE
    @Path("/registrations/{registrationId}")
    final public Response removeRegistrationEndpoint(@PathParam("registrationId") String registrationId) {
        return Response.noContent().build();
    }
*/
    @PATCH
    @Path("/subscriptions/{subscriptionId}")
    final public Response updateSubscriptionEndpoint(@PathParam("subscriptionId") String subscriptionID) {
        return Response.noContent().build();
    }



//    /*
//     * POJ RPC "bulk" Operations
//     */
//
//    /**
//     * Update, append or delete multiple entities in a single operation
//     * @param bulkUpdateRequest a BulkUpdateRequest with an actionType and a list of entities to update
//     * @param options an optional list of options separated by comma. keyValues option is not supported.
//     * @return http status 204 (no content)
//     * @throws Exception
//     */
//    @RequestMapping(method = RequestMethod.POST, value = {"/op/update"}, consumes = MediaType.APPLICATION_JSON_VALUE)
//    final public ResponseEntity bulkUpdateEndpoint(@RequestBody BulkUpdateRequest bulkUpdateRequest, @RequestParam Optional<String> options) throws Exception {
//
//        bulkUpdateRequest.getEntities().forEach(this::validateSyntax);
//        //TODO: to support keyValues as options
//        if (options.isPresent())  {
//            throw new UnsupportedOptionException(options.get());
//        }
//        bulkUpdate(bulkUpdateRequest);
//        return new ResponseEntity(HttpStatus.NO_CONTENT);
//    }
//
//    /**
//     * Query multiple entities in a single operation
//     * @param bulkQueryRequest defines the list of entities, attributes and scopes to match entities
//     * @param limit an optional limit
//     * @param offset an optional offset
//     * @param orderBy an optional list of attributes to order the entities
//     * @param options an optional list of options separated by comma. Possible value for option: count.
//     *        Theses keyValues,values and unique options are not supported.
//     *        If count is present then the total number of entities is returned in the response as a HTTP header named `X-Total-Count`.
//     * @return a list of Entities http status 200 (ok)
//     * @throws Exception
//     */
//    @RequestMapping(method = RequestMethod.POST, value = {"/op/query"}, consumes = MediaType.APPLICATION_JSON_VALUE)
//    final public ResponseEntity<List<Entity>> bulkQueryEndpoint(@RequestBody BulkQueryRequest bulkQueryRequest, @RequestParam Optional<Integer> limit,
//                                                  @RequestParam Optional<Integer> offset, @RequestParam Optional<List<String>> orderBy,
//                                                  @RequestParam Optional<Set<String>> options) throws Exception {
//
//        validateSyntax(bulkQueryRequest);
//        boolean count = false;
//        if (options.isPresent()) {
//            Set<String> optionsSet = options.get();
//            //TODO: to support keyValues, values and unique as options
//            if (optionsSet.contains("keyValues") || optionsSet.contains("values") || optionsSet.contains("unique")) {
//                throw new UnsupportedOptionException("keyValues, values or unique");
//            }
//            count = optionsSet.contains("count");
//        }
//        Paginated<Entity> paginatedEntity = bulkQuery(bulkQueryRequest, limit.orElse(0), offset.orElse(0), orderBy.orElse(new ArrayList<>()), count);
//        if (count) {
//            return new ResponseEntity<>(paginatedEntity.getItems(), xTotalCountHeader(paginatedEntity.getTotal()), HttpStatus.OK);
//        } else {
//            return new ResponseEntity<>(paginatedEntity.getItems(), HttpStatus.OK);
//        }
//    }
//
//    /**
//     * Create, update or delete registrations to multiple entities in a single operation
//     * @param bulkRegisterRequest defines the list of entities to register
//     * @return a list of registration ids
//     * @throws Exception
//     */
//    @RequestMapping(method = RequestMethod.POST, value = {"/op/register"}, consumes = MediaType.APPLICATION_JSON_VALUE)
//    final public ResponseEntity<List<String>> bulkRegisterEndpoint(@RequestBody BulkRegisterRequest bulkRegisterRequest) throws Exception {
//
//        bulkRegisterRequest.getRegistrations().forEach(this::validateSyntax);
//        return new ResponseEntity<>(bulkRegister(bulkRegisterRequest), HttpStatus.OK);
//    }
//
//    /**
//     * Discover registration matching entities and their attributes
//     * @param bulkQueryRequest defines the list of entities, attributes and scopes to match registrations
//     * @param offset an optional offset (0 for none)
//     * @param limit an optional limit (0 for none)
//     * @param options an optional list of options separated by comma. Possible value for option: count.
//     *        If count is present then the total number of registrations is returned in the response as a HTTP header named `X-Total-Count`.
//     * @return a paginated list of registration
//     */
//    @RequestMapping(method = RequestMethod.POST, value = {"/op/discover"}, consumes = MediaType.APPLICATION_JSON_VALUE)
//    final public ResponseEntity<List<Registration>> bulkDiscoverEndpoint(@RequestBody BulkQueryRequest bulkQueryRequest, @RequestParam Optional<Integer> limit,
//                                                                @RequestParam Optional<Integer> offset,
//                                                                @RequestParam Optional<Set<String>> options) {
//
//        validateSyntax(bulkQueryRequest);
//        boolean count = false;
//        if (options.isPresent()) {
//            Set<String> optionsSet = options.get();
//            count = optionsSet.contains("count");
//        }
//        Paginated<Registration> paginatedRegistration = bulkDiscover(bulkQueryRequest, limit.orElse(0), offset.orElse(0), count);
//        if (count) {
//            return new ResponseEntity<>(paginatedRegistration.getItems(), xTotalCountHeader(paginatedRegistration.getTotal()), HttpStatus.OK);
//        } else {
//            return new ResponseEntity<>(paginatedRegistration.getItems(), HttpStatus.OK);
//        }
//    }
//
//    /*
//     * Exception handling
//     */
//
//    @ExceptionHandler({UnsupportedOperationException.class})
//    public ResponseEntity<Object> unsupportedOperation(UnsupportedOperationException exception, HttpServletRequest request) {
//        logger.error("Unsupported operation: {}", exception.getMessage());
//        HttpStatus httpStatus = HttpStatus.NOT_IMPLEMENTED;
//        if (request.getHeader("Accept").contains(MediaType.TEXT_PLAIN_VALUE)) {
//            return new ResponseEntity<>(exception.getError().toString(), httpStatus);
//        }
//        return new ResponseEntity<>(exception.getError(), httpStatus);
//    }
//
//    @ExceptionHandler({UnsupportedOptionException.class})
//    public ResponseEntity<Object> unsupportedOption(UnsupportedOptionException exception, HttpServletRequest request) {
//        logger.error("Unsupported option: {}", exception.getMessage());
//        HttpStatus httpStatus = HttpStatus.NOT_IMPLEMENTED;
//        if (request.getHeader("Accept").contains(MediaType.TEXT_PLAIN_VALUE)) {
//            return new ResponseEntity<>(exception.getError().toString(), httpStatus);
//        }
//        return new ResponseEntity<>(exception.getError(), httpStatus);
//    }
//
//    @ExceptionHandler({BadRequestException.class})
//    public ResponseEntity<Object> incompatibleParameter(BadRequestException exception, HttpServletRequest request) {
//        logger.error("Bad request: {}", exception.getMessage());
//        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
//        if (request.getHeader("Accept").contains(MediaType.TEXT_PLAIN_VALUE)) {
//            return new ResponseEntity<>(exception.getError().toString(), httpStatus);
//        }
//        return new ResponseEntity<>(exception.getError(), httpStatus);
//    }
//
//    @ExceptionHandler({IncompatibleParameterException.class})
//    public ResponseEntity<Object> incompatibleParameter(IncompatibleParameterException exception, HttpServletRequest request) {
//        logger.error("Incompatible parameter: {}", exception.getMessage());
//        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
//        if (request.getHeader("Accept").contains(MediaType.TEXT_PLAIN_VALUE)) {
//            return new ResponseEntity<>(exception.getError().toString(), httpStatus);
//        }
//        return new ResponseEntity<>(exception.getError(), httpStatus);
//    }
//
//    @ExceptionHandler({InvalidatedSyntaxException.class})
//    public ResponseEntity<Object> invalidSyntax(InvalidatedSyntaxException exception, HttpServletRequest request) {
//        logger.error("Invalid syntax: {}", exception.getMessage());
//        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
//        if (request.getHeader("Accept").contains(MediaType.TEXT_PLAIN_VALUE)) {
//            return new ResponseEntity<>(exception.getError().toString(), httpStatus);
//        }
//        return new ResponseEntity<>(exception.getError(), httpStatus);
//    }
//
//    @ExceptionHandler({ConflictingEntitiesException.class})
//    public ResponseEntity<Object> conflictingEntities(ConflictingEntitiesException exception, HttpServletRequest request) {
//        logger.error("ConflictingEntities: {}", exception.getMessage());
//        HttpStatus httpStatus = HttpStatus.CONFLICT;
//        if (request.getHeader("Accept").contains(MediaType.TEXT_PLAIN_VALUE)) {
//            return new ResponseEntity<>(exception.getError().toString(), httpStatus);
//        }
//        return new ResponseEntity<>(exception.getError(), httpStatus);
//    }
//
//    @ExceptionHandler({NotAcceptableException.class})
//    public ResponseEntity<Object> notAcceptable(NotAcceptableException exception, HttpServletRequest request) {
//        logger.error("Not Acceptable: {}", exception.getMessage());
//        HttpStatus httpStatus = HttpStatus.NOT_ACCEPTABLE;
//        if (request.getHeader("Accept").contains(MediaType.TEXT_PLAIN_VALUE)) {
//            return new ResponseEntity<>(exception.getError().toString(), httpStatus);
//        }
//        return new ResponseEntity<>(exception.getError(), httpStatus);
//    }
//
//    @ExceptionHandler({IllegalArgumentException.class})
//    public ResponseEntity<Object> illegalArgument(IllegalArgumentException exception, HttpServletRequest request) {
//        logger.error("Illegal Argument: {}", exception.getMessage());
//        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
//        if (request.getHeader("Accept").contains(MediaType.TEXT_PLAIN_VALUE)) {
//            return new ResponseEntity<>(exception.getMessage(), httpStatus);
//        }
//        return new ResponseEntity<>(exception.getMessage(), httpStatus);
//    }

    /*
     * Methods overridden by child classes to handle the NGSI v2 requests
     */


    protected String listEntities(boolean isKeyValue) throws Exception {

        String mensaje = "[";
        int numAtributos = 0;
        int contador = 0;
        int numEntities = 0;
        int contadorEntities = 0;
        if(!isKeyValue) {
            Set<String> keys = entities.keySet();
            numEntities = keys.size();
            for (String key: keys) {
                Entity entity = entities.get(key);
                Map<String,Attribute> attributes = entity.getAttributes();
                mensaje = mensaje + "\n\t{\n\t\t";
                mensaje = mensaje + "\"id\": \"" + entity.getId() + "\",\n\t\t";
                mensaje = mensaje + "\"type\": \"" + entity.getType() +"\",\n\t\t";
                Set<String> attributesId = attributes.keySet();
                numAtributos = attributesId.size();
                contador = 0;
                for (String attributeId: attributesId) {
                    Attribute attribute = attributes.get(attributeId);
                    Map<String,Metadata> metadata = attribute.getMetadata();

                    mensaje = mensaje + "\"" + attributeId + "\": {\n\t\t\t";
                    mensaje = mensaje + "\"type\": \"" + attribute.getType().toString() + "\",\n\t\t\t";
                    mensaje = mensaje + "\"value\": \"" + attribute.getValue()+"\",\n\t\t\t";
                    if(metadata.keySet().isEmpty() && contador < numAtributos-1) {
                        mensaje = mensaje + "\"metadata\": {}\n\t\t},\n\t\t";
                        contador++;
                    }
                    else
                        mensaje = mensaje + "\"metadata\": {}\n\t\t}\n\t";
                }

                if(contadorEntities < numEntities -1) {
                    mensaje = mensaje + "},";
                    contadorEntities++;
                }
                else
                    mensaje = mensaje + "}\n";
            }
            mensaje = mensaje + "]";
        }
        return mensaje;
    }

    /**
     * Retrieve the list of supported operations under /v2
     * @return the list of supported operations under /v2
     * @throws Exception
     */
    protected Map<String,String> listResources() throws Exception {

        throw new UnsupportedOperationException("Retrieve API Resources");
    }

    /**
     * Create a new entity
     */
    protected Entity createEntity(String body){
        if(body == null || body.equals("") || body.equals(" "))
            return null;
        Entity entity = bodyToEntity(body);

        return entity;
    }

    /**
     * Retrieve an Entity by the entity ID
     * @param entityId the entity ID
     * @param type an optional type of entity (null for none)
     * @param attrs an optional list of attributes to return for the entity (null or empty for none)
     * @return the Entity
     * @throws ConflictingEntitiesException
     */
    protected Entity retrieveEntity(String entityId, String type, List<String> attrs) throws ConflictingEntitiesException {
        throw new UnsupportedOperationException("Retrieve Entity");
    }

    /**
     * Update existing or append some attributes to an entity
     * @param entityId the entity ID
     * @param type an optional type of entity (null for none)
     * @param attributes the attributes to update or to append
     * @param append boolean true if the operation is an append operation
     */
    protected void updateOrAppendEntity(String entityId, String type, Map<String, Attribute> attributes, Boolean append){
        throw new UnsupportedOperationException("Update Or Append Entity");
    }

    /**
     * Update existing attributes to an entity. The entity attributes are updated with the ones in the attributes.
     * If one or more attributes in the payload doesn't exist in the entity, an error if returned
     * @param entityId the entity ID
     * @param type an optional type of entity (null for none)
     * @param attributes the attributes to update
     */
    protected void updateExistingEntityAttributes(String entityId, String type, Map<String, Attribute> attributes){
        throw new UnsupportedOperationException("Update Existing Entity Attributes");
    }

    /**
     * Replace all the existing attributes of an entity with a new set of attributes
     * @param entityId the entity ID
     * @param type an optional type of entity (null for none)
     * @param attributes the new set of attributes
     */
    protected void replaceAllEntityAttributes(String entityId, String type, Map<String, Attribute> attributes){
        throw new UnsupportedOperationException("Replace All Entity Attributes");
    }

    /**
     * Delete an entity
     * @param entityId the entity ID
     */
    protected void removeEntity(String entityId){
        throw new UnsupportedOperationException("Remove Entity");
    }

    /**
     * Retrieve a list of entity types
     * @param limit an optional limit (0 for none)
     * @param offset an optional offset (0 for none)
     * @param count whether or not to count the total number of entity types
     * @return the list of entity types
     */
    protected Paginated<EntityType> retrieveEntityTypes(int limit, int offset, boolean count) {
        throw new UnsupportedOperationException("Retrieve Entity Types");
    }

    /**
     * Retrieve an Entity Type by the type with the union set of attribute name and attribute type and with the count
     * of entities belonging to that type
     * @param entityType the type of entity
     * @return the EntityType
     */
    protected EntityType retrieveEntityType(String entityType) {
        throw new UnsupportedOperationException("Retrieve Entity Type");
    }

    /**
     * Retrieve an Attribute by the entity ID
     * @param entityId the entity ID
     * @param attrName the attribute name
     * @param type an optional type to avoid ambiguity in the case there are several entities with the same entity id
     *             null for none
     * @return the Attribute
     * @throws ConflictingEntitiesException
     */
    protected Attribute retrieveAttributeByEntityId(String entityId, String attrName, String type) throws ConflictingEntitiesException {
        throw new UnsupportedOperationException("Retrieve Attribute by Entity ID");
    }

    /**
     * Update an Attribute by the entity ID
     * @param entityId the entity ID
     * @param attrName the attribute name
     * @param type an optional type to avoid ambiguity in the case there are several entities with the same entity id
     *             null for none
     * @param attribute the new attributes data
     * @throws ConflictingEntitiesException
     */
    protected void updateAttributeByEntityId(String entityId, String attrName, String type, Attribute attribute) throws ConflictingEntitiesException {
        throw new UnsupportedOperationException("Update Attribute by Entity ID");
    }

    /**
     * Delete an attribute
     * @param entityId the entity ID
     * @param attrName the attribute name
     * @param type an optional type to avoid ambiguity in the case there are several entities with the same entity id
     *             null for none
     * @throws ConflictingEntitiesException
     */
    protected void removeAttributeByEntityId(String entityId, String attrName, String type) throws ConflictingEntitiesException {
        throw new UnsupportedOperationException("Remove Attribute");
    }

    /**
     * Delete an attribute
     * @param entityId the entity ID
     * @param attrName the attribute name
     * @param type an optional type to avoid ambiguity in the case there are several entities with the same entity id
     *             null for none
     * @return value
     */
    protected Object retrieveAttributeValue(String entityId, String attrName, String type) {
        throw new UnsupportedOperationException("Retrieve Attribute Value");
    }

    /**
     * Update an Attribute Value
     * @param entityId the entity ID
     * @param attrName the attribute name
     * @param type an optional type to avoid ambiguity in the case there are several entities with the same entity id.
     *             null for none
     * @param value the new value
     * @throws ConflictingEntitiesException
     */
    protected void updateAttributeValue(String entityId, String attrName, String type, Object value) throws ConflictingEntitiesException {
        throw new UnsupportedOperationException("Update Attribute Value");
    }

    /**
     * Retrieve the list of all Registrations presents in the system
     * @return list of Registrations
     */
    protected List<Registration> listRegistrations() {
        throw new UnsupportedOperationException("Retrieve Registrations");
    }

    /**
     * Create a new registration
     * @param registration the registration to create
     */
    protected void createRegistration(Registration registration){
        throw new UnsupportedOperationException("Create Registration");
    }

    /**
     * Retrieve a Registration by the registration ID
     * @param registrationId the registration ID
     * @return the registration
     */
    protected Registration retrieveRegistration(String registrationId) {
        throw new UnsupportedOperationException("Retrieve Registration");
    }

    /**
     * Update some fields to a registration
     * @param registrationId the registration ID
     * @param registration the some fields of the registration to update
     */
    protected void updateRegistration(String registrationId, Registration registration){
        throw new UnsupportedOperationException("Update Registration");
    }

    /**
     * Delete a registration
     * @param registrationId the registration ID
     */
    protected void removeRegistration(String registrationId){
        throw new UnsupportedOperationException("Remove Registration");
    }

    /**
     * Retrieve the list of all Subscriptions present in the system
     * @param limit an optional limit (0 for none)
     * @param offset an optional offset (0 for none)
     * @return a paginated of list of Subscriptions
     * @throws Exception
     */
    protected Paginated<Subscription> listSubscriptions( int limit, int offset) throws Exception {
        throw new UnsupportedOperationException("List Subscriptions");
    }

    /**
     * Create a new subscription
     * @param subscription the subscription to create
     */
    protected void createSubscription(Subscription subscription){
        throw new UnsupportedOperationException("Create Subscription");
    }

    /**
     * Retrieve a subscription by the subscription ID
     * @param subscriptionId the registration ID
     * @return the registration
     */
    protected Subscription retrieveSubscription(String subscriptionId) {
        throw new UnsupportedOperationException("Retrieve Subscription");
    }

    /**
     * Update some fields to a subscription
     * @param subscriptionId the subscription ID
     * @param subscription the some fields of the subscription to update
     */
    protected void updateSubscription(String subscriptionId, Subscription subscription){
        throw new UnsupportedOperationException("Update Subscription");
    }

    /**
     * Delete a subscription
     * @param subscriptionId the subscription ID
     */
    protected void removeSubscription(String subscriptionId){
        throw new UnsupportedOperationException("Remove Subscription");
    }

    /**
     * Update, append or delete multiple entities in a single operation
     * @param bulkUpdateRequest a BulkUpdateRequest with an actionType and a list of entities to update
     */
    protected void bulkUpdate(BulkUpdateRequest bulkUpdateRequest){
        throw new UnsupportedOperationException("Update");
    }

    /**
     * Query multiple entities in a single operation
     * @param bulkQueryRequest an optional list of entity IDs (cannot be used with idPatterns)
     * @param limit an optional limit (0 for none)
     * @param offset an optional offset (0 for none)
     * @param orderBy an option list of attributes to define the order of entities (empty for none)
     * @param count is true if the count is required
     * @return a paginated of list of Entities
     */
    protected Paginated<Entity> bulkQuery(BulkQueryRequest bulkQueryRequest, int limit, int offset, List<String> orderBy, Boolean count){
        throw new UnsupportedOperationException("Query");
    }

    /**
     * Create, update or delete registrations to multiple entities in a single operation
     * @param bulkRegisterRequest defines the list of entities to register
     * @return a list of registration ids
     */
    protected List<String> bulkRegister(BulkRegisterRequest bulkRegisterRequest) {
        throw new UnsupportedOperationException("Register");
    }

    /**
     * Discover registration matching entities and their attributes
     * @param bulkQueryRequest defines the list of entities, attributes and scopes to match registrations
     * @param offset an optional offset (0 for none)
     * @param limit an optional limit (0 for none)
     * @param count is true if the count is required
     * @return a paginated list of registration
     */
    protected Paginated<Registration> bulkDiscover(BulkQueryRequest bulkQueryRequest, int limit, int offset, Boolean count) {
        throw new UnsupportedOperationException("Discover");
    }

    /*
     * Private Methods
     */

/*
    private void validateSyntax(String field) throws InvalidatedSyntaxException {
        if (( field.length() > 256) || (!fieldPattern.matcher(field).matches())) {
            throw new InvalidatedSyntaxException(field);
        }
    }

    private void validateSyntax(Collection<String> strings) {
        if (strings != null) {
            strings.forEach(this::validateSyntax);
        }
    }

    private void validateSyntax(Set<String> ids, Set<String> types, List<String> attrs) {
        validateSyntax(ids);
        validateSyntax(types);
        validateSyntax(attrs);
    }

    private void validateSyntax(String id, String type, List<String> attrs) {
        if (id != null) validateSyntax(id);
        if (type != null) validateSyntax(type);
        validateSyntax(attrs);
    }

    private void validateSyntax(String id, String type, String attributeName) {
        if (id != null) validateSyntax(id);
        if (type != null) validateSyntax(type);
        if (attributeName != null) validateSyntax(attributeName);
    }

    private void validateSyntax(Entity entity) {
        if (entity.getId() != null) {
            validateSyntax(entity.getId());
        }
        if (entity.getType() != null ) {
            validateSyntax(entity.getType());
        }
        if (entity.getAttributes() != null) {
            validateSyntax(entity.getAttributes());
        }

    private void validateSyntax(Attribute attribute) {
        //check attribute type
        if (attribute.getType() != null) {
            attribute.getType().ifPresent(this::validateSyntax);
        }
        Map<String, Metadata> metadatas = attribute.getMetadata();
        if (metadatas != null) {
            //check metadata name
            metadatas.keySet().forEach(this::validateSyntax);
            //check metadata type
            metadatas.values().forEach(metadata -> {
                if (metadata.getType() != null) {
                    validateSyntax(metadata.getType());
                }
            });
        }
    }

    private void validateSyntax(Map<String, Attribute> attributes) {
        //check attribute name
        attributes.keySet().forEach(this::validateSyntax);
        attributes.values().forEach(this::validateSyntax);
    }

    private void validateSyntax(String entityId, String type, Map<String, Attribute> attributes) {
        if (entityId != null) {
            validateSyntax(entityId);
        }
        if (type != null) {
            validateSyntax(type);
        }
        if (attributes != null) {
            validateSyntax(attributes);
        }
    }

    private void validateSyntax(List<SubjectEntity> subjectEntities) {
        subjectEntities.forEach(subjectEntity -> {
            if (subjectEntity.getId() != null) {
                subjectEntity.getId().ifPresent(this::validateSyntax);
            }
            if (subjectEntity.getType()!= null) {
                subjectEntity.getType().ifPresent(this::validateSyntax);
            }
        });
    }

    private void validateSyntax(Registration registration) {
        if (registration.getSubject() != null) {
            if (registration.getSubject().getEntities() != null) {
                validateSyntax(registration.getSubject().getEntities());
            }
            if (registration.getSubject().getAttributes() != null) {
                registration.getSubject().getAttributes().forEach(this::validateSyntax);
            }
        }
        Map<String, Metadata> metadatas = registration.getMetadata();
        if (metadatas != null) {
            //check metadata name
            metadatas.keySet().forEach(this::validateSyntax);
            //check metadata type
            metadatas.values().forEach(metadata -> {
                if (metadata.getType() != null) {
                    validateSyntax(metadata.getType());
                }
            });
        }
    }

    private void validateSyntax(Subscription subscription) {
        if (subscription.getSubject() != null) {
            if (subscription.getSubject().getEntities() != null) {
                validateSyntax(subscription.getSubject().getEntities());
            }
            if ((subscription.getSubject().getCondition() != null) && (subscription.getSubject().getCondition().getAttributes() != null)) {
                subscription.getSubject().getCondition().getAttributes().forEach(this::validateSyntax);
            }
        }
        if ((subscription.getNotification() != null) && (subscription.getNotification().getAttributes() != null)) {
            subscription.getNotification().getAttributes().forEach(this::validateSyntax);
        }
    }

    private void validateSyntax(BulkQueryRequest bulkQueryRequest) {
        validateSyntax(bulkQueryRequest.getEntities());
        validateSyntax(bulkQueryRequest.getAttributes());
        bulkQueryRequest.getScopes().forEach(scope -> {
            if (scope.getType() != null) {
                validateSyntax(scope.getType());
            }
        });
    }

    private HttpHeaders locationHeader(String entityId) {
        HttpHeaders headers = new HttpHeaders();
        headers.put("Location", Collections.singletonList("/v2/entities/" + entityId));
        return headers;
    }

    private HttpHeaders xTotalCountHeader(int countNumber) {
        HttpHeaders headers = new HttpHeaders();
        headers.put("X-Total-Count", Collections.singletonList(Integer.toString(countNumber)));
        return headers;
    }*/

    private Entity bodyToEntity(String body) {
        if(body == null || body.equals(""))
            return null;
        Entity entity;
        Attribute attribute = null;

        String newBody = body.replace("\n","");
        newBody = newBody.replace("\t","");
        newBody = newBody.replace(" ", "");
        newBody = newBody.replace("{","");
        newBody = newBody.replace("}","");
        newBody = newBody.replace("\"","");
        newBody = newBody.replace(":"," ");
        newBody = newBody.replaceAll("metadata","");

        String subCadenas[] = newBody.split(",");
        String id[] = subCadenas[0].split(" ");
        String type[] = subCadenas[1].split(" ");

        entity = new Entity(id[1],type[1]);
        String attributeId = "";
        for(int i = 2; i< subCadenas.length; i++) {
            if(subCadenas.equals(" "))
                continue;
            String subSubCadenas[] = subCadenas[i].split(" ");
            if(subSubCadenas.length == 3) {
                attribute = new Attribute();
                attributeId = subSubCadenas[0];
                attribute.setType(Optional.of(subSubCadenas[2]));
            }
            else if(subSubCadenas.length == 2) {
                attribute.setValue(subSubCadenas[1]);
                entity.setAttributes(attributeId,attribute);
            }
        }
        return entity;
    }
}
