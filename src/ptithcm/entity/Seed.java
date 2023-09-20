package ptithcm.entity;

import java.text.DecimalFormat;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Table(name="Seeds")
public class Seed {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idSeed;

	private String seedName;
	@ManyToOne
	@JoinColumn(name="idTypeSeed")
	private TypeSeed typeSeed;
	
	private Float price;

	private String images;
	private String information;

	private Integer number;
	@OneToMany(mappedBy="seed",fetch=FetchType.EAGER)
	private Collection<OrderDetail> orderDetails;
	public Integer getIdSeed() {
		return idSeed;
	}
	public void setIdSeed(Integer idSeed) {
		this.idSeed = idSeed;
	}
	public String getSeedName() {
		return seedName;
	}
	public void setSeedName(String seedName) {
		this.seedName = seedName;
	}
	public TypeSeed getTypeSeed() {
		return typeSeed;
	}
	public void setTypeSeed(TypeSeed typeSeed) {
		this.typeSeed = typeSeed;
	}
	public Float getPrice() {
		return price;
	}
	public void setPrice(Float price) {
		this.price = price;
	}
	public String getImages() {
		return images;
	}
	public void setImages(String images) {
		this.images = images;
	}
	public String getInformation() {
		return information;
	}
	public void setInformation(String information) {
		this.information = information;
	}
	public Integer getNumber() {
		return number;
	}
	public void setNumber(Integer number) {
		this.number = number;
	}
	public Collection<OrderDetail> getOrderDetails() {
		return orderDetails;
	}
	public void setOrderDetails(Collection<OrderDetail> orderDetails) {
		this.orderDetails = orderDetails;
	}
	public String getGiaVN() {
		// TODO Auto-generated method stub
		DecimalFormat formatter = new DecimalFormat("###,###,###");

		return formatter.format(price)+" VNĐ";

	}
	
}
