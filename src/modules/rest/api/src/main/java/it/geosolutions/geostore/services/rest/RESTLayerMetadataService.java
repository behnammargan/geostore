/* ====================================================================
 *
 * Copyright (C) 2007 - 2012 GeoSolutions S.A.S.
 * http://www.geo-solutions.it
 *
 * GPLv3 + Classpath exception
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.
 *
 * ====================================================================
 *
 * This software consists of voluntary contributions made by developers
 * of GeoSolutions.  For more information on GeoSolutions, please see
 * <http://www.geo-solutions.it/>.
 *
 */
package it.geosolutions.geostore.services.rest;

import it.geosolutions.geostore.core.model.LayerMetadata;
import it.geosolutions.geostore.services.exception.BadRequestServiceEx;
import it.geosolutions.geostore.services.exception.NotFoundServiceEx;
import it.geosolutions.geostore.services.rest.exception.BadRequestWebEx;
import it.geosolutions.geostore.services.rest.exception.NotFoundWebEx;
import it.geosolutions.geostore.services.rest.model.LayerMetadataList;
import org.apache.cxf.jaxrs.ext.multipart.Multipart;
import org.springframework.security.access.annotation.Secured;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;

/**
 * Interface RESTLayerMetadataService.
 *
 * @author Tobia di Pisa (tobia.dipisa at geo-solutions.it)
 * @author ETj (etj at geo-solutions.it)
 */
public interface RESTLayerMetadataService {

    /**
     * @param layerMetadata
     * @return long
     * @throws NotFoundServiceEx
     * @throws BadRequestServiceEx
     */
    @POST
    @Path("/")
    @Consumes({ MediaType.APPLICATION_XML, MediaType.TEXT_XML })
    @Produces({ MediaType.TEXT_PLAIN, MediaType.TEXT_XML, MediaType.APPLICATION_JSON })
    @Secured({ "ROLE_ADMIN" })
    long insert(@Context SecurityContext sc, @Multipart("layerMetadata") LayerMetadata layerMetadata)
            throws BadRequestServiceEx, NotFoundServiceEx;

    /**
     * @param id
     * @param layerMetadata
     * @return long
     * @throws NotFoundWebEx
     */
    @PUT
    @Path("/{id}")
    @Consumes({ MediaType.APPLICATION_XML, MediaType.TEXT_XML })
    @Secured({ "ROLE_ADMIN" })
    long update(@Context SecurityContext sc, @PathParam("id") long id,
                @Multipart("layerMetadata") LayerMetadata layerMetadata) throws NotFoundWebEx;

    /**
     * @param id
     * @throws NotFoundWebEx
     */
    @DELETE
    @Path("/{id}")
    // @RolesAllowed({ "ADMIN" })
    @Secured({ "ROLE_ADMIN" })
    void delete(@Context SecurityContext sc, @PathParam("id") long id) throws NotFoundWebEx;

    /**
     * @param id
     * @return LayerMetadata
     * @throws NotFoundWebEx
     */
    @GET
    @Path("/{id}")
    @Produces({ MediaType.TEXT_PLAIN, MediaType.TEXT_XML, MediaType.APPLICATION_JSON })
    // @RolesAllowed({ "ADMIN", "USER", "GUEST" })
    @Secured({ "ROLE_USER", "ROLE_ADMIN", "ROLE_ANONYMOUS" })
    LayerMetadata get(@Context SecurityContext sc, @PathParam("id") long id) throws NotFoundWebEx;

    /**
     * @param page
     * @param entries
     * @return LayerMetadata
     * @throws BadRequestWebEx
     */
    @GET
    @Path("/")
    @Produces({ MediaType.TEXT_XML, MediaType.APPLICATION_JSON })
    // @RolesAllowed({ "ADMIN", "USER", "GUEST" })
    @Secured({ "ROLE_USER", "ROLE_ADMIN", "ROLE_ANONYMOUS" })
    LayerMetadataList getAll(@Context SecurityContext sc, @QueryParam("page") Integer page,
                             @QueryParam("entries") Integer entries) throws BadRequestWebEx;

    /**
     * @param key
     * @return long
     */
    @GET
    @Path("/count/{key}")
    // @RolesAllowed({ "ADMIN", "USER", "GUEST" })
    @Secured({ "ROLE_USER", "ROLE_ADMIN", "ROLE_ANONYMOUS" })
    long getCount(@Context SecurityContext sc, @PathParam("key") String key);

    /**
     * @param key
     * @return LayerMetadata
     */
    @GET
    @Path("/getByKey/{key}")
    // @RolesAllowed({ "ADMIN", "USER", "GUEST" })
    @Secured({ "ROLE_USER", "ROLE_ADMIN", "ROLE_ANONYMOUS" })
    LayerMetadata getByKey(@Context SecurityContext sc, @PathParam("key") String key);

}
