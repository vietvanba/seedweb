package ptithcm.entity;

import java.text.DecimalFormat;
import java.util.Collection;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name="TheOrders")
public class TheOrder {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idOrder;
	@ManyToOne
	@JoinColumn(name="idUser")
	private User user;
//	private Integer idUser;
	@NotBlank(message="Không được để trống FirstName")
	private String firstName;
	@NotBlank(message="Không được để trống LastName")
	private String lastName;
	@NotBlank(message="Không được để trống Address")
	private String address;
	@NotBlank(message="Không được để trống Phone")
	private String phone;
	private Float price;
	private String statusOrder;
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern="dd/MM/yyyy")
	private Date orderTime;
	@OneToMany(mappedBy="theOrder",fetch=FetchType.EAGER)
	private Collection<OrderDetail> orderDetails;
	public Integer getIdOrder() {
		return idOrder;
	}
	public void setIdOrder(Integer idOrder) {
		this.idOrder = idOrder;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
	public String getFirstName() {
		return firstName;
	}
//	public Integer getIdUser() {
//		return idUser;
//	}
//	public void setIdUser(Integer idUser) {
//		this.idUser = idUser;
//	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public Float getPrice() {
		return price;
	}
	public void setPrice(Float price) {
		this.price = price;
	}
	public Date getOrderTime() {
		return orderTime;
	}
	public void setOrderTime(Date oderTime) {
		this.orderTime = oderTime;
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
	public String getStatusOrder() {
		return statusOrder;
	}
	public void setStatusOrder(String statusOrder) {
		this.statusOrder = statusOrder;
	}
	
}
