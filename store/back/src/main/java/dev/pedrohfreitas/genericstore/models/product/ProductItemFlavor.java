package dev.pedrohfreitas.genericstore.models.product;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import dev.pedrohfreitas.genericstore.configs.TenantLocalStorage;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
@Entity
@Table(name = "p_item_flavor")
public class ProductItemFlavor{
	
	public ProductItemFlavor() {
		this.tenantID = TenantLocalStorage.getTenantID();
	}
	public void setTenantID(String tenantID) {		
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private UUID id;
	
	@Builder.Default
	@Column(name = "created", nullable = false, updatable = false)
	private Timestamp created = Timestamp.from(Instant.now());
	
	@Column(name = "created_by", nullable = false, updatable = false)
	private String createdBy;
	
	@Builder.Default
	@Column(name = "updated", nullable = false, updatable = false)
	private Timestamp updated = Timestamp.from(Instant.now());
	
	@Column(name = "updated_By", nullable = false, updatable = false)
	private String updatedBy;
	
	@Column(name = "tenant_id", nullable = false, updatable = false)
	private String tenantID;
	
	@Builder.Default
	@Column(name = "actived", nullable = false)
	private Boolean actived = true;

	@Column(name = "flavor",length = 100, nullable = false)
	private String flavor;
	
	@Builder.Default
	@ManyToMany(fetch = FetchType.LAZY,mappedBy = "flavors")
	private Set<ProductItem> productItens = new HashSet();
	
	@Builder.Default
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "p_flavor_ingredient", joinColumns = @JoinColumn(name = "flavor_id"), inverseJoinColumns = @JoinColumn(name = "ingredient_id"))
	private Set<ProductIngredient> ingredients = new HashSet();
	
	@Builder.Default
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "p_flavor_added", joinColumns = @JoinColumn(name = "id_flavor"), inverseJoinColumns = @JoinColumn(name = "id_addeds"))
	private Set<ProductAdded> addeds = new HashSet();
	
	@Builder.Default
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "p_flavor_size", joinColumns = @JoinColumn(name = "id_flavor"), inverseJoinColumns = @JoinColumn(name = "id_size"))
	private Set<ProductSize> sizes = new HashSet();
	
	@Builder.Default
	@OneToMany(mappedBy = "flavor" ,fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<ProductItemFlavorSizePrice> prices = new HashSet();
	
}
