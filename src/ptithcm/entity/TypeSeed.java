package ptithcm.entity;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Table(name="TypeSeeds")
public class TypeSeed {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idTypeSeed;
	@NotBlank(message="Không được để trống Tên Loại")
	private String typeSeedName;
	@OneToMany(mappedBy="typeSeed",fetch=FetchType.EAGER)
	private Collection<Seed> seeds;
	public Integer getIdTypeSeed() {
		return idTypeSeed;
	}
	public void setIdTypeSeed(Integer idTypeSeed) {
		this.idTypeSeed = idTypeSeed;
	}
	public String getTypeSeedName() {
		return typeSeedName;
	}
	public void setTypeSeedName(String typeSeedName) {
		this.typeSeedName = typeSeedName;
	}
	public Collection<Seed> getSeeds() {
		return seeds;
	}
	public void setSeeds(Collection<Seed> seeds) {
		this.seeds = seeds;
	}
	
}
