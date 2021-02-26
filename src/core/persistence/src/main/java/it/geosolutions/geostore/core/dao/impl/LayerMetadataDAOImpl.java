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

package it.geosolutions.geostore.core.dao.impl;

import com.googlecode.genericdao.search.Filter;
import com.googlecode.genericdao.search.ISearch;
import com.googlecode.genericdao.search.Search;
import it.geosolutions.geostore.core.dao.LayerMetadataDAO;
import it.geosolutions.geostore.core.model.Category;
import it.geosolutions.geostore.core.model.LayerMetadata;
import it.geosolutions.geostore.core.model.SecurityRule;
import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Class AttributeDAOImpl.
 *
 * @author Mohse Karimi
 */
@Transactional(value = "geostoreTransactionManager")
public class LayerMetadataDAOImpl extends BaseDAO<LayerMetadata, Long> implements LayerMetadataDAO {

    private static final Logger LOGGER = Logger.getLogger(LayerMetadataDAOImpl.class);

    /*
     * (non-Javadoc)
     *
     * @see com.trg.dao.jpa.GenericDAOImpl#persist(T[])
     */
    @Override
    public void persist(LayerMetadata... entities) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.info("Inserting new entities for Attribute ... ");
        }

        super.persist(entities);
    }

    /*
     * (non-Javadoc)
     *
     * @see com.trg.dao.jpa.GenericDAOImpl#findAll()
     */
    @Override
    public List<LayerMetadata> findAll() {
        return super.findAll();
    }

    /*
     * (non-Javadoc)
     *
     * @see com.trg.dao.jpa.GenericDAOImpl#search(com.trg.search.ISearch)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<LayerMetadata> search(ISearch search) {
        return super.search(search);
    }

    /*
     * (non-Javadoc)
     *
     * @see com.trg.dao.jpa.GenericDAOImpl#merge(java.lang.Object)
     */
    @Override
    public LayerMetadata merge(LayerMetadata entity) {
        return super.merge(entity);
    }

    /*
     * (non-Javadoc)
     *
     * @see com.trg.dao.jpa.GenericDAOImpl#remove(java.lang.Object)
     */
    @Override
    public boolean remove(LayerMetadata entity) {
        return super.remove(entity);
    }

    /*
     * (non-Javadoc)
     *
     * @see com.trg.dao.jpa.GenericDAOImpl#removeById(java.io.Serializable)
     */
    @Override
    public boolean removeById(Long id) {
        return super.removeById(id);
    }

    /*
     * (non-Javadoc)
     *
     * @see it.geosolutions.geostore.core.dao.LayerMetadataDAO#findUserSecurityRule(java.lang.String, long)
     */
    @Override
    public List<SecurityRule> findUserSecurityRule(String userName, long layerMetadataId) {
        Search searchCriteria = new Search(Category.class);
        searchCriteria.addField("security");

        Filter securityFilter = Filter.some(
                "security",
                Filter.and(Filter.equal("layerMetadata.id", layerMetadataId),
                           Filter.equal("user.name", userName)));
        searchCriteria.addFilter(securityFilter);

        return super.search(searchCriteria);
    }

    /* (non-Javadoc)
     * @see it.geosolutions.geostore.core.dao.LayerMetadataDAO#findGroupSecurityRule(java.lang.String, long)
     */
    @Override
    public List<SecurityRule> findGroupSecurityRule(List<String> userGroups, long layerMetadataId) {
        Search searchCriteria = new Search(Category.class);
        searchCriteria.addField("security");

        Filter securityFilter = Filter.some(
                "security",
                Filter.and(Filter.equal("layerMetadata.id", layerMetadataId),
                           Filter.equal("user.groups.groupName", userGroups)));
        searchCriteria.addFilter(securityFilter);

        return super.search(searchCriteria);
    }

}
