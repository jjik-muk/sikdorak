package com.jjikmuk.sikdorak.store.domain;

import com.jjikmuk.sikdorak.common.domain.BaseTimeEntity;
import com.jjikmuk.sikdorak.common.domain.Deleted;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@NoArgsConstructor // for @Entity
public class Store extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "store_id")
    private Long id;

    @Embedded
    private StoreName storeName;

    @Embedded
    private ContactNumber contactNumber;

    @Embedded
    private Address address; // baseAddress, detailAddress

    @Embedded
    private StoreLocation location; // Float latitude, Float longitude

    @Embedded
    private Deleted deleted = Deleted.FALSE;

    public Store(String storeName, String contactNumber, String baseAddress, String detailAddress, Double latitude, Double longitude) {
        this.storeName = new StoreName(storeName);
        this.contactNumber = new ContactNumber(contactNumber);
        this.address = new Address(baseAddress, detailAddress);
        this.location = new StoreLocation(latitude, longitude);
    }

    public Long getId() {
        return id;
    }

    public String getStoreName() {
        return storeName.getStoreName();
    }

    public String getContactNumber() {
        return contactNumber.getContactNumber();
    }

    public String getBaseAddress() {
        return address.getBaseAddress();
    }

    public String getDetailAddress() {
        return address.getDetailAddress();
    }

    public double getLatitude() {
        return location.latitude();
    }

    public double getLongitude() {
        return location.longitude();
    }

    public boolean isDeleted() {
        return isDeleted();
    }

    public void editAll(String storeName, String contactNumber, String baseAddress, String detailAddress, Double latitude, Double longitude) {
        this.storeName = new StoreName(storeName);
        this.contactNumber = new ContactNumber(contactNumber);
        this.address = new Address(baseAddress, detailAddress);
        this.location = new StoreLocation(latitude, longitude);
    }

    public void delete() {
        deleted.delete();
    }
}
