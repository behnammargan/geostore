/*
 * ====================================================================
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
package it.geosolutions.geostore.services;

import it.geosolutions.geostore.core.model.LayerMetadata;
import it.geosolutions.geostore.services.exception.BadRequestServiceEx;
import it.geosolutions.geostore.services.exception.NotFoundServiceEx;

import java.util.List;

/**
 * Interafce LayerMetadata.
 *
 * @author Mohsen Karimi
 *
 */
public interface LayerMetadataService extends SecurityService {

    /**
     * @param layerMetadata
     * @return long
     * @throws BadRequestServiceEx
     * @throws NotFoundServiceEx
     */
    long insert(LayerMetadata layerMetadata) throws BadRequestServiceEx, NotFoundServiceEx;

    /**
     * @param layerMetadata
     * @return long
     * @throws NotFoundServiceEx
     * @throws BadRequestServiceEx
     */
    long update(LayerMetadata layerMetadata) throws BadRequestServiceEx;

    /**
     * @param id
     * @return
     */
    boolean delete(long id);

    /**
     * @param id
     * @return LayerMetadata
     */
    LayerMetadata get(long id);

    /**
     * @return the layerMetadata with the exact key requested, or null if none was found
     * @throws BadRequestServiceEx is a null name was given
     */
    LayerMetadata get(String key) throws BadRequestServiceEx;

    /**
     * @param page
     * @param entries
     * @return List<LayerMetadata>
     * @throws BadRequestServiceEx
     */
    List<LayerMetadata> getAll(Integer page, Integer entries) throws BadRequestServiceEx;

    /**
     * @param key
     * @return long
     */
    long getCount(String key);

}
