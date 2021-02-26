/*
 *  Copyright (C) 2007 - 2012 GeoSolutions S.A.S.
 *  http://www.geo-solutions.it
 *
 *  GPLv3 + Classpath exception
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package it.geosolutions.geostore.services;

import com.googlecode.genericdao.search.Search;
import it.geosolutions.geostore.core.dao.LayerMetadataDAO;
import it.geosolutions.geostore.core.dao.SecurityDAO;
import it.geosolutions.geostore.core.model.LayerMetadata;
import it.geosolutions.geostore.core.model.SecurityRule;
import it.geosolutions.geostore.services.exception.BadRequestServiceEx;
import it.geosolutions.geostore.services.exception.NotFoundServiceEx;
import org.apache.log4j.Logger;

import java.util.List;

/**
 * Class LayerMetadataServiceImpl.
 *
 * @author Mohsen Karimi
 */
public class LayerMetadataServiceImpl implements LayerMetadataService {

    private static final Logger LOGGER = Logger.getLogger(LayerMetadataServiceImpl.class);

    private LayerMetadataDAO layerMetadataDAO;
    private SecurityDAO securityDAO;

    public void setLayerMetadataDAO(LayerMetadataDAO layerMetadataDAO) {
        this.layerMetadataDAO = layerMetadataDAO;
    }

    public void setSecurityDAO(SecurityDAO securityDAO) {
        this.securityDAO = securityDAO;
    }

    /*
     * (non-Javadoc)
     *
     * @see it.geosolutions.geostore.services.LayerMetadataService#insert(it.geosolutions.geostore.core.model.LayerMetadata)
     */
    @Override
    public long insert(LayerMetadata layerMetadata) throws BadRequestServiceEx, NotFoundServiceEx {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Persisting LayerMetadata ... ");
        }

        if (layerMetadata == null) {
            throw new BadRequestServiceEx("LayerMetadata type must be specified !");
        }

        LayerMetadata lm = new LayerMetadata();
        lm.setKey(layerMetadata.getKey());
        lm.setValue(layerMetadata.getValue());
        lm.setLayerId(layerMetadata.getLayerId());

        layerMetadataDAO.persist(lm);

        // //
        // // Persisting SecurityRule
        // //
        // List<SecurityRule> rules = layerMetadata.getSecurity();
        //
        // if (rules != null) {
        // for (SecurityRule rule : rules) {
        // rule.setCategory(cat);
        // securityDAO.persist(rule);
        // }
        // }

        return lm.getId();
    }

    /*
     * (non-Javadoc)
     *
     * @see it.geosolutions.geostore.services.LayerMetadataService#update(it.geosolutions.geostore.core.model.LayerMetadata)
     */
    @Override
    public long update(LayerMetadata layerMetadata) throws BadRequestServiceEx {
        LayerMetadata lm = layerMetadataDAO.find(layerMetadata.getId());
        layerMetadataDAO.save(lm);
        return lm.getId();
    }

    /*
     * (non-Javadoc)
     *
     * @see it.geosolutions.geostore.services.LayerMetadataService#get(long)
     */
    @Override
    public LayerMetadata get(long id) {
        return layerMetadataDAO.find(id);
    }

    /*
     * (non-Javadoc)
     *
     * @see it.geosolutions.geostore.services.LayerMetadataService#get(long)
     */
    @Override
    public LayerMetadata get(String key) throws BadRequestServiceEx {
        if (key == null) {
            throw new BadRequestServiceEx("LayerMetadata key must be specified !");
        }

        Search searchCriteria = new Search(LayerMetadata.class);
        searchCriteria.addFilterEqual("key", key);
        List<LayerMetadata> layerMetadata = layerMetadataDAO.search(searchCriteria);
        if (layerMetadata.size() > 1) {
            LOGGER.warn("Found " + layerMetadata.size() + " LayerMetadata list with key '" + key + "'");
        }

        return layerMetadata.isEmpty() ? null : layerMetadata.get(0);
    }

    /*
     * (non-Javadoc)
     *
     * @see it.geosolutions.geostore.services.LayerMetadataService#delete(long)
     */
    @Override
    public boolean delete(long id) {
        return layerMetadataDAO.removeById(id);
    }

    /*
     * (non-Javadoc)
     *
     * @see it.geosolutions.geostore.services.LayerMetadataService#getAll(java.lang.Integer, java.lang.Integer)
     */
    @Override
    public List<LayerMetadata> getAll(Integer page, Integer entries) throws BadRequestServiceEx {

        if (((page != null) && (entries == null)) || ((page == null) && (entries != null))) {
            throw new BadRequestServiceEx("Page and entries params should be declared together.");
        }

        Search searchCriteria = new Search(LayerMetadata.class);

        if (page != null) {
            searchCriteria.setMaxResults(entries);
            searchCriteria.setPage(page);
        }

        searchCriteria.addSortAsc("key");

        return layerMetadataDAO.search(searchCriteria);
    }

    /*
     * (non-Javadoc)
     *
     * @see it.geosolutions.geostore.services.LayerMetadataService#getCount(java.lang.String)
     */
    @Override
    public long getCount(String key) {
        Search searchCriteria = new Search(LayerMetadata.class);

        if (key != null) {
            searchCriteria.addFilterILike("key", key);
        }

        return layerMetadataDAO.count(searchCriteria);
    }


    /*
     * (non-Javadoc)
     *
     * @see it.geosolutions.geostore.services.LayerMetadataService#getUserSecurityRule(java.lang.String, long)
     */
    @Override
    public List<SecurityRule> getUserSecurityRule(String userName, long categoryId) {
        return layerMetadataDAO.findUserSecurityRule(userName, categoryId);
    }

    /* (non-Javadoc)
     * @see it.geosolutions.geostore.services.SecurityService#getGroupSecurityRule(java.lang.String, long)
     */
    @Override
    public List<SecurityRule> getGroupSecurityRule(List<String> userName, long categoryId) {
        return layerMetadataDAO.findGroupSecurityRule(userName, categoryId);
    }
}
