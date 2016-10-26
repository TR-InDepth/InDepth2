package org.tr.indepth.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * A UserAttirbutes.
 */
@Entity
@Table(name = "user_attirbutes")
public class UserAttirbutes implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "lat", precision=10, scale=2)
    private BigDecimal lat;

    @Column(name = "longitude", precision=10, scale=2)
    private BigDecimal longitude;

    @Column(name = "active")
    private Integer active;

    @OneToOne(mappedBy = "")
    @JsonIgnore
    private User user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getLat() {
        return lat;
    }

    public void setLat(BigDecimal lat) {
        this.lat = lat;
    }

    public BigDecimal getLongitude() {
        return longitude;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }

    public Integer getActive() {
        return active;
    }

    public void setActive(Integer active) {
        this.active = active;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UserAttirbutes userAttirbutes = (UserAttirbutes) o;
        if(userAttirbutes.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, userAttirbutes.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "UserAttirbutes{" +
            "id=" + id +
            ", lat='" + lat + "'" +
            ", longitude='" + longitude + "'" +
            ", active='" + active + "'" +
            '}';
    }
}
