package ptithcm.entity;

import java.text.DecimalFormat;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="OrderDetails")
public class OrderDetail {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idOrderDetail;
	@NotNull(message="Không được để trống số lượng")
	@DecimalMin(value="1",message="số lượng không hợp lệ")
	private Integer number;
	@NotNull(message="Không được để trống giá")
	@DecimalMin(value="1",message="giá không hợp lệ")
	private Float price;
	@ManyToOne
	@JoinColumn(name ="idOrder")
	private TheOrder theOrder;
	@ManyToOne
	@JoinColumn(name ="idSeed")
	private Seed seed;
	public Integer getIdOrderDatail() {
		return idOrderDetail;
	}
	public void setIdOrderDatail(Integer idOrderDatail) {
		this.idOrderDetail = idOrderDatail;
	}
	public Integer getNumber() {
		return number;
	}
	public void setNumber(Integer number) {
		this.number = number;
	}
	public Float getPrice() {
		return price;
	}
	public void setPrice(Float price) {
		this.price = price;
	}
	public TheOrder getTheOrder() {
		return theOrder;
	}
	public void setTheOrder(TheOrder theOrder) {
		this.theOrder = theOrder;
	}
	public Seed getSeed() {
		return seed;
	}
	public void setSeed(Seed seed) {
		this.seed = seed;
	}
	public String getGiaVN() {
		// TODO Auto-generated method stub
		DecimalFormat formatter = new DecimalFormat("###,###,###");

		return formatter.format(price)+" VNĐ";

	}
	public String getTongGiaVN() {
		// TODO Auto-generated method stub
		DecimalFormat formatter = new DecimalFormat("###,###,###");

		return formatter.format(price*number)+" VNĐ";

	}
	
}
