package it.geosolutions.geostore.core.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Objects;

/**
 * @author Mohsen Karimi
 */
@Entity(name = "layer_metadata")
@Table(name = "layer_metadata")
@XmlRootElement(name = "LayerMetadata")
public class LayerMetadata {
    @Id
    private Long id;
    private String key;
    private String value;
    private String layerId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getLayerId() {
        return layerId;
    }

    public void setLayerId(String layerId) {
        this.layerId = layerId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LayerMetadata that = (LayerMetadata) o;
        return Objects.equals(id, that.id) &&
               Objects.equals(key, that.key) &&
               Objects.equals(value, that.value) &&
               Objects.equals(layerId, that.layerId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, key, value, layerId);
    }
}
