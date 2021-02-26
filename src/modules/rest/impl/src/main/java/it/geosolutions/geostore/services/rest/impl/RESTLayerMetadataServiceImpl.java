/*
 * $ Header: it.geosolutions.georepo.services..rest.impl.RESTLayerMetadataServiceImpl ,v. 0.1 9-set-2011 10.39.58 created by tobaro <tobia.dipisa at geo-solutions.it> $
 * $ Revision: 0.1 $
 * $ Date: 8-set-2011 10.39.58 $
 *
 * ====================================================================
 *
 * Copyright (C) 2007 - 2011 GeoSolutions S.A.S.
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
package it.geosolutions.geostore.services.rest.impl;

import it.geosolutions.geostore.core.model.LayerMetadata;
import it.geosolutions.geostore.core.model.User;
import it.geosolutions.geostore.services.LayerMetadataService;
import it.geosolutions.geostore.services.SecurityService;
import it.geosolutions.geostore.services.exception.BadRequestServiceEx;
import it.geosolutions.geostore.services.exception.NotFoundServiceEx;
import it.geosolutions.geostore.services.rest.RESTLayerMetadataService;
import it.geosolutions.geostore.services.rest.exception.BadRequestWebEx;
import it.geosolutions.geostore.services.rest.exception.ForbiddenErrorWebEx;
import it.geosolutions.geostore.services.rest.exception.NotFoundWebEx;
import it.geosolutions.geostore.services.rest.model.LayerMetadataList;
import org.apache.log4j.Logger;

import javax.ws.rs.core.SecurityContext;

/**
 * Class RESTLayerMetadataServiceImpl.
 *
 * @author Mohsen Karimi
 *
 */
public class RESTLayerMetadataServiceImpl extends RESTServiceImpl implements RESTLayerMetadataService {

    private final static Logger LOGGER = Logger.getLogger(RESTLayerMetadataServiceImpl.class);

    private LayerMetadataService layerMetadataService;

    /* (non-Javadoc)
     * @see it.geosolutions.geostore.services.rest.impl.RESTServiceImpl#getSecurityService()
     */
    @Override
    protected SecurityService getSecurityService() {
        return layerMetadataService;
    }

    /**
     * @param layerMetadataService the layerMetadataService to set
     */
    public void setLayerMetadataService(LayerMetadataService layerMetadataService) {
        this.layerMetadataService = layerMetadataService;
    }

    /*
     * (non-Javadoc)
     *
     * @see it.geosolutions.geostore.services.rest.RESTLayerMetadataService#insert(it.geosolutions.geostore.core.model.LayerMetadata)
     */
    @Override
    public long insert(SecurityContext sc, LayerMetadata layerMetadata) {
        if (layerMetadata == null)
            throw new BadRequestWebEx("LayerMetadata is null");
        if (layerMetadata.getId() != null)
            throw new BadRequestWebEx("Id should be null");

        long id = -1;

        //
        // Preparing Security Rule
        //
        // User authUser = extractAuthUser(sc);

        // SecurityRule securityRule = new SecurityRule();
        // securityRule.setCanRead(true);
        // securityRule.setCanWrite(true);
        // securityRule.setUser(authUser);
        //
        // List<SecurityRule> securities = new ArrayList<SecurityRule>();
        // securities.add(securityRule);
        //
        // layerMetadata.setSecurity(securities);

        try {
            id = layerMetadataService.insert(layerMetadata);
        } catch (NotFoundServiceEx e) {
            throw new NotFoundWebEx(e.getMessage());
        } catch (BadRequestServiceEx e) {
            throw new BadRequestWebEx(e.getMessage());
        }

        return id;
    }

    @Override
    public long update(SecurityContext sc, long id, LayerMetadata layerMetadata) {
        try {
            LayerMetadata old = layerMetadataService.get(id);
            if (old == null)
                throw new NotFoundWebEx("LayerMetadata not found");

            //
            // Authorization check.
            //
            boolean canUpdate = false;
            User authUser = extractAuthUser(sc);
            canUpdate = resourceAccessWrite(authUser, old.getId());

            if (canUpdate) {
                id = layerMetadataService.update(layerMetadata);
            } else {
                throw new ForbiddenErrorWebEx("This user cannot update this LayerMetadata !");
            }

        } catch (BadRequestServiceEx e) {
            throw new BadRequestWebEx(e.getMessage());
        }

        return id;
    }

    /*
     * (non-Javadoc)
     *
     * @see it.geosolutions.geostore.services.rest.RESTLayerMetadataService#delete(long)
     */
    @Override
    public void delete(SecurityContext sc, long id) throws NotFoundWebEx {
        //
        // Authorization check.
        //
        boolean canDelete = false;
        User authUser = extractAuthUser(sc);
        canDelete = resourceAccessWrite(authUser, id);

        if (canDelete) {
            boolean ret = layerMetadataService.delete(id);
            if (!ret)
                throw new NotFoundWebEx("LayerMetadata not found");
        } else
            throw new ForbiddenErrorWebEx("This user cannot delete this layerMetadata !");

    }

    /*
     * (non-Javadoc)
     *
     * @see it.geosolutions.geostore.services.rest.RESTLayerMetadataService#get(long)
     */
    @Override
    public LayerMetadata get(SecurityContext sc, long id) throws NotFoundWebEx {
        if (id == -1) {
            if (LOGGER.isDebugEnabled())
                LOGGER.debug("Retriving dummy data !");

            //
            // return test instance
            //
            LayerMetadata layerMetadata = new LayerMetadata();
            layerMetadata.setKey("dummy name");
            return layerMetadata;
        }

        LayerMetadata ret = layerMetadataService.get(id);
        if (ret == null)
            throw new NotFoundWebEx("LayerMetadata not found");

        return ret;
    }

    /*
     * (non-Javadoc)
     *
     * @see it.geosolutions.geostore.services.rest.RESTLayerMetadataService#getAll(java.lang.Integer, java.lang.Integer)
     */
    @Override
    public LayerMetadataList getAll(SecurityContext sc, Integer page, Integer entries)
            throws BadRequestWebEx {
        try {
            return new LayerMetadataList(layerMetadataService.getAll(page, entries));
        } catch (BadRequestServiceEx ex) {
            throw new BadRequestWebEx(ex.getMessage());
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see it.geosolutions.geostore.services.rest.RESTLayerMetadataService#getCount(java.lang.String)
     */
    @Override
    public long getCount(SecurityContext sc, String nameLike) {
        nameLike = nameLike.replaceAll("[*]", "%");
        return layerMetadataService.getCount(nameLike);
    }

    @Override
    public LayerMetadata getByKey(SecurityContext sc, String key) {
        try {
            return layerMetadataService.get(key);
        } catch (BadRequestServiceEx ex) {
            throw new BadRequestWebEx(ex.getMessage());
        }
    }
}
